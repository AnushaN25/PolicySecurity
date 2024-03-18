package com.example.securitydatabase.controller;

import java.time.LocalDate;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.securitydatabase.entity.Policy;
import com.example.securitydatabase.entity.PolicyDocument;
import com.example.securitydatabase.entity.PolicyPurchase;
import com.example.securitydatabase.exception.PolicyCreationException;
import com.example.securitydatabase.exception.PolicyDocumentNotFoundException;
import com.example.securitydatabase.exception.PolicyNotFoundException;
import com.example.securitydatabase.service.PolicyService;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PolicyController {

    @Autowired
    private PolicyService policyService;
    
    @PostMapping("/createPolicy")
    public Policy createPolicy(@RequestBody Policy policy) throws PolicyCreationException {
        return policyService.createPolicy(policy);
    }
    
    @DeleteMapping("deletePolicy/{policyId}")
    public void deletePolicy(@PathVariable long policyId) throws PolicyNotFoundException {
        policyService.deletePolicy(policyId);
    }

    @PutMapping("/updatePolicy/{policyId}")
    public Policy updatePolicy(@PathVariable long policyId, @RequestBody Policy policyDetails) throws PolicyNotFoundException {
        return policyService.updatePolicy(policyId, policyDetails)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found with id: " + policyId));
    }

    @GetMapping("/getAllPolicies")
    public List<Policy> getAllPolicies() {
        return policyService.getAllPolicies();
    }
    
    @GetMapping("/getPolicyById/{policyId}")
    public Optional<Policy> getPolicyById(@PathVariable long policyId) throws PolicyNotFoundException {
        return policyService.getPolicyById(policyId);
    }
    
    @PostMapping("/{policyId}/documents")
    public PolicyDocument attachDocumentToPolicy(@PathVariable Long policyId,@RequestBody PolicyDocument policyDocument
    ) throws PolicyNotFoundException {
        return policyService.attachDocumentToPolicy(policyId, policyDocument);
    }
    
    
   @GetMapping("/getDocumentBypolicyId/{policyId}")
    public PolicyDocument getDocumentBypolicyId(@PathVariable long policyId) throws PolicyDocumentNotFoundException {
		return policyService.getDocumentByPolicyId(policyId);
	   
   }
    		
    @PostMapping("/purchasePolicy/{policyId}")
    public PolicyPurchase purchasePolicy(@PathVariable long policyId,
                                         @RequestBody PolicyPurchase purchase) throws PolicyNotFoundException {
        return policyService.purchasePolicy(policyId, purchase.getPurchaseDate());
    }
    @GetMapping("/getPurchasesByPolicyId/{policyId}")
	 public List<PolicyPurchase> getPurchasesByPolicyId(@PathVariable long policyId) throws PolicyNotFoundException {
	     return policyService.getPurchasesByPolicyId(policyId);
	 }

   
}

