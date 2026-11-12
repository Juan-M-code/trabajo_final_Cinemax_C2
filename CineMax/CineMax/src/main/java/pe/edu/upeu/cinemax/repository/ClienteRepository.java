package pe.edu.upeu.cinemax.repository;

import pe.edu.upeu.cinemax.model.Cliente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRepository extends AbstractJpaRepository<Cliente, String> {

    @Override
    protected String getTableName() {
        return "cliente";
    }

    @Override
    protected String getPkColumn() {
        return "dni";
    }

    @Override
    public java.util.List<Cliente> findAll() {
        return executeQuery("SELECT * FROM cliente ORDER BY nombre");
    }

    @Override
    protected Cliente insert(Connection conn, Cliente e) throws SQLException {
        executeInsert(conn, "INSERT INTO cliente (dni, nombre, direccion, celular) VALUES (?, ?, ?, ?)",
                e.getDni(), e.getNombre(), e.getDireccion(), e.getCelular());
        return e;
    }

    @Override
    protected Cliente updateRow(Connection conn, Cliente e) throws SQLException {
        executeUpdate(conn, "UPDATE cliente SET nombre = ?, direccion = ?, celular = ? WHERE dni = ?",
                e.getNombre(), e.getDireccion(), e.getCelular(), e.getDni());
        return e;
    }

    @Override
    protected Cliente mapRow(ResultSet rs) throws SQLException {
        return Cliente.builder()
                .dni(rs.getString("dni"))
                .nombre(rs.getString("nombre"))
                .direccion(rs.getString("direccion"))
                .celular(rs.getString("celular"))
                .build();
    }
}
