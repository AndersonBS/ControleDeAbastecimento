package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Automovel;

public class AutomovelDAO {
    
    public List<Automovel> lerAutomoveis(int idUsuario) {
        ConectaMySQL database = new ConectaMySQL();
        List<Automovel> lista = new ArrayList<>();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT * FROM getAutomoveis WHERE `ID do Usuário` = ?;";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idUsuario);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Automovel automovel = new Automovel();
                    automovel.setId(resultSet.getInt("ID do Automóvel"));
                    automovel.setNome(resultSet.getString("Nome do Automóvel"));
                    automovel.setMarca(resultSet.getString("Marca do Automóvel"));
                    automovel.setAnoModelo(resultSet.getInt("Ano do Modelo do Automóvel"));
                    automovel.setDataAquisicao(resultSet.getDate("Data de Aquisição do Automóvel"));
                    automovel.setPlaca(resultSet.getString("Placa do Automóvel"));
                    lista.add(automovel);
                }
                preparedStatement.close();
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            return null;
        }
        database.closeConnection();
        return lista;
    }
    
    public Automovel lerAutomovel(int idAutomovel) {
        ConectaMySQL database = new ConectaMySQL();
        Automovel automovel = new Automovel();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT * FROM getAutomoveis WHERE `ID do Automóvel` = ?";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idAutomovel);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                resultSet.first();
                automovel.setId(resultSet.getInt("ID do Automóvel"));
                automovel.setNome(resultSet.getString("Nome do Automóvel"));
                automovel.setMarca(resultSet.getString("Marca do Automóvel"));
                automovel.setAnoModelo(resultSet.getInt("Ano do Modelo do Automóvel"));
                automovel.setDataAquisicao(resultSet.getDate("Data de Aquisição do Automóvel"));
                automovel.setPlaca(resultSet.getString("Placa do Automóvel"));
                
                preparedStatement.close();
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            return null;
        }
        database.closeConnection();
        return automovel;
    }
    
    public boolean alterarAutomovel(Automovel automovel) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL updateAutomovel(?, ?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, automovel.getId());
                preparedStatement.setString(2, automovel.getNome());
                preparedStatement.setString(3, automovel.getMarca());
                preparedStatement.setInt(4, automovel.getAnoModelo());
                preparedStatement.setDate(5, automovel.getDataAquisicao());
                preparedStatement.setString(6, automovel.getPlaca());
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
    
    public boolean salvarAutomovel(Automovel automovel) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL setAutomovel(?, ?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, automovel.getUsuario().getId());
                preparedStatement.setString(2, automovel.getNome());
                preparedStatement.setString(3, automovel.getMarca());
                preparedStatement.setInt(4, automovel.getAnoModelo());
                preparedStatement.setDate(5, automovel.getDataAquisicao());
                preparedStatement.setString(6, automovel.getPlaca());
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
    
    public boolean apagarAutomovel(int idAutomovel) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL deleteAutomovel(?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idAutomovel);
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
