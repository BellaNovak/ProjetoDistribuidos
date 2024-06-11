package operacoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import enumerations.Operacoes;
import enumerations.Status;
import lombok.Getter;

public class LookUpSkillsetResposta extends Resposta {

	@Getter
	private Map<String, Object> data = new TreeMap<>();

	public LookUpSkillsetResposta(Operacoes operation, Status status, List<Map<String, String>> skillset) {
		super(operation, status);
		data.put("skillset_size", String.valueOf(skillset.size()));
		data.put("skillset", skillset);
	}

	public LookUpSkillsetResposta(Operacoes operation, Status status) {
		super(operation, status);
		data.put("skillset_size", "0");
		data.put("skillset", new ArrayList<Map<String, String>>());
	}

}
