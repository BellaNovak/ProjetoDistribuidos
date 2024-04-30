package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;

public class LoginCandidateRequisicao extends Requisicao{
	
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public LoginCandidateRequisicao(Operacoes operation, String email, String password) {
		super(operation);
		data.put("email", email);
		data.put("password", password);
	}

}
