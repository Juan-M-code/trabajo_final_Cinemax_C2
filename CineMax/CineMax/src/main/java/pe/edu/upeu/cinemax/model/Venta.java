package pe.edu.upeu.cinemax.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Cabecera de una venta. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    private Long idVenta;
    private String dniCliente;
    private Long idUsuario;
    private LocalDate fechaVenta;
    private BigDecimal netoTotal;
    private BigDecimal igv;
    private BigDecimal precioTotal;

    // Datos de apoyo para mostrar en la tabla (no se guardan en la tabla venta)
    private String nombreCliente;
    private String nombreUsuario;

    @Override
    public String toString() {
        return "Venta " + (idVenta == null ? "" : idVenta);
    }
}
