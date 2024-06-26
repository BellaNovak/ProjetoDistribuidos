package operacoes;

import java.util.List;
import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class SearchProfileRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, Object> data = new TreeMap<>();
	
	public SearchProfileRequisicao(Operacoes operation, String token, List<String> skill) {
		super(operation, token);
		data.put("skill", skill);
	}

	public SearchProfileRequisicao(Operacoes operation, String token, String experience) {
		super(operation, token);
		data.put("experience", experience);
	}
	
	public SearchProfileRequisicao(Operacoes operation, String token, List<String> skill, String experience, String filter) {
		super(operation, token);
		data.put("skill", skill);
		data.put("experience", experience);
		data.put("filter", filter);
	}

}
