package entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Skillset {
	
	private int idSkillset;
	private String experience;
	private Candidate candidate;
	private Skill skill;
	
	public Skillset() {
		
	}

}
