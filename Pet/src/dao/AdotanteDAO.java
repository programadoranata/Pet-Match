package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Adotante;
import utils.ConnectionFactory;

public class AdotanteDAO {

    public void inserir(Adotante adotante) throws SQLException {
        String sql = "INSERT INTO adotantes (nome, telefone, tipo_preferido) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, adotante.getNome());
            stmt.setString(2, adotante.getTelefone());
            stmt.setString(3, adotante.getTipoPreferido());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Adotante adotante) throws SQLException {
        String sql = "UPDATE adotantes SET nome = ?, telefone = ?, tipo_preferido = ? WHERE id_adotante = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, adotante.getNome());
            stmt.setString(2, adotante.getTelefone());
            stmt.setString(3, adotante.getTipoPreferido());
            stmt.setInt(4, adotante.getIdAdotante());
            stmt.executeUpdate();
        }
    }

    public void deletar(int idAdotante) throws SQLException {
        String sql = "DELETE FROM adotantes WHERE id_adotante = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAdotante);
            stmt.executeUpdate();
        }
    }

    public Adotante buscarPorId(int idAdotante) throws SQLException {
        String sql = "SELECT * FROM adotantes WHERE id_adotante = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAdotante);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapAdotante(rs);
                }
            }
        }
        return null;
    }

    public List<Adotante> listarTodos() throws SQLException {
        List<Adotante> adotantes = new ArrayList<>();
        String sql = "SELECT * FROM adotantes";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                adotantes.add(mapAdotante(rs));
            }
        }
        return adotantes;
    }

    public List<Adotante> buscarPorNome(String filtro) throws SQLException {
        List<Adotante> adotantes = new ArrayList<>();
        String sql = "SELECT * FROM adotantes WHERE nome LIKE ? ORDER BY nome";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + filtro + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    adotantes.add(mapAdotante(rs));
                }
            }
        }
        return adotantes;
    }

    private Adotante mapAdotante(ResultSet rs) throws SQLException {
        Adotante adotante = new Adotante();
        adotante.setIdAdotante(rs.getInt("id_adotante"));
        adotante.setNome(rs.getString("nome"));
        adotante.setTelefone(rs.getString("telefone"));
        adotante.setTipoPreferido(rs.getString("tipo_preferido"));
        return adotante;
    }
}
