package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.markers.OnUserCreate;
import ru.practicum.shareit.user.markers.OnUserUpdate;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Validated
public class UserController {
    private final UserClient userClient;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> get(@PathVariable long userId) {
        return userClient.findById(userId);
    }

    @Validated(OnUserCreate.class)
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid UserDto user) {
        return userClient.create(user);
    }

    @Validated(OnUserUpdate.class)
    @PatchMapping("/{userId}")
    public ResponseEntity<Object> update(@PathVariable long userId, @RequestBody @Valid UserDto user) {
        return userClient.update(userId, user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> removeUser(@PathVariable long userId) {
        return userClient.removeUser(userId);
    }
}