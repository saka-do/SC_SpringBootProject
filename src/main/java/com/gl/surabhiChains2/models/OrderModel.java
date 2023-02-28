package com.gl.surabhiChains2.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor @Builder
public class OrderModel
{
	private int orderId;
	private ItemModel item;
	private int quantity;
	private int total;
}
