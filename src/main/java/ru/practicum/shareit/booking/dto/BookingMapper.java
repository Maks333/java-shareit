package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.user.dto.UserMapper;

import java.time.format.DateTimeFormatter;

public class BookingMapper {

    public static Booking toBooking(BookingCreateDto bookingDto) {
        Booking booking = new Booking();
        booking.setStartDate(bookingDto.getStart());
        booking.setEndDate(bookingDto.getEnd());
        return  booking;
    }

    public static BookingDto toBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss");
        bookingDto.setStart(formatter.format(booking.getStartDate()));
        bookingDto.setEnd(formatter.format(booking.getEndDate()));
        bookingDto.setStatus(booking.getStatus());

        bookingDto.setItem(ItemMapper.toBookedItemDto(booking.getItem()));
        bookingDto.setBooker(UserMapper.toBookerDto(booking.getBooker()));
        return bookingDto;
    }
}
