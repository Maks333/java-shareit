package ru.practicum.shareit.booking;

import java.util.List;

public interface BookingService {

    Booking createBooking(long userId, Booking booking);

    Booking changeBookingStatus(long userId, long bookingId, boolean approved);

    Booking getBooking(long bookingId, long id);

    List<Booking> getAllBookingsMadeByUser(long userId, BookingState state);

    List<Booking> getAllBookingsForAllItemsOfUser(long userId, BookingState state);
}
