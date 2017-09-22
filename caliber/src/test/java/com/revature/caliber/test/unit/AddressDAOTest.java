package com.revature.caliber.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.revature.caliber.CaliberTest;
import com.revature.caliber.beans.Address;
import com.revature.caliber.data.AddressDAO;


public class AddressDAOTest extends CaliberTest{

	private static final Logger log = Logger.getLogger(AddressDAOTest.class);
	private static final String ADDRESS_COUNT = "select count(address_id) from caliber_address";

	private AddressDAO dao;
	@Autowired
	public void setDao(AddressDAO dao) {
		this.dao = dao;
	}
	
	@Test
	public void saveAddressDAO(){
		log.info("Saving a new Address using AddressDAO");
		int before = jdbcTemplate.queryForObject(ADDRESS_COUNT, Integer.class);
		Address address = new Address(1, "Sunshine st","New Hope", "MN", "55428", "moneybags inc",false);
		dao.save(address);
		int after = jdbcTemplate.queryForObject(ADDRESS_COUNT, Integer.class);
		int addressId = address.getAddressId();
		assertEquals(address, dao.getAddressById(addressId));
		assertEquals(++before, after);
	}
	
	@Test
	public void getAllAddressDAO(){
		log.info("Getting all addresses using AddressDAO getAll function");
		int num = jdbcTemplate.queryForObject(ADDRESS_COUNT, Integer.class);
		assertNotNull(dao.getAll());
		assertEquals(dao.getAll().size(), num);
	}
	@Test
	public void findAllAddressDAO(){
		log.info("Find All Addresses");
		int size = jdbcTemplate.queryForObject(ADDRESS_COUNT, Integer.class);
		assertFalse(dao.findAll().isEmpty());
		assertEquals(dao.findAll().size(),size);
	}
	//Takes the address id and finds the address from the database
	@Test
	public void getAddressByIdDAO(){
		log.info("Finding Location by address");
		int search = 1;
		Address address = dao.getAddressById(search);
		assertEquals(dao.getAddressById(1).toString(),address.toString());
	}
	
	@Test
	public void nullGetAddressByInt(){
		log.info("About to fail gettingAddressByInt");
		Address address = dao.getAddressById(9999999);
		assertNull(address);
	}

	@Test
	public void updateAddressDAO(){
		log.info("UpdateAddessDAO Test");
		String zipcode = "11111";
		Address address = dao.getAddressById(2);
		address.setZipcode(zipcode);
		dao.update(address);
		assertEquals(zipcode, dao.getAddressById(2).getZipcode());
	}
}