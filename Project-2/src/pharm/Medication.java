package pharm;

import java.io.Serializable;
/**
 * A Medication
 * includes the National Drug Code (NDC, type, proprietary and non proprietary names,
 * form of dosage, route, substance, and other information
 * Information is taken from an FDA data file
 * @author Gaganajit Singh
 *
 */
public class Medication implements Serializable{

	private static final long serialVersionUID = 1L;
	private String ndc;
	private String typeName;
	private String proprietaryName;
	private String proprietaryNameSuffix;
	private String nonProprietaryName;
	private String dosageForm;
	private String route;
    private String applicationNumber;
    private String labelerName;
    private String substanceName;
    private double activeNumeratorStrength;
    private String activeIngredUnit;
    private String pharmClasses;
    
    
	public Medication(String ndc, String typeName, String proprietaryName, String proprietaryNameSuffix,
			String nonProprietaryName, String dosageForm, String route, String applicationNumber, String labelerName,
			String substanceName, double activeNumeratorStrength, String activeIngredUnit, String pharmClasses) {
		super();
		this.ndc = ndc;
		this.typeName = typeName;
		this.proprietaryName = proprietaryName;
		this.proprietaryNameSuffix = proprietaryNameSuffix;
		this.nonProprietaryName = nonProprietaryName;
		this.dosageForm = dosageForm;
		this.route = route;
		this.applicationNumber = applicationNumber;
		this.labelerName = labelerName;
		this.substanceName = substanceName;
		this.activeNumeratorStrength = activeNumeratorStrength;
		this.activeIngredUnit = activeIngredUnit;
		this.pharmClasses = pharmClasses;
	}


	public String getNdc() {
		return ndc;
	}


	public void setNdc(String ndc) {
		this.ndc = ndc;
	}


	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public String getProprietaryName() {
		return proprietaryName;
	}


	public void setProprietaryName(String proprietaryName) {
		this.proprietaryName = proprietaryName;
	}


	public String getProprietaryNameSuffix() {
		return proprietaryNameSuffix;
	}


	public void setProprietaryNameSuffix(String proprietaryNameSuffix) {
		this.proprietaryNameSuffix = proprietaryNameSuffix;
	}


	public String getNonProprietaryName() {
		return nonProprietaryName;
	}


	public void setNonProprietaryName(String nonProprietaryName) {
		this.nonProprietaryName = nonProprietaryName;
	}


	public String getDosageForm() {
		return dosageForm;
	}


	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}


	public String getRoute() {
		return route;
	}


	public void setRoute(String route) {
		this.route = route;
	}


	public String getApplicationNumber() {
		return applicationNumber;
	}


	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}


	public String getLabelerName() {
		return labelerName;
	}


	public void setLabelerName(String labelerName) {
		this.labelerName = labelerName;
	}


	public String getSubstanceName() {
		return substanceName;
	}


	public void setSubstanceName(String substanceName) {
		this.substanceName = substanceName;
	}


	public double getActiveNumeratorStrength() {
		return activeNumeratorStrength;
	}


	public void setActiveNumeratorStrength(double activeNumeratorStrength) {
		this.activeNumeratorStrength = activeNumeratorStrength;
	}


	public String getActiveIngredUnit() {
		return activeIngredUnit;
	}


	public void setActiveIngredUnit(String activeIngredUnit) {
		this.activeIngredUnit = activeIngredUnit;
	}


	public String getPharmClasses() {
		return pharmClasses;
	}


	public void setPharmClasses(String pharmClasses) {
		this.pharmClasses = pharmClasses;
	}


	@Override
	public String toString() {
		return "Medication [ndc=" + ndc + ", typeName=" + typeName + ", proprietaryName=" + proprietaryName
				+ ", proprietaryNameSuffix=" + proprietaryNameSuffix + ", nonProprietaryName=" + nonProprietaryName
				+ ", dosageForm=" + dosageForm + ", route=" + route + ", applicationNumber=" + applicationNumber
				+ ", labelerName=" + labelerName + ", substanceName=" + substanceName + ", activeNumeratorStrength="
				+ activeNumeratorStrength + ", activeIngredUnit=" + activeIngredUnit + ", pharmClasses=" + pharmClasses
				+ "]";
	}
    
    
    
	

}
