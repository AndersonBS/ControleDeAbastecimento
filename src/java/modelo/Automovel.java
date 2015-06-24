package modelo;

import java.sql.Date;

public class Automovel {
    private int id;
    private Usuario usuario;
    private String nome;
    private String marca;
    private int anoModelo;
    private Date dataAquisicao;
    private String placa;
    
    public Automovel() {};
    
    public Automovel(Usuario usuario, String nome, String marca, int anoModelo, 
            Date dataAquisicao, String placa) {
        this.usuario = usuario;
        this.nome = nome;
        this.marca = marca;
        this.anoModelo = anoModelo;
        this.dataAquisicao = dataAquisicao;
        this.placa = placa;
    };

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setAnoModelo(int anoModelo) {
        this.anoModelo = anoModelo;
    }

    public void setDataAquisicao(Date dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNome() {
        return nome;
    }

    public String getMarca() {
        return marca;
    }

    public int getAnoModelo() {
        return anoModelo;
    }

    public Date getDataAquisicao() {
        return dataAquisicao;
    }

    public String getPlaca() {
        return placa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
