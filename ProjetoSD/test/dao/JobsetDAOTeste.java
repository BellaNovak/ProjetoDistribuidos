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
		recruiter.setIdRecruiter(1);
		
		Jobset jobset = new Jobset();
		jobset.setExperience("2");
		jobset.setSkill(skill);
		jobset.setRecruiter(recruiter);
		jobset.setAvailable("yes");
		jobset.setSearchable("no");
		
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

		int idJobset = 4;
		int idRecruiter = 1;

		Connection conn = BancoDados.conectar();
		Jobset jobset = new JobsetDAO(conn).buscarEspecifica(idJobset, idRecruiter);

		if (jobset != null) {

			System.out.println(jobset.getRecruiter().getIdRecruiter() + " - " + jobset.getSkill().getIdSkill() + " - " + jobset.getExperience());

		} else {

			System.out.println("Não encontrado.");
		}
	}
	
	public static void atualizarDisponivelTeste() throws SQLException, IOException, ParseException {

		String available = "yes";
		int idJobset = 5;
		int idRecruiter = 4;

		Connection conn = BancoDados.conectar();
		new JobsetDAO(conn).atualizarDisponivel(available, idJobset, idRecruiter);

		System.out.println("Dados atualizados com sucesso.");
	}
	
	public static void atualizarDivulgavelTeste() throws SQLException, IOException, ParseException {

		String searchable = "YES";
		int idJobset = 8;
		int idRecruiter = 1;

		Connection conn = BancoDados.conectar();
		new JobsetDAO(conn).atualizarDivulgavel(searchable, idJobset, idRecruiter);

		System.out.println("Dados atualizados com sucesso.");
	}
	
	public static void atualizarJobsetTeste() throws SQLException, IOException, ParseException {

		int idSkill = 5;
		String experience = "2";
		int idJobset = 5;
		int idRecruiter = 2;

		Connection conn = BancoDados.conectar();
		new JobsetDAO(conn).atualizar(idSkill, experience, idJobset, idRecruiter);

		System.out.println("Dados atualizados com sucesso.");
	}

	public static void excluirJobsetTeste() throws SQLException, IOException {

		int idJobset = 2;
		int idRecruiter = 3;

		Connection conn = BancoDados.conectar();
		int linhasManipuladas = new JobsetDAO(conn).excluir(idJobset, idRecruiter);

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
			//JobsetDAOTeste.atualizarDisponivelTeste();
			JobsetDAOTeste.atualizarDivulgavelTeste();
			//JobsetDAOTeste.atualizarJobsetTeste();
			//JobsetDAOTeste.excluirJobsetTeste();

		} catch (SQLException | IOException e) {

			System.out.println(e.getMessage());
		}
	}

}
