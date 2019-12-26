package pharm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A list of medications, which can be searched by NDC or name
 * @author Gaganajit Singh
 *
 */
public class MedicationDB implements Serializable {

	private static final long serialVersionUID = 1L;
	private HashMap<String, Medication> medMap = new HashMap<String, Medication>();

	/**
	 * add a medication to the hash map.
	 * The key is the NDC code. 
	 * If the  key is already present
	 * in the hash map, do not add the medication
	 * @param med
	 */
	public void add(Medication med)  {	
		medMap.putIfAbsent(med.getNdc(), med);
	}
	

	  /**
	    * return the medication by its NDC 
	    * This will return null if the medication does not exist
	    * @param NDC
	    * @return the associated medication
	    */
	   public Medication getMedication(String ndc) {
		   return medMap.get(ndc);
	   }

	/**
	    * return a list of medications whose proprietary or nonproprietary
	    *name matches the parameter
	    * This will return an empty list if no matches 
	    * @param name
	    * @return a list of medications
	    */
	public List<Medication> getMedicationByName(String name) {
		ArrayList<Medication> medList = new ArrayList<Medication>();
		for (Medication med : medMap.values()) {
			if ((med.getProprietaryName().equalsIgnoreCase(name)) || (med.getNonProprietaryName().equalsIgnoreCase(name))){
				medList.add(med);
			}
		}
		return medList;
	}
	   
	/**
	 * checks if a drug is in the list, using the NDC
	 * @param ndc
	 * @return true if an entry for a drug with a matching NDC
	 * is in the list, false otherwise
	 */
	public Boolean drugInDB(String ndc) {
		if (medMap.containsKey(ndc)) {
			return true;
		}
		else {
			return false;
		}
	}
}
