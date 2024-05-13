package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import enumerations.Status;
import lombok.Getter;

public class LookUpRecruiterResposta extends Resposta{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public LookUpRecruiterResposta(Operacoes operation, Status status, String email, String password, String name, String industry, String description) {
		super(operation, status);
		data.put("email", email);
		data.put("password", password);
		data.put("name", name);
		data.put("industry", industry);
		data.put("description", description);
	}

}
