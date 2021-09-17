package com.monoscrito.alumno.beans.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.monoscrito.beans.response.OracionesBean;

public class TareaDetectiveOrtograficoRequestBean {
	
	@NotNull
	private Long idAlumno;
	
	@NotNull
	private List<OracionesBean> oraciones;
	
	@NotNull
	private Long idEjercicio;
	
	@NotNull
	private Long idAsignacion;

	public Long getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(Long idAlumno) {
		this.idAlumno = idAlumno;
	}

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

	public Long getIdAsignacion() {
		return idAsignacion;
	}

	public void setIdAsignacion(Long idAsignacion) {
		this.idAsignacion = idAsignacion;
	}

}
