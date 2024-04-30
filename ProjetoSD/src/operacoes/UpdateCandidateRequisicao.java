package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;


public class UpdateCandidateRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public UpdateCandidateRequisicao(Operacoes operation, String token, String email, String password, String name) {
		super(operation, token);
		data.put("email", email);
		data.put("password", password);
		data.put("name", name);
	}

}