package entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recruiter {
	
	private int idRecruiter;
	private String email;
	private String password;
	private String name;
	private String industry;
	private String description;
	
	public Recruiter() {
		
	}

}
