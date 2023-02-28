package com.gl.surabhiChains2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.surabhiChains2.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> 
{
	Optional<Cart> findByCartIdAndUserId(int cartId,int userId);

	List<Cart> findAllByUserId(int userId);
	
//	@Query("SELECT c.item,c.quantity,c.total FROM  Cart c WHERE c.userId = ?1")
//	List<Object[]> findAllByUserId(int userId);
	
}
