package com.unigo.validation;

public final class ValidationCode {

    private ValidationCode() {
    }

    public static String required(String field) {
        return "VALIDATION." + normalize(field) + ".REQUIRED";
    }

    public static String min(String field, int min) {
        return "VALIDATION." + normalize(field) + ".MIN_" + min;
    }

    public static String max(String field, int max) {
        return "VALIDATION." + normalize(field) + ".MAX_" + max;
    }

    public static String fromTo(String field, int min, int max) {
        return "VALIDATION."
                + normalize(field)
                + ".FROM_"
                + min
                + "_TO_"
                + max;
    }

    public static String invalid(String field) {
        return "VALIDATION." + normalize(field) + ".INVALID";
    }

    private static String normalize(String field) {
        return field
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .replace('-', '_')
                .toUpperCase();
    }
}
