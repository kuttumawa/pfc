package uned.dlr.pfc.controller;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<Proyecto> getProyecto(@PathVariable Long proyectoId) {
		Proyecto proyecto =existeProyecto(proyectoId);
		return new ResponseEntity<Proyecto>(proyecto, HttpStatus.OK);
	}
	@RequestMapping(value = "/proyectos", method = RequestMethod.GET)
	public ResponseEntity<List<Proyecto>> getProyectos(@RequestParam("userId") Long userId) {
		List<Proyecto> proyectos = proyectoService.getProyectosUser(userId);
		return new ResponseEntity<List<Proyecto>>(proyectos, HttpStatus.OK);
	}

	@RequestMapping(value = "/proyectos/{proyectoId}", method = RequestMethod.DELETE)
	public ResponseEntity<Codigo> borrarCodigo(@PathVariable Long proyectoId) throws Throwable {
		existeProyecto(proyectoId);
		proyectoService.borrarProyecto(proyectoId);
		return new ResponseEntity<Codigo>(HttpStatus.OK);
	}

	
	@RequestMapping(value = "/proyectos", method = RequestMethod.POST)
	public ResponseEntity<?> createProyecto(@Valid @RequestBody Proyecto proyecto) {
		existeUser(proyecto.getUser().getId());
		proyecto = proyectoService.crear(proyecto);
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newProyectoUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proyecto.getId())
				.toUri();
		responseHeaders.setLocation(newProyectoUri);
		return new ResponseEntity<>(proyecto, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/proyectos/{proyectoId}/codigos", method = RequestMethod.POST)
	public ResponseEntity<?> createCodigoEnProyecto(@PathVariable Long proyectoId,@Valid @RequestBody Codigo codigo) {
		proyectoService.addCodigo(proyectoId, codigo);
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newProyectoUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(codigo.getId())
				.toUri();
		responseHeaders.setLocation(newProyectoUri);
		return new ResponseEntity<>(codigo, responseHeaders, HttpStatus.CREATED);
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

}
