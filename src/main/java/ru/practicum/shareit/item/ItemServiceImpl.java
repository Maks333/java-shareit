package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ItemDtoWithAdditionalInfo findById(long userId, long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new NotFoundException("Item with id " + itemId + " is not found")
        );
        ItemDtoWithAdditionalInfo itemDto = ItemMapper.toItemDtoWithAdditionalInfo(item);
        addAdditionalInfoToItem(itemDto);
        return itemDto;
    }

    @Override
    public List<ItemDtoWithAdditionalInfo> findAll(long userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id " + userId + " is not found"));

        List<ItemDtoWithAdditionalInfo> items = itemRepository.findAllByOwnerId(userId).stream()
                .map(ItemMapper::toItemDtoWithAdditionalInfo)
                .toList();

        items.forEach(this::addAdditionalInfoToItem);
        return items;
    }

    private void addAdditionalInfoToItem(ItemDtoWithAdditionalInfo item) {
        List<ItemBookingDateProjection> lastBooking =
                bookingRepository.findAllCurrentBookingsOfItem(item.getId(), LocalDateTime.now());
        item.setLastBooking(lastBooking.isEmpty() ? null : lastBooking.getFirst());

        List<ItemBookingDateProjection> nextBooking =
                bookingRepository.findAllFutureBookingsOfItem(item.getId(), LocalDateTime.now());
        item.setNextBooking(nextBooking.isEmpty() ? null : nextBooking.getFirst());

        List<CommentDto> comments = commentRepository.findAllByItemId(item.getId()).stream()
                .map(CommentMapper::toCommentDto)
                .toList();
        item.setComments(comments);
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