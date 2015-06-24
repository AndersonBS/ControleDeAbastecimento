package modelo;

import java.sql.Date;

public class Lembrete {
    
    private int id;
    private Automovel automovel;
    private Date dataPrevista;
    private double valorOrcado;
    private String descricaoLocal;
    private String descricaoServico;

    public int getId() {
        return id;
    }

    public Automovel getAutomovel() {
        return automovel;
    }

    public Date getDataPrevista() {
        return dataPrevista;
    }

    public double getValorOrcado() {
        return valorOrcado;
    }

    public String getDescricaoLocal() {
        return descricaoLocal;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAutomovel(Automovel automovel) {
        this.automovel = automovel;
    }

    public void setDataPrevista(Date dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public void setValorOrcado(double valorOrcado) {
        this.valorOrcado = valorOrcado;
    }

    public void setDescricaoLocal(String descricaoLocal) {
        this.descricaoLocal = descricaoLocal;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }
    
}
