package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Pet;
import utils.ConnectionFactory;

public class PetDAO {

    public void inserir(Pet pet) throws SQLException {
        String sql = "INSERT INTO pets (nome, especie, idade, descricao, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pet.getNome());
            stmt.setString(2, pet.getEspecie());
            stmt.setInt(3, pet.getIdade());
            stmt.setString(4, pet.getDescricao());
            stmt.setString(5, pet.getStatus());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Pet pet) throws SQLException {
        String sql = "UPDATE pets SET nome = ?, especie = ?, idade = ?, descricao = ?, status = ? WHERE id_pet = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pet.getNome());
            stmt.setString(2, pet.getEspecie());
            stmt.setInt(3, pet.getIdade());
            stmt.setString(4, pet.getDescricao());
            stmt.setString(5, pet.getStatus());
            stmt.setInt(6, pet.getIdPet());
            stmt.executeUpdate();
        }
    }

    public void deletar(int idPet) throws SQLException {
        String sql = "DELETE FROM pets WHERE id_pet = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPet);
            stmt.executeUpdate();
        }
    }

    public Pet buscarPorId(int idPet) throws SQLException {
        String sql = "SELECT * FROM pets WHERE id_pet = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPet);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapPet(rs);
                }
            }
        }
        return null;
    }

    public List<Pet> listarTodos() throws SQLException {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                pets.add(mapPet(rs));
            }
        }
        return pets;
    }

    public List<Pet> buscarPorNome(String filtro) throws SQLException {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets WHERE nome LIKE ? ORDER BY nome";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + filtro + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pets.add(mapPet(rs));
                }
            }
        }
        return pets;
    }

    private Pet mapPet(ResultSet rs) throws SQLException {
        Pet pet = new Pet();
        pet.setIdPet(rs.getInt("id_pet"));
        pet.setNome(rs.getString("nome"));
        pet.setEspecie(rs.getString("especie"));
        pet.setIdade(rs.getInt("idade"));
        pet.setDescricao(rs.getString("descricao"));
        pet.setStatus(rs.getString("status"));
        return pet;
    }
}
