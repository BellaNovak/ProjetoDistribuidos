package operacoes;

import java.util.List;
import java.util.TreeMap;

import enumerations.Operacoes;
import lombok.Getter;

public class SearchJobRequisicao extends Requisicao {

	private String token;
	private Operacoes operation;
	private Data data;

	public SearchJobRequisicao(Operacoes operation, String token, Data data) {
		super(operation, token);
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public Operacoes getOperation() {
		return operation;
	}

	public Data getData() {
		return data;
	}

	public static class Data {
		private List<String> skill;
		private String filter;

		public Data(List<String> skill, String filter) {
			this.skill = skill;
			this.filter = filter;
		}

		public List<String> getSkill() {
			return skill;
		}

		public String getFilter() {
			return filter;
		}
	}
	/*@Getter
	private TreeMap<String, String> data = new TreeMap<String, String>();
	
	public SearchJobRequisicao(Operacoes operation, String token, String skill[], String filter) {
		super(operation, token);
		//data.put("skill", skill);
		data.put("filter", filter);
	}
	
	public SearchJobRequisicao(Operacoes operation, String token, String experience, String filter) {
		super(operation, token);
		data.put("experience", experience);
		data.put("filter", filter);
	}
	
	public SearchJobRequisicao(Operacoes operation, String token, String skill[], String experience, String filter) {
		super(operation, token);
		data.put("experience", experience);
		data.put("filter", filter);
	}*/

}
