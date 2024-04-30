package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import enumerations.Status;
import lombok.Getter;


public class LookUpCandidateResposta extends Resposta{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public LookUpCandidateResposta(Operacoes operation, Status status, String email, String password, String name) {
		super(operation, status);
		data.put("email", email);
		data.put("password", password);
		data.put("name", name);
	}
}