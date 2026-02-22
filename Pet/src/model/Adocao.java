package model;

import java.time.LocalDate;

public class Adocao {
    private int idAdocao;
    private int idPet;
    private int idAdotante;
    private LocalDate dataAdocao;

    public Adocao() {
    }

    public Adocao(int idAdocao, int idPet, int idAdotante, LocalDate dataAdocao) {
        this.idAdocao = idAdocao;
        this.idPet = idPet;
        this.idAdotante = idAdotante;
        this.dataAdocao = dataAdocao;
    }

    public int getIdAdocao() {
        return idAdocao;
    }

    public void setIdAdocao(int idAdocao) {
        this.idAdocao = idAdocao;
    }

    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public int getIdAdotante() {
        return idAdotante;
    }

    public void setIdAdotante(int idAdotante) {
        this.idAdotante = idAdotante;
    }

    public LocalDate getDataAdocao() {
        return dataAdocao;
    }

    public void setDataAdocao(LocalDate dataAdocao) {
        this.dataAdocao = dataAdocao;
    }
}
