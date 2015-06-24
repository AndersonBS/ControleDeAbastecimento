package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Lembrete;

public class LembreteDAO {
    public List<Lembrete> lerLembretes(int idAutomovel) {
        ConectaMySQL database = new ConectaMySQL();
        List<Lembrete> lista = new ArrayList<>();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT * FROM getLembretes WHERE `ID do Automóvel` = ?;";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idAutomovel);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Lembrete lembrete = new Lembrete();
                    lembrete.setId(resultSet.getInt("ID do Lembrete"));
                    lembrete.setDataPrevista(resultSet.getDate("Data Prevista"));
                    lembrete.setValorOrcado(resultSet.getDouble("Valor Orçado"));
                    lembrete.setDescricaoLocal(resultSet.getString("Descrição do Local"));
                    lembrete.setDescricaoServico(resultSet.getString("Descrição do Serviço"));
                    lista.add(lembrete);
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
    
    public Lembrete lerLembrete(int idLembrete) {
        ConectaMySQL database = new ConectaMySQL();
        Lembrete lembrete = new Lembrete();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT * FROM getLembretes WHERE `ID do Lembrete` = ?";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idLembrete);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                resultSet.first();
                lembrete.setId(resultSet.getInt("ID do Lembrete"));
                lembrete.setDataPrevista(resultSet.getDate("Data Prevista"));
                lembrete.setValorOrcado(resultSet.getDouble("Valor Orçado"));
                lembrete.setDescricaoLocal(resultSet.getString("Descrição do Local"));
                lembrete.setDescricaoServico(resultSet.getString("Descrição do Serviço"));
                
                preparedStatement.close();
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            return null;
        }
        database.closeConnection();
        return lembrete;
    }
    
    public boolean alterarLembrete(Lembrete lembrete) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL updateLembrete(?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, lembrete.getId());
                preparedStatement.setDate(2, lembrete.getDataPrevista());
                preparedStatement.setDouble(3, lembrete.getValorOrcado());
                preparedStatement.setString(4, lembrete.getDescricaoLocal());
                preparedStatement.setString(5, lembrete.getDescricaoServico());
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
    
    public boolean salvarLembrete(Lembrete lembrete) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL setLembrete(?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, lembrete.getAutomovel().getId());
                preparedStatement.setDate(2, lembrete.getDataPrevista());
                preparedStatement.setDouble(3, lembrete.getValorOrcado());
                preparedStatement.setString(4, lembrete.getDescricaoLocal());
                preparedStatement.setString(5, lembrete.getDescricaoServico());
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
    
    public boolean apagarLembrete(int idLembrete) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL deleteLembrete(?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idLembrete);
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
