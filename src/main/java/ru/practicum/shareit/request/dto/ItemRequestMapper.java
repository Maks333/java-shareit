package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.request.ItemRequest;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ItemRequestMapper {
    public static ItemRequest toItemRequest(ItemRequestCreateDto dto) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(dto.getDescription());
        return itemRequest;
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(itemRequest.getId());
        dto.setDescription(itemRequest.getDescription());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss").withZone(ZoneId.of("UTC"));
        dto.setCreated(formatter.format(itemRequest.getCreated()));
        return dto;
    }
}
