package com.gl.surabhiChains2.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "tbl_bills")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Bill
{
	@Id
	@SequenceGenerator( name ="bill_sequence",
						sequenceName = "bill_sequence",
						allocationSize = 1
						)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "bill_sequence")
	private Integer billId;
	private Integer userId;
	@CreationTimestamp
	private LocalDate billDate;
	private Integer totalBill;
}
