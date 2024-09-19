package ru.practicum.shareit.item.dto;

import lombok.Data;

@Data
public class ItemDtoWithDates {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private ItemBookingDateProjection lastBookingDates;
    private ItemBookingDateProjection nextBookingDates;
}
