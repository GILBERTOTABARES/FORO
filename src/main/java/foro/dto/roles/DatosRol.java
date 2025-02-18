package foro.dto.roles;

import jakarta.validation.constraints.NotBlank;

public record DatosRol(
		@NotBlank(message = "El rol es obligatorio")
		String nombre) {
}