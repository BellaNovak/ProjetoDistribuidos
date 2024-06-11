package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import entities.Candidate;
import entities.Skill;
import entities.Skillset;


public class SkillsetDAO {
	
	private Connection conn;

	public SkillsetDAO(Connection conn) {

		this.conn = conn;
	}

	public void cadastrar(Skillset skillset) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("insert into skillset (candidate_id, skill_id, experience) values (?, ?, ?)");

			st.setInt(1,  skillset.getCandidate().getIdCandidate());
			st.setInt(2, skillset.getSkill().getIdSkill());
			st.setString(3, skillset.getExperience());

			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public List<Skillset> buscarTodos() throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from skillset");

			rs = st.executeQuery();

			List<Skillset> listaSkillset = new ArrayList<>();

			while (rs.next()) {
				
				Skillset skillset = new Skillset();

	            Candidate candidate = new Candidate();
	            candidate.setIdCandidate(rs.getInt("candidate_id"));
	          
	            Skill skill = new Skill();
	            skill.setIdSkill(rs.getInt("skill_id"));
	              
	            skillset.setCandidate(candidate);
	            skillset.setSkill(skill);
	            skillset.setExperience(rs.getString("experience"));

	            listaSkillset.add(skillset);

			}

			return listaSkillset;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}

	public List<Skillset> buscarPorCandidate(int idCandidate) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from skillset where candidate_id = ?");
			
			st.setInt(1, idCandidate);

			rs = st.executeQuery();

			List<Skillset> listaSkillset = new ArrayList<>();

			while (rs.next()) {

				Skillset skillset = new Skillset();

	            Candidate candidate = new Candidate();
	            candidate.setIdCandidate(rs.getInt("candidate_id"));
	          
	            Skill skill = new Skill();
	            skill.setIdSkill(rs.getInt("skill_id"));
	              
	            skillset.setCandidate(candidate);
	            skillset.setSkill(skill);
	            skillset.setExperience(rs.getString("experience"));
		
				listaSkillset.add(skillset);
			}

			return listaSkillset;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Map<String, String>> buscarHabilidadesPorCandidate(int idCandidate) {

		//PreparedStatement st = null;

		List<Map<String, String>> skills = new ArrayList<>();

		try {

			//st = conn.prepareStatement("select skill_id, experience from skillset where candidate_id = ?");
			String sql = "SELECT skill.skill, skillset.experience " +
                     "FROM skillset " +
                     "JOIN skill ON skillset.skill_id = skill.id_skill " +
                     "WHERE skillset.candidate_id = ?";

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, idCandidate);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Map<String, String> skill = new TreeMap<>();
				skill.put("skill", rs.getString("skill"));
				skill.put("experience", rs.getString("experience"));
				skills.add(skill);
			}

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return skills;
	}
	
	public Skillset buscarEspecifica(int idCandidate, int idSkill) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from skillset where candidate_id = ? and skill_id = ?");

			st.setInt(1, idCandidate);
			st.setInt(2, idSkill);

			rs = st.executeQuery();

			if (rs.next()) {

				Skillset skillset = new Skillset();

	            Candidate candidate = new Candidate();
	            candidate.setIdCandidate(rs.getInt("candidate_id"));
	          
	            Skill skill = new Skill();
	            skill.setIdSkill(rs.getInt("skill_id"));
	              
	            skillset.setCandidate(candidate);
	            skillset.setSkill(skill);
	            skillset.setExperience(rs.getString("experience"));
				
				return skillset;
			}

			return null;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	

	/*public void atualizar(Skillset skillset) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update skillset set skill_id = ?, experience = ? where candidate_id = ? and skill_id = ?");
			//UPDATE `skillset` SET `skill_id`='8',`experience`='Intermediário' WHERE `candidate_id`='1' AND `skill_id`='5'

			st.setInt(1, skillset.getSkill().getIdSkill());
			st.setString(2, skillset.getExperience());
			st.setInt(3, skillset.getCandidate().getIdCandidate());
			st.setInt(4, skillset.getSkill().getIdSkill());

			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}*/
	
	public void atualizar(int newSkill, String experience, int idCandidate, int idSkill) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update skillset set skill_id = ?, experience = ? where candidate_id = ? and skill_id = ?");
		
			st.setInt(1, newSkill);
			st.setString(2, experience);
			st.setInt(3, idCandidate);
			st.setInt(4, idSkill);

			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int excluir(int idCandidate, int idSkill) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from skillset where candidate_id = ? and skill_id = ?");

			st.setInt(1, idCandidate);
			st.setInt(2, idSkill);

			int linhasManipuladas = st.executeUpdate();
			
			return linhasManipuladas;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}


}
