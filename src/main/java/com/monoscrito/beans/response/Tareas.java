package com.monoscrito.beans.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Tareas {

	private String nombre;
	
	@JsonIgnore
	private String tipo;
	
	private int idAsignacion;
	
	private String nombreLectura;
	
	private String instrucciones;
	
	private Integer tiempoAsignado;
	
	private Long idEjercicio;
	
	private List<OracionesBean> oraciones;
	
	public List<OracionesBean> getOraciones() {
		return oraciones;
	}

	public void setOraciones(List<OracionesBean> oraciones) {
		this.oraciones = oraciones;
	}

	public Long getIdEjercicio() {
		return idEjercicio;
	}

	public void setIdEjercicio(Long idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public Integer getTiempoAsignado() {
		return tiempoAsignado;
	}

	public void setTiempoAsignado(Integer tiempoAsignado) {
		this.tiempoAsignado = tiempoAsignado;
	}

	public String getNombreLectura() {
		return nombreLectura;
	}

	public void setNombreLectura(String nombreLectura) {
		this.nombreLectura = nombreLectura;
	}

	public String getInstrucciones() {
		return instrucciones;
	}

	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}

	public int getIdAsignacion() {
		return idAsignacion;
	}

	public void setIdAsignacion(int idAsignacion) {
		this.idAsignacion = idAsignacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
