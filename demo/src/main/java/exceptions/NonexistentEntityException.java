package exceptions;

/**
 * Excepción que se lanza cuando se intenta editar o borrar una entidad
 * que no existe en la base de datos.
 */
public class NonexistentEntityException extends Exception {

    public NonexistentEntityException(String message) {
        super(message);
    }

    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
