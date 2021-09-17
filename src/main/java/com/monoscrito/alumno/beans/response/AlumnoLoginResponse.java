package com.monoscrito.alumno.beans.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.monoscrito.beans.response.ResponseResult;

@JsonInclude(Include.NON_EMPTY)
public class AlumnoLoginResponse extends ResponseResult{
	
	private Long idAlumno;
	
	private Long idMaestro;
	
	private String nombres;
	
	private String apellidos;
	
	private String pseudonimo;
	
	private boolean tieneTareas;
	
	private boolean primerLogin;
	
	private int avanceMision;
	
	public Long getIdMaestro() {
		return idMaestro;
	}

	public void setIdMaestro(Long idMaestro) {
		this.idMaestro = idMaestro;
	}

	public boolean isPrimerLogin() {
		return primerLogin;
	}

	public void setPrimerLogin(boolean primerLogin) {
		this.primerLogin = primerLogin;
	}

	public boolean isTieneTareas() {
		return tieneTareas;
	}

	public void setTieneTareas(boolean tieneTareas) {
		this.tieneTareas = tieneTareas;
	}

	public int getAvanceMision() {
		return avanceMision;
	}

	public void setAvanceMision(int avanceMision) {
		this.avanceMision = avanceMision;
	}

	public Long getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(Long idAlumno) {
		this.idAlumno = idAlumno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPseudonimo() {
		return pseudonimo;
	}

	public void setPseudonimo(String pseudonimo) {
		this.pseudonimo = pseudonimo;
	}
	
}
