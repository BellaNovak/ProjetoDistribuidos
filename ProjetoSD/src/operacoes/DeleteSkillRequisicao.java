package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class DeleteSkillRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public DeleteSkillRequisicao(Operacoes operation, String token, String skill) {
		super(operation, token);
		data.put("skill", skill);
	}

}
