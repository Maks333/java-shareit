package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
public class BookingControllerTest {
    @Autowired
    ObjectMapper mapper;

    @MockBean
    BookingService bookingService;

    @Autowired
    private MockMvc mvc;

    private BookingDto bookingDto = new BookingDto(
            1L,
            LocalDateTime.now().withNano(0).toString(),
            LocalDateTime.now().withNano(0).toString(),
            BookingStatus.WAITING,
            null,
            null
    );


    @Test
    public void createBooking() throws Exception {
        when(bookingService.createBooking(anyLong(),any()))
                .thenReturn(bookingDto);

        mvc.perform(MockMvcRequestBuilders.post("/bookings")
                        .content(mapper.writeValueAsString(bookingDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.start", is(bookingDto.getStart())))
                .andExpect(jsonPath("$.end", is(bookingDto.getEnd())))
                .andExpect(jsonPath("$.status", is(bookingDto.getStatus().name())));
    }

    @Test
    public void changeBookingStatus() throws Exception {
        when(bookingService.changeBookingStatus(anyLong(), anyLong(), anyBoolean()))
                .thenReturn(bookingDto);

        mvc.perform(MockMvcRequestBuilders.patch("/bookings/{bookingId}", 1)
                        .content(mapper.writeValueAsString(bookingDto))
                        .param("approved","true")
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.start", is(bookingDto.getStart())))
                .andExpect(jsonPath("$.end", is(bookingDto.getEnd())))
                .andExpect(jsonPath("$.status", is(bookingDto.getStatus().name())));
    }


    @Test
    public void getBooking() throws Exception {
        when(bookingService.getBooking(anyLong(), anyLong()))
                .thenReturn(bookingDto);

        mvc.perform(MockMvcRequestBuilders.get("/bookings/{bookingId}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.start", is(bookingDto.getStart())))
                .andExpect(jsonPath("$.end", is(bookingDto.getEnd())))
                .andExpect(jsonPath("$.status", is(bookingDto.getStatus().name())));
    }

    @Test
    public void getAllBookingsMadeByUser() throws Exception {
        when(bookingService.getAllBookingsMadeByUser(anyLong(), any()))
                .thenReturn(List.of(bookingDto));

        mvc.perform(MockMvcRequestBuilders.get("/bookings")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .param("state", BookingState.ALL.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(bookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.[0].start", is(bookingDto.getStart())))
                .andExpect(jsonPath("$.[0].end", is(bookingDto.getEnd())))
                .andExpect(jsonPath("$.[0].status", is(bookingDto.getStatus().name())));
    }

    @Test
    public void getAllBookingsForAllItemsOfUser() throws Exception {
        when(bookingService.getAllBookingsForAllItemsOfUser(anyLong(), any()))
                .thenReturn(List.of(bookingDto));

        mvc.perform(MockMvcRequestBuilders.get("/bookings/owner")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .param("state", BookingState.ALL.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(bookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.[0].start", is(bookingDto.getStart())))
                .andExpect(jsonPath("$.[0].end", is(bookingDto.getEnd())))
                .andExpect(jsonPath("$.[0].status", is(bookingDto.getStatus().name())));
    }
}
