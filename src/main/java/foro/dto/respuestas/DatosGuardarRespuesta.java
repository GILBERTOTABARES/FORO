package foro.dto.respuestas;

import foro.modelo.Respuesta;
import jakarta.validation.constraints.NotBlank;

public record DatosGuardarRespuesta(
		@NotBlank(message = "Es obligatorio")
		String mensaje
		) {

	public DatosGuardarRespuesta(Respuesta respuesta) {
		this(respuesta.getMensaje());
	}

}