package operacoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import enumerations.Operacoes;
import enumerations.Status;
import lombok.Getter;

public class LookUpJobsetResposta extends Resposta {

	@Getter
	private Map<String, Object> data = new TreeMap<>();

	public LookUpJobsetResposta(Operacoes operation, Status status, List<Map<String, String>> jobset) {
		super(operation, status);
		data.put("jobset_size", String.valueOf(jobset.size()));
		data.put("jobset", jobset);
	}

	public LookUpJobsetResposta(Operacoes operation, Status status) {
		super(operation, status);
		data.put("jobset_size", "0");
		data.put("jobset", new ArrayList<Map<String, String>>());
	}

}
