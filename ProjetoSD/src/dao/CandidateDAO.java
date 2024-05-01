package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public Candidate buscarPorEmail(String nomeCandidate) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from candidate where email = ?");

			st.setString(1, nomeCandidate);

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

	public int excluir(int codigo) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from candidate where id_candidate = ?");

			st.setInt(1, codigo);

			int linhasManipuladas = st.executeUpdate();
			
			return linhasManipuladas;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
