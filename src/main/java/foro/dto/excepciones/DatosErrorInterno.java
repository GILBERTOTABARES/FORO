package foro.dto.excepciones;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public record DatosErrorInterno(
		int codigoDeError,
		HttpStatus status,
		LocalDateTime fechaYHora,
		String mensaje) {

	public DatosErrorInterno(Exception excepcion) {
		this(
				500, 
				HttpStatus.INTERNAL_SERVER_ERROR, 
				LocalDateTime.now(), 
				"Por favor, intenta más tarde.");
	}
}