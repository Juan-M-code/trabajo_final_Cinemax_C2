package pe.edu.upeu.cinemax.repository;

import pe.edu.upeu.cinemax.enums.EstadoPelicula;
import pe.edu.upeu.cinemax.model.Pelicula;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PeliculaRepository extends AbstractJpaRepository<Pelicula, Long> {

    @Override
    protected String getTableName() {
        return "pelicula";
    }

    @Override
    protected String getPkColumn() {
        return "id_pelicula";
    }

    @Override
    public List<Pelicula> findAll() {
        return executeQuery("SELECT * FROM pelicula ORDER BY titulo");
    }

    public Optional<Pelicula> findByTitulo(String titulo) {
        return executeQueryOne("SELECT * FROM pelicula WHERE LOWER(titulo) = LOWER(?)", titulo);
    }

    @Override
    protected Pelicula insert(Connection conn, Pelicula e) throws SQLException {
        long id = executeInsertGetKey(conn,
                "INSERT INTO pelicula (titulo, genero, duracion_min, clasificacion, estado) VALUES (?, ?, ?, ?, ?)",
                e.getTitulo(), e.getGenero(), e.getDuracionMin(), e.getClasificacion(),
                e.getEstado().getDescripcion());
        e.setIdPelicula(id);
        return e;
    }

    @Override
    protected Pelicula updateRow(Connection conn, Pelicula e) throws SQLException {
        executeUpdate(conn,
                "UPDATE pelicula SET titulo = ?, genero = ?, duracion_min = ?, clasificacion = ?, estado = ? WHERE id_pelicula = ?",
                e.getTitulo(), e.getGenero(), e.getDuracionMin(), e.getClasificacion(),
                e.getEstado().getDescripcion(), e.getIdPelicula());
        return e;
    }

    @Override
    protected Pelicula mapRow(ResultSet rs) throws SQLException {
        return Pelicula.builder()
                .idPelicula(rs.getLong("id_pelicula"))
                .titulo(rs.getString("titulo"))
                .genero(rs.getString("genero"))
                .duracionMin(rs.getInt("duracion_min"))
                .clasificacion(rs.getString("clasificacion"))
                .estado(EstadoPelicula.desde(rs.getString("estado")))
                .build();
    }
}
