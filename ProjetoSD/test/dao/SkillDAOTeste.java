package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import entities.Skill;

public class SkillDAOTeste {
	
	public static void buscarTodosSkillTeste() throws SQLException, IOException {

		Connection conn = BancoDados.conectar();
		List<Skill> listaSkill = new SkillDAO(conn).buscarTodos();

		for (Skill skill : listaSkill) {

			System.out.println(skill.getIdSkill() + " - " + skill.getSkill());
		}
	}
	
	public static void buscarPorCodigoTeste() throws SQLException, IOException {

		int idSkill = 2;

		Connection conn = BancoDados.conectar();
		Skill skill = new SkillDAO(conn).buscarPorCodigo(idSkill);

		if (skill != null) {

			System.out.println(skill.getIdSkill() + " - " + skill.getSkill());

		} else {

			System.out.println("Código não encontrado.");
		}
	}
	
	public static void buscarPorNomeTeste() throws SQLException, IOException {

		String skillNome = "HTML";

		Connection conn = BancoDados.conectar();
		Skill skill = new SkillDAO(conn).buscarPorNome(skillNome);

		if (skill != null) {

			System.out.println(skill.getIdSkill() + " - " + skill.getSkill());


		} else {

			System.out.println("Email não encontrado.");
		}
	}
	
	public static void main(String[] args) {

		try {

			SkillDAOTeste. buscarTodosSkillTeste();
			//SkillDAOTeste.buscarPorCodigoTeste();
			//SkillDAOTeste.buscarPorNomeTeste();

		} catch (SQLException | IOException e) {

			System.out.println(e.getMessage());
		}
	}

}
