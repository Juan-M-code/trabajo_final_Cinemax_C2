package pe.edu.upeu.cinemax.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upeu.cinemax.enums.EstadoPelicula;

/** Película que se proyecta en el cine. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pelicula {

    private Long idPelicula;

    @NotBlank(message = "El título es obligatorio.")
    @Size(max = 100, message = "El título no debe superar 100 caracteres.")
    private String titulo;

    @NotBlank(message = "El género es obligatorio.")
    @Size(max = 40, message = "El género no debe superar 40 caracteres.")
    private String genero;

    @NotNull(message = "La duración es obligatoria.")
    @Min(value = 1, message = "La duración debe ser mayor a 0 minutos.")
    private Integer duracionMin;

    @NotBlank(message = "La clasificación es obligatoria.")
    private String clasificacion;

    @NotNull(message = "El estado es obligatorio.")
    private EstadoPelicula estado;

    @Override
    public String toString() {
        return titulo == null ? "" : titulo;
    }
}
