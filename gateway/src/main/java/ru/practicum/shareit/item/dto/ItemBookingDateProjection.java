package ru.practicum.shareit.item.dto;

import java.time.LocalDateTime;

public interface ItemBookingDateProjection {
    LocalDateTime getStartDate();

    LocalDateTime getEndDate();
}
