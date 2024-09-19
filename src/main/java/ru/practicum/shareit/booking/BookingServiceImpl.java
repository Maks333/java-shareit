package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public BookingDto createBooking(long userId, @Valid BookingCreateDto bookingCreateDto) {
        if (bookingCreateDto.getStart().equals(bookingCreateDto.getEnd())) {
            throw new RuntimeException("Start and end dates should not be equal");
        }

        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User with " + userId + " is not found"));

        Item item = itemRepository.findById(bookingCreateDto.getItemId()).orElseThrow(() ->
                new NotFoundException("Item with " + bookingCreateDto.getItemId() + " is not found"));

        if (!item.getAvailable()) throw new RuntimeException("Item is not available");

        Booking booking = BookingMapper.toBooking(bookingCreateDto);
        booking.setItem(item);
        booking.setBooker(user);
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public BookingDto changeBookingStatus(long userId, long bookingId, boolean approved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new NotFoundException("Booking with " + bookingId + " is not found"));

        if (booking.getItem().getOwner().getId() != userId) {
            throw new RuntimeException("User with id " + userId + " is not owner of the item");
        }
        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto getBooking(long bookingId, long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new NotFoundException("Booking with " + bookingId + " is not found"));

        if (booking.getBooker().getId() != userId && booking.getItem().getOwner().getId() != userId) {
            throw new RuntimeException("User with id " + userId + " is not owner/booker of the item");
        }

        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public List<BookingDto> getAllBookingsMadeByUser(long userId, BookingState state) {
        switch (state) {
            default -> {
                return bookingRepository.findAllByBookerId(userId).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            }
        }
    }

    @Override
    public List<BookingDto> getAllBookingsForAllItemsOfUser(long userId, BookingState state) {
        userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id " + userId + " is not found"));
        return switch (state) {
            default -> bookingRepository.findAllByItemOwnerId(userId).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());

        };
    }
}
