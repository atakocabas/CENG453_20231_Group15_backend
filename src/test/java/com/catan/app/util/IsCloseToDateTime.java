package com.catan.app.util;

import org.mockito.ArgumentMatcher;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class IsCloseToDateTime implements ArgumentMatcher<LocalDateTime> {
    private final LocalDateTime expected;
    private final long leeway; // in seconds

    public IsCloseToDateTime(LocalDateTime expected, long leeway) {
        this.expected = expected;
        this.leeway = leeway;
    }

    @Override
    public boolean matches(LocalDateTime argument) {
        if (argument == null) {
            return false;
        }
        long diff = Math.abs(ChronoUnit.SECONDS.between(expected, argument));
        return diff <= leeway;
    }
}
