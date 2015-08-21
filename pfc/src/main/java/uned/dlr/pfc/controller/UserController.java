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

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<User> borrarUser(@PathVariable Long userId) throws Throwable {
		existeUser(userId);
		userService.borrar(userId);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
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

	public static void main(String[] args) {
		SpringApplication.run(UserController.class, args);
	}

}