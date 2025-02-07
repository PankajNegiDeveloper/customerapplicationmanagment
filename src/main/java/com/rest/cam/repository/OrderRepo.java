package com.rest.cam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rest.cam.entity.Customer;
import com.rest.cam.entity.Order;

import jakarta.transaction.Transactional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

	List<Order> findByCustomer(Customer customer);

	@Modifying
	@Transactional
	@Query("Delete from Order of WHERE of.orderid = :orderid")
	void deleteOrderByOrderId(Long orderid);

}
