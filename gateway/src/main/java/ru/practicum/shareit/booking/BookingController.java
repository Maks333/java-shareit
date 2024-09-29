package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingState;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> createBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                                @RequestBody @Valid BookingCreateDto booking) {
        return bookingClient.createBooking(userId, booking);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> changeBookingStatus(@RequestHeader("X-Sharer-User-Id") long userId,
                                                      @PathVariable long bookingId,
                                                      @RequestParam("approved") boolean approved) {
        return bookingClient.changeBookingStatus(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable long bookingId) {
        return bookingClient.getBooking(bookingId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBookingsMadeByUser(@RequestHeader("X-Sharer-User-Id") long userId,
                                                           @RequestParam(name = "state", required = false, defaultValue = "ALL") String state) {
        return bookingClient.getAllBookingsMadeByUser(userId, BookingState.of(state));
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllBookingsForAllItemsOfUser(@RequestHeader("X-Sharer-User-Id") long userId,
                                                                  @RequestParam(name = "state", required = false, defaultValue = "ALL") String state) {
        return bookingClient.getAllBookingsForAllItemsOfUser(userId, BookingState.of(state));
    }
}
