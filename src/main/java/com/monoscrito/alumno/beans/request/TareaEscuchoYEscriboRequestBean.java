package com.monoscrito.alumno.beans.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TareaEscuchoYEscriboRequestBean {
	
	@NotEmpty
	private String texto;
	
	@NotNull
	private Long idAlumno;
	
	@NotNull
	private Long idMaestro;
	
	@NotNull
	private Long idAsignacion;
	
	public Long getIdMaestro() {
		return idMaestro;
	}

	public void setIdMaestro(Long idMaestro) {
		this.idMaestro = idMaestro;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Long getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(Long idAlumno) {
		this.idAlumno = idAlumno;
	}

	public Long getIdAsignacion() {
		return idAsignacion;
	}

	public void setIdAsignacion(Long idAsignacion) {
		this.idAsignacion = idAsignacion;
	}
	
}
