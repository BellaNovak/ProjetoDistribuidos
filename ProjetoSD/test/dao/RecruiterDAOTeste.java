package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import entities.Recruiter;

public class RecruiterDAOTeste {
	
	public static void cadastrarRecruiterTeste() throws SQLException, IOException {

		Recruiter recruiter = new Recruiter();
		recruiter.setEmail("bella@gmail.com");
		recruiter.setPassword("abc123");
		recruiter.setName("Isabella Novaes");
		recruiter.setIndustry("Unimed");
		recruiter.setDescription("Empresa de saúde");

		Connection conn = BancoDados.conectar();
		new RecruiterDAO(conn).cadastrar(recruiter);

		System.out.println("Cadastro efetuado com sucesso.");
	}
	
	public static void buscarTodosRecruiterTeste() throws SQLException, IOException {

		Connection conn = BancoDados.conectar();
		List<Recruiter> listaRecruiter = new RecruiterDAO(conn).buscarTodos();

		for (Recruiter recruiter : listaRecruiter) {

			System.out.println(recruiter.getIdRecruiter() + " - " + recruiter.getEmail() + " - " + recruiter.getPassword() + " - " + recruiter.getName() + " - " + recruiter.getIndustry() + " - " + recruiter.getDescription());
		}
	}
	
	public static void buscarPorCodigoTeste() throws SQLException, IOException {

		int codigoRecruiter = 2;

		Connection conn = BancoDados.conectar();
		Recruiter recruiter = new RecruiterDAO(conn).buscarPorCodigo(codigoRecruiter);

		if (recruiter != null) {

			System.out.println(recruiter.getIdRecruiter() + " - " + recruiter.getEmail() + " - " + recruiter.getPassword() + " - " + recruiter.getName() + " - " + recruiter.getIndustry() + " - " + recruiter.getDescription());

		} else {

			System.out.println("Código não encontrado.");
		}
	}
	
	public static void buscarPorEmailTeste() throws SQLException, IOException {

		String emailRecruiter = "bella";

		Connection conn = BancoDados.conectar();
		Recruiter recruiter = new RecruiterDAO(conn).buscarPorEmail(emailRecruiter);

		if (recruiter != null) {

			System.out.println(recruiter.getIdRecruiter() + " - " + recruiter.getEmail() + " - " + recruiter.getPassword() + " - " + recruiter.getName() + " - " + recruiter.getIndustry() + " - " + recruiter.getDescription());


		} else {

			System.out.println("Email não encontrado.");
		}
	}
	
	public static void atualizarRecruiterTeste() throws SQLException, IOException {

		Recruiter recruiter = new Recruiter();
		recruiter.setIdRecruiter(2);
		recruiter.setEmail("bella99@gmail.com");
		recruiter.setPassword("12333");
		recruiter.setName("Isabella Novaes");
		recruiter.setIndustry("Embraer");
		recruiter.setDescription("Empresa de avião");
		
		Connection conn = BancoDados.conectar();
		new RecruiterDAO(conn).atualizar(recruiter);

		System.out.println("Dados da empresa atualizados com sucesso.");
	}
	
	public static void excluirRecruiterTeste() throws SQLException, IOException {

		int codigoRecruiter = 1;

		Connection conn = BancoDados.conectar();
		int linhasManipuladas = new CandidateDAO(conn).excluir(codigoRecruiter);

		if (linhasManipuladas > 0) {

			System.out.println("Empresa excluída com sucesso.");

		} else {

			System.out.println("Nenhum registro foi encontrado.");
		}

	}

	public static void main(String[] args) {

		try {

			//RecruiterDAOTeste.cadastrarRecruiterTeste();
			//RecruiterDAOTeste. buscarTodosRecruiterTeste();
			//RecruiterDAOTeste.buscarPorCodigoTeste();
			RecruiterDAOTeste.buscarPorEmailTeste();
			//RecruiterDAOTeste.atualizarRecruiterTeste();
			//RecruiterDAOTeste.excluirRecruiterTeste();

		} catch (SQLException | IOException e) {

			System.out.println(e.getMessage());
		}
	}

}
