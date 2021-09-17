package com.monoscrito.maestro.beans.request;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.monoscrito.maestro.beans.BuscaErrores;

@JsonInclude(Include.NON_EMPTY)
public class EjercicioBean {
	
	private Long idEjercicio;
	
	@Min(value = 1)
	private Long idMaestro;
	
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String tipo;
	
	@Min(value = 1)
	private int tiempo;
	
	private String instrucciones;
	
	private String lectura;
	
	private String creado;
	
	private String tipoDeDiferencias;
	
	private List<BuscaErrores> oraciones;
	
	public List<BuscaErrores> getOraciones() {
		return oraciones;
	}

	public void setOraciones(List<BuscaErrores> oraciones) {
		this.oraciones = oraciones;
	}

	public String getTipoDeDiferencias() {
		return tipoDeDiferencias;
	}

	public void setTipoDeDiferencias(String tipoDeDiferencias) {
		this.tipoDeDiferencias = tipoDeDiferencias;
	}

	public String getCreado() {
		return creado;
	}

	public void setCreado(String creado) {
		this.creado = creado;
	}

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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public String getInstrucciones() {
		return instrucciones;
	}

	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}

	public String getLectura() {
		return lectura;
	}

	public void setLectura(String lectura) {
		this.lectura = lectura;
	}

}
