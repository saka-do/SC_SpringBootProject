package com.gl.surabhiChains2.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gl.surabhiChains2.utilities.JwtUtil;
import com.gl.surabhiChains2.utilities.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter
{

	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		//read token from Auth Header
		String token = request.getHeader("Authorization");
		if(token!=null && jwtUtil.validateToken(token) )
		{
			//do validation
			String userName = jwtUtil.getUserName(token);
			log.info("User -> "+userName+" fetched from jwt token");
			if(userName!=null &&
							SecurityContextHolder.getContext().getAuthentication()==null)
			{
				UserDetailsImpl user = (UserDetailsImpl)userDetailsService.loadUserByUsername(userName);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
																											user,
																											null,
																											user.getAuthorities()
																											);
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					//final Object
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
			filterChain.doFilter(request, response);
		}
		
	}

