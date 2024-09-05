package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService service;

    @GetMapping("/{userId}")
    public User get(@PathVariable long userId) {
        return service.findById(userId);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return service.create(user);
    }

    @PatchMapping("/{userId}")
    public User update(@PathVariable long userId, @RequestBody User user) {
        return service.update(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        service.delete(userId);
    }
}
