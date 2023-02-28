package com.gl.surabhiChains2.udExceptions;

public class CartNotFoundException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public  CartNotFoundException(int cartId)
	{
		super("Cart with Id "+cartId+" Not found");
	}

}
