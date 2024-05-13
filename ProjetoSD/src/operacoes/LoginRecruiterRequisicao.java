package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class LoginRecruiterRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public LoginRecruiterRequisicao(Operacoes operation, String email, String password) {
		super(operation);
		data.put("email", email);
		data.put("password", password);
	}

}
