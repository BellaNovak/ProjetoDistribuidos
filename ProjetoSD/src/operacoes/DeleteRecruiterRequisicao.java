package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class DeleteRecruiterRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public DeleteRecruiterRequisicao(Operacoes operation, String token) {
		super(operation, token);
	}

}
