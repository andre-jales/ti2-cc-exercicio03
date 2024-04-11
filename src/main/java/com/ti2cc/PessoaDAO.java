package com.ti2cc;

import java.sql.*;

public class PessoaDAO {
	private Connection conexao;
	
	public PessoaDAO() {
		conexao = null;
	}
	
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";
		String serverName = "localhost";
		String mydatabase = "com.ti2cc";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
		String username = "postgres";
		String password = "postgres";
		boolean status = false;
		
		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexao efetuada com o postgres!");
		} catch (ClassNotFoundException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}
		
		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean inserirPessoa(Pessoa pessoa) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO pessoa (id, nome, idade, email) "
					       + "VALUES (" + pessoa.getId() + ", '" + pessoa.getNome() + "', '"
					       + pessoa.getIdade() + "', '" + pessoa.getEmail() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarPessoa(Pessoa pessoa) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "UPDATE pessoa SET nome = '" + pessoa.getNome() + "', idade = '"
					   +  pessoa.getIdade() + "', email = '" + pessoa.getEmail() + "'"
					   +  " WHERE id = " + pessoa.getId();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirPessoa(int id) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM pessoa WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Pessoa[] getPessoas() {
		Pessoa[] pessoas = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM pessoa");
				if(rs.next()) {
					rs.last();
					pessoas = new Pessoa[rs.getRow()];
					rs.beforeFirst();
					
					for (int i = 0; rs.next(); i++) {
						pessoas[i] = new Pessoa(rs.getInt("id"), rs.getString("Nome"),
								                rs.getInt("idade"), rs.getString("email"));
					}
				}
				st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pessoas;
	}
	
	public Pessoa getPessoaById(int id) {
	    Pessoa pessoa = null;
	    
	    try {
	        Statement st = conexao.createStatement();
	        ResultSet rs = st.executeQuery("SELECT * FROM pessoa WHERE id = " + id);
	        if (rs.next()) {
	            pessoa = new Pessoa(rs.getInt("id"), rs.getString("Nome"),
		                            rs.getInt("idade"), rs.getString("email"));
	        }
	        st.close();
	    } catch (Exception e) {
	        System.err.println(e.getMessage());
	    }
	    return pessoa;
	}
}