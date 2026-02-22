package controller;

import dao.AdocaoDAO;
import java.sql.SQLException;
import java.util.List;
import model.Adocao;

public class AdocaoController {
    private final AdocaoDAO adocaoDAO;

    public AdocaoController() {
        this.adocaoDAO = new AdocaoDAO();
    }

    public void criar(Adocao adocao) throws SQLException {
        adocaoDAO.inserir(adocao);
    }

    public void atualizar(Adocao adocao) throws SQLException {
        adocaoDAO.atualizar(adocao);
    }

    public void remover(int idAdocao) throws SQLException {
        adocaoDAO.deletar(idAdocao);
    }

    public Adocao buscarPorId(int idAdocao) throws SQLException {
        return adocaoDAO.buscarPorId(idAdocao);
    }

    public List<Adocao> listarTodos() throws SQLException {
        return adocaoDAO.listarTodos();
    }
}
