package com.example.securitydatabase.service;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.securitydatabase.entity.Policy;
import com.example.securitydatabase.entity.PolicyDocument;
import com.example.securitydatabase.entity.PolicyPurchase;
import com.example.securitydatabase.exception.PolicyCreationException;
import com.example.securitydatabase.exception.PolicyNotFoundException;
import com.example.securitydatabase.repository.PolicyDocumentRepository;
import com.example.securitydatabase.repository.PolicyPurchaseRepository;
import com.example.securitydatabase.repository.PolicyRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PolicyService{

    private static final Logger logger = LoggerFactory.getLogger(PolicyService.class);

    @Autowired
    private PolicyRepository policyRepo;
    
    @Autowired
    private PolicyDocumentRepository policyDocRepo;
    
    @Autowired
    private PolicyPurchaseRepository policyPurchaseRepo;

	
	public Policy createPolicy(Policy policy)throws PolicyCreationException {
		 logger.info("Creating new policy with details: {}", policy);
		    Policy createdPolicy = policyRepo.save(policy);
		    if (createdPolicy == null) {
		        String errorMessage = "Failed to create policy: returned policy object is null";
		        logger.error(errorMessage);
		        throw new PolicyCreationException(errorMessage);
		    }
		    return createdPolicy;
	}
	
	
	public boolean deletePolicy(long policyId)throws PolicyNotFoundException {
		logger.info("Deleting policy with ID: {}", policyId);
        if (policyRepo.existsById(policyId)) {
            policyRepo.deleteById(policyId);
            return true;
        } else {
            logger.error("Policy with ID {} not found", policyId);
            throw new PolicyNotFoundException("Policy with ID " + policyId + " not found");
        }
    }
	

	
	public Optional<Policy> updatePolicy(long policyId, Policy policyDetails) throws PolicyNotFoundException  {
		logger.info("Updating policy with ID: {}", policyId);
        Optional<Policy> optionalPolicy = policyRepo.findById(policyId);
        if (optionalPolicy.isPresent()) {
            Policy existingPolicy = optionalPolicy.get();
            existingPolicy.setPolicyName(policyDetails.getPolicyName());
            // Update existing policy attributes with the new details
            existingPolicy.setPolicyType(policyDetails.getPolicyType());
            existingPolicy.setMaxNoOfYears(policyDetails.getMaxNoOfYears());
            existingPolicy.setPremiumRate(policyDetails.getPremiumRate());
            existingPolicy.setMaxSumAssured(policyDetails.getMaxSumAssured());
            return Optional.of(policyRepo.save(existingPolicy));
        } else {
            logger.error("Policy with ID {} not found", policyId);
            throw new PolicyNotFoundException("Policy with ID " + policyId + " not found");
        }
	}

	

	
	public List<Policy> getAllPolicies() {
		 logger.info("Fetching all policies");
	        return policyRepo.findAll();
	}

	
	public Optional<Policy> getPolicyById(long policyId) throws PolicyNotFoundException {
		logger.info("Fetching policy with ID: {}", policyId);
        Optional<Policy> policyOptional = policyRepo.findById(policyId);
        if (policyOptional.isPresent()) {
            return policyOptional;
        } else {
            logger.error("Policy with ID {} not found", policyId);
            throw new PolicyNotFoundException("Policy with ID " + policyId + " not found");
        }
	}

	
	    public PolicyDocument attachDocumentToPolicy(long policyId, PolicyDocument policyDocument) throws PolicyNotFoundException {
	        logger.info("Attaching document {} to policy with ID {}", policyDocument.getDocumentId(), policyId);

	        // Check if the policy exists
	        Policy policy = policyRepo.findById(policyId)
	                .orElseThrow(() -> {
	                    String errorMessage = String.format("Policy with ID %d not found", policyId);
	                    logger.error(errorMessage);
	                    return new PolicyNotFoundException(errorMessage);
	                });

	        // Set the policy for the document
	        policyDocument.setPolicy(policy);

	        // Save the document
	        PolicyDocument attachedDocument = policyDocRepo.save(policyDocument);

	        logger.info("Document {} attached to policy with ID {}", attachedDocument.getDocumentId(), policyId);
	        return attachedDocument;
	    }
	 
	 
		public PolicyDocument getDocumentByPolicyId(long policyId) {
		    try {
		        Policy policy = policyRepo.findById(policyId)
		                .orElseThrow(() -> {
		                    String errorMessage = String.format("Policy with ID %d not found", policyId);
		                    logger.error(errorMessage);
		                    return new PolicyNotFoundException(errorMessage);
		                });
		        
		        // fetching document
		        Optional<PolicyDocument> optionalDocument = policyDocRepo.findById(policyId);
		        return optionalDocument.orElse(null); 
		    } catch (PolicyNotFoundException e) {
		        logger.error("PolicyNotFoundException occurred while fetching document for policy with ID {}", policyId, e);
		        return null; // or return an appropriate default value
		    }
		}

	
	public PolicyPurchase purchasePolicy(long policyId, LocalDate purchaseDate) throws PolicyNotFoundException {
		Optional<Policy> optionalPolicy = getPolicyById(policyId);
        if (optionalPolicy.isPresent()) {
            Policy policy = optionalPolicy.get();
            PolicyPurchase policyPurchase = new PolicyPurchase();
            policyPurchase.setPolicy(policy);
            policyPurchase.setPurchaseDate(purchaseDate);
            return policyPurchaseRepo.save(policyPurchase);
        } else {
            logger.error("Policy with ID {} not found.", policyId);
            throw new PolicyNotFoundException("Policy with ID " + policyId + " not found.");
        }
	}
	 
	    public List<PolicyPurchase> getPurchasesByPolicyId(long policyId) throws PolicyNotFoundException {
	        // Check if the policy exists
	        Policy policy = policyRepo.findById(policyId)
	                .orElseThrow(() -> new PolicyNotFoundException("Policy not found for ID: " + policyId));

	        // Fetch purchases related to the policy
	        return policyPurchaseRepo.findByPolicy(policy);
	    }
	 
	
	
}

	


	

	


    

	

