package dao;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;

public class UsuarioDAO {
    
    public Usuario isUsuario(String email, String senha) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL isUsuario(?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, senha);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                resultSet.first();
                if (resultSet.getBoolean("Output")) {
                    Usuario usuario = new Usuario();
                    usuario.setId(resultSet.getInt("ID"));
                    usuario.setNome(resultSet.getString("Nome"));
                    usuario.setHash(resultSet.getString("Hash"));
                    usuario.setEmail(email);
                    usuario.setSenha(senha);
                    preparedStatement.close();
                    database.closeConnection();
                    return usuario;
                } else {
                    preparedStatement.close();
                    database.closeConnection();
                    return null;
                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            database.closeConnection();
            return null;
        }
        return null;
    }
    
    public boolean isUsuarioHash(int id, String hash) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL isUsuarioHash(?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, hash);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                resultSet.first();
                if (resultSet.getBoolean("Output")) {
                    preparedStatement.close();
                    database.closeConnection();
                    return true;
                } else {
                    preparedStatement.close();
                    database.closeConnection();
                    return false;
                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, sqlException);
            database.closeConnection();
            return false;
        }
        return false;
    }
    
    public boolean salvarUsuario(Usuario usuario) throws FileNotFoundException {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "CALL setUsuario(?, ?, ?, ?);";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setString(1, usuario.getNome());
                preparedStatement.setString(2, usuario.getEmail());
                preparedStatement.setString(3, usuario.getSenha());
                
                byte[] foto = usuario.getFoto();
                if (foto != null) {
                    preparedStatement.setBinaryStream(4, new ByteArrayInputStream(foto));
                } else {
                    preparedStatement.setBinaryStream(4, null);
                }
                
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
    
    public byte[] lerFoto(int idUsuario) {
        ConectaMySQL database = new ConectaMySQL();
        try {
            if (database.getDb_connection() != null) {
                String sql = "SELECT foto FROM Usuario WHERE idUsuario = ?";
                PreparedStatement preparedStatement;
                preparedStatement = database.getDb_connection().prepareStatement(sql);
                preparedStatement.setInt(1, idUsuario);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.first();
                Blob foto = resultSet.getBlob("foto");
                preparedStatement.close();
                database.closeConnection();
                if (foto != null) {
                    return foto.getBytes(1,(new Long(foto.length())).intValue());
                }
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
