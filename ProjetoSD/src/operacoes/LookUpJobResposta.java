package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import enumerations.Status;
import lombok.Getter;

public class LookUpJobResposta extends Resposta{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public LookUpJobResposta(Operacoes operation, Status status, String skill, String experience, String id, String available, String searchable) {
		super(operation, status);
		data.put("skill", skill);
		data.put("experience", experience);
		data.put("id", id);
		data.put("available", available);
		data.put("searchable", searchable);
	}
	
	public LookUpJobResposta(Operacoes operation, Status status) {
		super(operation, status);
	}

}
