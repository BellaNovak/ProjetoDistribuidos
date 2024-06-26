package entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Jobset {
	
	private int idJobset;
	private String experience;
	private Recruiter recruiter;
	private Skill skill;
	private String available;
	private String searchable; 
	
	public Jobset() {
		
	}

}
