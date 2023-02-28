package com.gl.surabhiChains2.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString(exclude = "bill")
@Entity @Table(name="tbl_orders")
public class Order 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="item_id",referencedColumnName = "itemId")
	private Item item;
	private Integer quantity;
	private Integer total;
	@ManyToOne
	@JoinColumn(name="bill_id",referencedColumnName = "billId")
	private Bill bill;
}
