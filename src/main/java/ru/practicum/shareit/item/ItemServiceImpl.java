package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemDto create(long userId, @Valid ItemDto itemDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User with " + userId + " is not found"));

        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(user);
        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto update(long userId, long itemId, ItemDto itemDto) {
        Item item = itemRepository.findByOwnerIdAndId(userId, itemId).orElseThrow(
                () -> new NotFoundException("Item with id " + itemId + " is not found for user with id " + userId));

        if (itemDto.getName() != null && !itemDto.getName().isBlank()) item.setName(itemDto.getName());
        if (itemDto.getDescription() != null && !itemDto.getDescription().isBlank())
            item.setDescription(itemDto.getDescription());
        if (itemDto.getAvailable() != null) item.setAvailable(itemDto.getAvailable());
        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto findById(long userId, long itemId) {
        return ItemMapper.toItemDto(itemRepository.findByOwnerIdAndId(userId, itemId).orElseThrow(
                () -> new NotFoundException("Item with id " + itemId + " is not found for user with id " + userId)
        ));
    }

    @Override
    public List<ItemDtoWithDates> findAll(long userId) {
        PageRequest page = PageRequest.of(0, 2);
        List<ItemDtoWithDates> items = itemRepository.findAllByOwnerId(userId).stream()
                .map(ItemMapper::toItemDtoWithDates)
                .toList();

        items.forEach(item -> {
            Page<ItemBookingDateProjection> pages = bookingRepository.findItemBookingDates(item.getId(), page);
            try {
                List<ItemBookingDateProjection> dates = pages.getContent();
                item.setLastBookingDates(dates.getFirst());
                item.setNextBookingDates(dates.getLast());
            } catch (RuntimeException ignored) {
            }
        });

        return items;
    }

    @Override
    public List<ItemDto> searchAll(String text) {
        return itemRepository.findAllAvailableByText(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto createComment(long userId, long itemId, @Valid CommentCreateDto commentDto) {
        Booking booking = bookingRepository
                .findByBookerIdAndItemIdAndEndDateBeforeAndStatusIs(userId, itemId, LocalDateTime.now(), BookingStatus.APPROVED)
                .orElseThrow(() -> new RuntimeException("Item is not eligible for comment"));

        Comment comment = new Comment();
        comment.setItem(booking.getItem());
        comment.setAuthor(booking.getBooker());
        comment.setText(commentDto.getText());
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }
}