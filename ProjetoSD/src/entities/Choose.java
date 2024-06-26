package entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Choose {
	
	private int idSkillset;
	private Candidate candidate;
	private Recruiter recruiter;
	
	public Choose() {
		
	}

}
