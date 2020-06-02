package de.aestis.dndreloaded.Players.Professions;

public class Profession {
	
	public final Integer ID;
	
	private String Name;
	private Integer Level;
	private Integer CurrentExperience;
	private Integer RequiredExperience;
	
	public Profession (Integer ID) {this.ID = ID;}
	
	public Integer getID() {return this.ID;}
	
	private void calculateProfession() {
		//Might include CONST into Config
		
		Integer level = (int) (100 * Math.sqrt(this.getCurrentExperience()));
		System.out.println("Calculated Level: " + level);
	}
	
	public String getName() {return this.Name;}
	public void setName(String Name) {this.Name = Name;}
	
	public Integer getLevel() {return this.Level;}
	private void setLevel(Integer Level) {this.Level = Level;}
	
	public Integer getCurrentExperience() {return this.CurrentExperience;}
	public void setCurrentExperience(Integer CurrentExperience) {
		//CALC
		this.CurrentExperience = CurrentExperience;
	}
	
	public Integer getRequiredExperience() {return this.RequiredExperience;}
	private void setRequiredExperience(Integer RequiredExperience) {this.RequiredExperience = RequiredExperience;}
	
}
