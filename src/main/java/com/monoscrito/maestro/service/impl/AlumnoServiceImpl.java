package com.monoscrito.maestro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monoscrito.alumno.beans.request.ObtenerMisTareasRequestBean;
import com.monoscrito.alumno.beans.request.TareaDetectiveOrtograficoRequestBean;
import com.monoscrito.alumno.beans.request.TareaEscuchoYEscriboRequestBean;
import com.monoscrito.alumno.beans.response.AlumnoLoginResponse;
import com.monoscrito.alumno.beans.response.MisTareasResponseBean;
import com.monoscrito.beans.response.ResponseResult;
import com.monoscrito.commons.beans.request.LoginBean;
import com.monoscrito.maestro.dao.AlumnoDAO;
import com.monoscrito.maestro.service.AlumnoService;

@Service
public class AlumnoServiceImpl implements AlumnoService {

	@Autowired
	private AlumnoDAO alumnoDAO;
	
	@Override
	public AlumnoLoginResponse login(LoginBean loginBean) {
		return alumnoDAO.login(loginBean);
	}

	@Override
	public MisTareasResponseBean obtenerMisTareas(ObtenerMisTareasRequestBean obtenerMisTareasBean) {
		return alumnoDAO.obtenerMisTareas(obtenerMisTareasBean);
	}

	@Override
	public ResponseResult hacerTareaEscuchoYEscribo(TareaEscuchoYEscriboRequestBean tareaEscuchoYEscriboBean) {
		return alumnoDAO.hacerTareaEscuchoYEscribo(tareaEscuchoYEscriboBean);
	}

	@Override
	public ResponseResult hacerTareaDetectorOrtografico(TareaDetectiveOrtograficoRequestBean tareaDetectiveOrtografico) {
		return alumnoDAO.hacerTareaDetectorOrtografico(tareaDetectiveOrtografico);
	}

}
