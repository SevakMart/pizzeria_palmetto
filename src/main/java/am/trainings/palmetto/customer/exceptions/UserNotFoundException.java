package am.trainings.palmetto.customer.exceptions;

import am.trainings.palmetto.common.BaseException;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
