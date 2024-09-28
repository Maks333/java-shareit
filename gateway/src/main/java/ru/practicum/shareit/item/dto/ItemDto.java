package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    private Long id;
    @NotEmpty(message = "Name must be present")
    private String name;
    @NotEmpty(message = "Description must be present")
    private String description;
    @NotNull(message = "Availability must be present")
    private Boolean available;
    private Long requestId;
}
