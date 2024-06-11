package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class LookUpJobRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public LookUpJobRequisicao(Operacoes operation, String token, String id) {
		super(operation, token);
		data.put("id", id);
	}
	
}
