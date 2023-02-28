package com.gl.surabhiChains2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Table(name = "tbl_user_carts")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
@ToString(exclude = "userId")
public class Cart
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartId;
	@ManyToOne
	private Item item;
	private Integer quantity;
	private Integer total;
	@Column(name="user_id")
	private Integer userId;
	
}
