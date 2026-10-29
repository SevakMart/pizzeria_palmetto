package am.trainings.palmetto.customer.exceptions;

import am.trainings.palmetto.common.BaseException;

public class DuplicateUsernameException extends BaseException {


    public DuplicateUsernameException(String message) {
        super(message);
    }
}
