package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Choose;

public class ChooseDAO {
	
	private Connection conn;

	public ChooseDAO(Connection conn) {

		this.conn = conn;
	}

	public void cadastrar(Choose choose) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("insert into choose (candidate_id, recruiter_id) values (?, ?)");

			st.setInt(1, choose.getCandidate().getIdCandidate());
			st.setInt(2, choose.getRecruiter().getIdRecruiter());

			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public List<Map<String, String>> buscarEmpresasPorCandidato(int candidateId) throws SQLException {
        String query = "SELECT recruiter.name, recruiter.industry, recruiter.email, recruiter.description " +
                       "FROM choose " +
                       "JOIN recruiter ON choose.recruiter_id = recruiter.id_recruiter " +
                       "WHERE choose.candidate_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, candidateId);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Map<String, String>> companyList = new ArrayList<>();
                while (rs.next()) {
                    Map<String, String> companyData = new HashMap<>();
                    companyData.put("name", rs.getString("name"));
                    companyData.put("industry", rs.getString("industry"));
                    companyData.put("email", rs.getString("email"));
                    companyData.put("description", rs.getString("description"));
                    companyList.add(companyData);
                }
                return companyList;
            }
        }
    }

}
