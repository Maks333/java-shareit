package ru.practicum.shareit.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import ru.practicum.shareit.user.markers.OnUserCreate;
import ru.practicum.shareit.user.markers.OnUserUpdate;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class User {
    private Long id;
    @NotEmpty(groups = OnUserCreate.class, message = "Name must be present")
    private String name;
    @NotEmpty(groups = OnUserCreate.class, message = "Email must be present")
    @Email(groups = {OnUserCreate.class, OnUserUpdate.class}, message = "Email should be valid")
    private String email;
}
