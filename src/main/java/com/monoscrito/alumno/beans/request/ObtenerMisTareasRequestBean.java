package com.monoscrito.alumno.beans.request;

import javax.validation.constraints.Min;

public class ObtenerMisTareasRequestBean {
	
	@Min(value = 1)
	private int idAlumno;

	public int getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
	}

}
