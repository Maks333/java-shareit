package ru.practicum.shareit.request.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@Data
public class ItemRequestDto {
    private Long id;
    private String description;
    private String created;
    private List<Response> responses = new ArrayList<>();

    @Data
    static class Response {
        private Long itemId;
        private String itemName;
        private Long ownerId;
    }
}
