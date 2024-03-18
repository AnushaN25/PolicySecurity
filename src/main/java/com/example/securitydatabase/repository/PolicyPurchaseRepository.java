package com.example.securitydatabase.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.securitydatabase.entity.Policy;
import com.example.securitydatabase.entity.PolicyPurchase;



public interface PolicyPurchaseRepository extends JpaRepository<PolicyPurchase,Long>{


	List<PolicyPurchase> findByPolicy(Policy policy);
	

}
