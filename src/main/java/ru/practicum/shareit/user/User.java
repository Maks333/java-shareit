package ru.practicum.shareit.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import ru.practicum.shareit.user.markers.onUserCreate;
import ru.practicum.shareit.user.markers.onUserUpdate;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class User {
    private Long id;
    @NotEmpty(groups = onUserCreate.class, message = "Name must be present")
    private String name;
    @NotEmpty(groups = onUserCreate.class, message = "Email must be present")
    @Email(groups = {onUserCreate.class, onUserUpdate.class}, message = "Email should be valid")
    private String email;
}
