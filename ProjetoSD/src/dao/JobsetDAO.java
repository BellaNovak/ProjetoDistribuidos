package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	
	public void atualizar(String experience, int idRecruiter, int idSkill) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update jobset set experience = ? where recruiter_id = ? and skill_id = ?");
		
			st.setString(1, experience);
			st.setInt(2, idRecruiter);
			st.setInt(3, idSkill);

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
