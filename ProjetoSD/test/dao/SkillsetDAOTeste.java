package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import entities.Candidate;
import entities.Skill;
import entities.Skillset;

public class SkillsetDAOTeste {
	
	public static void cadastrarSkillsetTeste() throws SQLException, IOException {
		
		Skill skill = new Skill();
		skill.setIdSkill(10);
		
		Candidate candidate = new Candidate();
		candidate.setIdCandidate(6);
		
		Skillset skillset = new Skillset();
		skillset.setExperience("Intermediário");
		skillset.setSkill(skill);
		skillset.setCandidate(candidate);
		
		Connection conn = BancoDados.conectar();
		new SkillsetDAO(conn).cadastrar(skillset);

		System.out.println("Cadastro efetuado com sucesso.");
	}

	public static void buscarTodosSkillsetTeste() throws SQLException, IOException {

		Connection conn = BancoDados.conectar();
		List<Skillset> listaSkillset = new SkillsetDAO(conn).buscarTodos();

		for (Skillset skillset : listaSkillset) {

			System.out.println(skillset.getCandidate().getIdCandidate() + " - " + skillset.getSkill().getIdSkill() + " - " + skillset.getExperience());
		}
	}
	
	public static void buscarPorCandidateTeste() throws SQLException, IOException {

		int idCandidate = 5;
		
		Connection conn = BancoDados.conectar();
		List<Skillset> listaSkillset = new SkillsetDAO(conn).buscarPorCandidate(idCandidate);

		for (Skillset skillset : listaSkillset) {

			System.out.println(skillset.getCandidate().getIdCandidate() + " - " + skillset.getSkill().getIdSkill() + " - " + skillset.getExperience());
		}
	}

	public static void buscarEspecificaTeste() throws SQLException, IOException {

		int idCandidate = 5;
		int idSkill = 7;

		Connection conn = BancoDados.conectar();
		Skillset skillset = new SkillsetDAO(conn).buscarEspecifica(idCandidate, idSkill);

		if (skillset != null) {

			System.out.println(skillset.getCandidate().getIdCandidate() + " - " + skillset.getSkill().getIdSkill() + " - " + skillset.getExperience());

		} else {

			System.out.println("Não encontrado.");
		}
	}
	

	public static void atualizarSkillsetTeste() throws SQLException, IOException, ParseException {

		
		int newSkill = 10;
		String experience = "12";
		int idCandidate = 1;
		int idSkill = 10;

		Connection conn = BancoDados.conectar();
		new SkillsetDAO(conn).atualizar(newSkill, experience, idCandidate, idSkill);

		System.out.println("Dados atualizados com sucesso.");
	}

	public static void excluirSkillsetTeste() throws SQLException, IOException {

		int idCandidate = 1;
		int idSkill = 7;

		Connection conn = BancoDados.conectar();
		int linhasManipuladas = new SkillsetDAO(conn).excluir(idCandidate, idSkill);

		if (linhasManipuladas > 0) {

			System.out.println("Excluído com sucesso.");

		} else {

			System.out.println("Nenhum registro foi encontrado.");
		}

	}

	public static void main(String[] args) throws ParseException {

		try {
			
			//SkillsetDAOTeste.cadastrarSkillsetTeste();
			//SkillsetDAOTeste.buscarTodosSkillsetTeste();
			//SkillsetDAOTeste.buscarPorCandidateTeste();
			//SkillsetDAOTeste.buscarEspecificaTeste();
			SkillsetDAOTeste.atualizarSkillsetTeste();
			//SkillsetDAOTeste.excluirSkillsetTeste();

		} catch (SQLException | IOException e) {

			System.out.println(e.getMessage());
		}
	}

}
