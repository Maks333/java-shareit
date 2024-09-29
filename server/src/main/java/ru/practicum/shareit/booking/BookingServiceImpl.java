package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public BookingDto createBooking(long userId, BookingCreateDto bookingCreateDto) {
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
        Booking booking = getBookingById(bookingId);

        if (booking.getItem().getOwner().getId() != userId) {
            throw new RuntimeException("User with id " + userId + " is not owner of the item");
        }
        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    private Booking getBookingById(long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() ->
                new NotFoundException("Booking with " + bookingId + " is not found"));
    }

    @Override
    public BookingDto getBooking(long bookingId, long userId) {
        Booking booking = getBookingById(bookingId);

        if (booking.getBooker().getId() != userId && booking.getItem().getOwner().getId() != userId) {
            throw new RuntimeException("User with id " + userId + " is not owner/booker of the item");
        }

        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public List<BookingDto> getAllBookingsMadeByUser(long userId, BookingState state) {
        userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id " + userId + " is not found"));

        Sort sort = Sort.by("startDate").ascending();
        List<Booking> bookings = switch (state) {
            case PAST -> bookingRepository.findAllByBookerIdAndEndDateBefore(userId, LocalDateTime.now(), sort);
            case CURRENT -> bookingRepository.findAllCurrentBookingsOfUser(userId, LocalDateTime.now(), sort);
            case FUTURE -> bookingRepository.findAllByBookerIdAndStartDateAfter(userId, LocalDateTime.now(), sort);
            case WAITING -> bookingRepository.findAllByBookerIdAndStatusIs(userId, BookingStatus.WAITING, sort);
            case REJECTED -> bookingRepository.findAllByBookerIdAndStatusIs(userId, BookingStatus.REJECTED, sort);
            default -> bookingRepository.findAllByBookerId(userId, sort);
        };
        return bookings.stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getAllBookingsForAllItemsOfUser(long userId, BookingState state) {
        userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id " + userId + " is not found"));

        Sort sort = Sort.by("startDate").ascending();
        List<Booking> bookings = switch (state) {
            case PAST -> bookingRepository.findAllByItemOwnerIdAndEndDateBefore(userId, LocalDateTime.now(), sort);
            case CURRENT -> bookingRepository.findAllCurrentBookingsOfSharer(userId, LocalDateTime.now(), sort);
            case FUTURE -> bookingRepository.findAllByItemOwnerIdAndStartDateAfter(userId, LocalDateTime.now(), sort);
            case WAITING -> bookingRepository.findAllByItemOwnerIdAndStatusIs(userId, BookingStatus.WAITING, sort);
            case REJECTED -> bookingRepository.findAllByItemOwnerIdAndStatusIs(userId, BookingStatus.REJECTED, sort);
            default -> bookingRepository.findAllByItemOwnerId(userId, sort);
        };
        return bookings.stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }
}
