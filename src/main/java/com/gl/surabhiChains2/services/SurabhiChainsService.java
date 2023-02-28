package com.gl.surabhiChains2.services;

import java.util.List;

import com.gl.surabhiChains2.entities.Bill;
import com.gl.surabhiChains2.models.BillModel;
import com.gl.surabhiChains2.udExceptions.CartNotFoundException;


public interface SurabhiChainsService {

	/**
	 * receives one argument userId
	 * fetches userCart items from cart 
	 * saves in Orders , generates bill and deletes cartItems in cart
	 *  
	 */

	BillModel checkout(int userId,String userName) throws CartNotFoundException;

	/**
	 * See User previous Order history
	 */

	List<BillModel> getUserOrdersHistory(int userId,String userName);

	/**
	 * get Today bills,Admin part
	 */

	List<Bill> getTodayBills();

	/**
	 * get current month sales
	 */
	List<Bill> getCurrentMonthBills();

}