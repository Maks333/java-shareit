package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoWithAdditionalInfo {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private ItemBookingDateProjection lastBooking;
    private ItemBookingDateProjection nextBooking;
    private List<CommentDto> comments;
}
