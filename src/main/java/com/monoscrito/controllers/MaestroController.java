package com.monoscrito.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.monoscrito.beans.response.InicializarAsignacionesBean;
import com.monoscrito.beans.response.ListaEjerciciosResponse;
import com.monoscrito.beans.response.ResponseResult;
import com.monoscrito.commons.beans.request.LoginBean;
import com.monoscrito.maestro.beans.request.EjercicioBean;
import com.monoscrito.maestro.beans.request.EntregasRequestBean;
import com.monoscrito.maestro.beans.request.GrupoAlumnosBean;
import com.monoscrito.maestro.beans.request.InitAsignacionesRequestBean;
import com.monoscrito.maestro.beans.request.PerfilBean;
import com.monoscrito.maestro.beans.request.RealizaAsignacionRequestBean;
import com.monoscrito.maestro.beans.request.ResultadosRequestBean;
import com.monoscrito.maestro.beans.response.EntregasList;
import com.monoscrito.maestro.beans.response.GrupoResponse;
import com.monoscrito.maestro.beans.response.ResultadosList;
import com.monoscrito.maestro.service.MaestroService;

@RestController
@CrossOrigin(origins = {"https://localhost:4200","https://monoscrito.ngrok.io"})
public class MaestroController {
	
	@Autowired
	private MaestroService maestroService;
	
	@PostMapping("/maestro/crear-perfil")
	public ResponseEntity<ResponseResult> crearPerfil(@RequestBody @Valid PerfilBean perfilBean, BindingResult bindingResult){
		
		ResponseResult response = null;
		
		if (bindingResult.hasErrors()) {
			response = new ResponseResult();
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMensaje("Faltan campos por enviar");
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				response.getErrores().add(fieldError.getField() + " - " +fieldError.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			response = maestroService.crearPerfil(perfilBean);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}
	
	@PostMapping("/maestro/login")
	public ResponseEntity<ResponseResult> login(@RequestBody @Valid LoginBean loginBean, BindingResult bindingResult){
		
		ResponseResult response = null;
		
		if (bindingResult.hasErrors()) {
			response = new ResponseResult();
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMensaje("Faltan campos por enviar");
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				response.getErrores().add(fieldError.getField() + " - " +fieldError.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			response = maestroService.login(loginBean);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}
	
	@PostMapping("/maestro/grupo")
	public ResponseEntity<ResponseResult> crearGrupo(@RequestBody @Valid GrupoAlumnosBean grupoAlumnos, BindingResult bindingResult){
		
		ResponseResult response = null;
		
		if (bindingResult.hasErrors()) {
			response = new ResponseResult();
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMensaje("Faltan campos por enviar");
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				response.getErrores().add(fieldError.getField() + " - " +fieldError.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			response = maestroService.crearGrupo(grupoAlumnos);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}
	
	@GetMapping("/maestro/grupo/{idMaestro}")
	public ResponseEntity<GrupoResponse> obtenerGrupo(@PathVariable Long idMaestro){
		 GrupoResponse response = maestroService.obtenerGrupo(idMaestro);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@PostMapping("/maestro/ejercicio")
	public ResponseEntity<ResponseResult> crearEjercicio(@RequestBody @Valid EjercicioBean ejercicioBean, BindingResult bindingResult){
		
		ResponseResult response = null;
		
		if (bindingResult.hasErrors()) {
			response = new ResponseResult();
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMensaje("Faltan campos por enviar");
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				response.getErrores().add(fieldError.getField() + " - " +fieldError.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			response = maestroService.crearEjercicio(ejercicioBean);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@GetMapping("/maestro/ejercicio")
	public ResponseEntity<ListaEjerciciosResponse> obtenerListaEjercicios(){
		ListaEjerciciosResponse response = maestroService.obtenerListaEjercicios();
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@PostMapping("/maestro/inicializar-asignaciones")
	public ResponseEntity<InicializarAsignacionesBean> inicializarModuloAsignaciones(@RequestBody @Valid InitAsignacionesRequestBean initAsignacionesRequest, BindingResult bindingResult){
		
		InicializarAsignacionesBean response = null;
		
		if (bindingResult.hasErrors()) {
			response = new InicializarAsignacionesBean();
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMensaje("Faltan campos por enviar");
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				response.getErrores().add(fieldError.getField() + " - " +fieldError.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			response = maestroService.inicializarModuloAsignaciones(initAsignacionesRequest);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}
	
	@PostMapping("/maestro/realiza-asignacion")
	public ResponseEntity<ResponseResult> realizaAsignacion(@RequestBody @Valid RealizaAsignacionRequestBean realizaAsignacionRequest, BindingResult bindingResult){
		
		ResponseResult response = null;
		
		if (bindingResult.hasErrors()) {
			response = new ResponseResult();
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMensaje("Faltan campos por enviar");
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				response.getErrores().add(fieldError.getField() + " - " +fieldError.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			response = maestroService.realizaAsignacion(realizaAsignacionRequest);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}
	
	@PostMapping("/maestro/entregas")
	public ResponseEntity<EntregasList> obtieneEntregas(@RequestBody @Valid EntregasRequestBean entregasRequest, BindingResult bindingResult){
		
		EntregasList response = null;
		
		if (bindingResult.hasErrors()) {
			response = new EntregasList();
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMensaje("Faltan campos por enviar");
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				response.getErrores().add(fieldError.getField() + " - " +fieldError.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			response = maestroService.obtieneEntregas(entregasRequest);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}
	
	@PostMapping("/maestro/resultados")
	public ResponseEntity<ResultadosList> obtieneResultados(@RequestBody @Valid ResultadosRequestBean resultadosRequest, BindingResult bindingResult){
		
		ResultadosList response = null;
		
		if (bindingResult.hasErrors()) {
			response = new ResultadosList();
			response.setStatus(HttpStatus.BAD_REQUEST);
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				response.getErrores().add(fieldError.getField() + " - " +fieldError.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			response = maestroService.obtieneResultados(resultadosRequest);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

}

