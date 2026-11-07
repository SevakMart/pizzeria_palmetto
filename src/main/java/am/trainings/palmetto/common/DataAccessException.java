package am.trainings.palmetto.common;

public class DataAccessException extends BaseException {

    public DataAccessException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
