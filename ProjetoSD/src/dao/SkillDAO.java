package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Skill;

public class SkillDAO {
	
	private Connection conn;

	public SkillDAO(Connection conn) {

		this.conn = conn;
	}
	
	public List<Skill> buscarTodos() throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from skill order by id_skill");

			rs = st.executeQuery();

			List<Skill> listaSkill = new ArrayList<>();

			while (rs.next()) {

				Skill skill = new Skill();
				
				skill.setIdSkill(rs.getInt("id_skill"));
				skill.setSkill(rs.getString("skill"));

				listaSkill.add(skill);
			}

			return listaSkill;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}

	public Skill buscarPorCodigo(int idSkill) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from skill where id_skill = ?");

			st.setInt(1, idSkill);

			rs = st.executeQuery();

			if (rs.next()) {

				Skill skill = new Skill();

				skill.setIdSkill(rs.getInt("id_skill"));
				skill.setSkill(rs.getString("skill"));

				return skill;
			}

			return null;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}

	public Skill buscarPorNome(String skillNome) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from skill where skill = ?");

			st.setString(1, skillNome);

			rs = st.executeQuery();

			if (rs.next()) {

				Skill skill = new Skill();

				skill.setIdSkill(rs.getInt("id_skill"));
				skill.setSkill(rs.getString("skill"));

				return skill;
			}

			return null;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}

}
