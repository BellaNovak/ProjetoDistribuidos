package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import entities.Candidate;

public class CandidateDAOTeste {
	
	public static void cadastrarCandidateTeste() throws SQLException, IOException {

		Candidate candidate = new Candidate();
		candidate.setEmail("bella@gmail.com");
		candidate.setPassword("abc123");
		candidate.setName("Isabella Novaes");

		Connection conn = BancoDados.conectar();
		new CandidateDAO(conn).cadastrar(candidate);

		System.out.println("Cadastro efetuado com sucesso.");
	}
	
	public static void buscarTodosCandidateTeste() throws SQLException, IOException {

		Connection conn = BancoDados.conectar();
		List<Candidate> listaCandidate = new CandidateDAO(conn).buscarTodos();

		for (Candidate candidate : listaCandidate) {

			System.out.println(candidate.getIdCandidate() + " - " + candidate.getEmail() + " - " + candidate.getPassword() + " - " + candidate.getName());
		}
	}
	
	public static void buscarPorCodigoTeste() throws SQLException, IOException {

		int codigoCandidate = 5;

		Connection conn = BancoDados.conectar();
		Candidate candidate = new CandidateDAO(conn).buscarPorCodigo(codigoCandidate);

		if (candidate != null) {

			System.out.println(candidate.getIdCandidate() + " - " + candidate.getEmail() + " - " + candidate.getPassword() + " - " + candidate.getName());

		} else {

			System.out.println("Código não encontrado.");
		}
	}
	
	public static void buscarPorEmailTeste() throws SQLException, IOException {

		String emailCandidate = "bella";

		Connection conn = BancoDados.conectar();
		Candidate candidate = new CandidateDAO(conn).buscarPorEmail(emailCandidate);

		if (candidate != null) {

			System.out.println(candidate.getIdCandidate() + " - " + candidate.getEmail() + " - " + candidate.getPassword() + " - " + candidate.getName());


		} else {

			System.out.println("Email não encontrado.");
		}
	}
	
	public static void atualizarCandidateTeste() throws SQLException, IOException {

		Candidate candidate = new Candidate();
		candidate.setIdCandidate(2);
		candidate.setEmail("bella99@gmail.com");
		candidate.setPassword("12333");
		candidate.setName("Isabella Novaes");
		
		Connection conn = BancoDados.conectar();
		new CandidateDAO(conn).atualizar(candidate);

		System.out.println("Dados do candidato atualizados com sucesso.");
	}
	
	public static void excluirCandidateTeste() throws SQLException, IOException {

		int codigoCandidate = 1;

		Connection conn = BancoDados.conectar();
		int linhasManipuladas = new CandidateDAO(conn).excluir(codigoCandidate);

		if (linhasManipuladas > 0) {

			System.out.println("Candidato excluída com sucesso.");

		} else {

			System.out.println("Nenhum registro foi encontrado.");
		}

	}

	public static void main(String[] args) {

		try {

			//CandidateDAOTeste.cadastrarCandidateTeste();
			CandidateDAOTeste. buscarTodosCandidateTeste();
			//CandidateDAOTeste.buscarPorCodigoTeste();
			//CandidateDAOTeste.buscarPorEmailTeste();
			//CandidateDAOTeste.atualizarCandidateTeste();
			//CandidateDAOTeste.excluirCandidateTeste();

		} catch (SQLException | IOException e) {

			System.out.println(e.getMessage());
		}
	}
}
