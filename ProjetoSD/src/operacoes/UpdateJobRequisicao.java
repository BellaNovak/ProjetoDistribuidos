package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class UpdateJobRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public UpdateJobRequisicao(Operacoes operation, String token, String id, String skill, String experience, String available, String searchable) {
		super(operation, token);
		data.put("id", id);
		data.put("skill", skill);
		data.put("experience", experience);
		data.put("available", available);
		data.put("searchable", searchable);
	}

}
