package pharm;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PharmacyTest {
  private Prescription prescrip;
  private Prescription prescrip2;
  private Prescription prescrip3;
  
  private Pharmacy testPharm;

  
  @BeforeEach
  public void setup() {
	  prescrip = new Prescription("Dr Jones", "Joe", "65162-673", 3, 2);
	  prescrip2 = new Prescription("Dr Jones", "Fred", "11111-111", 4, 2);
	  prescrip3 = new Prescription("Dr Jones", "Fred", "22222-111", 4, 3);
	  testPharm = new Pharmacy();
	  testPharm.setId("69997");
	  testPharm.setBusName("WALGREENS #16395");
	  testPharm.setAddress("2000 CANAL ST");
  }

	
  @Test
  void testStoreInChain() {
		assertTrue(testPharm.storeInChain("WALGREENS"));
  }
  
 
@Test
void testListPrescriptions() {
	 testPharm.receivePrescription(prescrip2);
	 testPharm.receivePrescription(prescrip);
	 List<Prescription> prescrips = testPharm.listPrescriptions();
	 String id1 = prescrips.get(0).getId();
	 String id2 = prescrips.get(1).getId();
	 
	 assertTrue((id1.compareTo(id2)) < 0);

}

@Test
void testListUnfilled() {
	 testPharm.receivePrescription(prescrip);
	 testPharm.receivePrescription(prescrip3);
	 testPharm.receivePrescription(prescrip2);
	 testPharm.fillPrescription(prescrip3.getId());
	 List<Prescription> prescrips = testPharm.listUnfilled();
     
	 assertEquals("65162-673", prescrips.get(0).getDrugNDC());
	 assertEquals("11111-111", prescrips.get(1).getDrugNDC());
	 
	 LocalDateTime recvDate1 = prescrips.get(0).getRecvDate();
	 LocalDateTime recvDate2 = prescrips.get(1).getRecvDate();
	 assertTrue((recvDate1.compareTo(recvDate2)) < 0);
}

@Test
void testReceivePrescrip() {
	  testPharm.receivePrescription(prescrip);
	  Prescription res = testPharm.getPrescripById(prescrip.getId());
	  assertNotNull(res);
	  assertEquals("Dr Jones", res.getProviderName());
	  assertEquals("Joe", res.getPatientName());
	  assertEquals("65162-673", res.getDrugNDC());
	  assertEquals(Prescription.Status.UNFILLED, res.getStatus());
	  
}

@Test 
void testFillPrescrip() {
	 testPharm.receivePrescription(prescrip);
	 int numRefills = prescrip.getRefills();
	Boolean success = testPharm.fillPrescription(prescrip.getId());
	assertTrue(success);
	Prescription res = testPharm.getPrescripById(prescrip.getId());
	assertEquals(--numRefills, res.getRefills());
	assertEquals(Prescription.Status.FILLED, res.getStatus());
}

@Test 
void testFillPrescripNotExists() {
	Boolean success = testPharm.fillPrescription("333");  //doesn't exist
    assertFalse(success);
}

@Test 
void testFillPrescripNoRefills() {
	 // change prescription to 0 refills
	prescrip.setRefills(0);
	 testPharm.receivePrescription(prescrip);
	Boolean success = testPharm.fillPrescription(prescrip.getId());  //doesn't exist
    assertFalse(success);
}
}
