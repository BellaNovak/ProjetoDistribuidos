package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class LogoutCandidateRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public LogoutCandidateRequisicao(Operacoes operation, String token) {
		super(operation, token);
	}
}