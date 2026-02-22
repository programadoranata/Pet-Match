package controller;

import dao.AdotanteDAO;
import java.sql.SQLException;
import java.util.List;
import model.Adotante;

public class AdotanteController {
    private final AdotanteDAO adotanteDAO;

    public AdotanteController() {
        this.adotanteDAO = new AdotanteDAO();
    }

    public void criar(Adotante adotante) throws SQLException {
        adotanteDAO.inserir(adotante);
    }

    public void atualizar(Adotante adotante) throws SQLException {
        adotanteDAO.atualizar(adotante);
    }

    public void remover(int idAdotante) throws SQLException {
        adotanteDAO.deletar(idAdotante);
    }

    public Adotante buscarPorId(int idAdotante) throws SQLException {
        return adotanteDAO.buscarPorId(idAdotante);
    }

    public List<Adotante> listarTodos() throws SQLException {
        return adotanteDAO.listarTodos();
    }

    public List<Adotante> buscarPorNome(String filtro) throws SQLException {
        return adotanteDAO.buscarPorNome(filtro);
    }
}
