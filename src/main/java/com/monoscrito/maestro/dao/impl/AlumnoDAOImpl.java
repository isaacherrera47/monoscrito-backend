package com.monoscrito.maestro.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.monoscrito.alumno.beans.request.ObtenerMisTareasRequestBean;
import com.monoscrito.alumno.beans.request.TareaDetectiveOrtograficoRequestBean;
import com.monoscrito.alumno.beans.request.TareaEscuchoYEscriboRequestBean;
import com.monoscrito.alumno.beans.response.AlumnoLoginResponse;
import com.monoscrito.alumno.beans.response.MisTareasResponseBean;
import com.monoscrito.beans.response.OracionesBean;
import com.monoscrito.beans.response.ResponseResult;
import com.monoscrito.beans.response.Tareas;
import com.monoscrito.commons.beans.request.LoginBean;
import com.monoscrito.maestro.dao.AlumnoDAO;

@Repository
public class AlumnoDAOImpl implements AlumnoDAO{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AlumnoDAOImpl.class);
	
	private static final String QUERY_LOGIN = "SELECT id_alumno idAlumno, nombres, apellidos, pseudonimo, primer_login primerLogin, id_maestro idMaestro "
			+ "FROM public.alumnos WHERE pseudonimo = :usuario AND password = :password";
	
	private static final String ACTUALIZA_LOGIN = "UPDATE public.alumnos SET primer_login = FALSE WHERE id_alumno = :idAlumno";
	
	private static final String QUERY_TIENE_TAREA = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM public.asignaciones a "
			+ "WHERE id_alumno = :idAlumno AND (now() < a.fecha_entrega AND a.entregado = FALSE) OR (now() > a.fecha_entrega AND a.permite_extemporaneas = TRUE AND a.entregado = FALSE)";
	
	private static final String QUERY_OBTENER_MIS_TAREAS = "SELECT e.nombre nombre, e.tipo_ejercicio tipo, e.tiempo_asignado tiempoAsignado, e.nombre_lectura nombreLectura, e.instrucciones, "
			+ "a.id_asignacion idAsignacion, e.nombre_lectura nombreLectura, e.tiempo_asignado tiempoAsignado, e.id_ejercicio idEjercicio FROM public.asignaciones a, public.ejercicios e "
			+ "WHERE e.id_ejercicio = a.id_ejercicio AND a.id_alumno = :idAlumno AND ((now() < a.fecha_entrega AND a.entregado = FALSE) OR (now() > a.fecha_entrega AND a.permite_extemporaneas = TRUE)) "
			+ "AND a.entregado = FALSE";
	
	private static final String QUERY_GUARDA_TAREA_REALIZADA_ESCUCHO_Y_ESCRIBO = "INSERT INTO public.tareas_entregadas (id_alumno, id_asignacion, id_maestro, dictado, puntos_obtenidos) "
			+ "VALUES (:idAlumno, :idAsignacion, :idMaestro, :dictado, :puntosObtenidos) ";
	
	private static final String QUERY_ACTUALIZA_FECHA_ENTREGA_TAREA = "UPDATE public.asignaciones SET entregado = true, fecha_finalizacion = now() WHERE id_asignacion = :idAsignacion AND id_alumno = :idAlumno";
	
	private static final String QUERY_GET_LISTA_ORACIONES = "SELECT id_oracion idOracion, texto_mal textoMal FROM public.oraciones_detective_ortografico WHERE id_ejercicio = :idEjercicio";
	
	private static final String QUERY_GUARDA_ORACIONES_CONTESTADAS = "INSERT INTO public.respuestas_detective_ortografico (id_oracion, id_alumno, texto_contestado, id_ejercicio, id_asignacion) "
			+ "VALUES (:idOracion, :idAlumno, :textoContestado, :idEjercicio, :idAsignacion)";
		
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public AlumnoLoginResponse login(LoginBean loginBean) {
		
		AlumnoLoginResponse loginResponse = new AlumnoLoginResponse();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		try{
			parameters.addValue("usuario", loginBean.getUsuario());
			parameters.addValue("password", loginBean.getPassword());
			
			loginResponse = namedParameterJdbcTemplate.queryForObject(QUERY_LOGIN, parameters, 
					 new BeanPropertyRowMapper<AlumnoLoginResponse>(AlumnoLoginResponse.class));
			
			parameters.addValue("idAlumno", loginResponse.getIdAlumno());
			loginResponse.setTieneTareas(namedParameterJdbcTemplate.queryForObject(QUERY_TIENE_TAREA, parameters, Boolean.class));
			
			parameters.addValue("idMaestro", loginResponse.getIdMaestro());
			
			loginResponse.setAvanceMision(30);
			
			if(loginResponse.isPrimerLogin()) {
				namedParameterJdbcTemplate.update(ACTUALIZA_LOGIN, parameters);
			}
			
		}catch(EmptyResultDataAccessException e) {
			loginResponse.setStatus(HttpStatus.NOT_FOUND);
			loginResponse.setMensaje("No se encontro ningun alumno con esas credenciales");
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			loginResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			loginResponse.setMensaje("Hubo un error en el servidor al hacer el login");
		}
		
		return loginResponse;
	}

	@Override
	public MisTareasResponseBean obtenerMisTareas(ObtenerMisTareasRequestBean obtenerMisTareasBean) {
		
		MisTareasResponseBean response = new MisTareasResponseBean();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		try{
			parameters.addValue("idAlumno", obtenerMisTareasBean.getIdAlumno());
			List<Tareas> listTareasResult = namedParameterJdbcTemplate.query(QUERY_OBTENER_MIS_TAREAS, parameters,
					new BeanPropertyRowMapper<Tareas>(Tareas.class));
				
			HashMap<String, List<Tareas>> misTareas = new HashMap<>();
			List<Tareas> tareasAgrupadas = null;
			
			
			for(Tareas tarea: listTareasResult){
				
				tareasAgrupadas = null;
				if(misTareas.containsKey(tarea.getTipo())){

					if("DETECTIVE ORTOGRAFICO".equals(tarea.getTipo())) {
						parameters.addValue("idEjercicio", tarea.getIdEjercicio());
						tarea.setOraciones(namedParameterJdbcTemplate.query(QUERY_GET_LISTA_ORACIONES, parameters,
								new BeanPropertyRowMapper<OracionesBean>(OracionesBean.class)));
					}
					
					tareasAgrupadas = misTareas.get(tarea.getTipo());
					tareasAgrupadas.add(tarea);
					misTareas.put(tarea.getTipo(), tareasAgrupadas);
				}else {

					parameters.addValue("idEjercicio", tarea.getIdEjercicio());
					if("DETECTIVE ORTOGRAFICO".equals(tarea.getTipo())) {
						tarea.setOraciones(namedParameterJdbcTemplate.query(QUERY_GET_LISTA_ORACIONES, parameters,
								new BeanPropertyRowMapper<OracionesBean>(OracionesBean.class)));
					}
					
					tareasAgrupadas = new ArrayList<>();
					tareasAgrupadas.add(tarea);
					misTareas.put(tarea.getTipo(), tareasAgrupadas);
				}
			}
			
			response.setMisTareas(misTareas);
			
		}catch(EmptyResultDataAccessException e) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setMensaje("No se encontraron tareas asignadas al alumno");
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMensaje("Hubo un error al obtener las tareas del al alumno");
		}
		
		return response;
	}

	@Override
	public ResponseResult hacerTareaEscuchoYEscribo(TareaEscuchoYEscriboRequestBean tareaEscuchoYEscriboBean) {
		
		ResponseResult response = new ResponseResult();
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		try{

			parameters.addValue("idAlumno", tareaEscuchoYEscriboBean.getIdAlumno());
			parameters.addValue("idAsignacion", tareaEscuchoYEscriboBean.getIdAsignacion());
			parameters.addValue("idMaestro", tareaEscuchoYEscriboBean.getIdMaestro());
			parameters.addValue("dictado", tareaEscuchoYEscriboBean.getTexto());
			parameters.addValue("puntosObtenidos", 60);

			if(namedParameterJdbcTemplate.update(QUERY_GUARDA_TAREA_REALIZADA_ESCUCHO_Y_ESCRIBO, parameters) > 0) {
				namedParameterJdbcTemplate.update(QUERY_ACTUALIZA_FECHA_ENTREGA_TAREA, parameters);
				LOGGER.info("Tarea realizada guardada correctamente");
			}
			
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMensaje("Hubo un error al guardar la tarea realizada");
		}
		
		return response;
	}

	@Override
	public ResponseResult hacerTareaDetectorOrtografico(TareaDetectiveOrtograficoRequestBean tareaDetectiveOrtografico) {
		
		ResponseResult response = new ResponseResult();
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		try{
			
			parameters.addValue("idAlumno", tareaDetectiveOrtografico.getIdAlumno());
			parameters.addValue("idEjercicio", tareaDetectiveOrtografico.getIdEjercicio());
			parameters.addValue("idAsignacion", tareaDetectiveOrtografico.getIdAsignacion());
			
			for(OracionesBean oracion: tareaDetectiveOrtografico.getOraciones()) {
				parameters.addValue("textoContestado", oracion.getTextoContestado());
				parameters.addValue("idOracion", oracion.getIdOracion());
				namedParameterJdbcTemplate.update(QUERY_GUARDA_ORACIONES_CONTESTADAS, parameters);
			}
			
			namedParameterJdbcTemplate.update(QUERY_ACTUALIZA_FECHA_ENTREGA_TAREA, parameters);
			LOGGER.info("Tarea realizada guardada correctamente");

		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMensaje("Hubo un error al guardar la tarea realizada");
		}
		
		
		return response;
	}

}
