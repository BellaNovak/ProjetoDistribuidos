package operacoes;

import enumerations.Operacoes;
import enumerations.Status;
import lombok.Getter;
import lombok.Setter;

public class Resposta {
	
	@Setter
	@Getter
	private Operacoes operation;
	@Setter
	@Getter
	private Status status;
	
	public Resposta(Operacoes operation, Status status) {
		this.operation = operation;
		this.status = status;
	}

}