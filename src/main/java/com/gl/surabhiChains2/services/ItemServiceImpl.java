package com.gl.surabhiChains2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.surabhiChains2.entities.Item;
import com.gl.surabhiChains2.models.ItemModel;
import com.gl.surabhiChains2.repositories.ItemRepository;
import com.gl.surabhiChains2.udExceptions.ItemNotFoundException;


@Service
public class ItemServiceImpl implements ItemService
{
	@Autowired
	ItemRepository itemRepository;

	//add Item to items
	@Override
	public Item addItem(Item item)
	{
		return itemRepository.saveAndFlush(item);
	}

	//get Item by itemId
	@Override
	public Item getItemById(int itemId)throws ItemNotFoundException
	{
		Optional<Item> optItem=itemRepository.findById(itemId);
		//TODO: do for empty item
		if(optItem.isEmpty())
				throw new  ItemNotFoundException(itemId);
		return optItem.get();
	}

	//update Item price
	@Override
	public Item updateItem(int itemId, ItemModel newItemDetails) throws ItemNotFoundException
	{
		Item item=getItemById(itemId);
		item.setPrice(newItemDetails.getPrice());
		item.setItemName(newItemDetails.getItemName());
		return itemRepository.saveAndFlush(item);
	}

	//delete item by itemId
	@Override
	public String deleteItem(int itemId)throws ItemNotFoundException
	{
		getItemById(itemId);
		itemRepository.deleteById(itemId);
		return "DELETED";
	}

	//get all items
	@Override
	public List<Item> getAllItem()
	{
		return itemRepository.findAll();
	}

}
