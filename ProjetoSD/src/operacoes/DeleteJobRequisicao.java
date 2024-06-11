package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class DeleteJobRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public DeleteJobRequisicao(Operacoes operation, String token, String id) {
		super(operation, token);
		data.put("id", id);
	}

}
