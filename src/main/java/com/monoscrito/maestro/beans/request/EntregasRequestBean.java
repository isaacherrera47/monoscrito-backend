package com.monoscrito.maestro.beans.request;

import javax.validation.constraints.NotNull;

public class EntregasRequestBean {
	
	@NotNull
	private Long idMaestro;

	public Long getIdMaestro() {
		return idMaestro;
	}

	public void setIdMaestro(Long idMaestro) {
		this.idMaestro = idMaestro;
	}

}
