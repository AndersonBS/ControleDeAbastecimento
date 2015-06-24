package modelo;

import java.sql.Date;

public class Seguro {
    
    private int id;
    private Automovel automovel;
    private Date inicioVigencia;
    private Date fimVigencia;
    private double valorParcela;
    private int quantidadeParcelas;
    private double valorFranquia;
    private double valorCobertura;
    private String anotacoes;

    public int getId() {
        return id;
    }

    public Automovel getAutomovel() {
        return automovel;
    }

    public Date getInicioVigencia() {
        return inicioVigencia;
    }

    public Date getFimVigencia() {
        return fimVigencia;
    }

    public double getValorParcela() {
        return valorParcela;
    }

    public int getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public double getValorFranquia() {
        return valorFranquia;
    }

    public double getValorCobertura() {
        return valorCobertura;
    }

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAutomovel(Automovel automovel) {
        this.automovel = automovel;
    }

    public void setInicioVigencia(Date inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public void setFimVigencia(Date fimVigencia) {
        this.fimVigencia = fimVigencia;
    }

    public void setValorParcela(double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public void setQuantidadeParcelas(int quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public void setValorFranquia(double valorFranquia) {
        this.valorFranquia = valorFranquia;
    }

    public void setValorCobertura(double valorCobertura) {
        this.valorCobertura = valorCobertura;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }
            
}
