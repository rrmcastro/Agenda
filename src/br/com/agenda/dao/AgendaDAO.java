package br.com.agenda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import br.com.agenda.data.ConexaoJDBC;
import br.com.agenda.data.ConexaoMysqlJDBC;
import br.com.agenda.model.Agenda;

public class AgendaDAO {
	
	private final ConexaoJDBC conexao;
	
	private PreparedStatement stmt;
	private ResultSet rs;
	private String sqlQuery = "";
	
	public AgendaDAO() throws SQLException, ClassNotFoundException {
		this.conexao = (ConexaoJDBC) new ConexaoMysqlJDBC().getConnection();		
	}
	
	public void cadastrar(Agenda agenda) throws SQLException {
		
		try {
			sqlQuery = "INSERT INTO contatos (nome, telefone, email, endereco) VALUES (?, ?, ?, ?);";
			
			stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
			
			stmt.setString(1, agenda.getNome());
			stmt.setString(2, agenda.getTelefone());
			stmt.setString(3, agenda.getEmail());
			stmt.setString(4, agenda.getEndereco());
			
			stmt.execute();
			stmt.conexao.commit();
		} catch (SQLException e) {
			this.conexao.rollback();
			throw e;
		}
	}
	
	public void alterar(Agenda agenda) {
		
		String sqlQuery = "UPDATE contatos SET nome=?, telefone=?, email=?, endereco=? WHERE id=?";
		
		try {
			stmt = ((Connection) conexao).prepareStatement(sqlQuery);
			
			stmt.setString(1, agenda.getNome());
			stmt.setString(2, agenda.getTelefone());
			stmt.setString(3, agenda.getEmail());
			stmt.setString(4, agenda.getEndereco());
			
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
		} finally {
			ConexaoMysqlJDBC.close();
		}
	}
	
	public void remover(Agenda agenda) {
		
		String sqlQuery = "DELETE FROM contatos WHERE id=?";
		
		try {
			PreparedStatement stmt =  ((Connection) conexao).prepareStatement(sqlQuery);
			
			stmt.setLong(1, agenda.getId());
			
			stmt.execute();

			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
		}
	}
	
	
	
	
}
