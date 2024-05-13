package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class SignUpRecruiterRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public SignUpRecruiterRequisicao(Operacoes operation, String email, String password, String name, String industry, String description) {
		super(operation);
		data.put("email", email);
		data.put("password", password);
		data.put("name", name);
		data.put("industry", industry);
		data.put("description", description);
	}

}
