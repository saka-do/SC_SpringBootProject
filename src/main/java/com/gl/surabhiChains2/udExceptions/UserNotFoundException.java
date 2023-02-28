package com.gl.surabhiChains2.udExceptions;

public class UserNotFoundException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(String userName)
	{
		super(userName+"Not Found");
	}
	public UserNotFoundException(int userId)
	{
		super(userId+"Not Found");
	}

}
