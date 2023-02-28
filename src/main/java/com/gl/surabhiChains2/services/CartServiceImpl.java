package com.gl.surabhiChains2.services;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.surabhiChains2.entities.Cart;
import com.gl.surabhiChains2.entities.Item;
import com.gl.surabhiChains2.repositories.CartRepository;
import com.gl.surabhiChains2.udExceptions.CartNotFoundException;
import com.gl.surabhiChains2.udExceptions.ItemNotFoundException;



@Service
public class CartServiceImpl
{
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ItemService itemService;
	
	//Add Item to cart
	public Cart addCart(int userId,int itemId,int quantity) throws ItemNotFoundException
	{
		Item item = itemService.getItemById(itemId);
		int cartTotal=item.getPrice()*quantity;
		
		Cart cartItem = Cart.builder()
									.item(item)
									.quantity(quantity)
									.total(cartTotal)
									.userId(userId)
									.build();
		
		return cartRepository.saveAndFlush(cartItem);
	}
	
	//Get CartItem by userId and cartID
	public Cart getCartbyIdAndUserId(int cartId,int userId) throws CartNotFoundException
	{
		return cartRepository.findByCartIdAndUserId(cartId, userId).orElseThrow(() -> new CartNotFoundException(cartId));
	}
	
	
	//Update cart items quanity
	public Cart updateCart(int cartId,int userId,int newQuantity) throws CartNotFoundException
	{
		Cart cartItem = getCartbyIdAndUserId(cartId, userId);
		cartItem.setQuantity(newQuantity);
		cartItem.setTotal(cartItem.getItem().getPrice()*newQuantity);
		return cartRepository.saveAndFlush(cartItem);
	}
	
	//Delete cartItem of user
	public String deleteCart(int cartId,int userId) throws CartNotFoundException
	{
		getCartbyIdAndUserId(cartId, userId);
		cartRepository.deleteById(cartId);
		return "DELETE,SUCCESS";
	}

	//get User items in cart
	public List<Cart> getUserItemsInCart(int userId)
	{
		List<Cart> cartItems= cartRepository.findAllByUserId(userId);
		return cartItems;
	}
}
