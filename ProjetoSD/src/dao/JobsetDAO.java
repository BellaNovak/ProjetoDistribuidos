package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

			st = conn.prepareStatement("insert into jobset (recruiter_id, skill_id, experience, available, searchable) values (?, ?, ?, ?, ?)");

			st.setInt(1,  jobset.getRecruiter().getIdRecruiter());
			st.setInt(2, jobset.getSkill().getIdSkill());
			st.setString(3, jobset.getExperience());
			st.setString(4, jobset.getAvailable());
			st.setString(5, jobset.getSearchable());

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
	            jobset.setIdJobset(rs.getInt("id_jobset"));
	            jobset.setExperience(rs.getString("experience"));
	            jobset.setAvailable(rs.getString("available"));
	            jobset.setSearchable(rs.getString("searchable"));

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
	            jobset.setIdJobset(rs.getInt("id_jobset"));
	            jobset.setExperience(rs.getString("experience"));
	            jobset.setAvailable(rs.getString("available"));
	            jobset.setSearchable(rs.getString("searchable"));

	            listaJobset.add(jobset);
	            
			}

			return listaJobset;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Map<String, String>> buscarVagasPorRecruiter(int idRecruiter) {

		List<Map<String, String>> jobs = new ArrayList<>();

		try {

			String sql = "SELECT skill.skill, jobset.experience, jobset.skill_id, jobset.id_jobset, jobset.available, jobset.searchable " +
                     "FROM jobset " +
                     "JOIN skill ON jobset.skill_id = skill.id_skill " +
                     "WHERE jobset.recruiter_id = ? ";

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, idRecruiter);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Map<String, String> job = new TreeMap<>();
				job.put("skill", rs.getString("skill"));
				job.put("experience", rs.getString("experience"));
				job.put("id", String.valueOf(rs.getInt("id_jobset")));
				job.put("available", rs.getString("available"));
				job.put("searchable", rs.getString("searchable"));
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
	
	public List<Map<String, String>> buscarEmpregosPorSkill(List<String> skills) throws SQLException {
	    if (skills == null || skills.isEmpty()) {
	        return new ArrayList<>();
	    }

	    StringBuilder queryBuilder = new StringBuilder();
	    queryBuilder.append("SELECT jobset.recruiter_id, jobset.experience, skill.skill, jobset.available ")
	                .append("FROM jobset ")
	                .append("JOIN skill ON jobset.skill_id = skill.id_skill ")
	                .append("WHERE jobset.searchable = 'YES' AND skill.skill IN (");

	    for (int i = 0; i < skills.size(); i++) {
	        queryBuilder.append("?");
	        if (i < skills.size() - 1) {
	            queryBuilder.append(", ");
	        }
	    }
	    queryBuilder.append(")");

	    String query = queryBuilder.toString();

	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        for (int i = 0; i < skills.size(); i++) {
	            stmt.setString(i + 1, skills.get(i));
	        }

	        try (ResultSet rs = stmt.executeQuery()) {
	            List<Map<String, String>> jobList = new ArrayList<>();
	            while (rs.next()) {
	                Map<String, String> jobData = new HashMap<>();
	                jobData.put("id", rs.getString("recruiter_id"));
	                jobData.put("experience", rs.getString("experience"));
	                jobData.put("skill", rs.getString("skill"));
					jobData.put("available", rs.getString("available"));
	                jobList.add(jobData);
	            }
	            return jobList;
	        }
	    }
	}
	
	public List<Map<String, String>> buscarEmpregosPorExperiencia(int experience) throws SQLException {
	    String query = "SELECT jobset.recruiter_id, jobset.experience, skill.skill, jobset.available " +
	                   "FROM jobset " +
	                   "JOIN skill ON jobset.skill_id = skill.id_skill " +
	                   "WHERE jobset.experience <= ? AND jobset.searchable = 'YES'";

	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setInt(1, experience);

	        try (ResultSet rs = stmt.executeQuery()) {
	            List<Map<String, String>> jobList = new ArrayList<>();
	            while (rs.next()) {
	                Map<String, String> jobData = new HashMap<>();
	                jobData.put("id", rs.getString("recruiter_id"));
	                jobData.put("experience", rs.getString("experience"));
	                jobData.put("skill", rs.getString("skill"));
					jobData.put("available", rs.getString("available"));
	                jobList.add(jobData);
	            }
	            return jobList;
	        }
	    }
	}
	
	public List<Map<String, String>> buscarEmpregosPorSkillEExperiencia(List<String> skills, int experience, String filter) throws SQLException {
	    if (skills == null || skills.isEmpty()) {
	        return new ArrayList<>();
	    }

	    StringBuilder queryBuilder = new StringBuilder();
	    queryBuilder.append("SELECT jobset.recruiter_id, jobset.experience, skill.skill, jobset.available ")
	                .append("FROM jobset ")
	                .append("JOIN skill ON jobset.skill_id = skill.id_skill ")
	                .append("WHERE jobset.searchable = 'YES' ")
                    .append("AND jobset.experience <= ? ");

	    if (filter.equalsIgnoreCase("AND")) {
	        queryBuilder.append("AND skill.skill IN (");
	    } else {
	        queryBuilder.append("OR skill.skill IN (");
	    }

	    for (int i = 0; i < skills.size(); i++) {
	        queryBuilder.append("?");
	        if (i < skills.size() - 1) {
	            queryBuilder.append(", ");
	        }
	    }
	    queryBuilder.append(")");

	    String query = queryBuilder.toString();

	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setInt(1, experience);
	        for (int i = 0; i < skills.size(); i++) {
	            stmt.setString(i + 2, skills.get(i));
	        }

	        try (ResultSet rs = stmt.executeQuery()) {
	            List<Map<String, String>> jobList = new ArrayList<>();
	            while (rs.next()) {
	                Map<String, String> jobData = new HashMap<>();
	                jobData.put("id", rs.getString("recruiter_id"));
	                jobData.put("experience", rs.getString("experience"));
	                jobData.put("skill", rs.getString("skill"));
					jobData.put("available", rs.getString("available"));
	                jobList.add(jobData);
	            }
	            return jobList;
	        }
	    }
	}
	
	
	public Jobset buscarEspecifica(int idJobset, int idRecruiter) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from jobset where id_jobset = ? and recruiter_id = ?");

			st.setInt(1, idJobset);
			st.setInt(2, idRecruiter);

			rs = st.executeQuery();

			if (rs.next()) {

				Jobset jobset = new Jobset();

	            Recruiter recruiter = new Recruiter();
	            recruiter.setIdRecruiter(rs.getInt("recruiter_id"));
	          
	            Skill skill = new Skill();
	            skill.setIdSkill(rs.getInt("skill_id"));
	              
	            jobset.setRecruiter(recruiter);
	            jobset.setSkill(skill);
	            jobset.setIdJobset(rs.getInt("id_jobset"));
	            jobset.setExperience(rs.getString("experience"));
	            jobset.setAvailable(rs.getString("available"));
	            jobset.setSearchable(rs.getString("searchable"));
				
				return jobset;
			}

			return null;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public void atualizarDisponivel(String available, int idJobset, int idRecruiter) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update jobset set available = ? where id_jobset = ? and recruiter_id = ?");
		
			st.setString(1, available);
			st.setInt(2, idJobset);
			st.setInt(3, idRecruiter);

			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public void atualizarDivulgavel(String searchable, int idJobset, int idRecruiter) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update jobset set searchable = ? where id_jobset = ? and recruiter_id = ?");
		
			st.setString(1, searchable);
			st.setInt(2, idJobset);
			st.setInt(3, idRecruiter);

			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public void atualizar(int idSkill, String experience, int idJobset, int idRecruiter) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update jobset set skill_id = ?, experience = ? where id_jobset = ? and recruiter_id = ?");
		
			st.setInt(1, idSkill);
			st.setString(2, experience);
			st.setInt(3, idJobset);
			st.setInt(4, idRecruiter);

			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int excluir(int idJobset, int idRecruiter) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from jobset where id_jobset = ? and recruiter_id = ?");

			st.setInt(1, idJobset);
			st.setInt(2, idRecruiter);

			int linhasManipuladas = st.executeUpdate();
			
			return linhasManipuladas;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

}
