package com.gl.surabhiChains2.models;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BillModel 
{
	private int billId;
	private LocalDate billDate;
	private List<OrderModel> billOrders;
	private Integer billTotal;
	private String userName;
}
