package com.gl.surabhiChains2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gl.surabhiChains2.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>
{
	@Query(value="SELECT * FROM tbl_orders WHERE bill_id=?1",nativeQuery = true)
	List<Order> findAllbyBillId(int billId);

}
