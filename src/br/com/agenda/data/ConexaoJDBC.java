package br.com.agenda.data;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConexaoJDBC {
	
	public Connection getConnection();
	
	public static void close() {
	}
	
	public void commit() throws SQLException;
	
	public void rollback();
}
