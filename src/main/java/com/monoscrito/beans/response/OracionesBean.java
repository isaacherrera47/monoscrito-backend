package com.monoscrito.beans.response;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class OracionesBean {
	
	@NotNull
	private Long idOracion;
	
	private String textoBien;
	
	private String textoContestado;
	
	private String textoMal;
	
	public String getTextoMal() {
		return textoMal;
	}

	public void setTextoMal(String textoMal) {
		this.textoMal = textoMal;
	}

	public String getTextoContestado() {
		return textoContestado;
	}

	public void setTextoContestado(String textoContestado) {
		this.textoContestado = textoContestado;
	}

	public Long getIdOracion() {
		return idOracion;
	}

	public void setIdOracion(Long idOracion) {
		this.idOracion = idOracion;
	}

	public String getTextoBien() {
		return textoBien;
	}

	public void setTextoBien(String textoBien) {
		this.textoBien = textoBien;
	}
	
}
