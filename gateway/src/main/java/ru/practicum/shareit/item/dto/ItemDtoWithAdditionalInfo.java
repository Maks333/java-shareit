package ru.practicum.shareit.item.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemDtoWithAdditionalInfo {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private ItemBookingDateProjection lastBooking;
    private ItemBookingDateProjection nextBooking;
    private List<CommentDto> comments;
}
