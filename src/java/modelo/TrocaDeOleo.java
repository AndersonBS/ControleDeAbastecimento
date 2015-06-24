package modelo;

import java.sql.Date;

public class TrocaDeOleo {
    
    private int id;
    private Automovel automovel;
    private String nomeOleo;
    private String tipoOleo;
    private Date dataTroca;
    private Date dataProximaTroca;
    private boolean trocaFiltro;
    private double preco;

    public int getId() {
        return id;
    }

    public Automovel getAutomovel() {
        return automovel;
    }

    public String getNomeOleo() {
        return nomeOleo;
    }

    public String getTipoOleo() {
        return tipoOleo;
    }

    public Date getDataTroca() {
        return dataTroca;
    }

    public Date getDataProximaTroca() {
        return dataProximaTroca;
    }

    public boolean getTrocaFiltro() {
        return trocaFiltro;
    }

    public double getPreco() {
        return preco;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAutomovel(Automovel automovel) {
        this.automovel = automovel;
    }

    public void setNomeOleo(String nomeOleo) {
        this.nomeOleo = nomeOleo;
    }

    public void setTipoOleo(String tipoOleo) {
        this.tipoOleo = tipoOleo;
    }

    public void setDataTroca(Date dataTroca) {
        this.dataTroca = dataTroca;
    }

    public void setDataProximaTroca(Date dataProximaTroca) {
        this.dataProximaTroca = dataProximaTroca;
    }

    public void setTrocaFiltro(boolean trocaFiltro) {
        this.trocaFiltro = trocaFiltro;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    
}
