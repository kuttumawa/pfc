package uned.dlr.pfc.controller;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import uned.dlr.pfc.model.Codigo;
import uned.dlr.pfc.model.Proyecto;
import uned.dlr.pfc.model.User;
import uned.dlr.pfc.service.CodigoServiceIF;
import uned.dlr.pfc.service.ProyectoServiceIF;
import uned.dlr.pfc.service.UserServiceIF;

@SpringBootApplication
@RestController
public class ProyectoController {

	
	@Inject
	private CodigoServiceIF codigoService;
	@Inject
	private ProyectoServiceIF proyectoService;
	@Inject
	private UserServiceIF userService;

	@RequestMapping(value = "/proyectos/{proyectoId}", method = RequestMethod.GET)
	public ResponseEntity<Proyecto> getProyecto(@RequestHeader("Authorization") String authorization,@PathVariable Long proyectoId) {
		User user=checkAutenticacion(authorization);
		Proyecto proyecto=existeProyectoYEstaAutorizado(proyectoId,user);
		return new ResponseEntity<Proyecto>(proyecto, HttpStatus.OK);
	}
	@RequestMapping(value = "/proyectos", method = RequestMethod.GET)
	public ResponseEntity<List<Proyecto>> getProyectos(@RequestHeader("Authorization") String authorization) {
		User user=checkAutenticacion(authorization);
		List<Proyecto> proyectos = proyectoService.getProyectosUser(user.getId());
		return new ResponseEntity<List<Proyecto>>(proyectos, HttpStatus.OK);
	}

	@RequestMapping(value = "/proyectos/{proyectoId}", method = RequestMethod.DELETE)
	public ResponseEntity<Proyecto> borrarCodigo(@RequestHeader("Authorization") String authorization,@PathVariable Long proyectoId) throws Throwable {
		User user=checkAutenticacion(authorization);
		Proyecto proyecto=existeProyectoYEstaAutorizado(proyectoId,user);
		proyectoService.borrarProyecto(proyectoId);
		return new ResponseEntity<Proyecto>(proyecto,HttpStatus.OK);
	}

	
	@RequestMapping(value = "/proyectos", method = RequestMethod.POST)
	public ResponseEntity<?> createProyecto(@RequestHeader("Authorization") String authorization,@Valid @RequestBody Proyecto proyecto) {
		User user=checkAutenticacion(authorization);
		proyecto = proyectoService.crear(proyecto);
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newProyectoUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proyecto.getId())
				.toUri();
		responseHeaders.setLocation(newProyectoUri);
		return new ResponseEntity<>(proyecto, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/proyectos/{proyectoId}/codigos", method = RequestMethod.POST)
	public ResponseEntity<?> createCodigoEnProyecto(@RequestHeader("Authorization") String authorization,@PathVariable Long proyectoId,@Valid @RequestBody Codigo codigo) {
		User user=checkAutenticacion(authorization);
		Proyecto proyecto=existeProyectoYEstaAutorizado(proyectoId,user);
		proyectoService.addCodigo(proyectoId, codigo);
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newProyectoUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(codigo.getId())
				.toUri();
		responseHeaders.setLocation(newProyectoUri);
		return new ResponseEntity<>(codigo, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/proyectos/{proyectoId}/codigos/{codigoId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> borrarCodigoEnProyecto(@RequestHeader("Authorization") String authorization,@PathVariable Long proyectoId,@PathVariable Long codigoId) throws Exception {
		User user=checkAutenticacion(authorization);
		Proyecto proyecto=existeProyectoYEstaAutorizado(proyectoId,user);
		Codigo codigo=existeCodigoYEstaAutorizado(codigoId,user);
		proyectoService.borrarCodigoProyecto(proyectoId, codigoId);
		return new ResponseEntity<>(codigo,HttpStatus.OK);
	}

	protected Proyecto existeProyecto(Long proyectoId) throws RecursoNoEncontradoException {
		Proyecto proyecto = proyectoService.getProyecto(proyectoId);
		if (proyecto == null) {
			throw new RecursoNoEncontradoException("No existe el proyecto con id " + proyectoId);
		}
		return proyecto;
	}
	protected User existeUser(Long userId) throws RecursoNoEncontradoException {
		User user = userService.get(userId);
		if (user == null) {
			throw new RecursoNoEncontradoException("No existe el usuario con id " + userId);
		}
		return user;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProyectoController.class, args);
	}
	protected Proyecto existeProyectoYEstaAutorizado(Long proyectoId,User user)
			throws RecursoNoEncontradoException {
		Proyecto proyecto = proyectoService.getProyecto(proyectoId);
		if (proyecto == null) {
			throw new RecursoNoEncontradoException(
					"No existe el recurso con id " + proyectoId);
		}
		if(!proyecto.getUser().getId().equals(user.getId())) 
			throw new AutorizacionNoValidaException("El usuario no tiene autorización para acceder al recurso");
             		
		return proyecto;
	}
	protected Codigo existeCodigoYEstaAutorizado(Long codigoId,User user)
			throws RecursoNoEncontradoException {
		Codigo codigo = codigoService.getCodigo(codigoId);
		if (codigo == null) {
			throw new RecursoNoEncontradoException(
					"No existe el recurso con id " + codigoId);
		}
		if(!codigo.getPropietarios().contains(user.getId())) 
			throw new AutorizacionNoValidaException("El usuario no tiene autorización para acceder al recurso");
             		
		return codigo;
	}

	private User checkAutenticacion(String authorization) {
		if(authorization==null || authorization.length()<1) throw new BadAutenticacionException("Error de autenticacion");
		byte[] decoded = Base64.decodeBase64(authorization.replace("Basic ", ""));
		User user=null;
		try {
			String[] userPass=new String(decoded, "UTF-8").split(":");
			user=userService.find(userPass[0], userPass[1]);
			if(user==null) throw new BadAutenticacionException("Error de autenticacion");

		}catch (Exception e){
			throw new BadAutenticacionException("Error de autenticacion");
		}
		return user;
	}

}
