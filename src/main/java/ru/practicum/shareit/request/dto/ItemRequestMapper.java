package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss").withZone(ZoneId.systemDefault());
        dto.setCreated(formatter.format(itemRequest.getCreated()));
        return dto;
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest, List<Item> items) {
        ItemRequestDto dto = toItemRequestDto(itemRequest);
        dto.setResponses(items.stream().map(ItemRequestMapper::toResponse).toList());
        return dto;
    }

    private static ItemRequestDto.Response toResponse(Item item) {
        ItemRequestDto.Response response = new ItemRequestDto.Response();
        response.setItemId(item.getId());
        response.setItemName(item.getName());
        response.setOwnerId(item.getOwner().getId());
        return response;
    }
}
