package com.example.securitydatabase.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.securitydatabase.entity.Policy;
import com.example.securitydatabase.entity.PolicyDocument;



@Repository
public interface PolicyDocumentRepository extends JpaRepository<PolicyDocument,Long> {

	

}
