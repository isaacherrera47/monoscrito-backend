package com.monoscrito.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.monoscrito.alumno.beans.request.ObtenerMisTareasRequestBean;
import com.monoscrito.alumno.beans.request.TareaDetectiveOrtograficoRequestBean;
import com.monoscrito.alumno.beans.request.TareaEscuchoYEscriboRequestBean;
import com.monoscrito.alumno.beans.response.AlumnoLoginResponse;
import com.monoscrito.alumno.beans.response.MisTareasResponseBean;
import com.monoscrito.beans.response.ResponseResult;
import com.monoscrito.commons.beans.request.LoginBean;
import com.monoscrito.maestro.service.AlumnoService;

@RestController
@CrossOrigin(origins = {"https://localhost:4200","https://monoscrito.ngrok.io"})
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;
	
	@PostMapping("/alumno/login")
	public ResponseEntity<AlumnoLoginResponse> login(@RequestBody @Valid LoginBean loginBean, BindingResult bindingResult){
		
		AlumnoLoginResponse response = null;
		
		if (bindingResult.hasErrors()) {
			response = new AlumnoLoginResponse();
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMensaje("Faltan campos por enviar");
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				response.getErrores().add(fieldError.getField() + " - " +fieldError.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			response = alumnoService.login(loginBean);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}
	
	@PostMapping("/alumno/obtener-mis-tareas")
	public ResponseEntity<MisTareasResponseBean> obtenerMisTareas(@RequestBody @Valid ObtenerMisTareasRequestBean obtenerMisTareasBean, BindingResult bindingResult){
		
		MisTareasResponseBean response = null;
		
		if (bindingResult.hasErrors()) {
			response = new MisTareasResponseBean();
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMensaje("Faltan campos por enviar");
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				response.getErrores().add(fieldError.getField() + " - " +fieldError.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			response = alumnoService.obtenerMisTareas(obtenerMisTareasBean);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}
	
	@PostMapping("/alumno/hacer-tarea/escucho-y-escribo")
	public ResponseEntity<ResponseResult> hacerTareaEscuchoYEscribo(@RequestBody @Valid TareaEscuchoYEscriboRequestBean tareaEscuchoYEscriboBean, BindingResult bindingResult){
		
		ResponseResult response = null;
		
		if (bindingResult.hasErrors()) {
			response = new MisTareasResponseBean();
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMensaje("Faltan campos por enviar");
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				response.getErrores().add(fieldError.getField() + " - " +fieldError.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			response = alumnoService.hacerTareaEscuchoYEscribo(tareaEscuchoYEscriboBean);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}
	
	@PostMapping("/alumno/hacer-tarea/detective-ortografico")
	public ResponseEntity<ResponseResult> hacerTareaDetectorOrtografico(@RequestBody @Valid TareaDetectiveOrtograficoRequestBean realizaOraciones, BindingResult bindingResult){
		
		ResponseResult response = null;
		
		if (bindingResult.hasErrors()) {
			response = new MisTareasResponseBean();
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMensaje("Faltan campos por enviar");
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				response.getErrores().add(fieldError.getField() + " - " +fieldError.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			response = alumnoService.hacerTareaDetectorOrtografico(realizaOraciones);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

}
	