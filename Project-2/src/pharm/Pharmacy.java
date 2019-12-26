package pharm;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Represents one pharmacy's information. The information consists of a city generated id, an owner, the name of the pharmacy,
 * its street address, suite, city, state, zip code, phone number, and a type
 * It also contains a list of prescriptions received by the pharmacy
 * @author Gaganajit Singh
 *
 */
public class Pharmacy implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String owner;
	private String busName;
	private String address;
	private String suite;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String type;
	private int nextIdNum=0;
	
	private HashMap<String, Prescription> prescrips = new HashMap<String, Prescription>();
	
	public Pharmacy() {}
	
	public Pharmacy(String id, String owner, String busName, String address, String suite, String city, String state, String zip,
			String phone, String type) {
		super();
		this.id = id;
		this.owner = owner;
		this.busName = busName;
		this.address = address;
		this.suite = suite;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	/**
	 * This returns true if the business name of a pharmacy
	 * indicates that it is part of a chain. The business
	 * names for chain members all have the format
	 * ChainName Number
	 * For example WALGREENS #02262
	 * so we just test the name of the chain against the first part
	 * of the business name
	 * @param chainName
	 * @return true if the pharmacy is in the chain, false otherwise
	 */
	public Boolean storeInChain(String chainName) {
		return (busName.startsWith(chainName.toUpperCase()));
		
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSuite() {
		return suite;
	}

	public void setSuite(String suite) {
		this.suite = suite;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * returns a formatted string containing the
	 * information about the pharmacy
	 */
	@Override
	public String toString() {
		String newline = System.lineSeparator();
		return "id: " + id + newline + 
				"owner: " + owner + newline +
				"store name: " + busName + newline +
				"address: " + address + newline + 
				 "suite: "+ suite + newline +
				 "city: " + city + ", state: " + state + ", zip: " + zip + newline +
				 "phone: " + phone + newline + "type: " + type;
				
	}

	/**
	 * when a prescription is received, an id specific to the
	 * pharmacy is generated, the time of receipt is recorded,
	 * the prescription is marked as
	 * UNFILLED and the prescription is stored
	 * in the list of prescriptions.
	 * @param prescrip
	 */
    public void receivePrescription(Prescription prescrip) {
    	String id = generatePrescriptionId();
    	prescrip.setId(id);
    	prescrip.setRecvDate(LocalDateTime.now());
    	prescrip.setStatus(Prescription.Status.UNFILLED);
    	prescrips.put(id, prescrip);
    }
    
    /**
     * Action taken to fill a prescription
     * If the prescription is in the list, and it
     * still has refills permitted, then set
     * the status to FILED and record the date
     * filled
     * @param id the NDC
     * @return true if the prescription is filled,
     * false otherwise
     */
    public Boolean fillPrescription(String id) {
    	Prescription prescrip = prescrips.get(id);
    	if (prescrip == null) {
    		return false;
    	}
    	if (prescrip.getRefills() == 0) {
    		return false;
    	}
    	else {
    		prescrip.setStatus(Prescription.Status.FILLED);
    		prescrip.setFillDate(LocalDateTime.now());
    		int refills = prescrip.getRefills();
    		prescrip.setRefills(--refills);
    		return true;
    		
    	}
    }
    
    /**
     * get a prescription based on NDC
     * @param id  the NDC
     * @return the prescription or null if not found
     */
    public Prescription getPrescripById(String id) {
    	return prescrips.get(id);
    }
    
    private String generatePrescriptionId() {
    	String numberAsString = String.format("%05d", nextIdNum++);
    	return numberAsString;
    }

    /**
     * return the list of prescriptions sorted by NDC
     * @return
     */
	public List<Prescription> listPrescriptions() {
		ArrayList<Prescription> sortedPrescrips = new ArrayList<Prescription>(prescrips.values());
		Collections.sort(sortedPrescrips, (Prescription p1, Prescription p2)-> p1.getId().compareTo(p2.getId()));
	       return sortedPrescrips;
	
	}

	/**
	 * returns a list of unfilled prescriptions sorted by receive date
	 * @return
	 */
	public List<Prescription> listUnfilled() {
		ArrayList<Prescription> unfilledPrescrips = new ArrayList<Prescription>();
		for (Prescription prescrip : prescrips.values()) {
			if (prescrip.getStatus().equals(Prescription.Status.UNFILLED)) {				
				unfilledPrescrips.add(prescrip);
			}
		}
		Collections.sort(unfilledPrescrips, (Prescription p1, Prescription p2)-> p1.getRecvDate().compareTo(p2.getRecvDate()));
	       return unfilledPrescrips;
	
	}
}
