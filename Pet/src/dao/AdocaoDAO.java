package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Adocao;
import utils.ConnectionFactory;

public class AdocaoDAO {

    public void inserir(Adocao adocao) throws SQLException {
        String sql = "INSERT INTO adocoes (id_pet, id_adotante, data_adocao) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adocao.getIdPet());
            stmt.setInt(2, adocao.getIdAdotante());
            if (adocao.getDataAdocao() != null) {
                stmt.setDate(3, Date.valueOf(adocao.getDataAdocao()));
            } else {
                stmt.setDate(3, null);
            }
            stmt.executeUpdate();
        }
    }

    public void atualizar(Adocao adocao) throws SQLException {
        String sql = "UPDATE adocoes SET id_pet = ?, id_adotante = ?, data_adocao = ? WHERE id_adocao = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adocao.getIdPet());
            stmt.setInt(2, adocao.getIdAdotante());
            if (adocao.getDataAdocao() != null) {
                stmt.setDate(3, Date.valueOf(adocao.getDataAdocao()));
            } else {
                stmt.setDate(3, null);
            }
            stmt.setInt(4, adocao.getIdAdocao());
            stmt.executeUpdate();
        }
    }

    public void deletar(int idAdocao) throws SQLException {
        String sql = "DELETE FROM adocoes WHERE id_adocao = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAdocao);
            stmt.executeUpdate();
        }
    }

    public Adocao buscarPorId(int idAdocao) throws SQLException {
        String sql = "SELECT * FROM adocoes WHERE id_adocao = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAdocao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapAdocao(rs);
                }
            }
        }
        return null;
    }

    public List<Adocao> listarTodos() throws SQLException {
        List<Adocao> adocoes = new ArrayList<>();
        String sql = "SELECT * FROM adocoes";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                adocoes.add(mapAdocao(rs));
            }
        }
        return adocoes;
    }

    private Adocao mapAdocao(ResultSet rs) throws SQLException {
        Adocao adocao = new Adocao();
        adocao.setIdAdocao(rs.getInt("id_adocao"));
        adocao.setIdPet(rs.getInt("id_pet"));
        adocao.setIdAdotante(rs.getInt("id_adotante"));
        Date data = rs.getDate("data_adocao");
        if (data != null) {
            adocao.setDataAdocao(data.toLocalDate());
        }
        return adocao;
    }
}
