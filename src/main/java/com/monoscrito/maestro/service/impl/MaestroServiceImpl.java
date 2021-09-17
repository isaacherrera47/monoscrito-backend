package com.monoscrito.maestro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.monoscrito.beans.response.InicializarAsignacionesBean;
import com.monoscrito.beans.response.ListaEjerciciosResponse;
import com.monoscrito.beans.response.ResponseResult;
import com.monoscrito.commons.beans.request.LoginBean;
import com.monoscrito.maestro.beans.request.EjercicioBean;
import com.monoscrito.maestro.beans.request.EntregasRequestBean;
import com.monoscrito.maestro.beans.request.GrupoAlumnosBean;
import com.monoscrito.maestro.beans.request.InitAsignacionesRequestBean;
import com.monoscrito.maestro.beans.request.PerfilBean;
import com.monoscrito.maestro.beans.request.RealizaAsignacionRequestBean;
import com.monoscrito.maestro.beans.request.ResultadosRequestBean;
import com.monoscrito.maestro.beans.response.EntregasList;
import com.monoscrito.maestro.beans.response.GrupoResponse;
import com.monoscrito.maestro.beans.response.LoginResponse;
import com.monoscrito.maestro.beans.response.ResultadosList;
import com.monoscrito.maestro.dao.MaestroDAO;
import com.monoscrito.maestro.service.MaestroService;

@Service
public class MaestroServiceImpl implements MaestroService{
	
	@Autowired
	private MaestroDAO maestroDAO;

	@Override
	public ResponseResult crearPerfil(PerfilBean perfilBean) {
		return maestroDAO.crearPerfil(perfilBean);
	}

	@Override
	public LoginResponse login(LoginBean loginBean) {
		return maestroDAO.login(loginBean);
	}

	@Override
	public ResponseResult crearGrupo(GrupoAlumnosBean grupoAlumnos) {
		return maestroDAO.crearGrupo(grupoAlumnos);
	}

	@Override
	public GrupoResponse obtenerGrupo(Long idMaestro) {
		return maestroDAO.obtenerGrupo(idMaestro);
	}

	@Override
	public ResponseResult crearEjercicio(EjercicioBean ejercicioBean) {
		
		if("DETECTIVE ORTOGRAFICO".equals(ejercicioBean.getTipo()) && (ejercicioBean.getOraciones() == null || ejercicioBean.getOraciones().isEmpty())) {
			ResponseResult result = new ResponseResult();
			result.setMensaje("Para ejercicios 'DETECTIVE ORTOGRAFICO' es necesario mandar al menos 1 oracion");
			result.setStatus(HttpStatus.BAD_REQUEST);
			return result;
		}
		
		return maestroDAO.crearEjercicio(ejercicioBean);
	}

	@Override
	public ListaEjerciciosResponse obtenerListaEjercicios() {
		return maestroDAO.obtenerListaEjercicios();
	}

	@Override
	public InicializarAsignacionesBean inicializarModuloAsignaciones(InitAsignacionesRequestBean initAsignacionesRequest) {
		return maestroDAO.inicializarModuloAsignaciones(initAsignacionesRequest);
	}

	@Override
	public ResponseResult realizaAsignacion(RealizaAsignacionRequestBean realizaAsignacionRequest) {
		return maestroDAO.realizaAsignacion(realizaAsignacionRequest);
	}

	@Override
	public EntregasList obtieneEntregas(EntregasRequestBean entregasRequest) {
		return maestroDAO.obtieneEntregas(entregasRequest);
	}

	@Override
	public ResultadosList obtieneResultados(ResultadosRequestBean resultadosRequest) {
		return maestroDAO.obtieneResultados(resultadosRequest);
	}
	
}
