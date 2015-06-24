package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Combustivel;

public class CombustivelDAO {
    
    public List<Combustivel> lerCombustiveis(int idUsuario) {
        ConectaMySQL database = new ConectaMySQL();
        List<Combustivel> lista = new ArrayList<>();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT * FROM getCombustiveis WHERE `ID do Usuário` = ?;";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idUsuario);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Combustivel combustivel = new Combustivel();
                    combustivel.setId(resultSet.getInt("ID do Combustível"));
                    combustivel.setTipo(resultSet.getString("Combustível"));
                    lista.add(combustivel);
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
    
    public Combustivel lerCombustivel(int idCombustivel) {
        ConectaMySQL database = new ConectaMySQL();
        Combustivel combustivel = new Combustivel();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT * FROM getCombustiveis WHERE `ID do Combustível` = ?";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idCombustivel);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                resultSet.first();
                combustivel.setId(resultSet.getInt("ID do Combustível"));
                combustivel.setTipo(resultSet.getString("Combustível"));
                
                preparedStatement.close();
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            return null;
        }
        database.closeConnection();
        return combustivel;
    }
    
    public boolean alterarCombustivel(Combustivel combustivel) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL updateCombustivel(?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, combustivel.getId());
                preparedStatement.setString(2, combustivel.getTipo());
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
    
    public boolean salvarCombustivel(Combustivel combustivel) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL setCombustivel(?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, combustivel.getUsuario().getId());
                preparedStatement.setString(2, combustivel.getTipo());
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
    
    public boolean apagarCombustivel(int id) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL deleteCombustivel(?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, id);
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
    
    public List<Combustivel> despesasCombustivel(int idAutomovel) {
        ConectaMySQL database = new ConectaMySQL();
        List<Combustivel> lista = new ArrayList<>();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT `Combustível`, SUM(`Valor Total`) AS 'Total' FROM getAbastecimentos "
                        + "JOIN getCombustiveis ON getAbastecimentos.`ID do Combustível` = getCombustiveis.`ID do Combustível` "
                        + "WHERE `ID do Automóvel` = ? GROUP BY `Combustível` ORDER BY `Total` DESC;";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idAutomovel);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {
                    Combustivel combustivel = new Combustivel();
                    combustivel.setTipo(resultSet.getString("Combustível"));
                    combustivel.setTotal(resultSet.getDouble("Total"));
                    lista.add(combustivel);
                }
                preparedStatement.close();
                database.closeConnection();
                return lista;
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            database.closeConnection();
            return null;
        }
        return null;
    }
    
}
