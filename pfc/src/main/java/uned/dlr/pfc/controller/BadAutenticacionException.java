package uned.dlr.pfc.controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BadAutenticacionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BadAutenticacionException() {
	}

	public BadAutenticacionException(String message) {
		super(message);
	}

	public BadAutenticacionException(String message, Throwable cause) {
		super(message, cause);
	}
}