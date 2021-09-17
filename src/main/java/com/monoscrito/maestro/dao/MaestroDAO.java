package com.monoscrito.maestro.dao;

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

public interface MaestroDAO {
	
	ResponseResult crearPerfil(PerfilBean perfilBean);
	
	LoginResponse login(LoginBean loginBean);
	
	ResponseResult crearGrupo(GrupoAlumnosBean grupoAlumnos);
	
	GrupoResponse obtenerGrupo(Long idMaestro);
	
	ResponseResult crearEjercicio(EjercicioBean ejercicioBean);
	
	ListaEjerciciosResponse obtenerListaEjercicios();
	
	InicializarAsignacionesBean inicializarModuloAsignaciones(InitAsignacionesRequestBean initAsignacionesRequest);
	
	ResponseResult realizaAsignacion(RealizaAsignacionRequestBean realizaAsignacionRequest);
	
	EntregasList obtieneEntregas(EntregasRequestBean entregasRequest);
	
	ResultadosList obtieneResultados(ResultadosRequestBean resultadosRequest);

}
