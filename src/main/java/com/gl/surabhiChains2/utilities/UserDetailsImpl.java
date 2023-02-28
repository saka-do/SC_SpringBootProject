package com.gl.surabhiChains2.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gl.surabhiChains2.entities.User;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor @ToString
public class UserDetailsImpl implements UserDetails
{
	
	/**
	 * Implementing userDetails to override default spring User
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private String userName;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public static UserDetailsImpl build(User user)
	{
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
		return new UserDetailsImpl(
									user.getUid(),
									user.getUserName(),
									user.getPassword(),
									authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getUserId()
	{
		return this.userId;
	}

}
