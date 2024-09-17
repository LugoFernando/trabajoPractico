package com.comic.repositorios.implementacion;

import com.comic.entidades.Figura;
import com.comic.repositorios.FiguraRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository ("FiguraRepositorio")
public class FiguraRepositorioImpl implements FiguraRepositorio {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FiguraRepositorioImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private RowMapper<Figura> figuraRowMapper = new RowMapper<>() {

        @Override
        public Figura mapRow(ResultSet rs, int rowNum) throws SQLException {
            Figura figura = new Figura();
            figura.setId(rs.getLong("id"));
            figura.setNombre(rs.getString("nombre"));
            figura.setPrecio(rs.getDouble("precio"));
            figura.setEstado(rs.getString("estado"));
            figura.setDescripcion(rs.getString("descripcion"));
            figura.setFotoUrl(rs.getString("foto_url"));
            return figura;
        }
    };

    @Override
    public List<Figura> buscarTodo() {
        return jdbcTemplate.query("SELECT * FROM figuras", figuraRowMapper);
    }

    @Override
    public void guardar(Figura figura) {
        jdbcTemplate.update("INSERT INTO figuras (nombre, precio, estado, descripcion, foto_url) VALUES (?, ?, ?, ?, ?)",
                figura.getNombre(), figura.getPrecio(), figura.getEstado(), figura.getDescripcion(), figura.getFotoUrl());
    }

    @Override
    public Figura buscarPorId(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM figuras WHERE id = ?", figuraRowMapper, id);
    }

    @Override
    public void BorrarPorId(Long id) {
        jdbcTemplate.update("DELETE FROM figuras WHERE id = ?", id);
    }
}
