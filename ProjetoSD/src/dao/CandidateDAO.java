package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Candidate;

public class CandidateDAO {
	
	private Connection conn;

	public CandidateDAO(Connection conn) {

		this.conn = conn;
	}

	public void cadastrar(Candidate candidate) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("insert into candidate (email, password, name) values (?, ?, ?)");

			st.setString(1, candidate.getEmail());
			st.setString(2, candidate.getPassword());
			st.setString(3, candidate.getName());

			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public List<Candidate> buscarTodos() throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from candidate order by name");

			rs = st.executeQuery();

			List<Candidate> listaCandidate = new ArrayList<>();

			while (rs.next()) {

				Candidate candidate = new Candidate();

				candidate.setIdCandidate(rs.getInt("id_candidate"));
				candidate.setEmail(rs.getString("email"));
				candidate.setPassword(rs.getString("password"));
				candidate.setName(rs.getString("name"));

				listaCandidate.add(candidate);
			}

			return listaCandidate;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}

	public Candidate buscarPorCodigo(int codigoCandidate) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from candidate where id_candidate = ?");

			st.setInt(1, codigoCandidate);

			rs = st.executeQuery();

			if (rs.next()) {

				Candidate candidate = new Candidate();

				candidate.setIdCandidate(rs.getInt("id_candidate"));
				candidate.setEmail(rs.getString("email"));
				candidate.setPassword(rs.getString("password"));
				candidate.setName(rs.getString("name"));

				return candidate;
			}

			return null;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}

	public Candidate buscarPorEmail(String emailCandidate) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from candidate where email = ?");

			st.setString(1, emailCandidate);

			rs = st.executeQuery();

			if (rs.next()) {

				Candidate candidate = new Candidate();

				candidate.setIdCandidate(rs.getInt("id_candidate"));
				candidate.setEmail(rs.getString("email"));
				candidate.setPassword(rs.getString("password"));
				candidate.setName(rs.getString("name"));

				return candidate;
			}

			return null;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Map<String, String>> buscarPerfisPorSkill(List<String> skills) throws SQLException {
        if (skills == null || skills.isEmpty()) {
            return new ArrayList<>();
        }

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT skillset.candidate_id, skillset.id_skillset, skillset.experience, skill.skill ")
                    .append("FROM skillset ")
                    .append("JOIN skill ON skillset.skill_id = skill.id_skill ")
                    .append("WHERE skill.skill IN (");

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
                List<Map<String, String>> profileList = new ArrayList<>();
                while (rs.next()) {
                    Map<String, String> profileData = new HashMap<>();
                    profileData.put("id", rs.getString("id_skillset"));
                    profileData.put("skill", rs.getString("skill"));
                    profileData.put("experience", rs.getString("experience"));
                    profileData.put("id_user", rs.getString("candidate_id"));
                    profileList.add(profileData);
                }
                return profileList;
            }
        }
    }
	
	public List<Map<String, String>> buscarPerfisPorExperiencia(int experience) throws SQLException {
	    String query = "SELECT skillset.candidate_id, skillset.id_skillset, skillset.experience, skill.skill " +
	                   "FROM skillset " +
	                   "JOIN skill ON skillset.skill_id = skill.id_skill " +
	                   "WHERE skillset.experience <= ?";

	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setInt(1, experience);

	        try (ResultSet rs = stmt.executeQuery()) {
	            List<Map<String, String>> profileList = new ArrayList<>();
	            while (rs.next()) {
	            	Map<String, String> profileData = new HashMap<>();
                    profileData.put("id", rs.getString("id_skillset"));
                    profileData.put("skill", rs.getString("skill"));
                    profileData.put("experience", rs.getString("experience"));
                    profileData.put("id_user", rs.getString("candidate_id"));
                    profileList.add(profileData);
	            }
	            return profileList;
	        }
	    }
	}
	
	public List<Map<String, String>> buscarPerfisPorSkillEExperiencia(List<String> skills, int experience, String filter) throws SQLException {
	    if (skills == null || skills.isEmpty()) {
	        return new ArrayList<>();
	    }

	    StringBuilder queryBuilder = new StringBuilder();
	    queryBuilder.append("SELECT skillset.candidate_id, skillset.id_skillset, skillset.experience, skill.skill ")
        			.append("FROM skillset ")
        			.append("JOIN skill ON skillset.skill_id = skill.id_skill ")
	                .append("WHERE skillset.experience <= ? ");

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
	            List<Map<String, String>> profileList = new ArrayList<>();
	            while (rs.next()) {
	            	Map<String, String> profileData = new HashMap<>();
                    profileData.put("id", rs.getString("id_skillset"));
                    profileData.put("skill", rs.getString("skill"));
                    profileData.put("experience", rs.getString("experience"));
                    profileData.put("id_user", rs.getString("candidate_id"));
                    profileList.add(profileData);
	            }
	            return profileList;
	        }
	    }
	}

	public void atualizar(Candidate candidate) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update candidate set email = ?, password = ?, name = ? where id_candidate = ?");

			st.setString(1, candidate.getEmail());
			st.setString(2, candidate.getPassword());
			st.setString(3, candidate.getName());
			st.setInt(4, candidate.getIdCandidate());

			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int excluir(int codigoCandidate) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from candidate where id_candidate = ?");

			st.setInt(1, codigoCandidate);

			int linhasManipuladas = st.executeUpdate();

			return linhasManipuladas;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
