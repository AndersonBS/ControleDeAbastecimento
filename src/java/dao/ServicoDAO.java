package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Servico;

public class ServicoDAO {
    public List<Servico> lerServicos(int idAutomovel) {
        ConectaMySQL database = new ConectaMySQL();
        List<Servico> lista = new ArrayList<>();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT * FROM getServicos WHERE `ID do Automóvel` = ?;";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idAutomovel);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Servico servico = new Servico();
                    servico.setId(resultSet.getInt("ID do Serviço"));
                    servico.setData(resultSet.getDate("Data"));
                    servico.setDescricao(resultSet.getString("Descrição"));
                    servico.setValorGasto(resultSet.getDouble("Valor Gasto"));
                    lista.add(servico);
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
    
    public Servico lerServico(int idServico) {
        ConectaMySQL database = new ConectaMySQL();
        Servico servico = new Servico();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT * FROM getServicos WHERE `ID do Serviço` = ?";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idServico);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                resultSet.first();
                servico.setId(resultSet.getInt("ID do Serviço"));
                servico.setData(resultSet.getDate("Data"));
                servico.setDescricao(resultSet.getString("Descrição"));
                servico.setValorGasto(resultSet.getDouble("Valor Gasto"));
                
                preparedStatement.close();
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            return null;
        }
        database.closeConnection();
        return servico;
    }
    
    public boolean alterarServico(Servico servico) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL updateServico(?, ?, ?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, servico.getId());
                preparedStatement.setDate(2, servico.getData());
                preparedStatement.setString(3, servico.getDescricao());
                preparedStatement.setDouble(4, servico.getValorGasto());
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
    
    public boolean salvarServico(Servico servico) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL setServico(?, ?, ?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, servico.getAutomovel().getId());
                preparedStatement.setDate(2, servico.getData());
                preparedStatement.setString(3, servico.getDescricao());
                preparedStatement.setDouble(4, servico.getValorGasto());
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
    
    public boolean apagarServico(int idServico) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL deleteServico(?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idServico);
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
                String sql = "SELECT SUM(`Valor Gasto`) AS Total FROM getServicos WHERE `ID do Automóvel` = ?;";
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
                String sql = "SELECT SUM(`Valor Gasto`) AS 'Total' "
                        + "FROM getServicos WHERE `ID do Automóvel` = ? "
                        + "AND YEAR(`Data`) = YEAR(CURDATE());";
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
                String sql = "SELECT SUM(`Valor Gasto`) AS 'Total' FROM getServicos "
                        + "WHERE `ID do Automóvel` = ? AND YEAR(`Data`) = YEAR(CURDATE()) "
                        + "AND MONTH(`Data`) = MONTH(CURDATE());";
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
