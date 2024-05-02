package operacoes;

import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class RequisicaoInvalida extends Requisicao{
	
	@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public RequisicaoInvalida(Operacoes operation) {
		super(operation);
	}

}
