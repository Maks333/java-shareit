package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;

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
    public BookingDto createBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                    @RequestBody BookingCreateDto booking) {
        return service.createBooking(userId, booking);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto changeBookingStatus(@RequestHeader("X-Sharer-User-Id") long userId,
                                          @PathVariable long bookingId,
                                          @RequestParam("approved") boolean approved) {
        return service.changeBookingStatus(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                 @PathVariable long bookingId) {
        return service.getBooking(bookingId, userId);
    }

    @GetMapping
    public List<BookingDto> getAllBookingsMadeByUser(@RequestHeader("X-Sharer-User-Id") long userId,
                                                     @RequestParam(name = "state", required = false, defaultValue = "ALL") String state) {
        return service.getAllBookingsMadeByUser(userId, BookingState.of(state));
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllBookingsForAllItemsOfUser(@RequestHeader("X-Sharer-User-Id") long userId,
                                                            @RequestParam(name = "state", required = false, defaultValue = "ALL") String state) {
        return service.getAllBookingsForAllItemsOfUser(userId, BookingState.of(state));
    }
}
