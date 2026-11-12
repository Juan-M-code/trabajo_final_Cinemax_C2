package pe.edu.upeu.cinemax.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Cliente identificado por su DNI. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @NotBlank(message = "El DNI es obligatorio.")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener exactamente 8 dígitos.")
    private String dni;

    @NotBlank(message = "El nombre del cliente es obligatorio.")
    @Size(max = 80, message = "El nombre no debe superar 80 caracteres.")
    private String nombre;

    @Size(max = 120, message = "La dirección no debe superar 120 caracteres.")
    private String direccion;

    @Size(max = 15, message = "El celular no debe superar 15 caracteres.")
    private String celular;

    @Override
    public String toString() {
        return nombre == null ? dni : nombre + " (" + dni + ")";
    }
}
