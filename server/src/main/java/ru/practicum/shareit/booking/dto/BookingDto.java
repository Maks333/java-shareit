package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.dto.BookedItemDto;
import ru.practicum.shareit.user.dto.BookerDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private String start;
    private String end;
    private BookingStatus status;
    private BookerDto booker;
    private BookedItemDto item;
}
