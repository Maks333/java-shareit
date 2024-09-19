package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService service;

    @PostMapping
    public Booking createBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                 Booking booking) {
        return service.createBooking(userId, booking);
    }

    @PatchMapping("/{bookingId}")
    public Booking changeBookingStatus(@RequestHeader("X-Sharer-User-Id") long userId,
                                       @PathVariable long bookingId,
                                       @RequestParam("approved") boolean approved) {
        return service.changeBookingStatus(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public Booking getBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                              @PathVariable long bookingId) {
        return service.getBooking(userId, bookingId);
    }

    @GetMapping
    public List<Booking> getAllBookingsMadeByUser
            (@RequestHeader("X-Sharer-User-Id") long userId,
             @RequestParam(name = "state", required = false, defaultValue = "ALL") BookingState state) {
        return service.getAllBookingsMadeByUser(userId, state);
    }

    @GetMapping("/owner")
    public List<Booking> getAllBookingsForAllItemsOfUser
            (@RequestHeader("X-Sharer-User-Id") long userId,
             @RequestParam(name = "state", required = false, defaultValue = "ALL") BookingState state) {
        return service.getAllBookingsForAllItemsOfUser(userId, state);
    }
}
