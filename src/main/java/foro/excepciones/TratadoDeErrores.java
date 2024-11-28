package foro.excepciones;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import foro.dto.excepciones.DatosErrorApi;
import foro.dto.excepciones.DatosErrorCambioDeEstadoInvalido;
import foro.dto.excepciones.DatosErrorFormatoNoSoportado;
import foro.dto.excepciones.DatosErrorFormatoRequeridoNoAceptable;
import foro.dto.excepciones.DatosErrorIdDeEntidadInvalido;
import foro.dto.excepciones.DatosErrorInterno;
import foro.dto.excepciones.DatosErrorMetodoNoSoportado;
import foro.dto.excepciones.DatosErrorPertenenciaInvalida;
import foro.dto.excepciones.DatosErrorRecursoNoEncontrado;
import foro.dto.excepciones.DatosErrorValidacion;
import foro.dto.excepciones.DatosErrorValorDuplicado;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class TratadoDeErrores {


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> tratarError400(MethodArgumentNotValidException excepcion) {
		var errores = excepcion.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
		return ResponseEntity.badRequest().body(errores);
	}


	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<DatosErrorApi> tratarError400(HttpMessageNotReadableException excepcion) {
		DatosErrorApi datosError = new DatosErrorApi(400, HttpStatus.BAD_REQUEST, excepcion, null);
		return ResponseEntity.badRequest().body(datosError);
	}


	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<DatosErrorValorDuplicado> tratarError400(DataIntegrityViolationException excepcion) {
		DatosErrorValorDuplicado datosError = new DatosErrorValorDuplicado(excepcion);
		return ResponseEntity.badRequest().body(datosError);		
	}


	@ExceptionHandler(TransaccionSobreEntidadInexistenteException.class)
	public ResponseEntity<DatosErrorIdDeEntidadInvalido> tratarError400(TransaccionSobreEntidadInexistenteException excepcion) {
		DatosErrorIdDeEntidadInvalido datosError = new DatosErrorIdDeEntidadInvalido(excepcion);
		return ResponseEntity.badRequest().body(datosError);
	}


	@ExceptionHandler(PertenenciaInvalidaExcepcion.class)
	public ResponseEntity<DatosErrorPertenenciaInvalida> tratarError400(PertenenciaInvalidaExcepcion excepcion) {
		DatosErrorPertenenciaInvalida datosError = new DatosErrorPertenenciaInvalida(excepcion);
		return ResponseEntity.badRequest().body(datosError);
	}


	@ExceptionHandler(RecursoNoEncontradoException.class)
	public ResponseEntity<DatosErrorRecursoNoEncontrado> tratarError404(RecursoNoEncontradoException excepcion) {
		DatosErrorRecursoNoEncontrado datosError = new DatosErrorRecursoNoEncontrado(excepcion);
		return ResponseEntity.status(datosError.status()).body(datosError);
	}


	@ExceptionHandler(CambioDeEstadoInvalidoException.class) 
	public ResponseEntity<DatosErrorCambioDeEstadoInvalido> tratarError400(CambioDeEstadoInvalidoException excepcion){
		DatosErrorCambioDeEstadoInvalido datosError = new DatosErrorCambioDeEstadoInvalido(excepcion);
		return ResponseEntity.badRequest().body(datosError);		
	}


	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<DatosErrorMetodoNoSoportado> tratarError405(
			HttpRequestMethodNotSupportedException excepcion, 
			HttpServletRequest request) {

		DatosErrorMetodoNoSoportado datosError = new DatosErrorMetodoNoSoportado(excepcion, request);
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(datosError);
	}


	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public ResponseEntity<DatosErrorFormatoRequeridoNoAceptable> tratarError406(
			HttpMediaTypeNotAcceptableException excepcion,
			HttpServletRequest peticion) {

		DatosErrorFormatoRequeridoNoAceptable datosError = 
				new DatosErrorFormatoRequeridoNoAceptable(excepcion, peticion);

		return ResponseEntity
				.status(HttpStatus.NOT_ACCEPTABLE)
				.contentType(MediaType.APPLICATION_JSON)
				.body(datosError);
	}


	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<DatosErrorFormatoNoSoportado> tratarError415(
			HttpMediaTypeNotSupportedException excepcion) {

		DatosErrorFormatoNoSoportado datosError = new DatosErrorFormatoNoSoportado(excepcion);
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(datosError);
	}


	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> tratarError500(Exception excepcion) {
		System.out.println("Error no atrapado:");
		excepcion.printStackTrace();
		DatosErrorInterno datosError = new DatosErrorInterno(excepcion);
		return new ResponseEntity<>(datosError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}