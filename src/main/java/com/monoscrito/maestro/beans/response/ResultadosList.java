package com.monoscrito.maestro.beans.response;

import java.util.List;

import com.monoscrito.beans.response.ResponseResult;

public class ResultadosList extends ResponseResult {

	private List<ResultadosBean> resultadosList;

	public List<ResultadosBean> getResultadosList() {
		return resultadosList;
	}

	public void setResultadosList(List<ResultadosBean> resultadosList) {
		this.resultadosList = resultadosList;
	}
	
	
}
