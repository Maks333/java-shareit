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
    private List<Response> items = new ArrayList<>();

    @Data
    static class Response {
        private Long id;
        private String name;
        private Long ownerId;
    }
}
