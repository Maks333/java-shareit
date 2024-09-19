package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.user.dto.UserMapper;

import java.time.format.DateTimeFormatter;

public class BookingMapper {

    public static Booking toBookingFromDto(BookingCreateDto bookingCreateDto) {
        Booking booking = new Booking();
        booking.setStartDate(bookingCreateDto.getStart());
        booking.setEndDate(bookingCreateDto.getEnd());
        return  booking;
    }

    public static BookingDto toBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss");
        bookingDto.setStart(formatter.format(booking.getStartDate()));
        bookingDto.setEnd(formatter.format(booking.getEndDate()));
        bookingDto.setStatus(booking.getStatus());

        bookingDto.setItem(ItemMapper.toBookedItemDto(booking.getItem()));
        bookingDto.setBooker(UserMapper.toBookerDto(booking.getBooker()));
        return bookingDto;
    }
}
