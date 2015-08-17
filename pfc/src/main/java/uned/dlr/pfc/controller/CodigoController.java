package uned.dlr.pfc.controller;

import java.net.URI;

import javax.inject.Inject;

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
import uned.dlr.pfc.service.CodigoServiceIF;

@SpringBootApplication
@RestController
public class CodigoController {
	
	String CODIGO="function myFunction() {var myGirls = [\"Cecilie\", \"Lone\"];\nvar myBoys = [\"Emil\", \"Tobias\", \"Linus\"];\nvar myChildren = myGirls.concat(myBoys);\ndocument.getElementById(\"demo\").innerHTML = myChildren;";
	@Inject
	private CodigoServiceIF codigoService;
	
	@RequestMapping(value="/codigos/{codigoId}",method=RequestMethod.GET)
	public ResponseEntity<Codigo> getCodigo(@PathVariable Long codigoId){
		Codigo c=new Codigo("PruebaDLR");
		c.setCode(CODIGO);
		codigoService.crear(c);
		Codigo codigo=codigoService.getCodigo(codigoId);
		return new ResponseEntity<Codigo>(codigo,HttpStatus.OK);
	}
	
	@RequestMapping(value="/codigos/{codigoId}",method=RequestMethod.DELETE)
	public ResponseEntity<Codigo> borrarCodigo(@PathVariable Long codigoId) throws Throwable{
		codigoService.borrar(codigoId);
		return new ResponseEntity<Codigo>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/codigos/{codigoId}/ejecutar",method=RequestMethod.GET)
	public ResponseEntity<String> ejecutarCodigo(@PathVariable Long codigoId) throws Throwable{
		String resultado=codigoService.ejecutar(codigoId);
		return new ResponseEntity<String>(resultado,HttpStatus.OK);
	}
	
	@RequestMapping(value="/codigos",	method=RequestMethod.POST) 
	public	ResponseEntity<?>	createCodigo(@RequestBody	Codigo	codigo)	{	
		codigo	=	codigoService.crear(codigo);	
	    HttpHeaders	responseHeaders	=	new	HttpHeaders();						
	    URI	newCodigoUri	=	ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(codigo.getId()).toUri();						
	    responseHeaders.setLocation(newCodigoUri);								
	    return	new	ResponseEntity<>(null,	responseHeaders,HttpStatus.CREATED); 
	}
	
	public static void main(String[] args){
		SpringApplication.run(CodigoController.class,args);
	}
	


}
