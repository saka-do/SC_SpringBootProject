package com.gl.surabhiChains2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl.surabhiChains2.entities.Bill;
import com.gl.surabhiChains2.entities.Item;
import com.gl.surabhiChains2.entities.User;
import com.gl.surabhiChains2.models.BillModel;
import com.gl.surabhiChains2.models.ItemModel;
import com.gl.surabhiChains2.models.UserInfo;
import com.gl.surabhiChains2.services.ItemService;
import com.gl.surabhiChains2.services.SurabhiChainsService;
import com.gl.surabhiChains2.services.UserService;
import com.gl.surabhiChains2.udExceptions.ItemNotFoundException;
import com.gl.surabhiChains2.udExceptions.UserNotFoundException;

@RestController
@RequestMapping("/surabhichains.com/admin/api")
public class AdminController 
{
	@Autowired
	UserService userService;
	
	@Autowired
	SurabhiChainsService surabhiService;
	
	@Autowired
	ItemService itemsMenu;
	
	@Autowired
	BCryptPasswordEncoder pwdEncoder;
	
	/**
	 * User Crud Operations by Admin (Admin Story -> 2)
	 * 	A2.1 -> Create user with deafault password as his userName
	 * A2.2 -> Read user Details
	 *  A2.3 -> Update user role
	 *  A2.4 -> Delete User
	 *  A2.5 -> Get all UserDetails
	 */
	
	@PostMapping("/user")
	public ResponseEntity<?> addUser(@RequestBody User newUser)
	{
		if(newUser.getUserName().length() == 0 || newUser == null)
			return ResponseEntity.badRequest().body("Error : UserName Should not be empty");
		
		if( userService.existsByUserName(newUser.getUserName()) )
			return ResponseEntity.badRequest().body("Error : "+newUser.getUserName()+" Already registered");
		
		if(newUser.getRole() != null && newUser.getRole().equalsIgnoreCase("admin"))
			newUser.setRole("ADMIN");
		else
			newUser.setRole("CUSTOMER");
		
		newUser.setPassword( pwdEncoder.encode(newUser.getUserName()));
		
		User savedUser = userService.registerUser(newUser);
		UserInfo userModel = UserInfo.builder()
																	.userId(savedUser.getUid())
																	.userName(savedUser.getUserName())
																	.email(savedUser.getEmail())
																	.role(savedUser.getRole())
																	.message("Successfully Registerd Details")
																	.build();
		return new ResponseEntity<UserInfo>(userModel,HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userName}")
	public ResponseEntity<UserInfo>  getUser(@PathVariable String userName) throws UserNotFoundException
	{
		User foundUser = userService.getUserByUserName(userName);
		UserInfo userModel = UserInfo.builder()
																.userId(foundUser.getUid())
																.userName(foundUser.getUserName())
																.email(foundUser.getEmail())
																.role(foundUser.getRole())
																.message("User Details")
																.build();
		return ResponseEntity.ok(userModel);
	}
	
	@PutMapping("user/{userName}")
	public ResponseEntity<String> updateUser(@PathVariable String userName,
																		@RequestParam String newRole) throws UserNotFoundException
	{
		User updatedUser = userService.updateUser(userName,newRole);
		
		return ResponseEntity.ok(updatedUser.getUserName()+" updated Role : "+updatedUser.getRole());
		
	}
	
	@DeleteMapping("user/{userName}")
	public ResponseEntity<String> deleteUser(@PathVariable String userName) throws UserNotFoundException
	{
		userService.deleteUser(userName);
		return ResponseEntity.ok(userName+" Deleted Successfully");
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserInfo>> getAllUsers()
	{
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	
	/**
	 * 	 Admin Operations related to sales (Admin Story -> 3,4,5)
	 * 		A3 ->Admin able to see all the bills generated today
	 * 		A4 ->Admin able to see the total sale from this month
	 * 		A5 ->Admin able to see all the Orders done by a specific user
	 */
	
	@GetMapping("/today_bills")
	public ResponseEntity<List<Bill>> getTodayBills()
	{
		List<Bill> todayBills = surabhiService.getTodayBills();
		 return new ResponseEntity<List<Bill>>(todayBills,HttpStatus.OK);
	}
	
	@GetMapping("/month_sales")
	public ResponseEntity<List<Bill>> getCurrentMonthSales()
	{
		List<Bill> currentMonthBills = surabhiService.getCurrentMonthBills();
		return ResponseEntity.ok(currentMonthBills);
	}
	
	@GetMapping("/orders/{userId}")
	public ResponseEntity<List<BillModel>> getUserOrders(@PathVariable int  userId)
	{
		User user = userService.getUserById(userId);
		return ResponseEntity.ok(surabhiService.getUserOrdersHistory(userId,user.getUserName()));
	}
	
	/**
	 *  Items in Surabhi Menu Crud operations (Admin Story ->6)
	 *  A6.1 -> Create Item in Menu (save an item in Repository)
	 *  A6.2 -> Read Item details
	 *  A6.3 ->	Update Item price
	 *  A6.4 -> Delete Item
	 *  A6.5 -> Get All Items
	 */
	
	@PostMapping("/items/item")
	public ResponseEntity<?> addItem(@RequestBody Item item)
	{
		if(item.getItemName().isEmpty())
		{
			return new ResponseEntity<String>("Error ! provide valid name for item",HttpStatus.BAD_REQUEST);
		}
		
		Item savedItem = itemsMenu.addItem(item);
		return new ResponseEntity<Item>(savedItem,HttpStatus.CREATED);
	}
	
	@PutMapping("/items/{itemId}")
	public ResponseEntity<?> updateItem(@PathVariable int itemId,@RequestBody ItemModel newItemDetails) throws ItemNotFoundException
	{
		Item updatedItem = itemsMenu.updateItem(itemId, newItemDetails);
		return new ResponseEntity<Item>(updatedItem,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/items/{itemId}")
	public ResponseEntity<String> deleteItem(@PathVariable int itemId) throws ItemNotFoundException
	{
		itemsMenu.deleteItem(itemId);
		return ResponseEntity.ok("Item with Id : "+itemId+" DELETED");
	}
	
	@GetMapping("/items/{itemId}")
	public ResponseEntity<Item> getItem(@PathVariable int itemId) throws ItemNotFoundException
	{
		Item item = itemsMenu.getItemById(itemId);
		return new ResponseEntity<Item>(item,HttpStatus.OK);
	}
	
	@GetMapping("/items")
	public ResponseEntity<List<Item>> getAllItems()
	{
		List<Item> items = itemsMenu.getAllItem();
		return ResponseEntity.ok(items);
	}
}
