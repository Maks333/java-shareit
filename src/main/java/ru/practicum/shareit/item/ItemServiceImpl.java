package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public Item create(long userId, ItemDto itemDto) {
        return itemRepository.create(userId, itemDto);
    }

    @Override
    public Item update(long userId, long itemId, ItemDto itemDto) {
        return itemRepository.update(userId, itemId, itemDto);
    }

    @Override
    public Item findById(long userId, long itemId) {
        return itemRepository.findById(userId, itemId);
    }

    @Override
    public List<Item> findAll(long userId) {
        return itemRepository.findAll(userId);
    }

    @Override
    public List<Item> searchAll(String text) {
        return itemRepository.searchAll(text);
    }
}
