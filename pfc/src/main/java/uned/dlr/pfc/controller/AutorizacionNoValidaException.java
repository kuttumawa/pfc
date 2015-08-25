package uned.dlr.pfc.controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AutorizacionNoValidaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AutorizacionNoValidaException() {
	}

	public AutorizacionNoValidaException(String message) {
		super(message);
	}

	public AutorizacionNoValidaException(String message, Throwable cause) {
		super(message, cause);
	}
}