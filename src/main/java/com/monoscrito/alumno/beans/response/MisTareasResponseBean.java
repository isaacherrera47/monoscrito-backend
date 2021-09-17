package com.monoscrito.alumno.beans.response;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.monoscrito.beans.response.ResponseResult;
import com.monoscrito.beans.response.Tareas;

@JsonInclude(Include.NON_EMPTY)
public class MisTareasResponseBean extends ResponseResult {
	
	private HashMap<String, List<Tareas>> misTareas;

	public HashMap<String, List<Tareas>> getMisTareas() {
		return misTareas;
	}

	public void setMisTareas(HashMap<String, List<Tareas>> misTareas) {
		this.misTareas = misTareas;
	}

}
