package com.example.taskmasters.exceptions;

public final class NameSizeException extends Exception {

    public static final int MINIMUM_LENGTH = 3;
    public static final int MAXIMUM_LENGTH = 128;

    public NameSizeException(String name) {
        super(buildErrorMessage(name));
    }

    private static String buildErrorMessage(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("Name");

        if (name.length() <= MINIMUM_LENGTH) {
            sb.append(" does not achieve the minimum length of " + MINIMUM_LENGTH);
        } else {
            sb.append(" exceeds the maximum length of " + MAXIMUM_LENGTH);
        }

        sb.append(" characters.");

        return sb.toString();
    }
}