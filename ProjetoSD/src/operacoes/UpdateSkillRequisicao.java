package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class UpdateSkillRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public UpdateSkillRequisicao(Operacoes operation, String token, String skill, String experience, String newSkill) {
		super(operation, token);
		data.put("skill", skill);
		data.put("experience", experience);
		data.put("newSkill", newSkill);
	}

}
