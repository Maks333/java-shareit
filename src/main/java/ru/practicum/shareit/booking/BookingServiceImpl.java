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
                new NotFoundException("User with " + userId + " is not found")
        );

        Item item = itemRepository.findById(bookingCreateDto.getItemId()).orElseThrow(() ->
                new NotFoundException("Item with " + bookingCreateDto.getItemId() + " is not found")
        );

        if (!item.getAvailable()) throw new RuntimeException("Item is not available");

        Booking booking = BookingMapper.toBooking(bookingCreateDto);
        booking.setItem(item);
        booking.setBooker(user);
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public Booking changeBookingStatus(long userId, long bookingId, boolean approved) {
        return null;
    }

    @Override
    public Booking getBooking(long bookingId, long id) {
        return null;
    }

    @Override
    public List<Booking> getAllBookingsMadeByUser(long userId, BookingState state) {
        return List.of();
    }

    @Override
    public List<Booking> getAllBookingsForAllItemsOfUser(long userId, BookingState state) {
        return List.of();
    }
}
