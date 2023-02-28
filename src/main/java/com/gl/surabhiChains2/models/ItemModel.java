package com.gl.surabhiChains2.models;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ItemModel
{
	private String itemName;
	private Integer price;
}
