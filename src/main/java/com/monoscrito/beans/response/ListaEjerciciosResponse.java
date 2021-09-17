package com.monoscrito.beans.response;

import java.util.List;

import com.monoscrito.maestro.beans.request.EjercicioBean;

public class ListaEjerciciosResponse extends ResponseResult{
	
	private List<EjercicioBean> listaEjercicios;

	public List<EjercicioBean> getListaEjercicios() {
		return listaEjercicios;
	}

	public void setListaEjercicios(List<EjercicioBean> listaEjercicios) {
		this.listaEjercicios = listaEjercicios;
	}
	
}
