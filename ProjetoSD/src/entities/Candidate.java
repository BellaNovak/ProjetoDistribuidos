package entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Candidate {
	
	private int idCandidate;
	private String email;
	private String password;
	private String name;
	
	public Candidate() {
		
	}

}
