package pharm;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import ui.*;

public class SystemStartup {
    private static final String PHARMFILE = "Drug_Stores_NOLA.csv";
    private static final String MEDFILE = "Product.csv";
    private static final String PHARMSERIALFILE = "pharmsys.txt";


	public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        PharmacyDB pharmDB;
        MedicationDB medDB;
        MenuUI ui;
    	
     // if the serialized file exists, use it
        try {
			if (serializedFileExists(PHARMSERIALFILE)){
				pharmDB = loadPharmacyDB();
				
			} else{
				// otherwise, this is the first time running
				// this program so just load the pharmacy info
				//  from the text files
		       pharmDB = loadPharmacies();
		     
			}
			medDB = loadMedications();
			
			// log in as either a provider making prescriptions or a pharmacy filling them
			System.out.println("Enter PHARM if you are a pharmacist or PROV if you are a provider");
			String option = in.nextLine();
			if (option.equalsIgnoreCase("PROV")) {
			     ui =  new PrescriptionUI(pharmDB, medDB);
			}
			else {
				 ui = new PharmacistUI(pharmDB, medDB);
			}
			ui.run();
			in.close();

			// write to serialized file
			writePharmacyDB(pharmDB);
			
			System.out.println("bye");	
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static MedicationDB loadMedications() {
		MedicationDB medDB = new MedicationDB();
		
		 try {
		            Reader reader = Files.newBufferedReader(Paths.get(MEDFILE));
				    CSVParser parser = new CSVParserBuilder().build();
		            CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).withSkipLines(1).build();
		       
		            String[] nextRecord;
		            while ((nextRecord = csvReader.readNext()) != null) {	        
		                String ndc = nextRecord[0];
		     
		                String typeName = nextRecord[1];     
		                String proprietaryName = nextRecord[2];
		                String  proprietaryNameSuffix = nextRecord[3];
		                String nonProprietaryName= nextRecord[4];
		                String dosageForm = nextRecord[5];
		                String route = nextRecord[6];
		                String applicationNumber = nextRecord[7];
		                String labelerName = nextRecord[8];
		                String substanceName = nextRecord[9];
		        		double activeNumeratorStrength = convertActiveNumeratorStrength(nextRecord[10]);
		        		
		        		String activeIngredUnit = nextRecord[11];
		        		String pharmClasses = nextRecord[12];
		                Medication med = new Medication(ndc, typeName, proprietaryName, proprietaryNameSuffix, nonProprietaryName,
		                		dosageForm, route, applicationNumber, labelerName, substanceName, activeNumeratorStrength,
		                		activeIngredUnit, pharmClasses);
		    
		                medDB.add(med);
		            }
		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 
		return medDB;
	}


	// this method handles the case where the active numerator strength
	// field has several values, separated by semi colons
	// it just returns the first value
	// this is useful when loading the Medication.csv file
	private static double convertActiveNumeratorStrength(String activeStr) {
		if (activeStr.isEmpty()) {
			return 0.0;
		}
		int pos = activeStr.indexOf(';');
		if (pos >0) {
		     String firstPart = activeStr.substring(0,pos);
		     return Double.parseDouble(firstPart);
		}
	
		return Double.parseDouble(activeStr);
	}


	private static PharmacyDB loadPharmacies() {
		PharmacyDB pharms = new PharmacyDB();
		
		 try {
		            Reader reader = Files.newBufferedReader(Paths.get(PHARMFILE));
				    CSVParser parser = new CSVParserBuilder().build();
		            CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).withSkipLines(1).build();
		       
		            String[] nextRecord;
		            while ((nextRecord = csvReader.readNext()) != null) {
		                String id = nextRecord[0];
		                String ownerName = nextRecord[1];
		                String busName = nextRecord[2];
		                String addr = nextRecord[3];
		                String suite = nextRecord[4];
		                String city = nextRecord[5];
		                String state = nextRecord[6];
		                String zip = nextRecord[7];
		                String phone = nextRecord[8];
		                String busType = nextRecord[9];
		                
		                Pharmacy pharm = new Pharmacy(id, ownerName, busName, addr, suite, city, state, zip, phone, busType);
		    
		                pharms.add(pharm);
		            }
		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 
		return pharms;
	}

	private static Boolean serializedFileExists(String filePath) {
		File tmpDir = new File(filePath);
		return tmpDir.exists();
	}
	
	private static PharmacyDB loadPharmacyDB() throws IOException, ClassNotFoundException {
		    PharmacyDB pharmDB;
			FileInputStream fin = new FileInputStream(PHARMSERIALFILE);
			ObjectInputStream oin = new ObjectInputStream(fin);
			pharmDB = (PharmacyDB) oin.readObject();
			oin.close();

		return pharmDB;
	}

	private static void writePharmacyDB(PharmacyDB pharmDB) throws Exception, IOException {
		ObjectOutputStream out=null;
		out = new ObjectOutputStream( new FileOutputStream(PHARMSERIALFILE));
		out.writeObject(pharmDB); 
		out.close();
	}
}
	



