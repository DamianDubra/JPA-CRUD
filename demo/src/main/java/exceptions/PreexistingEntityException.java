package exceptions;

/**
 * Excepción que se lanza cuando se intenta crear una entidad
 * que ya existe en la base de datos (mismo id).
 */
public class PreexistingEntityException extends Exception {

    public PreexistingEntityException(String message) {
        super(message);
    }

    public PreexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
