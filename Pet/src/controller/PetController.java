package controller;

import dao.PetDAO;
import java.sql.SQLException;
import java.util.List;
import model.Pet;

public class PetController {
    private final PetDAO petDAO;

    public PetController() {
        this.petDAO = new PetDAO();
    }

    public void criar(Pet pet) throws SQLException {
        petDAO.inserir(pet);
    }

    public void atualizar(Pet pet) throws SQLException {
        petDAO.atualizar(pet);
    }

    public void remover(int idPet) throws SQLException {
        petDAO.deletar(idPet);
    }

    public Pet buscarPorId(int idPet) throws SQLException {
        return petDAO.buscarPorId(idPet);
    }

    public List<Pet> listarTodos() throws SQLException {
        return petDAO.listarTodos();
    }

    public List<Pet> buscarPorNome(String filtro) throws SQLException {
        return petDAO.buscarPorNome(filtro);
    }
}
