package com.test.maybank.demo.exception;


import com.test.maybank.demo.dto.CommonRs;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private static final String EXCEPTION="Exception : ";

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> globalException(Exception exception) {
		return HandleGlobalAndConstraint(exception);
	}
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> constraintViolationException(Exception exception) {
		return HandleGlobalAndConstraint(exception);
	}

	private ResponseEntity<Object> HandleGlobalAndConstraint(Exception exception){
		log.error(EXCEPTION, exception);
		String message = (exception instanceof NullPointerException) ? exception.getLocalizedMessage() : exception.getMessage();
		return new ResponseEntity<>(new CommonRs<Object>(HttpStatus.BAD_REQUEST.value(), message), HttpStatus.BAD_REQUEST);
	}
}


