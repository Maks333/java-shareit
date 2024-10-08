package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import ru.practicum.shareit.user.markers.OnUserCreate;
import ru.practicum.shareit.user.markers.OnUserUpdate;

@Data
public class UserDto {
    private Long id;
    @NotEmpty(groups = OnUserCreate.class, message = "Name must be present")
    private String name;
    @NotEmpty(groups = OnUserCreate.class, message = "Email must be present")
    @Email(groups = {OnUserCreate.class, OnUserUpdate.class}, message = "Email should be valid")
    private String email;
}
