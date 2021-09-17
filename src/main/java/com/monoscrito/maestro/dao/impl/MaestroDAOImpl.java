package com.monoscrito.maestro.dao.impl;

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

import com.monoscrito.beans.response.InicializarAsignacionesBean;
import com.monoscrito.beans.response.ListaEjerciciosResponse;
import com.monoscrito.beans.response.ResponseResult;
import com.monoscrito.commons.beans.request.LoginBean;
import com.monoscrito.maestro.beans.Alumno;
import com.monoscrito.maestro.beans.BuscaErrores;
import com.monoscrito.maestro.beans.request.CatalogoEjercicio;
import com.monoscrito.maestro.beans.request.EjercicioBean;
import com.monoscrito.maestro.beans.request.EntregasRequestBean;
import com.monoscrito.maestro.beans.request.GrupoAlumnosBean;
import com.monoscrito.maestro.beans.request.InitAsignacionesRequestBean;
import com.monoscrito.maestro.beans.request.PerfilBean;
import com.monoscrito.maestro.beans.request.RealizaAsignacionRequestBean;
import com.monoscrito.maestro.beans.request.ResultadosRequestBean;
import com.monoscrito.maestro.beans.response.EjerciciosDetalleBean;
import com.monoscrito.maestro.beans.response.EntregasBean;
import com.monoscrito.maestro.beans.response.EntregasList;
import com.monoscrito.maestro.beans.response.GrupoResponse;
import com.monoscrito.maestro.beans.response.LoginResponse;
import com.monoscrito.maestro.beans.response.ResultadosList;
import com.monoscrito.maestro.dao.MaestroDAO;

@Repository
public class MaestroDAOImpl implements MaestroDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(MaestroDAOImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private static final String QUERY_CREAR_PERFIL = "INSERT INTO public.maestros(escuela, nombres, apellidos, email, usuario, password, pseudonimo, avatar) "
			+ "VALUES (:escuela, :nombres, :apellidos, :email, :usuario, :password, :pseudonimo, :avatar)";
	
	private static final String QUERY_LOGIN = "SELECT id_maestro idMaestro, grupo, avatar, nombres FROM public.maestros WHERE usuario = :usuario AND password = :password";
	
	private static final String QUERY_AGREGAR_GRUPO_A_MAESTRO = "UPDATE public.maestros SET grupo = :nombre WHERE id_maestro = :idMaestro";
	
	private static final String QUERY_CREAR_ALUMNO = "INSERT INTO public.alumnos(nombres, apellidos, pseudonimo, password, id_maestro) "
			+ "VALUES (:nombres, :apellidos, :pseudonimo, :password, :idMaestro)";
	
	private static final String QUERY_OBTENER_GRUPO = "SELECT a.id_alumno id, a.nombres, a.apellidos, a.pseudonimo, a.password, a.avatar, ma.grupo nombreGrupo "
			+ "FROM alumnos a, maestros ma WHERE a.id_maestro = ma.id_maestro AND a.id_maestro  = :idMaestro ORDER BY 2 asc";
	
	private static final String QUERY_CREAR_EJERCICIO= "INSERT INTO public.ejercicios (id_ejercicio, nombre, id_maestro, tipo_ejercicio, tiempo_asignado,  nombre_lectura, instrucciones, creado, puntos_posibles, tipo_diferencias) "
			+ "VALUES (:idEjercicio, :nombre, :idMaestro, :tipo, :tiempo, :lectura, :instrucciones, now(), :puntosPosibles, :tipoDiferencias)";
	
	private static final String QUERY_OBTENER_LISTA_EJERCICIOS = "SELECT id_ejercicio idEjercicio, nombre, tipo_ejercicio tipo, to_char(creado, 'DD-MON-YYYY') creado, tiempo_asignado tiempo "
			+ "FROM public.ejercicios ORDER BY creado desc";
	
	private static final String QUERY_OBTENER_CATALOGO_EJERCICIOS = "SELECT id_ejercicio id, nombre FROM public.ejercicios ORDER BY nombre ASC";
	
	private static final String QUERY_OBTENER_ALUMNOS_POR_MAESTRO = "SELECT id_alumno id, concat(nombres,' ',apellidos) nombres FROM public.alumnos WHERE id_maestro = :idMaestro order by nombres";
	
	private static final String QUERY_AGREGAR_ASIGNACION_ALUMNO = "INSERT INTO public.asignaciones (id_ejercicio, id_alumno, id_maestro, fecha_entrega, permite_extemporaneas) VALUES (:idEjercicio, :idAlumno, :idMaestro, :fechaEntrega, :permiteExtemporaneas)";
	
	private static final String QUERY_AGREGAR_ORACIONES_EJERCICIO_DETECTIVE_ORTOGRAFICO = "INSERT INTO public.oraciones_detective_ortografico (id_ejercicio, texto_bien, texto_mal) VALUES (:idEjercicio, :textoBien, :textoMal)";
	
	private static final String GET_ID_ASIGNACION = "SELECT NEXTVAL('seq_alumnos_ejercicios')";
	
	private static final String GET_ID_EJERCICIO = "SELECT NEXTVAL('sq_id_ejercicio')";
	
	private static final String OBTENER_EJERCICIOS_POR_ALUMNO = "SELECT DISTINCT e.nombre nombre, a.fecha_entrega fechaEntrega, e.tipo_ejercicio tipo, e.id_ejercicio idEjercicio "
			+ "FROM public.asignaciones a "
			+ "JOIN public.alumnos al ON a.id_alumno = al.id_alumno AND a.id_maestro = al.id_maestro "
			+ "JOIN public.ejercicios e ON e.id_ejercicio = a.id_ejercicio AND e.id_maestro = a.id_maestro "
			+ "WHERE a.id_maestro = :idMaestro";
	
	private static final String OBTENER_ENTREGAS_POR_ALUMNO = "SELECT e.id_ejercicio idEjercicio, e.tipo_ejercicio tipoEjercicio, alum.nombres || ' ' || alum.apellidos nombreAlumno, e.nombre nombreEjercicio, "
			+ "CASE WHEN asig.entregado THEN 'ENTREGADO' "
			+ "WHEN (asig.permite_extemporaneas IS TRUE AND asig.fecha_finalizacion IS NULL ) THEN 'EXTEMPORANEO' "
			+ "WHEN (asig.fecha_finalizacion IS NULL AND now() > asig.fecha_entrega AND asig.permite_extemporaneas IS FALSE ) THEN 'NO_ENTREGADO' "
			+ "WHEN (asig.fecha_finalizacion IS NULL AND now() > asig.fecha_entrega AND asig.permite_extemporaneas IS TRUE ) THEN 'ABIERTO' "
			+ "ELSE 'NA' END status "
			+ "FROM public.alumnos alum "
			+ "LEFT JOIN public.asignaciones asig ON asig.id_alumno = alum.id_alumno AND asig.id_maestro = alum.id_maestro "
			+ "JOIN public.ejercicios e ON e.id_ejercicio = asig.id_ejercicio AND e.id_maestro = asig.id_maestro WHERE asig.id_maestro = :idMaestro ";
	
	@Override
	public ResponseResult crearPerfil(PerfilBean perfilBean) {
		
		ResponseResult result = new ResponseResult();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
	
		try{
			parameters.addValue("escuela", perfilBean.getEscuela());
			parameters.addValue("nombres", perfilBean.getNombres());
			parameters.addValue("apellidos", perfilBean.getApellidos());
			parameters.addValue("email", perfilBean.getEmail());
			parameters.addValue("usuario", perfilBean.getUsuario());
			parameters.addValue("password", perfilBean.getPassword());
			parameters.addValue("pseudonimo", perfilBean.getPseudonimo());
			parameters.addValue("avatar", perfilBean.getAvatar());
			
			namedParameterJdbcTemplate.update(QUERY_CREAR_PERFIL, parameters);
			LOGGER.info("Perfil creado");
			
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}

	@Override
	public LoginResponse login(LoginBean loginBean) {
		
		LoginResponse loginResponse = new LoginResponse();
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		try{
			
			parameters.addValue("usuario", loginBean.getUsuario());
			parameters.addValue("password", loginBean.getPassword());
			
			loginResponse = namedParameterJdbcTemplate.queryForObject(QUERY_LOGIN, parameters, 
					 new BeanPropertyRowMapper<LoginResponse>(LoginResponse.class));
			
		}catch(EmptyResultDataAccessException e) {
			loginResponse.setStatus(HttpStatus.NO_CONTENT);
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			loginResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return loginResponse;
	}

	@Override
	public ResponseResult crearGrupo(GrupoAlumnosBean grupoAlumnos) {
		
		ResponseResult responseResult = new ResponseResult();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		try{
			
			parameters.addValue("idMaestro", grupoAlumnos.getIdMaestro());
			parameters.addValue("nombre", grupoAlumnos.getNombre());
			namedParameterJdbcTemplate.update(QUERY_AGREGAR_GRUPO_A_MAESTRO, parameters);
			
			for(Alumno alumno: grupoAlumnos.getAlumnos()) {
				parameters = new MapSqlParameterSource();
				parameters.addValue("nombres", alumno.getNombres());
				parameters.addValue("apellidos", alumno.getApellidos());
				parameters.addValue("pseudonimo", alumno.getPseudonimo());
				parameters.addValue("password", alumno.getPassword());
				parameters.addValue("idMaestro", grupoAlumnos.getIdMaestro());
				namedParameterJdbcTemplate.update(QUERY_CREAR_ALUMNO, parameters);
			}
		
			
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			responseResult.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
		return responseResult;
	}

	@Override
	public GrupoResponse obtenerGrupo(Long idMaestro) {
		
		GrupoResponse grupoResponse = new GrupoResponse();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		try{
			parameters.addValue("idMaestro", idMaestro);
			List<Alumno> alumnos = namedParameterJdbcTemplate.query(QUERY_OBTENER_GRUPO, parameters, 
					 new BeanPropertyRowMapper<Alumno>(Alumno.class));
			
			if(alumnos.size()>0) {
				grupoResponse.setNombre(alumnos.get(0).getNombreGrupo());
				grupoResponse.setAlumnos(alumnos);
			}else {
				grupoResponse.setStatus(HttpStatus.NO_CONTENT);
			}
			
		}catch(EmptyResultDataAccessException e) {
			grupoResponse.setStatus(HttpStatus.NO_CONTENT);
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			grupoResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return grupoResponse;
	}

	@Override
	public ResponseResult crearEjercicio(EjercicioBean ejercicioBean) {
		
		ResponseResult response = new ResponseResult();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		Integer puntosPosibles = null;
	
		try{
			parameters.addValue("idMaestro", ejercicioBean.getIdMaestro());
			parameters.addValue("nombre", ejercicioBean.getNombre());
			parameters.addValue("tipo", ejercicioBean.getTipo());
			parameters.addValue("tiempo", ejercicioBean.getTiempo());
			parameters.addValue("instrucciones", ejercicioBean.getInstrucciones());
			parameters.addValue("lectura", ejercicioBean.getLectura());
			parameters.addValue("tipoDiferencias", ejercicioBean.getTipoDeDiferencias());
			
			if("QUIERO LEER ALGO".equals(ejercicioBean.getTipo())) {
				puntosPosibles = 60;
			}else if("ESCUCHO Y ESCRIBO".equals(ejercicioBean.getTipo())) {
				puntosPosibles = 80;
			}else if("SOY ESCRITOR".equals(ejercicioBean.getTipo())) {
				puntosPosibles = 105;
			}else if("DETECTIVE ORTOGRAFICO".equals(ejercicioBean.getTipo())) {
				
				puntosPosibles = 80;
			}else if("SE PERDIERON LETRAS".equals(ejercicioBean.getTipo())) {
				puntosPosibles = 1;
			}
			
			parameters.addValue("puntosPosibles", puntosPosibles);
			parameters.addValue("idEjercicio", namedParameterJdbcTemplate.queryForObject(GET_ID_EJERCICIO, parameters, Long.class));
			namedParameterJdbcTemplate.update(QUERY_CREAR_EJERCICIO, parameters);
			LOGGER.info("Ejercicio creado");
			
			if("DETECTIVE ORTOGRAFICO".equals(ejercicioBean.getTipo())) {
				
				for(BuscaErrores oracion: ejercicioBean.getOraciones()) {
					parameters.addValue("textoBien", oracion.getTextoBien());
					parameters.addValue("textoMal", oracion.getTextoMal());
					namedParameterJdbcTemplate.update(QUERY_AGREGAR_ORACIONES_EJERCICIO_DETECTIVE_ORTOGRAFICO, parameters);
				}
			}
			
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}

	@Override
	public ListaEjerciciosResponse obtenerListaEjercicios() {
		
		ListaEjerciciosResponse listaResponse = new ListaEjerciciosResponse();
		
		List<EjercicioBean> listaEjercicios = namedParameterJdbcTemplate.query(QUERY_OBTENER_LISTA_EJERCICIOS, 
				 new BeanPropertyRowMapper<EjercicioBean>(EjercicioBean.class));
		
		listaResponse.setListaEjercicios(listaEjercicios);
		
		return listaResponse;
	}

	@Override
	public InicializarAsignacionesBean inicializarModuloAsignaciones(InitAsignacionesRequestBean initAsignacionesRequest) {
		
		InicializarAsignacionesBean response = new InicializarAsignacionesBean();
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		try{

			parameters.addValue("idMaestro", initAsignacionesRequest.getIdMaestro());
			
			List<CatalogoEjercicio> listaEjercicios = namedParameterJdbcTemplate.query(QUERY_OBTENER_CATALOGO_EJERCICIOS, 
					 new BeanPropertyRowMapper<CatalogoEjercicio>(CatalogoEjercicio.class));
			
			List<Alumno> listaAlumnos = namedParameterJdbcTemplate.query(QUERY_OBTENER_ALUMNOS_POR_MAESTRO, parameters,
					 new BeanPropertyRowMapper<Alumno>(Alumno.class));
			
			response.setEjercicios(listaEjercicios);
			response.setAlumnos(listaAlumnos);
			
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}

	@Override
	public ResponseResult realizaAsignacion(RealizaAsignacionRequestBean realizaAsignacionRequest) {
		
		ResponseResult response = new ResponseResult();
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		try{
			
			parameters.addValue("idMaestro", realizaAsignacionRequest.getIdMaestro());
			parameters.addValue("idEjercicio", realizaAsignacionRequest.getIdEjercicio());
			parameters.addValue("fechaEntrega", realizaAsignacionRequest.getFechaEntrega());
			parameters.addValue("permiteExtemporaneas", realizaAsignacionRequest.getPermitirExtemporaneas());
			
			for(Long idAlumno: realizaAsignacionRequest.getIdAlumnos()) {
				parameters.addValue("idAlumno", idAlumno);
				parameters.addValue("idAsignacion", namedParameterJdbcTemplate.queryForObject(GET_ID_ASIGNACION, parameters, Long.class));
				namedParameterJdbcTemplate.update(QUERY_AGREGAR_ASIGNACION_ALUMNO, parameters);
			}
		}catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
		
	}

	@Override
	public EntregasList obtieneEntregas(EntregasRequestBean entregasRequest) {
		
		EntregasList response = new EntregasList();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		try{
			parameters.addValue("idMaestro", entregasRequest.getIdMaestro());
			
			response.setListaEjercicios(namedParameterJdbcTemplate.query(OBTENER_EJERCICIOS_POR_ALUMNO, parameters,
				 new BeanPropertyRowMapper<EjerciciosDetalleBean>(EjerciciosDetalleBean.class)));
		
			response.setListaEntregas(namedParameterJdbcTemplate.query(OBTENER_ENTREGAS_POR_ALUMNO, parameters,
					 new BeanPropertyRowMapper<EntregasBean>(EntregasBean.class)));
			
		
		}catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}

	@Override
	public ResultadosList obtieneResultados(ResultadosRequestBean resultadosRequest) {
		
		ResultadosList response = new ResultadosList();
		
		return response;
	}
	
}
