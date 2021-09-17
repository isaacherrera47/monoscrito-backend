package com.monoscrito.beans.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseResult {
	
	private HttpStatus status;
	
	private String mensaje;
	
	@JsonInclude(Include.NON_EMPTY)
	private List<String> errores = new ArrayList<>();
	
	public ResponseResult() {
		this.status = HttpStatus.OK;
		this.mensaje = "Operacion correcta";
	}
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<String> getErrores() {
		return errores;
	}

	public void setErrores(List<String> errores) {
		this.errores = errores;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
