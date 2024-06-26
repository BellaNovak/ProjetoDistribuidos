package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class SetJobSearchableRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<>();
	
	public SetJobSearchableRequisicao(Operacoes operation, String token, String id, String searchable) {
		super(operation, token);
		data.put("id", id);
		data.put("searchable", searchable);
	}

}
