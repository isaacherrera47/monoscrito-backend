package com.monoscrito.beans.response;

import java.util.List;

import com.monoscrito.maestro.beans.Alumno;
import com.monoscrito.maestro.beans.request.CatalogoEjercicio;

public class InicializarAsignacionesBean extends ResponseResult {
	
	private List<CatalogoEjercicio> ejercicios;
	
	private List<Alumno> alumnos;

	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public List<CatalogoEjercicio> getEjercicios() {
		return ejercicios;
	}

	public void setEjercicios(List<CatalogoEjercicio> ejercicios) {
		this.ejercicios = ejercicios;
	}
	
}
