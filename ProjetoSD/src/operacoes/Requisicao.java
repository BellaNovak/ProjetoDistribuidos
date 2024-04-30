package operacoes;

import enumerations.Operacoes;
import lombok.Getter;
import lombok.Setter;


public class Requisicao {
	
	@Setter
	@Getter
	private Operacoes operation;
	@Getter
	private String token;
	
	public Requisicao(Operacoes operation) {
		this.operation = operation;
		this.token = null;
	}
	
	public Requisicao(Operacoes operation, String token) {
		this.operation = operation;
		this.token = token;
	}

}