package operacoes;

import java.util.List;
import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class SearchJobRequisicao extends Requisicao {

	@Getter
	private TreeMap<String, Object> data = new TreeMap<>();

	/*public SearchJobRequisicao(Operacoes operation, String token, String[] skill) {
		super(operation, token);
		data.put("skill", Arrays.asList(skill));
	}*/
	
	public SearchJobRequisicao(Operacoes operation, String token, List<String> skill) {
		super(operation, token);
		data.put("skill", skill);
	}

	public SearchJobRequisicao(Operacoes operation, String token, String experience) {
		super(operation, token);
		data.put("experience", experience);
	}
	
	public SearchJobRequisicao(Operacoes operation, String token, List<String> skill, String experience, String filter) {
		super(operation, token);
		data.put("skill", skill);
		data.put("experience", experience);
		data.put("filter", filter);
	}

	/*public SearchJobRequisicao(Operacoes operation, String token, String[] skill, String experience, String filter) {
		super(operation, token);
		data.put("skill", Arrays.asList(skill));
		data.put("experience", experience);
		data.put("filter", filter);
	}*/

}
