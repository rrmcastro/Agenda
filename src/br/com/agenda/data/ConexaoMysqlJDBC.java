package br.com.agenda.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoMysqlJDBC implements ConexaoJDBC {

	private Connection connection = null;

	private static final String USERNAME = "root";

	private static final String PASSWORD = "";

	private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/agenda?useTimezone=true&serverTimezone=UTC";

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException ex) {
			throw new RuntimeException("Erro na conexão: " + ex);
		}
	
	}	
	

	public void close(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException ex) {
			Logger.getLogger(ConexaoMysqlJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void close(Connection con, PreparedStatement stmt) {
		
		close(con);
		
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException ex) {
			Logger.getLogger(ConexaoMysqlJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void close(Connection con, PreparedStatement stmt, ResultSet rs) {
		
		close(con, stmt);
	
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException ex) {
			Logger.getLogger(ConexaoMysqlJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	

	public void commit() throws SQLException {
		this.connection.commit();
		this.close();
	}

	@Override
	public void rollback() {
		if (this.connection != null) {
			try {
				this.connection.rollback();
			} catch (SQLException ex) {
				Logger.getLogger(ConexaoMysqlJDBC.class.getName()).log(Level.SEVERE, null, ex);
			} finally {
				this.close();
			}
		}
	}
}
