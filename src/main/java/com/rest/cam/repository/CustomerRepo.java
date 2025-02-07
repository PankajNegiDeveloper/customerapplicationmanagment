package com.rest.cam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.cam.entity.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
