package com.monoscrito.maestro.beans.response;

import java.util.List;

import com.monoscrito.beans.response.ResponseResult;
import com.monoscrito.maestro.beans.Alumno;

public class GrupoResponse extends ResponseResult{

	private String nombre;
	
	private List<Alumno> alumnos;

	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
