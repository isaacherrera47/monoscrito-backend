package com.monoscrito.maestro.dao;

import com.monoscrito.alumno.beans.request.ObtenerMisTareasRequestBean;
import com.monoscrito.alumno.beans.request.TareaDetectiveOrtograficoRequestBean;
import com.monoscrito.alumno.beans.request.TareaEscuchoYEscriboRequestBean;
import com.monoscrito.alumno.beans.response.AlumnoLoginResponse;
import com.monoscrito.alumno.beans.response.MisTareasResponseBean;
import com.monoscrito.beans.response.ResponseResult;
import com.monoscrito.commons.beans.request.LoginBean;

public interface AlumnoDAO {

	AlumnoLoginResponse login(LoginBean loginBean);
	
	MisTareasResponseBean obtenerMisTareas(ObtenerMisTareasRequestBean obtenerMisTareasBean);
	
	ResponseResult hacerTareaEscuchoYEscribo(TareaEscuchoYEscriboRequestBean tareaEscuchoYEscriboBean);
	
	ResponseResult hacerTareaDetectorOrtografico(TareaDetectiveOrtograficoRequestBean tareaDetectiveOrtografico);
	
}
