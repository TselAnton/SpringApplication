package com.tsel.app.exception;

import static java.lang.String.format;

public class BusStopNotExistException extends RuntimeException {

    public BusStopNotExistException(Integer busStop) {
        super(format("Bus stop with name \"%d\" doesn't exist", busStop));
    }
}
