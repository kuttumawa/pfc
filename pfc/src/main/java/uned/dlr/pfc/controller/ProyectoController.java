package uned.dlr.pfc.controller;

import java.net.URI;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import uned.dlr.pfc.model.Codigo;
import uned.dlr.pfc.model.Proyecto;
import uned.dlr.pfc.service.CodigoServiceIF;
import uned.dlr.pfc.service.ProyectoServiceIF;

@SpringBootApplication
@RestController
public class ProyectoController {

	
	@Inject
	private CodigoServiceIF codigoService;
	@Inject
	private ProyectoServiceIF proyectoService;

	@RequestMapping(value = "/proyectos/{proyectoId}", method = RequestMethod.GET)
	public ResponseEntity<Proyecto> getProyecto(@PathVariable Long proyectoId) {
		Proyecto proyecto =existeProyecto(proyectoId);
		return new ResponseEntity<Proyecto>(proyecto, HttpStatus.OK);
	}

	@RequestMapping(value = "/proyectos/{proyectoId}", method = RequestMethod.DELETE)
	public ResponseEntity<Codigo> borrarCodigo(@PathVariable Long proyectoId) throws Throwable {
		existeProyecto(proyectoId);
		proyectoService.borrarProyecto(proyectoId);
		return new ResponseEntity<Codigo>(HttpStatus.OK);
	}

	
	@RequestMapping(value = "/proyectos", method = RequestMethod.POST)
	public ResponseEntity<?> createProyecto(@Valid @RequestBody Proyecto proyecto) {
		proyecto = proyectoService.crear(proyecto);
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newProyectoUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proyecto.getId())
				.toUri();
		responseHeaders.setLocation(newProyectoUri);
		return new ResponseEntity<>(proyecto, responseHeaders, HttpStatus.CREATED);
	}

	protected Proyecto existeProyecto(Long proyectoId) throws RecursoNoEncontradoException {
		Proyecto proyecto = proyectoService.getProyecto(proyectoId);
		if (proyecto == null) {
			throw new RecursoNoEncontradoException("No existe el proyecto con id " + proyectoId);
		}
		return proyecto;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProyectoController.class, args);
	}

}
