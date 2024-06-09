package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import enumerations.Status;
import lombok.Getter;

public class LookUpSkillResposta extends Resposta{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public LookUpSkillResposta(Operacoes operation, Status status, String skill, String experience, String id) {
		super(operation, status);
		data.put("skill", skill);
		data.put("experience", experience);
		data.put("id", id);
	}
	
	public LookUpSkillResposta(Operacoes operation, Status status) {
		super(operation, status);
	}

}
