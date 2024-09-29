package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.booking.dto.BookingCreateDto;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
public class BookingControllerTest {
    @Autowired
    ObjectMapper mapper;

    @MockBean
    BookingClient bookingClient;

    @Autowired
    private MockMvc mvc;

    private BookingCreateDto bookingDto = new BookingCreateDto(
            1L,
            LocalDateTime.now().withNano(0).minus(Duration.ofDays(1)),
            LocalDateTime.now().withNano(0)
    );


    @Test
    public void validationStartTest() throws Exception {
        when(bookingClient.createBooking(anyLong(), any()))
                .thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/bookings")
                        .content(mapper.writeValueAsString(bookingDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void validateEndTest() throws Exception {
        when(bookingClient.createBooking(anyLong(), any()))
                .thenReturn(null);

        bookingDto.setStart(LocalDateTime.now().plus(Duration.ofDays(1)));
        bookingDto.setEnd(LocalDateTime.now().minus(Duration.ofDays(1)));
        mvc.perform(MockMvcRequestBuilders.post("/bookings")
                        .content(mapper.writeValueAsString(bookingDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void validateIdTest() throws Exception {
        when(bookingClient.createBooking(anyLong(), any()))
                .thenReturn(null);

        bookingDto.setStart(LocalDateTime.now().plus(Duration.ofDays(1)));
        bookingDto.setEnd(LocalDateTime.now().plus(Duration.ofDays(1)));
        bookingDto.setItemId(null);
        mvc.perform(MockMvcRequestBuilders.post("/bookings")
                        .content(mapper.writeValueAsString(bookingDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}