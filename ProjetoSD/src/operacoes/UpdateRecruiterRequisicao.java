package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class UpdateRecruiterRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public UpdateRecruiterRequisicao(Operacoes operation, String token, String email, String password, String name, String industry, String description) {
		super(operation, token);
		data.put("email", email);
		data.put("password", password);
		data.put("name", name);
		data.put("industry", industry);
		data.put("description", description);
	}

}
