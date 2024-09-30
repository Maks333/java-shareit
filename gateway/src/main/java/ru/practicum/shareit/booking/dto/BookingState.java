package ru.practicum.shareit.booking.dto;

public enum BookingState {
    ALL, CURRENT, PAST, FUTURE, WAITING, REJECTED;

    public static BookingState of(String value) {
        if (value == null || value.isBlank()) throw new RuntimeException("State value is empty or null");
        return switch (value.toUpperCase()) {
            case "ALL" -> BookingState.ALL;
            case "CURRENT" -> BookingState.CURRENT;
            case "PAST" -> BookingState.PAST;
            case "FUTURE" -> BookingState.FUTURE;
            case "WAITING" -> BookingState.WAITING;
            case "REJECTED" -> BookingState.REJECTED;
            default -> throw new RuntimeException("Invalid state value: " + value);
        };
    }
}
