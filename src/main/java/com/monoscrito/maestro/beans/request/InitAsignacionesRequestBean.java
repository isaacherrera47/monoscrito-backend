package com.monoscrito.maestro.beans.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class InitAsignacionesRequestBean {
	
	@Min(value = 1)
	@NotNull
	private Long idMaestro;

	public Long getIdMaestro() {
		return idMaestro;
	}

	public void setIdMaestro(Long idMaestro) {
		this.idMaestro = idMaestro;
	}
	
	

}
