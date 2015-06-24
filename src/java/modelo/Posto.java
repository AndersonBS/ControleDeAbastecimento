package modelo;

public class Posto {
    
    private int id;
    private Usuario usuario;
    private String nome;
    private String marca;
    private String endereco;
    private String telefone;
    private boolean cartao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getMarca() {
        return marca;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public boolean getCartao() {
        return cartao;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCartao(boolean cartao) {
        this.cartao = cartao;
    }
    
}
