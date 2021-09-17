package com.monoscrito.maestro.beans.request;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RealizaAsignacionRequestBean {
	
	@NotNull
	@Min(value = 1)
	private Long idMaestro;
	
	@NotNull
	@Min(value = 1)
	private Long idEjercicio;
	
	@NotNull
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime fechaEntrega;
	
	@NotNull
	private Boolean permitirExtemporaneas;
	
	@NotNull
	@Size(min = 1)
	private List<Long> idAlumnos;
	
	public Long getIdEjercicio() {
		return idEjercicio;
	}

	public void setIdEjercicio(Long idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public Long getIdMaestro() {
		return idMaestro;
	}

	public void setIdMaestro(Long idMaestro) {
		this.idMaestro = idMaestro;
	}

	public LocalDateTime getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDateTime fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Boolean getPermitirExtemporaneas() {
		return permitirExtemporaneas;
	}

	public void setPermitirExtemporaneas(Boolean permitirExtemporaneas) {
		this.permitirExtemporaneas = permitirExtemporaneas;
	}

	public List<Long> getIdAlumnos() {
		return idAlumnos;
	}

	public void setIdAlumnos(List<Long> idAlumnos) {
		this.idAlumnos = idAlumnos;
	}
}
