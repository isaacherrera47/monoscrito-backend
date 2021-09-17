package com.monoscrito.maestro.beans.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.monoscrito.beans.response.ResponseResult;

@JsonInclude(Include.NON_EMPTY)
public class EntregasList extends ResponseResult {
	
	private List<EjerciciosDetalleBean> listaEjercicios;
	
	private List<EntregasBean> listaEntregas;
	
	public List<EntregasBean> getListaEntregas() {
		return listaEntregas;
	}

	public void setListaEntregas(List<EntregasBean> listaEntregas) {
		this.listaEntregas = listaEntregas;
	}

	public List<EjerciciosDetalleBean> getListaEjercicios() {
		return listaEjercicios;
	}

	public void setListaEjercicios(List<EjerciciosDetalleBean> listaEjercicios) {
		this.listaEjercicios = listaEjercicios;
	}
	
}
