package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(long userId, Booking booking) {
        return null;
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
