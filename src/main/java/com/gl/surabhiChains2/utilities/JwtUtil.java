package com.gl.surabhiChains2.utilities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;


/**
 * Generate Token using subject
 *  *Validate Token with tokenUsername and database username
 */

@Component
@Slf4j
public class JwtUtil
{
	@Value("${jwt.secretKey}")
	private String jwtSecretKey;
	
	@Value("${jwt.expireTimeMs}")
	private int jwtExpiteTime;
	
	//1.generate token
	public String generateJwtToken(Authentication authentication)
	{
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", userPrincipal.getUserId());
		
		return doGenerateToken(claims,userPrincipal.getUsername());
	}

   /*while creating the token -
	*1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	*2. Sign the JWT using the HS512 algorithm and secret key.
	*/
	private String doGenerateToken(Map<String, Object> claims, String username)
	{
		return Jwts.builder()
							.setClaims(claims)
							.setSubject(username)
							.setIssuedAt(new Date(System.currentTimeMillis()))
							.setIssuer("SurabhiChains")
							.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(jwtExpiteTime)))
							.signWith(SignatureAlgorithm.HS512,jwtSecretKey.getBytes())
							.compact();
	}
	
	//2.Read claims
	public Claims getClaims(String token) throws MalformedJwtException,
													SignatureException,
													ExpiredJwtException,
													UnsupportedJwtException,
													IllegalArgumentException
	{
		return Jwts.parser()
							.setSigningKey(jwtSecretKey.getBytes())
							.parseClaimsJws(token)
							.getBody();
				
	}
	
	//3.Get username
	public String getUserName(String token)
	{
		if(validateToken(token))
		{
			return getClaims(token).getSubject();
		}
		return "";
	}
	
//	//4.get Expiry Date
//	public Date getExpDate(String token)
//	{
//		return getClaims(token).getExpiration();
//	}
//	
//	//5.validate ExpDate
//	public boolean isTokenExp(String token)
//	{
//		Date expDate = getExpDate(token);
//		return expDate.before(new Date(System.currentTimeMillis()));
//	}
	
	//6.validate USer name from token,expdate
	public boolean validateToken(String authtoken)
	{
		
		try {
			getClaims(authtoken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}
}
