package com.tsel.app.exception;

import static java.lang.String.format;

public class RouteBuildException extends RuntimeException {

    public RouteBuildException(Integer busStop) {
        super(format("Bus stop with number \"%d\" doesn't exist", busStop));
    }

    public RouteBuildException(Integer p1, Integer p2) {
        super(format("Bus stops \"%d\" и \"%d\" has not connected with each other", p1, p2));
    }
}
