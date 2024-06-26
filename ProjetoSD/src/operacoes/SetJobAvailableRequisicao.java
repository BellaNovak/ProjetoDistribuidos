package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class SetJobAvailableRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<>();
	
	public SetJobAvailableRequisicao(Operacoes operation, String token, String id, String available) {
		super(operation, token);
		data.put("id", id);
		data.put("available", available);
	}

}
