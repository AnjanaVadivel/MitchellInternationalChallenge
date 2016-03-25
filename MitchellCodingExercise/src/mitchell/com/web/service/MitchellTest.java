package mitchell.com.web.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class MitchellTest {
	
	Mitchell_DBOperations op=new Mitchell_DBOperations();

	@Test
	public void test1() {
		op.fnCreateStoreClaims();
		assertEquals("The vehicle Vin 1M8GDM9AXKP042788","1M8GDM9AXKP042788",op.Vin);
	}
	
	@Test
	public void test2(){
		op.fnCreateStoreClaims();
		assertNotNull("As the claim number is a primary key,it should not be null",op.Claim_number);
	}
	
	@Test
	public void test3(){
		op.fnCreateStoreClaims();
		String create_firstname=op.Claimant_First_Name;
		op.fnUpdateClaim();
		String update_firstname=op.Claimant_First_Name;
		assertSame("Used to check if a particular field remains the same",create_firstname,update_firstname);
	}

}
