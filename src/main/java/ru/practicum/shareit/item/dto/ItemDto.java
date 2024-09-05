package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import ru.practicum.shareit.item.markers.onItemCreate;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    @NotEmpty(groups = onItemCreate.class, message = "Name must be present")
    private String name;
    @NotEmpty(groups = onItemCreate.class, message = "Description must be present")
    private String description;
    @NotEmpty(groups = onItemCreate.class, message = "Availability must be present")
    private Boolean available;
}
