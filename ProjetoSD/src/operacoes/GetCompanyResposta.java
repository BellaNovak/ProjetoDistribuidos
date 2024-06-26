package operacoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import enumerations.Operacoes;
import enumerations.Status;
import lombok.Getter;

public class GetCompanyResposta extends Resposta{
	
	@Getter
	private Map<String, Object> data = new TreeMap<>();

	public GetCompanyResposta(Operacoes operation, Status status, List<Map<String, String>> company) {
		super(operation, status);
		data.put("company_size", String.valueOf(company.size()));
        data.put("company", company);
	}

	public GetCompanyResposta(Operacoes operation, Status status) {
		super(operation, status);
		data.put("company_size", "0");
        data.put("company", new ArrayList<Map<String, String>>());
	}

}
