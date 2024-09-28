package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.item.dto.BookedItemDto;
import ru.practicum.shareit.user.dto.BookerDto;

@Data
public class BookingDto {
    private Long id;
    private String start;
    private String end;
    private BookingStatus status;
    private BookerDto booker;
    private BookedItemDto item;
}
