package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class LookUpJobsetRequisicao extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public LookUpJobsetRequisicao(Operacoes operation, String token) {
		super(operation, token);
	}

}
