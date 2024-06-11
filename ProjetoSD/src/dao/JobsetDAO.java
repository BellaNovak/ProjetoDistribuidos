package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import entities.Jobset;
import entities.Recruiter;
import entities.Skill;

public class JobsetDAO {
	
	private Connection conn;

	public JobsetDAO(Connection conn) {

		this.conn = conn;
	}

	public void cadastrar(Jobset jobset) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("insert into jobset (recruiter_id, skill_id, experience) values (?, ?, ?)");

			st.setInt(1,  jobset.getRecruiter().getIdRecruiter());
			st.setInt(2, jobset.getSkill().getIdSkill());
			st.setString(3, jobset.getExperience());

			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public List<Jobset> buscarTodos() throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from jobset");

			rs = st.executeQuery();

			List<Jobset> listaJobset = new ArrayList<>();

			while (rs.next()) {
				
				Jobset jobset = new Jobset();

	            Recruiter recruiter = new Recruiter();
	            recruiter.setIdRecruiter(rs.getInt("recruiter_id"));
	          
	            Skill skill = new Skill();
	            skill.setIdSkill(rs.getInt("skill_id"));
	              
	            jobset.setRecruiter(recruiter);
	            jobset.setSkill(skill);
	            jobset.setExperience(rs.getString("experience"));

	            listaJobset.add(jobset);

			}

			return listaJobset;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}

	public List<Jobset> buscarPorRecruiter(int idRecruiter) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from jobset where recruiter_id = ?");
			
			st.setInt(1, idRecruiter);

			rs = st.executeQuery();

			List<Jobset> listaJobset = new ArrayList<>();

			while (rs.next()) {

				Jobset jobset = new Jobset();

	            Recruiter recruiter = new Recruiter();
	            recruiter.setIdRecruiter(rs.getInt("recruiter_id"));
	          
	            Skill skill = new Skill();
	            skill.setIdSkill(rs.getInt("skill_id"));
	              
	            jobset.setRecruiter(recruiter);
	            jobset.setSkill(skill);
	            jobset.setExperience(rs.getString("experience"));

	            listaJobset.add(jobset);
	            
			}

			return listaJobset;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Map<String, String>> buscarHabilidadesPorRecruiter(int idRecruiter) {

		//PreparedStatement st = null;

		List<Map<String, String>> jobs = new ArrayList<>();

		try {

			//st = conn.prepareStatement("select skill_id, experience from skillset where candidate_id = ?");
			String sql = "SELECT skill.skill, jobset.experience, jobset.skill_id " +
                     "FROM jobset " +
                     "JOIN skill ON jobset.skill_id = skill.id_skill " +
                     "WHERE jobset.recruiter_id = ?";

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, idRecruiter);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Map<String, String> job = new TreeMap<>();
				job.put("skill", rs.getString("skill"));
				job.put("experience", rs.getString("experience"));
				job.put("id", String.valueOf(rs.getInt("skill_id")));
				jobs.add(job);
			}

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jobs;
	}
	
	public Jobset buscarEspecifica(int idRecruiter, int idSkill) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from jobset where recruiter_id = ? and skill_id = ?");

			st.setInt(1, idRecruiter);
			st.setInt(2, idSkill);

			rs = st.executeQuery();

			if (rs.next()) {

				Jobset jobset = new Jobset();

	            Recruiter recruiter = new Recruiter();
	            recruiter.setIdRecruiter(rs.getInt("recruiter_id"));
	          
	            Skill skill = new Skill();
	            skill.setIdSkill(rs.getInt("skill_id"));
	              
	            jobset.setRecruiter(recruiter);
	            jobset.setSkill(skill);
	            jobset.setExperience(rs.getString("experience"));
				
				return jobset;
			}

			return null;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Map<String, String>> buscarEmpregosPorCriterios(List<String> skills, String filter) {
        List<Map<String, String>> jobs = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("SELECT skill.skill, jobset.experience, jobset.skill_id FROM jobset JOIN skill ON jobset.skill_id = skill.id_skill");

            if (skills != null && !skills.isEmpty()) {
                sql.append(" WHERE ");
                for (int i = 0; i < skills.size(); i++) {
                    if (i > 0) {
                        sql.append(filter.equalsIgnoreCase("E") ? " AND " : " OR ");
                    }
                    sql.append("skill.skill = ?");
                }
            }

            PreparedStatement stmt = conn.prepareStatement(sql.toString());

            if (skills != null && !skills.isEmpty()) {
                for (int i = 0; i < skills.size(); i++) {
                    stmt.setString(i + 1, skills.get(i));
                }
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, String> job = new TreeMap<>();
                job.put("skill", rs.getString("skill"));
                job.put("experience", rs.getString("experience"));
                job.put("id", String.valueOf(rs.getInt("skill_id")));
                jobs.add(job);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobs;
    }
	
	
	public void atualizar(int idNew, String experience, int idRecruiter, int idSkill) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update jobset set skill_id = ?, experience = ? where recruiter_id = ? and skill_id = ?");
		
			st.setInt(1, idNew);
			st.setString(2, experience);
			st.setInt(3, idRecruiter);
			st.setInt(4, idSkill);

			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int excluir(int idRecruiter, int idSkill) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from jobset where recruiter_id = ? and skill_id = ?");

			st.setInt(1, idRecruiter);
			st.setInt(2, idSkill);

			int linhasManipuladas = st.executeUpdate();
			
			return linhasManipuladas;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

}
