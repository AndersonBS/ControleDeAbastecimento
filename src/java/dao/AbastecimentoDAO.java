package dao;

import java.util.ArrayList;
import java.util.List;
import modelo.Abastecimento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbastecimentoDAO {
    
    public List<Abastecimento> lerAbastecimentos(int idAutomovel, String filtroDataInicial, 
            String filtroDataFinal, String filtroCombustivel, String filtroPosto) {
        if (filtroDataInicial == null || "".equals(filtroDataInicial)) filtroDataInicial = "0001-01-01";
        if (filtroDataFinal == null || "".equals(filtroDataFinal)) filtroDataFinal = "9999-12-30";
        ConectaMySQL database = new ConectaMySQL();
        List<Abastecimento> lista = new ArrayList<>();
        try {
            if (database.getDb_connection() != null) {
                PreparedStatement preparedStatement;
                ResultSet resultSet;
                
                String sql = "SELECT * FROM getAbastecimentos" + 
    " JOIN getCombustiveis ON getAbastecimentos.`ID do Combustível` = getCombustiveis.`ID do Combustível`" + 
    " JOIN getPostos ON getAbastecimentos.`ID do Posto` = getPostos.`ID do Posto`" + 
    " WHERE `ID do Automóvel` = ? AND `Data` >= ? AND `Data` <= ? AND `Combustível` LIKE ? AND `Nome` LIKE ? "
                        + "ORDER BY `Data` DESC;";
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idAutomovel);
                preparedStatement.setString(2, filtroDataInicial);
                preparedStatement.setString(3, filtroDataFinal);
                preparedStatement.setString(4, "%" + filtroCombustivel + "%");
                preparedStatement.setString(5, "%" + filtroPosto + "%");
                resultSet = preparedStatement.executeQuery();
                
                while (resultSet.next()) {
                    Abastecimento abastecimento = new Abastecimento();
                    abastecimento.setId(resultSet.getInt("ID do Abastecimento"));
                    abastecimento.setData(resultSet.getDate("Data"));     
                    abastecimento.setOdometro(resultSet.getDouble("Odômetro"));
                    abastecimento.setLitros(resultSet.getDouble("Litros"));
                    abastecimento.setValorTotal(resultSet.getDouble("Valor Total"));
                    abastecimento.setAnotacoes(resultSet.getString("Anotações"));
                    
                    CombustivelDAO combustivelDAO = new CombustivelDAO();
                    abastecimento.setCombustivel(combustivelDAO.lerCombustivel(resultSet.getInt("ID do Combustível")));

                    PostoDAO postoDAO = new PostoDAO();
                    abastecimento.setPosto(postoDAO.lerPosto(resultSet.getInt("ID do Posto")));
                    
                    lista.add(abastecimento);
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
    
    public List<Abastecimento> lerAbastecimentos(int idAutomovel) {
        ConectaMySQL database = new ConectaMySQL();
        List<Abastecimento> lista = new ArrayList<>();
        try {
            if (database.getDb_connection() != null) {
                PreparedStatement preparedStatement;
                ResultSet resultSet;
                String sql = "SELECT * FROM getAbastecimentos WHERE `ID do Automóvel` = ? ORDER BY `Data`;";
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idAutomovel);
                resultSet = preparedStatement.executeQuery();
                
                while (resultSet.next()) {
                    Abastecimento abastecimento = new Abastecimento();
                    abastecimento.setId(resultSet.getInt("ID do Abastecimento"));
                    abastecimento.setData(resultSet.getDate("Data"));     
                    abastecimento.setOdometro(resultSet.getDouble("Odômetro"));
                    abastecimento.setLitros(resultSet.getDouble("Litros"));
                    abastecimento.setValorTotal(resultSet.getDouble("Valor Total"));
                    abastecimento.setAnotacoes(resultSet.getString("Anotações"));
                    
                    CombustivelDAO combustivelDAO = new CombustivelDAO();
                    abastecimento.setCombustivel(combustivelDAO.lerCombustivel(resultSet.getInt("ID do Combustível")));

                    PostoDAO postoDAO = new PostoDAO();
                    abastecimento.setPosto(postoDAO.lerPosto(resultSet.getInt("ID do Posto")));
                    
                    lista.add(abastecimento);
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
    
    public Abastecimento lerAbastecimento(int idAbastecimento) {
        ConectaMySQL database = new ConectaMySQL();
        Abastecimento abastecimento = new Abastecimento();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT * FROM getAbastecimentos WHERE `ID do Abastecimento` = ?";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idAbastecimento);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                resultSet.first();
                abastecimento.setId(resultSet.getInt("ID do Abastecimento"));
                abastecimento.setData(resultSet.getDate("Data"));
                abastecimento.setOdometro(resultSet.getDouble("Odômetro"));
                abastecimento.setLitros(resultSet.getDouble("Litros"));
                abastecimento.setValorTotal(resultSet.getDouble("Valor Total"));
                abastecimento.setAnotacoes(resultSet.getString("Anotações"));

                AutomovelDAO automovelDAO = new AutomovelDAO();
                abastecimento.setAutomovel(automovelDAO.lerAutomovel(resultSet.getInt("ID do Automóvel")));

                CombustivelDAO combustivelDAO = new CombustivelDAO();
                abastecimento.setCombustivel(combustivelDAO.lerCombustivel(resultSet.getInt("ID do Combustível")));

                PostoDAO postoDAO = new PostoDAO();
                abastecimento.setPosto(postoDAO.lerPosto(resultSet.getInt("ID do Posto")));
                
                preparedStatement.close();
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            return null;
        }
        database.closeConnection();
        return abastecimento;
    }
    
    public boolean alterarAbastecimento(Abastecimento abastecimento) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL updateAbastecimento(?, ?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, abastecimento.getId());
                preparedStatement.setInt(2, abastecimento.getAutomovel().getId());
                preparedStatement.setDate(3, abastecimento.getData());
                preparedStatement.setInt(4, abastecimento.getCombustivel().getId());
                preparedStatement.setDouble(5, abastecimento.getOdometro());
                preparedStatement.setDouble(6, abastecimento.getLitros());
                preparedStatement.setDouble(7, abastecimento.getValorTotal());
                preparedStatement.setInt(8, abastecimento.getPosto().getId());
                preparedStatement.setString(9, abastecimento.getAnotacoes());
                preparedStatement.execute();
                System.out.println(preparedStatement);
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
    
    public boolean salvarAbastecimento(Abastecimento abastecimento) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL setAbastecimento(?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, abastecimento.getAutomovel().getId());
                preparedStatement.setDate(2, abastecimento.getData());
                preparedStatement.setInt(3, abastecimento.getCombustivel().getId());
                preparedStatement.setDouble(4, abastecimento.getOdometro());
                preparedStatement.setDouble(5, abastecimento.getLitros());
                preparedStatement.setDouble(6, abastecimento.getValorTotal());
                preparedStatement.setInt(7, abastecimento.getPosto().getId());
                preparedStatement.setString(8, abastecimento.getAnotacoes());
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
    
    public boolean apagarAbastecimento(int idAbastecimento) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL deleteAbastecimento(?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idAbastecimento);
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
                String sql = "SELECT SUM(`Valor Total`) AS Total FROM getAbastecimentos WHERE `ID do Automóvel` = ?;";
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
                String sql = "SELECT SUM(`Valor Total`) AS 'Total' FROM getAbastecimentos WHERE "
                        + "`ID do Automóvel` = ? AND YEAR(`Data`) = YEAR(CURDATE());";
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
                String sql = "SELECT SUM(`Valor Total`) AS 'Total' FROM getAbastecimentos WHERE"
                        + " `ID do Automóvel` = ? AND YEAR(`Data`) = YEAR(CURDATE()) AND MONTH(`Data`) = MONTH(CURDATE());";
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
    
    public double odometroTotal(int idAutomovel) {
        ConectaMySQL database = new ConectaMySQL();
        double total = 0;
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT SUM(`Odômetro`) AS 'Total' FROM getAbastecimentos WHERE `ID do Automóvel` = ?;";
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
