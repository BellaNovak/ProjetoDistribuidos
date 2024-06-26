package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class ChooseCandidateRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public ChooseCandidateRequisicao(Operacoes operation, String token, String id_user) {
		super(operation, token);
		data.put("id_user", id_user);
	}

}
