package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {

    @Transactional
    BookingDto createBooking(long userId, @Valid BookingCreateDto booking);

    @Transactional
    BookingDto changeBookingStatus(long userId, long bookingId, boolean approved);

    BookingDto getBooking(long bookingId, long userId);

    List<BookingDto> getAllBookingsMadeByUser(long userId, BookingState state);

    List<BookingDto> getAllBookingsForAllItemsOfUser(long userId, BookingState state);
}
