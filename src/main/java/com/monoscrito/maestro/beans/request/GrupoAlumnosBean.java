package com.monoscrito.maestro.beans.request;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.monoscrito.maestro.beans.Alumno;

public class GrupoAlumnosBean {
	
	@NotNull
	@Min(value = 1)
	private Long idMaestro;
	
	@NotEmpty
	private String nombre;
	
	@NotNull
	@Size(min = 1, message = "Se necesita enviar por lo menos 1 alumno")
	private List<Alumno> alumnos;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public Long getIdMaestro() {
		return idMaestro;
	}

	public void setIdMaestro(Long idMaestro) {
		this.idMaestro = idMaestro;
	}
	
}
