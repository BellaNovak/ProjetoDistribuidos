package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class LookUpSkillRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public LookUpSkillRequisicao(Operacoes operation, String token, String skill) {
		super(operation, token);
		data.put("skill", skill);
	}

}
