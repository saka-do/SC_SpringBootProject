package com.gl.surabhiChains2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.surabhiChains2.entities.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>
{

}
