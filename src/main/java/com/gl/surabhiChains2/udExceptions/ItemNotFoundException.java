package com.gl.surabhiChains2.udExceptions;

public class ItemNotFoundException extends Exception 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ItemNotFoundException(int itemId) 
	{
		super("Item with with Id "+itemId+" Not found");
	}
}
