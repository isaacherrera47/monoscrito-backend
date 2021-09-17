package com.monoscrito.maestro.beans.response;

import com.monoscrito.beans.response.ResponseResult;

public class LoginResponse extends ResponseResult{
	
	private Long idMaestro;
	
	private String grupo;
	
	private String avatar;
	
	private String nombres;
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public Long getIdMaestro() {
		return idMaestro;
	}

	public void setIdMaestro(Long idMaestro) {
		this.idMaestro = idMaestro;
	}
	
}
