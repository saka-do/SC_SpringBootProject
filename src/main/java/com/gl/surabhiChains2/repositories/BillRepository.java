package com.gl.surabhiChains2.repositories;

import java.time.LocalDate;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gl.surabhiChains2.entities.Bill;


@Repository
public interface BillRepository extends JpaRepository<Bill, Integer>
{
	List<Bill> findAllByUserId(int userId); 
	List<Bill> findAllByBillDate(LocalDate date);
	
	@Query("SELECT b FROM Bill b WHERE month(b.billDate)=month(?1)")
	List<Bill> findBillsByCurrentMonth(LocalDate date);
	
}
