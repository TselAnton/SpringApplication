package com.tsel.app.exception;

import static java.lang.String.format;

public class NonExistentWayException extends RuntimeException {

    public NonExistentWayException(Integer p1, Integer p2) {
        super(format("Bus stops \"%d\" и \"%d\" has not connected with each other", p1, p2));
    }
}
