package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Recruiter;

public class RecruiterDAO {
	
	private Connection conn;

	public RecruiterDAO(Connection conn) {

		this.conn = conn;
	}

	public void cadastrar(Recruiter recruiter) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("insert into recruiter (email, password, name, industry, description) values (?, ?, ?, ?, ?)");

			st.setString(1, recruiter.getEmail());
			st.setString(2, recruiter.getPassword());
			st.setString(3, recruiter.getName());
			st.setString(4, recruiter.getIndustry());
			st.setString(5, recruiter.getDescription());

			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public List<Recruiter> buscarTodos() throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from recruiter order by name");

			rs = st.executeQuery();

			List<Recruiter> listaRecruiter = new ArrayList<>();

			while (rs.next()) {

				Recruiter recruiter = new Recruiter();

				recruiter.setIdRecruiter(rs.getInt("id_recruiter"));
				recruiter.setEmail(rs.getString("email"));
				recruiter.setPassword(rs.getString("password"));
				recruiter.setName(rs.getString("name"));
				recruiter.setIndustry(rs.getString("industry"));
				recruiter.setDescription(rs.getString("description"));

				listaRecruiter.add(recruiter);
			}

			return listaRecruiter;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public Recruiter buscarPorCodigo(int codigoRecruiter) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from recruiter where id_recruiter = ?");
			
			st.setInt(1, codigoRecruiter);

			rs = st.executeQuery();

			if (rs.next()) {

				Recruiter recruiter = new Recruiter();

				recruiter.setIdRecruiter(rs.getInt("id_recruiter"));
				recruiter.setEmail(rs.getString("email"));
				recruiter.setPassword(rs.getString("password"));
				recruiter.setName(rs.getString("name"));
				recruiter.setIndustry(rs.getString("industry"));
				recruiter.setDescription(rs.getString("description"));
				
				return recruiter;
			}

			return null;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public Recruiter buscarPorEmail(String emailRecruiter) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from recruiter where email = ?");

			st.setString(1, emailRecruiter);

			rs = st.executeQuery();

			if (rs.next()) {

				Recruiter recruiter = new Recruiter();

				recruiter.setIdRecruiter(rs.getInt("id_recruiter"));
				recruiter.setEmail(rs.getString("email"));
				recruiter.setPassword(rs.getString("password"));
				recruiter.setName(rs.getString("name"));
				recruiter.setIndustry(rs.getString("industry"));
				recruiter.setDescription(rs.getString("description"));


				return recruiter;
			}

			return null;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}

	public void atualizar(Recruiter recruiter) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update recruiter set email = ?, password = ?, name = ?, industry = ?, description = ? where id_recruiter = ?");

			st.setString(1, recruiter.getEmail());
			st.setString(2, recruiter.getPassword());
			st.setString(3, recruiter.getName());
			st.setString(4, recruiter.getIndustry());
			st.setString(5, recruiter.getDescription());
			st.setInt(6, recruiter.getIdRecruiter());

			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public int excluir(int codigoRecruiter) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from recruiter where id_recruiter = ?");

			st.setInt(1, codigoRecruiter);

			int linhasManipuladas = st.executeUpdate();
			
			return linhasManipuladas;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

}
