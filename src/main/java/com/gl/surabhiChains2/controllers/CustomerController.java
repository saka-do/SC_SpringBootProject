package com.gl.surabhiChains2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl.surabhiChains2.entities.Cart;
import com.gl.surabhiChains2.entities.Item;
import com.gl.surabhiChains2.models.BillModel;
import com.gl.surabhiChains2.services.CartServiceImpl;
import com.gl.surabhiChains2.services.ItemServiceImpl;
import com.gl.surabhiChains2.services.SurabhiChainsServiceImpl;
import com.gl.surabhiChains2.services.UserService;
import com.gl.surabhiChains2.udExceptions.CartNotFoundException;
import com.gl.surabhiChains2.udExceptions.ItemNotFoundException;
import com.gl.surabhiChains2.utilities.UserDetailsImpl;

@RestController
@RequestMapping("/surabhichains.com/customer/api")
public class CustomerController
{
	
	@Autowired
	CartServiceImpl cartService;
	
	@Autowired
	SurabhiChainsServiceImpl surabhiService;
	
	@Autowired
	ItemServiceImpl itemService;
	
	@Autowired
	UserService userservice;
	/*Users Story 2. As a user I should be able to see all the 
	 * 	items available along with the price.
	 */
	
	@GetMapping("/items")
	public ResponseEntity<List<Item>> getAllItems()
	{
		List<Item> items = itemService.getAllItem();
		return new ResponseEntity<List<Item>>(items,HttpStatus.OK);
	}
	
	@GetMapping("/add_item_to_cart")
	public ResponseEntity<Cart> orderItem(@RequestParam int itemId,@RequestParam int quantity) throws ItemNotFoundException
	{
		Authentication holder = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl)holder.getPrincipal();
		
		Cart cart = cartService.addCart(user.getUserId(),itemId,quantity);
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);	
	}
	
	@GetMapping("/mycart")
	public ResponseEntity<List<Cart>> getCartItems()
	{
		Authentication holder = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl)holder.getPrincipal();
		
		List<Cart> cartItems = cartService.getUserItemsInCart(user.getUserId());
		return new ResponseEntity<List<Cart>>(cartItems,HttpStatus.OK);
	}
	
	@PutMapping("/mycart/{cartId}")
	public ResponseEntity<List<Cart>> updateItem(@PathVariable int cartId,
												@RequestParam("newQuantity") int newQuantity) throws CartNotFoundException
	{
		Authentication holder = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl)holder.getPrincipal();
		
		cartService.updateCart(cartId, user.getUserId(), newQuantity);
		List<Cart> cartItems = cartService.getUserItemsInCart(user.getUserId());
		return new ResponseEntity<List<Cart>>(cartItems,HttpStatus.OK);
	}
	
	@DeleteMapping("/mycart/{cartId}")
	public ResponseEntity<String> deleteItem(@PathVariable int cartId) throws CartNotFoundException
	{
		Authentication holder = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl)holder.getPrincipal();
		
		String msg = cartService.deleteCart(cartId, user.getUserId());
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	@GetMapping("/checkout")
	public ResponseEntity<BillModel> checkout() throws CartNotFoundException
	{
		Authentication holder = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl)holder.getPrincipal();
		
		BillModel bill = surabhiService.checkout(user.getUserId(),user.getUsername());
		
		return new ResponseEntity<BillModel>(bill,HttpStatus.OK);
	}
	
	@GetMapping("/my_orders")
	public ResponseEntity<List<BillModel>> getmyOrders()
	{
		Authentication holder = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl)holder.getPrincipal();
		
		List<BillModel> myOrders = surabhiService.getUserOrdersHistory(user.getUserId(),user.getUsername());
		return new ResponseEntity<List<BillModel>>(myOrders,HttpStatus.OK);
	}
	
}
