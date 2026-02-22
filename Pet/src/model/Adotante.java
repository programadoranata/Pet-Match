package model;

public class Adotante {
    private int idAdotante;
    private String nome;
    private String telefone;
    private String tipoPreferido;

    public Adotante() {
    }

    public Adotante(int idAdotante, String nome, String telefone, String tipoPreferido) {
        this.idAdotante = idAdotante;
        this.nome = nome;
        this.telefone = telefone;
        this.tipoPreferido = tipoPreferido;
    }

    public int getIdAdotante() {
        return idAdotante;
    }

    public void setIdAdotante(int idAdotante) {
        this.idAdotante = idAdotante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipoPreferido() {
        return tipoPreferido;
    }

    public void setTipoPreferido(String tipoPreferido) {
        this.tipoPreferido = tipoPreferido;
    }
}
