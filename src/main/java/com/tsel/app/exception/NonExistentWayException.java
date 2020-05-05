package com.tsel.app.exception;

import static java.lang.String.format;

public class NonExistentWayException extends RuntimeException {

    public NonExistentWayException(Integer p1, Integer p2) {
        super(format("Остановки \"%d\" и \"%d\" не имеют общей дороги", p1, p2));
    }
}
