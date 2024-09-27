package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public static Item toItem(ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        return item;
    }

    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        return itemDto;
    }

    public static BookedItemDto toBookedItemDto(Item item) {
        BookedItemDto bookedItemDto = new BookedItemDto();
        bookedItemDto.setId(item.getId());
        bookedItemDto.setName(item.getName());
        return bookedItemDto;
    }

    public static ItemDtoWithAdditionalInfo toItemDtoWithAdditionalInfo(Item item) {
        ItemDtoWithAdditionalInfo itemDto = new ItemDtoWithAdditionalInfo();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        return itemDto;
    }
}
