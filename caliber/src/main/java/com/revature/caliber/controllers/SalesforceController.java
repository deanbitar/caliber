package com.revature.caliber.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.caliber.beans.Batch;
import com.revature.caliber.security.models.SalesforceUser;
import com.revature.caliber.services.SalesforceService;

@RestController
public class SalesforceController {

	private static final Logger log = Logger.getLogger(SalesforceController.class);
	
	@Autowired
	private SalesforceService salesforceService;

	public void setSalesforceService(SalesforceService salesforceService) {
		this.salesforceService = salesforceService;
	}
	
	/**
	 * TODO delete me.
	 * Used to grab access_token for running local tests of Salesforce API
	 * @return
	 */
	@RequestMapping(value="/salesforce/token", method=RequestMethod.GET)
	public String getSalesforceToken(){
		log.info("Getting access_token for testing purposes only");
		return ((SalesforceUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getSalesforceToken().getAccessToken();
	}
	
	/**
	 * Gets all current batches from salesforce
	 * 
	 * @return the all batches
	 */
	@RequestMapping(value ="/all/batch/importget", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	// @PreAuthorize("hasAnyRole('VP')")
	public ResponseEntity<List<Batch>> getAllSalesforceBatches() {
		log.info("Fetching all salesforce batches");
		List<Batch> batches = salesforceService.findAllBatches();
		return new ResponseEntity<>(batches, HttpStatus.OK);
	}
/*
	 * Gets all the relevent batches
	 * @return Batches in JSON
	 */
	@RequestMapping(value="/all/batch/importget", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Iterable<Batch> getAllReleventBatches(){
		return salesforceService.getAllRelevantBatches();
	}
}
