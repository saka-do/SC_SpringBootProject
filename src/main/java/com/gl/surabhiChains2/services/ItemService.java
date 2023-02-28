package com.gl.surabhiChains2.services;

import java.util.List;

import com.gl.surabhiChains2.entities.Item;
import com.gl.surabhiChains2.models.ItemModel;
import com.gl.surabhiChains2.udExceptions.ItemNotFoundException;


public interface ItemService {

	//add Item to items
	Item addItem(Item item);

	//get Item by itemId
	Item getItemById(int itemId) throws ItemNotFoundException;

	//update Item
	Item updateItem(int itemId, ItemModel newItemDetails)throws ItemNotFoundException;

	//delete item by itemId
	String deleteItem(int itemId) throws ItemNotFoundException;

	//get all items
	List<Item> getAllItem();


}