package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingServiceImplTest {
    private final BookingService bookingService;
    private final ItemService itemService;
    private final UserService userService;

    @Test
    public void getBookingByIdTest() {
        UserDto userToCreate = new UserDto();
        userToCreate.setName("name");
        userToCreate.setEmail("email");
        long userId = userService.create(userToCreate).getId();

        ItemDto itemToCreate = new ItemDto();
        itemToCreate.setAvailable(true);
        itemToCreate.setDescription("description");
        itemToCreate.setName("name");
        long itemId = itemService.create(userId, itemToCreate).getId();

        BookingCreateDto bookingCreateDto = new BookingCreateDto();
        bookingCreateDto.setStart(LocalDateTime.now().plus(Duration.ofMinutes(10)));
        bookingCreateDto.setEnd(LocalDateTime.now().plus(Duration.ofMinutes(15)));
        bookingCreateDto.setItemId(itemId);
        long bookingId = bookingService.createBooking(userId, bookingCreateDto).getId();


        BookingDto bookingDto = bookingService.getBooking(bookingId, userId);
        assertThat(bookingDto.getId(), notNullValue());
        assertThat(bookingDto.getItem(), allOf(
                hasProperty("name", equalTo(itemToCreate.getName())),
                hasProperty("id", notNullValue())
        ));
        assertThat(bookingDto.getStart(), is(equalTo(bookingCreateDto.getStart().withNano(0).toString())));
        assertThat(bookingDto.getEnd(), is(equalTo(bookingCreateDto.getEnd().withNano(0).toString())));
        assertThat(bookingDto.getStatus(), equalTo(BookingStatus.WAITING));
        assertThat(bookingDto.getBooker(), hasProperty("id", notNullValue()));
    }
}
