package com.example.securitydatabase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.securitydatabase.entity.Policy;



@Repository
public interface PolicyRepository extends JpaRepository<Policy,Long>{

	//List<Policy> findBypolicyId(long policyId);

}
