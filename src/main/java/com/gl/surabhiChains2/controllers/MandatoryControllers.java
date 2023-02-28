package com.gl.surabhiChains2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.surabhiChains2.entities.User;
import com.gl.surabhiChains2.models.AuthRequest;
import com.gl.surabhiChains2.models.AuthResponse;
import com.gl.surabhiChains2.services.UserServiceImpl;
import com.gl.surabhiChains2.utilities.JwtUtil;

@RestController
public class MandatoryControllers
{	
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) 
	{
		Authentication authentication = authenticationManager.authenticate(
								new UsernamePasswordAuthenticationToken(authRequest.getUserName(),
																		authRequest.getPassword())
								);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtil.generateJwtToken(authentication);
		AuthResponse jwtResponse = AuthResponse.builder()
													.jwToken(jwt)
													.message("SUCCESS").build();
		return new ResponseEntity<AuthResponse>(jwtResponse,HttpStatus.OK);			
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User newUser)
	{
		if(newUser.getPassword().length()==0 || newUser.getUserName().length()==0 )
		{
			return ResponseEntity.badRequest().body("Error : UserName and Password should not be empty");
		}
		
		if(userService.existsByUserName(newUser.getUserName()))
		{
			return ResponseEntity.badRequest().body("Error : "+newUser.getUserName()+" Already Taken");
		}
		
		// Normal User can't be registered as admin;
		newUser.setRole("CUSTOMER");
		
		newUser.setPassword(pwdEncoder.encode(newUser.getPassword()));
		User savedUser = userService.registerUser(newUser);
		return new ResponseEntity<String>("Registered with User ID : "+savedUser.getUid()+
										  "Registered Role : "+savedUser.getRole(),
										  HttpStatus.CREATED);
	}

}
