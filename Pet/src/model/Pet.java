package model;

public class Pet {
    private int idPet;
    private String nome;
    private String especie;
    private int idade;
    private String descricao;
    private String status;

    public Pet() {
    }

    public Pet(int idPet, String nome, String especie, int idade, String descricao, String status) {
        this.idPet = idPet;
        this.nome = nome;
        this.especie = especie;
        this.idade = idade;
        this.descricao = descricao;
        this.status = status;
    }

    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
