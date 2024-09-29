package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemRequestRepository itemRequestRepository;

    @Override
    public ItemDto create(long userId, ItemDto itemDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User with " + userId + " is not found"));

        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(user);
        if (itemDto.getRequestId() != null) {
            ItemRequest itemRequest = itemRequestRepository.findById(itemDto.getRequestId())
                    .orElseThrow(() -> new NotFoundException("ItemRequest with id " + itemDto.getRequestId() + " is not found"));
            item.setRequest(itemRequest);
        }
        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto update(long userId, long itemId, ItemDto itemDto) {
        Item item = getItemByOwnerIdAndItemId(userId, itemId);

        if (itemDto.getName() != null && !itemDto.getName().isBlank()) item.setName(itemDto.getName());
        if (itemDto.getDescription() != null && !itemDto.getDescription().isBlank())
            item.setDescription(itemDto.getDescription());
        if (itemDto.getAvailable() != null) item.setAvailable(itemDto.getAvailable());
        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    private Item getItemByOwnerIdAndItemId(long userId, long itemId) {
        return itemRepository.findByOwnerIdAndId(userId, itemId).orElseThrow(
                () -> new NotFoundException("Item with id " + itemId + " is not found for user with id " + userId));
    }

    @Override
    public ItemDtoWithAdditionalInfo findById(long userId, long itemId) {
        Item item = getItemById(itemId);
        ItemDtoWithAdditionalInfo itemDto = ItemMapper.toItemDtoWithAdditionalInfo(item);
        addAdditionalInfoToItem(itemDto);
        return itemDto;
    }

    private Item getItemById(long itemId) {
        return itemRepository.findById(itemId).orElseThrow(
                () -> new NotFoundException("Item with id " + itemId + " is not found")
        );
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
    public CommentDto createComment(long userId, long itemId, CommentCreateDto commentDto) {
        Booking booking = bookingRepository
                .findByBookerIdAndItemIdAndEndDateLessThanEqualAndStatusIs(userId, itemId, LocalDateTime.now(), BookingStatus.APPROVED)
                .orElseThrow(() -> new RuntimeException("Item is not eligible for comment"));

        Comment comment = new Comment();
        comment.setItem(booking.getItem());
        comment.setAuthor(booking.getBooker());
        comment.setText(commentDto.getText());
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }
}