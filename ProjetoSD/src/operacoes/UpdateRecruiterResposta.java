package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import enumerations.Status;
import lombok.Getter;

public class UpdateRecruiterResposta extends Resposta{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public UpdateRecruiterResposta(Operacoes operation, Status status) {
		super(operation, status);
	}
	
}
