package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import entities.Jobset;
import entities.Recruiter;
import entities.Skill;

public class JobsetDAOTeste {
	
public static void cadastrarJobsetTeste() throws SQLException, IOException {
		
		Skill skill = new Skill();
		skill.setIdSkill(7);
		
		Recruiter recruiter = new Recruiter();
		recruiter.setIdRecruiter(2);
		
		Jobset jobset = new Jobset();
		jobset.setExperience("Avançado");
		jobset.setSkill(skill);
		jobset.setRecruiter(recruiter);
		
		Connection conn = BancoDados.conectar();
		new JobsetDAO(conn).cadastrar(jobset);

		System.out.println("Cadastro efetuado com sucesso.");
	}

	public static void buscarTodosJobsetTeste() throws SQLException, IOException {

		Connection conn = BancoDados.conectar();
		List<Jobset> listaJobset = new JobsetDAO(conn).buscarTodos();

		for (Jobset jobset : listaJobset) {

			System.out.println(jobset.getRecruiter().getIdRecruiter() + " - " + jobset.getSkill().getIdSkill() + " - " + jobset.getExperience());
		}
	}
	
	public static void buscarPorRecruiterTeste() throws SQLException, IOException {

		int idRecruiter = 2;
		
		Connection conn = BancoDados.conectar();
		List<Jobset> listaJobset = new JobsetDAO(conn).buscarPorRecruiter(idRecruiter);

		for (Jobset jobset : listaJobset) {

			System.out.println(jobset.getRecruiter().getIdRecruiter() + " - " + jobset.getSkill().getIdSkill() + " - " + jobset.getExperience());
		}
	}

	public static void buscarEspecificaTeste() throws SQLException, IOException {

		int idRecruiter = 1;
		int idSkill = 10;

		Connection conn = BancoDados.conectar();
		Jobset jobset = new JobsetDAO(conn).buscarEspecifica(idRecruiter, idSkill);

		if (jobset != null) {

			System.out.println(jobset.getRecruiter().getIdRecruiter() + " - " + jobset.getSkill().getIdSkill() + " - " + jobset.getExperience());

		} else {

			System.out.println("Não encontrado.");
		}
	}
	

	public static void atualizarJobsetTeste() throws SQLException, IOException, ParseException {

		int idNew = 8;
		String experience = "Básico";
		int idRecruiter = 1;
		int idSkill = 5;

		Connection conn = BancoDados.conectar();
		new JobsetDAO(conn).atualizar(idNew, experience, idRecruiter, idSkill);

		System.out.println("Dados atualizados com sucesso.");
	}

	public static void excluirJobsetTeste() throws SQLException, IOException {

		int idRecruiter = 1;
		int idSkill = 5;

		Connection conn = BancoDados.conectar();
		int linhasManipuladas = new JobsetDAO(conn).excluir(idRecruiter, idSkill);

		if (linhasManipuladas > 0) {

			System.out.println("Excluído com sucesso.");

		} else {

			System.out.println("Nenhum registro foi encontrado.");
		}

	}

	public static void main(String[] args) throws ParseException {

		try {
			
			//JobsetDAOTeste.cadastrarJobsetTeste();
			//JobsetDAOTeste.buscarTodosJobsetTeste();
			//JobsetDAOTeste.buscarPorRecruiterTeste();
			//JobsetDAOTeste.buscarEspecificaTeste();
			JobsetDAOTeste.atualizarJobsetTeste();
			//JobsetDAOTeste.excluirJobsetTeste();

		} catch (SQLException | IOException e) {

			System.out.println(e.getMessage());
		}
	}

}
