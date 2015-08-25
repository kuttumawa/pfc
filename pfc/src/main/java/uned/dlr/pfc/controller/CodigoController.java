package uned.dlr.pfc.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import uned.dlr.pfc.model.Codigo;
import uned.dlr.pfc.model.User;
import uned.dlr.pfc.service.CodigoServiceIF;
import uned.dlr.pfc.service.UserServiceIF;

@SpringBootApplication
@RestController
public class CodigoController {

	String CODIGO = "var numero = prompt(\"Introduce un número y se mostrará su factorial\");"
			+ "\nvar resultado = 1;"
			+ "\n"
			+ "\nfor(var i=1; i<=numero; i++) {"
			+ "\n  resultado *= i;"
			+ "\n}" + "\nalert(resultado);";
	@Inject
	private CodigoServiceIF codigoService;
	@Inject
	private UserServiceIF userService;

	@RequestMapping(value = "/codigos/{codigoId}", method = RequestMethod.GET)
	public ResponseEntity<Codigo> getCodigo(@RequestHeader("Authorization") String authorization,@PathVariable Long codigoId) {
		User user=checkAutenticacion(authorization);
		Codigo codigo=existeCodigoYEstaAutorizado(codigoId,user);
		return new ResponseEntity<Codigo>(codigo, HttpStatus.OK);
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

	@RequestMapping(value = "/codigos/{codigoId}/js", method = RequestMethod.GET)
	public ResponseEntity<String> getCodigoJs(@PathVariable Long codigoId) {
		Codigo codigo = codigoService.getCodigo(codigoId);
		return new ResponseEntity<String>(codigo != null ? codigo.getCode()
				: "", HttpStatus.OK);
	}

	@RequestMapping(value = "/codigos/{codigoId}/test/js", method = RequestMethod.GET)
	public ResponseEntity<String> getTestCodigoJs(@PathVariable Long codigoId) {
		Codigo codigo = codigoService.getCodigo(codigoId);
		return new ResponseEntity<String>((codigo != null
				&& codigo.getTest() != null) ? codigo.getTest().getCode() : "",
				HttpStatus.OK);
	}

	@RequestMapping(value = "/codigos/{codigoId}/execute", method = RequestMethod.GET)
	public ResponseEntity<Resultado> executeCodigo(@RequestHeader("Authorization") String authorization,@PathVariable Long codigoId) {
		User user=checkAutenticacion(authorization);
		Codigo codigo=existeCodigoYEstaAutorizado(codigoId,user);
		Resultado resultado = new Resultado();
		resultado.setResultado(codigoService.ejecutar(codigoId));
		return new ResponseEntity<Resultado>(resultado, HttpStatus.OK);
	}

	@RequestMapping(value = "/codigos/{codigoId}", method = RequestMethod.DELETE)
	public ResponseEntity<Codigo> borrarCodigo(@RequestHeader("Authorization") String authorization,@PathVariable Long codigoId)
			throws Throwable {
		User user=checkAutenticacion(authorization);
		Codigo codigo=existeCodigoYEstaAutorizado(codigoId,user);
		codigoService.borrar(codigoId);
		return new ResponseEntity<Codigo>(HttpStatus.OK);
	}

	@RequestMapping(value = "/codigos/{codigoId}/ejecutar", method = RequestMethod.GET)
	public ResponseEntity<String> ejecutarCodigo(@RequestHeader("Authorization") String authorization,@PathVariable Long codigoId)
			throws Throwable {
		User user=checkAutenticacion(authorization);
		Codigo codigo=existeCodigoYEstaAutorizado(codigoId,user);
		String resultado = codigoService.ejecutar(codigoId);
		return new ResponseEntity<String>(resultado, HttpStatus.OK);
	}

	@RequestMapping(value = "/codigos/{codigoId}/revisar", method = RequestMethod.GET)
	public ResponseEntity<String> lintCodigo(@RequestHeader("Authorization") String authorization,@PathVariable Long codigoId)
			throws Throwable {
		User user=checkAutenticacion(authorization);
		Codigo codigo=existeCodigoYEstaAutorizado(codigoId,user);
		String resultado = codigoService.revisar(codigoId);
		return new ResponseEntity<String>(resultado, HttpStatus.OK);
	}

	@RequestMapping(value = "/codigos", method = RequestMethod.POST)
	public ResponseEntity<?> createCodigo(@RequestHeader("Authorization") String authorization,@Valid @RequestBody Codigo codigo) {
		User user=checkAutenticacion(authorization);
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newCodigoUri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(codigo.getId()).toUri();
		responseHeaders.setLocation(newCodigoUri);
		return new ResponseEntity<>(codigo, responseHeaders, HttpStatus.CREATED);
	}



	public static void main(String[] args) {
		SpringApplication.run(CodigoController.class, args);
	}

}
