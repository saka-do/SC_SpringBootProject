package com.gl.surabhiChains2.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.surabhiChains2.entities.Bill;
import com.gl.surabhiChains2.entities.Cart;
import com.gl.surabhiChains2.entities.Item;
import com.gl.surabhiChains2.entities.Order;
import com.gl.surabhiChains2.models.BillModel;
import com.gl.surabhiChains2.models.ItemModel;
import com.gl.surabhiChains2.models.OrderModel;
import com.gl.surabhiChains2.repositories.BillRepository;
import com.gl.surabhiChains2.repositories.OrderRepository;
import com.gl.surabhiChains2.udExceptions.CartNotFoundException;


@Service
public class SurabhiChainsServiceImpl implements  SurabhiChainsService
{
	@Autowired
	CartServiceImpl cartService;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	BillRepository billRepo;
	
	
	/**
	 * receives one argument userId
	 * fetches userCart items from cart 
	 * saves in Orders , generates bill and deletes cartItems in cart
	 * @throws CartNotFoundException 
	 */
	
	@Override
	public BillModel checkout(int userId,String userName) throws CartNotFoundException
	{
		List<Cart> userItemsInCart = cartService.getUserItemsInCart(userId);
		
		Integer billTotal=0;
		List<Order> ordersToSave = new ArrayList<Order>();
		
		List<OrderModel> orderModels = new ArrayList<>();
		
		for(Cart cart : userItemsInCart)
		{
			billTotal+=cart.getTotal();
			ordersToSave.add(Order.builder()
									.item(cart.getItem())
									.quantity(cart.getQuantity())
									.total(cart.getTotal())
									.build()
									);
			
			
			cartService.deleteCart(cart.getCartId(), userId);
		}
		
		//save bill on bills table
		Bill checkoutBill = billRepo.saveAndFlush(Bill.builder()
														.totalBill(billTotal)
														.userId(userId)
														.build()
														);
		//save order in orders table
		for(Order order:ordersToSave)
		{
			order.setBill(checkoutBill);
			Order savedOrder = orderRepo.saveAndFlush(order);
			orderModels.add(convertToOrderDTO(savedOrder));
		}
		
		return BillModel.builder()
												.billId(checkoutBill.getBillId())
												.billDate(checkoutBill.getBillDate())
												.billOrders(orderModels)
												.billTotal(checkoutBill.getTotalBill())
												.userName(userName)
												.build();
	}
	
	/**
	 * See User previous Order history
	 */
	@Override
	public List<BillModel> getUserOrdersHistory(int userId,String userName)
	{
		List<Bill> userBills = billRepo.findAllByUserId(userId);
		List<BillModel> myOrders =  new ArrayList<BillModel>();
		for(Bill b : userBills)
		{
			List<Order> billOrders = orderRepo.findAllbyBillId(b.getBillId());
			List<OrderModel> billOrderModels = billOrders.stream().map((order)-> convertToOrderDTO(order)).collect(Collectors.toList());
			myOrders.add(BillModel.builder()
											.billId(b.getBillId())
											.billDate(b.getBillDate())
											.billOrders(billOrderModels)
											.billTotal(b.getTotalBill())
											.userName(userName)
											.build());
		}
		return myOrders;	
	}
	
	/**
	 * get Today bills,Admin part
	 */
	
	@Override
	public List<Bill> getTodayBills()
	{
		List<Bill> todayBills = billRepo.findAllByBillDate(LocalDate.now());
		return todayBills;
	}
	
	/**
	 * get current month sales
	 */
	@Override
	public List<Bill> getCurrentMonthBills()
	{
		List<Bill> currentMonthBills = billRepo.findBillsByCurrentMonth(LocalDate.now());
		return currentMonthBills;
	}
	
	public List<Order> getOdersbyBillId(int cBill)
	{
		// TODO Auto-generated method stub
		List<Order> orders = orderRepo.findAllbyBillId(cBill);
		System.out.println(orders);
		return orders;
	}
	
	private OrderModel convertToOrderDTO(Order order)
	{
		return OrderModel.builder()
													.item(convertToItemDTO(order.getItem()))
													.orderId(order.getOrderId())
													.quantity(order.getQuantity())
													.total(order.getTotal())
													.build();
	}
	
	private ItemModel convertToItemDTO(Item item)
	{
		return ItemModel.builder()
									.itemName(item.getItemName())
									.price(item.getPrice())
									.build();
	}
}
