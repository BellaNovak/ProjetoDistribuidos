package operacoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import enumerations.Operacoes;
import enumerations.Status;
import lombok.Getter;

public class SearchProfileResposta extends Resposta{
	
	@Getter
	private Map<String, Object> data = new TreeMap<>();

	public SearchProfileResposta(Operacoes operation, Status status, List<Map<String, String>> profile) {
		super(operation, status);
		data.put("profile_size", String.valueOf(profile.size()));
        data.put("profile", profile);
	}

	public SearchProfileResposta(Operacoes operation, Status status) {
		super(operation, status);
		data.put("profile_size", "0");
        data.put("profile", new ArrayList<Map<String, String>>());
	}

}
