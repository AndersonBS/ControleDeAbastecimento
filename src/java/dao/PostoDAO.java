/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Posto;

/**
 *
 * @author anderson
 */
public class PostoDAO {
    
    public List<Posto> lerPostos(int idUsuario) {
        ConectaMySQL database = new ConectaMySQL();
        List<Posto> lista = new ArrayList<>();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT * FROM getPostos WHERE `ID do Usuário` = ?;";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idUsuario);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Posto posto = new Posto();
                    posto.setId(resultSet.getInt("ID do Posto"));
                    posto.setNome(resultSet.getString("Nome"));
                    posto.setMarca(resultSet.getString("Marca do Combustível"));
                    posto.setEndereco(resultSet.getString("Endereço"));
                    posto.setTelefone(resultSet.getString("Telefone"));
                    posto.setCartao(resultSet.getBoolean("Aceita Cartão?"));
                    lista.add(posto);
                }
                preparedStatement.close();
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            return null;
        }
        return lista;
    }
    
    public Posto lerPosto(int idPosto) {
        ConectaMySQL database = new ConectaMySQL();
        Posto posto = new Posto();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT * FROM getPostos WHERE `ID do Posto` = ?";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idPosto);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                resultSet.first();
                posto.setId(resultSet.getInt("ID do Posto"));
                posto.setNome(resultSet.getString("Nome"));
                posto.setMarca(resultSet.getString("Marca do Combustível"));
                posto.setEndereco(resultSet.getString("Endereço"));
                posto.setTelefone(resultSet.getString("Telefone"));
                posto.setCartao(resultSet.getBoolean("Aceita Cartão?"));
                
                preparedStatement.close();
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            return null;
        }
        database.closeConnection();
        return posto;
    }
    
    public boolean alterarPosto(Posto posto) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL updatePosto(?, ?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, posto.getId());
                preparedStatement.setString(2, posto.getNome());
                preparedStatement.setString(3, posto.getMarca());
                preparedStatement.setString(4, posto.getEndereco());
                preparedStatement.setString(5, posto.getTelefone());
                preparedStatement.setBoolean(6, posto.getCartao());
                preparedStatement.execute();
                preparedStatement.close();
                database.closeConnection();
                return true;
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            database.closeConnection();
            return false;
        }
        return false;
    }
    
    public boolean salvarPosto(Posto posto) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL setPosto(?, ?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, posto.getUsuario().getId());
                preparedStatement.setString(2, posto.getNome());
                preparedStatement.setString(3, posto.getMarca());
                preparedStatement.setString(4, posto.getEndereco());
                preparedStatement.setString(5, posto.getTelefone());
                preparedStatement.setBoolean(6, posto.getCartao());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                database.closeConnection();
                return true;
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            database.closeConnection();
            return false;
        }
        return false;
    }
    
    public boolean apagarPosto(int idPosto) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL deletePosto(?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idPosto);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                database.closeConnection();
                return true;
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            database.closeConnection();
            return false;
        }
        return false;
    }
    
}
