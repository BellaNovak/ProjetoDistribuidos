package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class IncludeJobRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public IncludeJobRequisicao(Operacoes operation, String token, String skill, String experience) {
		super(operation, token);
		data.put("skill", skill);
		data.put("experience", experience);
	}

}
