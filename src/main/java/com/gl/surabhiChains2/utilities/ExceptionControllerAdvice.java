package com.gl.surabhiChains2.utilities;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gl.surabhiChains2.models.ErrorModel;
import com.gl.surabhiChains2.udExceptions.CartNotFoundException;
import com.gl.surabhiChains2.udExceptions.ItemNotFoundException;

@RestControllerAdvice
public class ExceptionControllerAdvice
{
	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<ErrorModel> itemNotFoundExceptionHandler(ItemNotFoundException e)
	{
		ErrorModel error=new ErrorModel();
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setErrorMessage(e.getMessage());
		error.setTimeStamp(LocalDateTime.now());
		
		return new ResponseEntity<ErrorModel>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CartNotFoundException.class)
	public ResponseEntity<ErrorModel> cartNotFoundExceptionHandler(CartNotFoundException e)
	{
		ErrorModel error=new ErrorModel();
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setErrorMessage(e.getMessage());
		error.setTimeStamp(LocalDateTime.now());
		
		return new ResponseEntity<ErrorModel>(error,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorModel> UsernameNotFoundExceptionHandler(Exception e)
	{
		ErrorModel error=new ErrorModel();
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setErrorMessage(e.getMessage());
		error.setTimeStamp(LocalDateTime.now());
		
		return new ResponseEntity<ErrorModel>(error,HttpStatus.NOT_FOUND);
	}
}
