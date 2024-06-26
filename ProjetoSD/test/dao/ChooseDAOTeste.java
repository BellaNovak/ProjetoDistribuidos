package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import entities.Candidate;
import entities.Choose;
import entities.Recruiter;

public class ChooseDAOTeste {
	
	public static void cadastrarChooseTeste() throws SQLException, IOException {
		
		Candidate candidate = new Candidate();
		candidate.setIdCandidate(1);
		
		Recruiter recruiter = new Recruiter();
		recruiter.setIdRecruiter(2);
		
		Choose choose = new Choose();
		choose.setCandidate(candidate);
		choose.setRecruiter(recruiter);
		
		Connection conn = BancoDados.conectar();
		new ChooseDAO(conn).cadastrar(choose);

		System.out.println("Cadastro efetuado com sucesso.");
	}
	
	public static void main(String[] args) throws ParseException {

		try {
			
			ChooseDAOTeste.cadastrarChooseTeste();

		} catch (SQLException | IOException e) {

			System.out.println(e.getMessage());
		}
	}

}
