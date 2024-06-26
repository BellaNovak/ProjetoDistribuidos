package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import enumerations.Status;
import lombok.Getter;

public class SetJobSearchableResposta extends Resposta{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public  SetJobSearchableResposta(Operacoes operation, Status status) {
		super(operation, status);
	}
}
