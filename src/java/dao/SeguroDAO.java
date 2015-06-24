package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Seguro;

public class SeguroDAO {
    public List<Seguro> lerSeguros(int idAutomovel) {
        ConectaMySQL database = new ConectaMySQL();
        List<Seguro> lista = new ArrayList<>();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT * FROM getSeguros WHERE `ID do Automóvel` = ?;";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idAutomovel);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Seguro seguro = new Seguro();
                    seguro.setId(resultSet.getInt("ID do Seguro"));
                    seguro.setInicioVigencia(resultSet.getDate("Data do Início da Vigência"));
                    seguro.setFimVigencia(resultSet.getDate("Data do Fim da Vigência"));
                    seguro.setValorParcela(resultSet.getDouble("Valor da Parcela"));
                    seguro.setQuantidadeParcelas(resultSet.getInt("Quantidade de Parcelas"));
                    seguro.setValorFranquia(resultSet.getDouble("Valor da Franquia"));
                    seguro.setValorCobertura(resultSet.getDouble("Valor da Cobertura"));
                    seguro.setAnotacoes(resultSet.getString("Anotações"));
                    lista.add(seguro);
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
    
    public Seguro lerSeguro(int idSeguro) {
        ConectaMySQL database = new ConectaMySQL();
        Seguro seguro = new Seguro();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT * FROM getSeguros WHERE `ID do Seguro` = ?";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idSeguro);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                resultSet.first();
                seguro.setId(resultSet.getInt("ID do Seguro"));
                seguro.setInicioVigencia(resultSet.getDate("Data do Início da Vigência"));
                seguro.setFimVigencia(resultSet.getDate("Data do Fim da Vigência"));
                seguro.setValorParcela(resultSet.getDouble("Valor da Parcela"));
                seguro.setQuantidadeParcelas(resultSet.getInt("Quantidade de Parcelas"));
                seguro.setValorFranquia(resultSet.getDouble("Valor da Franquia"));
                seguro.setValorCobertura(resultSet.getDouble("Valor da Cobertura"));
                seguro.setAnotacoes(resultSet.getString("Anotações"));
                
                preparedStatement.close();
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            return null;
        }
        database.closeConnection();
        return seguro;
    }
    
    public boolean alterarSeguro(Seguro seguro) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL updateSeguro(?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, seguro.getId());
                preparedStatement.setDate(2, seguro.getInicioVigencia());
                preparedStatement.setDate(3, seguro.getFimVigencia());
                preparedStatement.setDouble(4, seguro.getValorParcela());
                preparedStatement.setInt(5, seguro.getQuantidadeParcelas());
                preparedStatement.setDouble(6, seguro.getValorFranquia());
                preparedStatement.setDouble(7, seguro.getValorCobertura());
                preparedStatement.setString(8, seguro.getAnotacoes());
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
    
    public boolean salvarSeguro(Seguro seguro) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL setSeguro(?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, seguro.getAutomovel().getId());
                preparedStatement.setDate(2, seguro.getInicioVigencia());
                preparedStatement.setDate(3, seguro.getFimVigencia());
                preparedStatement.setDouble(4, seguro.getValorParcela());
                preparedStatement.setInt(5, seguro.getQuantidadeParcelas());
                preparedStatement.setDouble(6, seguro.getValorFranquia());
                preparedStatement.setDouble(7, seguro.getValorCobertura());
                preparedStatement.setString(8, seguro.getAnotacoes());
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
    
    public boolean apagarSeguro(int idSeguro) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL deleteSeguro(?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idSeguro);
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
                String sql = "SELECT SUM(`Valor Da Parcela` * IF(`Quantidade de Parcelas` > "
                        + "PERIOD_DIFF(DATE_FORMAT(CURDATE(), '%Y%m'), DATE_FORMAT(`Data do Início da Vigência`, '%Y%m')) + 1, "
                        + "PERIOD_DIFF(DATE_FORMAT(CURDATE(), '%Y%m'), DATE_FORMAT(`Data do Início da Vigência`, '%Y%m')) + 1, "
                        + "`Quantidade de Parcelas`)) AS 'Total' FROM getSeguros WHERE `ID do Automóvel` = ?;";
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
    
    /*public double despesasAno(int idAutomovel, String ano) {
        ConectaMySQL database = new ConectaMySQL();
        double total = 0;
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT SUM(IF(`Quantidade de Parcelas` >= PERIOD_DIFF(DATE_FORMAT(CURDATE(), '%Y%m'), "
                        + "DATE_FORMAT(`Data do Início da Vigência`, '%Y%m')) + 1, (MONTH(CURDATE()) - "
                        + "IF(PERIOD_DIFF(DATE_FORMAT(`Data do Início da Vigência`, '%Y%m'), "
                        + "DATE_FORMAT(?, '%Y%m')) > 0, PERIOD_DIFF(DATE_FORMAT(`Data do Início da Vigência`, '%Y%m'), "
                        + "DATE_FORMAT(?, '%Y%m')), 0)) * `Valor Da Parcela`, `Valor Da Parcela` * "
                        + "PERIOD_DIFF(DATE_FORMAT(CURDATE(), '%Y%m'), DATE_FORMAT(`Data do Início da Vigência`, '%Y%m')))) "
                        + "AS 'Total' FROM getSeguros WHERE `ID do Automóvel` = ?;";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setString(1, ano + "-01-00");
                preparedStatement.setString(2, ano + "-01-00");
                preparedStatement.setInt(3, idAutomovel);
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
    }*/
    
    /*public double despesasMes(int idAutomovel, String mes, String ano) {
        ConectaMySQL database = new ConectaMySQL();
        double total = 0;
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT SUM(IF(`Quantidade de Parcelas` >= PERIOD_DIFF(DATE_FORMAT(?, '%Y%m'), "
                        + "DATE_FORMAT(`Data do Início da Vigência`, '%Y%m')) + 1, `Valor Da Parcela`, 0)) "
                        + "AS 'Total' FROM getSeguros WHERE `ID do Automóvel` = ?;";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setString(1, ano + "-" + mes + "-01");
                preparedStatement.setInt(2, idAutomovel);
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
    }*/
}
