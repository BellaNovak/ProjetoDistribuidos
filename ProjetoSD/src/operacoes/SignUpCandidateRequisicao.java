package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class SignUpCandidateRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public SignUpCandidateRequisicao(Operacoes operation, String email, String password, String name) {
		super(operation);
		data.put("email", email);
		data.put("password", password);
		data.put("name", name);
	}

}