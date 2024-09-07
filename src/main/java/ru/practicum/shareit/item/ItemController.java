package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService service;

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                       @RequestBody ItemDto itemDto) {
        return service.create(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId,
                       @PathVariable long itemId, @RequestBody ItemDto itemDto) {
        return service.update(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemDto get(@RequestHeader("X-Sharer-User-Id") long userId,
                    @PathVariable long itemId) {
        return service.findById(userId, itemId);
    }

    @GetMapping
    public List<Item> getAll(@RequestHeader("X-Sharer-User-Id") long userId) {
        return service.findAll(userId);
    }

    @GetMapping("/search")
    public List<Item> searchAll(@RequestParam String text) {
        return service.searchAll(text);
    }
}
