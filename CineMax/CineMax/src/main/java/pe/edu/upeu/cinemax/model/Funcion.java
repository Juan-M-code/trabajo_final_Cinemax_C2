package pe.edu.upeu.cinemax.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upeu.cinemax.enums.EstadoFuncion;

import java.time.LocalDate;
import java.time.LocalTime;

/** Proyección de una película en una sala, con fecha y hora. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Funcion {

    private Long idFuncion;

    @NotNull(message = "Selecciona una película.")
    private Long idPelicula;

    @NotNull(message = "Selecciona una sala.")
    private Long idSala;

    @NotNull(message = "La fecha es obligatoria.")
    private LocalDate fecha;

    @NotNull(message = "La hora es obligatoria.")
    private LocalTime hora;

    @NotNull(message = "El estado es obligatorio.")
    private EstadoFuncion estado;

    // Datos de apoyo para mostrar en la tabla (no se guardan en la tabla funcion)
    private String nombrePelicula;
    private String nombreSala;

    @Override
    public String toString() {
        String base = nombrePelicula == null ? "Función " + idFuncion : nombrePelicula;
        return base + " · " + fecha + " " + hora;
    }
}
