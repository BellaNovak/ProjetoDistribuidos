package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class LoginCandidateRequisicao extends Requisicao{

	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public LoginCandidateRequisicao(Operacoes operation, String email, String password) {
		super(operation);
		data.put("email", email);
		data.put("password", password);
	}

	/*public TreeMap<String, String> data() {
		return null;
	}*/
	
	/*public void testar(){
		System.out.println(data.get("email"));
	}*/

}