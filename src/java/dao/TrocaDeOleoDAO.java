package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.TrocaDeOleo;

public class TrocaDeOleoDAO {
    public List<TrocaDeOleo> lerTrocasDeOleo(int idAutomovel) {
        ConectaMySQL database = new ConectaMySQL();
        List<TrocaDeOleo> lista = new ArrayList<>();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT * FROM getTrocasDeOleo WHERE `ID do Automóvel` = ?;";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idAutomovel);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    TrocaDeOleo trocaDeOleo = new TrocaDeOleo();
                    trocaDeOleo.setId(resultSet.getInt("ID da Troca de Óleo"));
                    trocaDeOleo.setNomeOleo(resultSet.getString("Nome do Óleo"));
                    trocaDeOleo.setTipoOleo(resultSet.getString("Tipo de Óleo"));
                    trocaDeOleo.setDataTroca(resultSet.getDate("Data da Troca"));
                    trocaDeOleo.setDataProximaTroca(resultSet.getDate("Data da Próxima Troca"));
                    trocaDeOleo.setTrocaFiltro(resultSet.getBoolean("Troca de Filtro?"));
                    trocaDeOleo.setPreco(resultSet.getDouble("Preço"));
                    lista.add(trocaDeOleo);
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
    
    public TrocaDeOleo lerTrocaDeOleo(int idTrocaDeOleo) {
        ConectaMySQL database = new ConectaMySQL();
        TrocaDeOleo trocaDeOleo = new TrocaDeOleo();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT * FROM getTrocasDeOleo WHERE `ID da Troca de Óleo` = ?";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idTrocaDeOleo);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                resultSet.first();
                trocaDeOleo.setId(resultSet.getInt("ID da Troca de Óleo"));
                trocaDeOleo.setNomeOleo(resultSet.getString("Nome do Óleo"));
                trocaDeOleo.setTipoOleo(resultSet.getString("Tipo de Óleo"));
                trocaDeOleo.setDataTroca(resultSet.getDate("Data da Troca"));
                trocaDeOleo.setDataProximaTroca(resultSet.getDate("Data da Próxima Troca"));
                trocaDeOleo.setTrocaFiltro(resultSet.getBoolean("Troca de Filtro?"));
                trocaDeOleo.setPreco(resultSet.getDouble("Preço"));
                
                preparedStatement.close();
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            return null;
        }
        database.closeConnection();
        return trocaDeOleo;
    }
    
    public boolean alterarTrocaDeOleo(TrocaDeOleo trocaDeOleo) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL updateTrocaDeOleo(?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, trocaDeOleo.getId());
                preparedStatement.setString(2, trocaDeOleo.getNomeOleo());
                preparedStatement.setString(3, trocaDeOleo.getTipoOleo());
                preparedStatement.setDate(4, trocaDeOleo.getDataTroca());
                preparedStatement.setDate(5, trocaDeOleo.getDataProximaTroca());
                preparedStatement.setBoolean(6, trocaDeOleo.getTrocaFiltro());
                preparedStatement.setDouble(7, trocaDeOleo.getPreco());
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
    
    public boolean salvarTrocaDeOleo(TrocaDeOleo trocaDeOleo) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL setTrocaDeOleo(?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, trocaDeOleo.getAutomovel().getId());
                preparedStatement.setString(2, trocaDeOleo.getNomeOleo());
                preparedStatement.setString(3, trocaDeOleo.getTipoOleo());
                preparedStatement.setDate(4, trocaDeOleo.getDataTroca());
                preparedStatement.setDate(5, trocaDeOleo.getDataProximaTroca());
                preparedStatement.setBoolean(6, trocaDeOleo.getTrocaFiltro());
                preparedStatement.setDouble(7, trocaDeOleo.getPreco());
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
    
    public boolean apagarTrocaDeOleo(int idTrocaDeOleo) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL deleteTrocaDeOleo(?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idTrocaDeOleo);
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
    
    public double despesasTotal(int idAutomovel) {
        ConectaMySQL database = new ConectaMySQL();
        double total = 0;
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT SUM(`Preço`) AS 'Total' FROM getTrocasDeOleo WHERE `ID do Automóvel` = ?;";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idAutomovel);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.first();
                total = resultSet.getDouble("Total");
                preparedStatement.close();
                database.closeConnection();
                return total;
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            database.closeConnection();
            return total;
        }
        return total;
    }
    
    public double despesasAno(int idAutomovel) {
        ConectaMySQL database = new ConectaMySQL();
        double total = 0;
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT SUM(`Preço`) AS 'Total' FROM getTrocasDeOleo WHERE"
                        + " `ID do Automóvel` = ? AND YEAR(`Data da Troca`) = YEAR(CURDATE());";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idAutomovel);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.first();
                total = resultSet.getDouble("Total");
                preparedStatement.close();
                database.closeConnection();
                return total;
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            database.closeConnection();
            return total;
        }
        return total;
    }
    
    public double despesasMes(int idAutomovel) {
        ConectaMySQL database = new ConectaMySQL();
        double total = 0;
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT SUM(`Preço`) AS 'Total' FROM getTrocasDeOleo WHERE"
                        + " `ID do Automóvel` = ? AND YEAR(`Data da Troca`) = YEAR(CURDATE()) "
                        + "AND MONTH(`Data da Troca`) = MONTH(CURDATE());";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idAutomovel);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.first();
                total = resultSet.getDouble("Total");
                preparedStatement.close();
                database.closeConnection();
                return total;
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            database.closeConnection();
            return total;
        }
        return total;
    }
    
}
