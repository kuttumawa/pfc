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

import uned.dlr.pfc.model.User;
import uned.dlr.pfc.service.UserServiceIF;



@SpringBootApplication
@RestController
public class UserController {

	@Inject
	private UserServiceIF userService;

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable Long userId) {
		User user = existeUser(userId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/login", method = RequestMethod.GET)
	public ResponseEntity<User> getUserConPassword(@RequestHeader("Authorization") String authorization) {
		User u = checkAutenticacion(authorization);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers(@RequestParam(required=false) String term,@RequestParam(required=false) String nombre) {
		String filtro="";
		if(nombre!=null) filtro=nombre;
		else filtro=(term!=null?term:"");
		List<User> users = userService.getAll(filtro);
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<User> borrarUser(@PathVariable Long userId) throws Throwable {
		existeUser(userId);
		userService.borrar(userId);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
		if(userService.existeUser(user.getNombre())) throw new BadAutenticacionException("El usuario ya existe");
		user = userService.crear(user);
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newUserUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
				.toUri();
		responseHeaders.setLocation(newUserUri);
		return new ResponseEntity<>(user, responseHeaders, HttpStatus.CREATED);
	}

	protected User existeUser(Long userId) throws RecursoNoEncontradoException {
		User user = userService.get(userId);
		if (user == null) {
			throw new RecursoNoEncontradoException("No existe el usuario con id " + userId);
		}
		return user;
	}
	protected User existeUserConPassword(String user,String pass) throws RecursoNoEncontradoException {
		User u = userService.find(user,pass);
		if (u == null) {
			throw new RecursoNoEncontradoException("No existe  usuario " + user);
		}
		return u;
	}

	public static void main(String[] args) {
		SpringApplication.run(UserController.class, args);
	}
	private User checkAutenticacion(String authorization) {
		if(authorization==null || authorization.length()<1) throw new BadAutenticacionException();
		byte[] decoded = Base64.decodeBase64(authorization.replace("Basic ", ""));
		User user=null;
		try {
			String[] userPass=new String(decoded, "UTF-8").split(":");
			user=userService.find(userPass[0], userPass[1]);
			if(user==null) throw new BadAutenticacionException("Usuario o password no válido");

		}catch (Exception e){
			throw new BadAutenticacionException();
		}
		return user;
	}

}
