package ui;

import java.util.List;


import pharm.Pharmacy;
import pharm.PharmacyDB;
import pharm.MedicationDB;
import pharm.Medication;
import pharm.Prescription;

/**
 * A user interface for a provider who is writing prescriptions and
 * sending them to pharmacies
 *
 */
public class PrescriptionUI extends MenuUI{
	private PharmacyDB pharmDB;
	private MedicationDB medDB;
	private String providerName;
	/**
	 * create a PharmacyUI object that collaborates
	 * with the PharmacyDB passed in as a parameter
	 * @param system
	 */
	   public PrescriptionUI(PharmacyDB pharmDB, MedicationDB medDB){
         this.pharmDB=pharmDB;
         this.medDB = medDB;
	   }
	   
	   /**
		   * this method handles all interaction with the user
		   * It repeatedly displays a menu of options, allows
		   * the customer to enter an option, and then displays results
		   * from processing the user's choice
		   */
		   public void run()
		   {
			   System.out.println("Welcome to the prescription system");
			   System.out.println("Enter your name ");
			   providerName = in.nextLine();
			   
			   String command = readCommand();

			      while (!command.equalsIgnoreCase("QUIT")){
			    	  switch (command) {
			    	  case "LISTPHARM":
			    	      listSortedPharmacies();
			    		  break;		    	  
			    	  case "FINDPHARM":
			    	      displayPharmacyById();
			    		  break;
			    	  case "LISTBYZIP":
			    		  displayPharmaciesInZip();
			  
			    		  break;
			    	  case "FINDPHARMBYNAME":
			    		  listPharmaciesInChain();
			    		  break;
			    	  case "MEDBYNAME":
			    		  displayMedsByName();
			    		  break;
			    	  case "MEDBYNDC":
			    		  displayMedByNDC();
			    		  break;
			    	  case "PRESCRIBE":
			    		  prescribe();
			    		  break;
			    	  
			    	  default:
			    		  System.out.println("Invalid command");
			    	  }

			         command = readCommand();
			      }
			    System.out.println("Bye!");		   
			   in.close();
			   }
		   
		   private void prescribe() {
		      System.out.println("Enter NDC  for the drug to be prescribed ");
		      String id =  in.nextLine();
		      System.out.println("Enter the pharmacy id");
		      String pharmId = in.nextLine();
		      Pharmacy pharm = pharmDB.getPharmById(pharmId);
		      if (!medDB.drugInDB(id)) {
		    	  System.out.println("This drug is not in the medication list");
		      }
		      else if (pharm == null) {
		    	  System.out.println("The pharmacy does not exist");
		      }
		      else {
		    	  System.out.println("Enter the patient name");
		    	  String pat = in.nextLine();
		    	  System.out.println("ENter the frequency");
		    	  String freqStr = in.nextLine();
		    	  int freq = Integer.parseInt(freqStr);
		    	  System.out.println("Enter the number of refills");
		    	  String refillStr = in.nextLine();
		    	  int refills = Integer.parseInt(refillStr);
		    	  Prescription prescrip = new Prescription(providerName, pat, id, freq, refills);
		    	  pharm.receivePrescription(prescrip);
		    	  System.out.println("prescription sent to " + pharm.getBusName() + " at " + pharm.getAddress());
		      }
		      
		      }
		

			
		private void displayMedByNDC() {
			   System.out.println("Enter the NDC for the drug");
	    	      String id = in.nextLine();
	    	      Medication med = medDB.getMedication(id);
	    	      if (med == null) {
	    	    	  System.out.println("There is no medication with this NDC");
	    	      }
	    	      else {
	    	         System.out.println(med.toString());
	    	      }
		
	}

		private void displayMedsByName() {
			 System.out.println("Enter the drug name");
   	      String name = in.nextLine();
   	      List<Medication> meds = medDB.getMedicationByName(name);
   	      if (meds.isEmpty()) {
   	    	  System.out.println("No medications were found for this name");
   	      }
   	      else {
   	      for (Medication med : meds) {
   	    	  System.out.println(med.toString());
   	      }
   	      }
		
	}

		private  void listSortedPharmacies() {
			   List<Pharmacy>pharms = pharmDB.getPharmaciesSortedOnName();
	    		 for (Pharmacy pharm : pharms) {
	    			 System.out.println(pharm.toString());
	    		 }
		   }
		  
		   private void listPharmaciesInChain() {
			   System.out.println("Enter the chain name");
	    		  String chainName = in.nextLine();
	    		  List<Pharmacy>pharms = pharmDB.getPharmaciesByName(chainName);
	    		  if (pharms.isEmpty()) {
	    			  System.out.println("No pharmacies were found for this name");
	    		  }
		    	  for (Pharmacy pharm : pharms) {
		    			 System.out.println(pharm.toString());
		    		 }
		   }
		   private void displayPharmacyById() {
			      System.out.println("Enter the id");
	    	      String id = in.nextLine();
	    	      Pharmacy pharm = pharmDB.getPharmById(id);
	    	      if (pharm == null) {
	    	    	  System.out.println("Pharmacy was not found");
	    	      }
	    	      else {
	    	      System.out.println(pharm.toString());
	    	      }
		   }
		   
		   private void displayPharmaciesInZip() {
		  		  System.out.println("Enter a zip code");
	    		  String zip = in.nextLine();
	    		  List<Pharmacy>pharms = pharmDB.getPharmaciesByZip(zip);
		    	  for (Pharmacy pharm : pharms) {
		    			 System.out.println(pharm.toString());
		    		 }
		   }
		   
		   private String readCommand() {
			   System.out.println();
			   System.out.println( "Please enter a command" );
			   System.out.println("LISTPHARM: Show all pharmacies sorted by name");
			   System.out.println("FINDPHARM: Find and display a pharmacy by its id");
			   System.out.println("LISTBYZIP: Show all pharmacies in a zip code");
			   System.out.println("FINDPHARMBYNAME: Display pharmacy or pharmacies listed under a name");
			   System.out.println("MEDBYNAME: show all medications that match the input name");
			   System.out.println("MEDBYNDC: find and display a medication by its NDC");
			   System.out.println("PRESCRIBE: create a prescription and send to a pharmacy");
			   System.out.println("QUIT: quit");
		       System.out.println(">>>>>>");     
		       String command = in.nextLine().toUpperCase();
		       return command;
	}

}
