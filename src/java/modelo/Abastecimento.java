package modelo;

import java.text.DecimalFormat;
import java.sql.Date;

public class Abastecimento {
    private int id;
    private Automovel automovel;
    private Date data;
    private Combustivel combustivel;
    private double odometro;
    private double litros;
    private double valorTotal;
    private Posto posto;
    private String anotacoes;
    
    public Abastecimento() {};
    
    public Abastecimento(Automovel automovel, Date data, Combustivel combustivel, 
            double odometro, double litros, double valorTotal, 
            Posto posto, String anotacoes) {
        this.automovel = automovel;
        this.data = data;
        this.combustivel = combustivel;
        this.odometro = odometro;
        this.litros = litros;
        this.valorTotal = valorTotal;
        this.posto = posto;
        this.anotacoes = anotacoes;
    };
    
    public double getValorPorLitro() {
        return valorTotal / litros;
    }
    
    public double getConsumoPorKM() {
        return odometro / litros;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Automovel getAutomovel() {
        return automovel;
    }

    public Date getData() {
        return data;
    }

    public Combustivel getCombustivel() {
        return combustivel;
    }

    public double getOdometro() {
        return odometro;
    }

    public double getLitros() {
        return litros;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public Posto getPosto() {
        return posto;
    }

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setAutomovel(Automovel automovel) {
        this.automovel = automovel;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setCombustivel(Combustivel combustivel) {
        this.combustivel = combustivel;
    }

    public void setOdometro(double odometro) {
        this.odometro = odometro;
    }

    public void setLitros(double litros) {
        this.litros = litros;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setPosto(Posto posto) {
        this.posto = posto;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }
    
}
