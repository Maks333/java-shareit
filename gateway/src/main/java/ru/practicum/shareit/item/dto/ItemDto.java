package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO Sprint add-controllers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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