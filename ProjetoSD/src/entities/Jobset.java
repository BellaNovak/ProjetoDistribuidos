package entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Jobset {
	
	private String experience;
	private Recruiter recruiter;
	private Skill skill;
	
	public Jobset() {
		
	}

}
