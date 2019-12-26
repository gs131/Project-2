package ui;

import java.time.LocalDateTime;
import java.util.List;

import pharm.Medication;
import pharm.MedicationDB;
import pharm.Pharmacy;
import pharm.PharmacyDB;
import pharm.Prescription;

/**
 * A user interface for a pharmacist who is managing prescriptions
 *
 */
public class PharmacistUI extends MenuUI {
	private PharmacyDB pharmDB;
	private MedicationDB medDB;
	
	private String pharmID;
	private Pharmacy pharm;

	public PharmacistUI(PharmacyDB pharmDB, MedicationDB medDB) {
		this.pharmDB = pharmDB;
		this.medDB = medDB;
	}

	  public void run()
	   {
		   System.out.println("Welcome to the pharmacy system");
		   System.out.println("Enter your pharmacy ID ");
		   pharmID = in.nextLine();
		   pharm = pharmDB.getPharmById(pharmID);
		   if (pharm == null) {
			   System.out.println("Sorry, we do not have a listing for your pharmacy");
		   }
		   else {
		   String command = readCommand();

		      while (!command.equalsIgnoreCase("QUIT")){
		    	  switch (command) {
		    	
		    	  case "MEDBYNAME":
		    		  displayMedsByName();
		    		  break;
		    	  case "MEDBYNDC":
		    		  displayMedByNDC();
		    		  break;
		    	  case "LIST":
		    		  listPrescrip();
		    		  break;
		    	  case "LISTUNFILLED":
		    		  listUnfilled();
		    		  break;
		    	  case "FILL":
		    		  fill();
		    		  break;
		    	
		    	  default:
		    		  System.out.println("Invalid command");
		    	  }

		         command = readCommand();
		      }
		   }
		    System.out.println("Bye!");		   
		   in.close();
		   }
	   
	   private void fill() {
		System.out.println("Enter id for the prescription to fill");
		String id = in.nextLine();
		Prescription prescrip = pharm.getPrescripById(id);
		if (prescrip == null) {
			System.out.println("This prescription has not been received");
			return;
		}
		if (prescrip.getRefills() ==0) {
			System.out.println("This prescription has no refills left");
			return;
		}
		Boolean success = pharm.fillPrescription(id);
		if (success) {
			System.out.println("The prescription " + prescrip.getId() + " for patient " + prescrip.getPatientName() + " was filled on " + prescrip.getFillDate());
		}
		else {
			System.out.println("The prescription " + prescrip.getId() + " for patient " + prescrip.getPatientName()+ " was not filled");
		}
		
	}

	

	private void displayMedByNDC() {
		   System.out.println("Enter the NDC for the drug");
   	      String id = in.nextLine();
   	      Medication med = medDB.getMedication(id);
   	      System.out.println(med.toString());
	
}

	private void displayMedsByName() {
		 System.out.println("Enter the drug name");
	      String name = in.nextLine();
	      List<Medication> meds = medDB.getMedicationByName(name);
	      for (Medication med : meds) {
	    	  System.out.println(med.toString());
	      }
	
}

	private  void listPrescrip() {
		 List<Prescription> prescrips = pharm.listPrescriptions();
			if (prescrips.isEmpty()) {
				System.out.println("There are no prescriptions");
			}
   		 for (Prescription prescrip : prescrips) {
   			 System.out.println(prescrip.toString());
   		 }
	   }
	
	private void listUnfilled() {
		List<Prescription> prescrips = pharm.listUnfilled();
		if (prescrips.isEmpty()) {
			System.out.println("There are no unfilled prescriptions");
		}
		for (Prescription prescrip: prescrips) {
			System.out.println(prescrip.toString());
		}
	}
	  
	
	   
	   private String readCommand() {
		   System.out.println();

		   System.out.println("MEDBYNAME: show all medications that match the input name");
		   System.out.println("MEDBYNDC: find and display a medication by its NDC");
		   System.out.println("LIST: list all prescriptions, sorted by id");
		   System.out.println("LISTUNFILLED: list all unfilled prescriptions sorted by date/time of receipt");
		   System.out.println("FILL: fill a prescription");
		   System.out.println("QUIT: quit");
	       System.out.println(">>>>>>");     
	       String command = in.nextLine().toUpperCase();
	       return command;
}

}
