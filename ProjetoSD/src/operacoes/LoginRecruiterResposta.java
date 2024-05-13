package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import enumerations.Status;
import lombok.Getter;

public class LoginRecruiterResposta extends Resposta{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public LoginRecruiterResposta(Operacoes operation, Status status, String token) {
		super(operation, status);
		data.put("token", token);
	}
	
	public LoginRecruiterResposta(Operacoes operation, Status status) {
		super(operation, status);
	}

}
