package uned.dlr.pfc.controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BadOperationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BadOperationException() {
	}

	public BadOperationException(String message) {
		super(message);
	}

	public BadOperationException(String message, Throwable cause) {
		super(message, cause);
	}
}