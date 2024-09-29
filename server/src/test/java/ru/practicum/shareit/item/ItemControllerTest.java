package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithAdditionalInfo;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest {
    @Autowired
    ObjectMapper mapper;

    @MockBean
    ItemService itemService;

    @Autowired
    private MockMvc mvc;

    private ItemDto itemDto = new ItemDto (
           1L,
            "name",
            "description",
            true,
            1L
    );

    private ItemDtoWithAdditionalInfo itemDtoWithAdditionalInfo = new ItemDtoWithAdditionalInfo(
            1L,
            "name",
            "description",
            true,
            null,
            null,
            null
    );

    private CommentDto commentDto = new CommentDto(
            1L,
            "text",
            "author_name",
            LocalDateTime.now().atZone(ZoneId.of("UTC")).toLocalDateTime()
    );



    @Test
    void createTest() throws Exception {
        when(itemService.create(anyLong(),any()))
                .thenReturn(itemDto);

        mvc.perform(MockMvcRequestBuilders.post("/items")
                        .content(mapper.writeValueAsString(itemDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())));
    }

    @Test
    void updateTest() throws Exception {
        when(itemService.update(anyLong(), anyLong(), any()))
                .thenReturn(itemDto);

        mvc.perform(MockMvcRequestBuilders.patch("/items/{itemId}", 1)
                        .content(mapper.writeValueAsString(itemDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())));
    }

    @Test
    void get() throws Exception {
        when(itemService.findById(anyLong(), anyLong()))
                .thenReturn(itemDtoWithAdditionalInfo);

        mvc.perform(MockMvcRequestBuilders.get("/items/{itemId}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDtoWithAdditionalInfo.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDtoWithAdditionalInfo.getName())))
                .andExpect(jsonPath("$.description", is(itemDtoWithAdditionalInfo.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDtoWithAdditionalInfo.getAvailable())));
    }

    @Test
    void getAll() throws Exception {
        when(itemService.findAll(anyLong()))
                .thenReturn(List.of(itemDtoWithAdditionalInfo));

        mvc.perform(MockMvcRequestBuilders.get("/items")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(itemDtoWithAdditionalInfo.getId()), Long.class))
                .andExpect(jsonPath("$.[0].name", is(itemDtoWithAdditionalInfo.getName())))
                .andExpect(jsonPath("$.[0].description", is(itemDtoWithAdditionalInfo.getDescription())))
                .andExpect(jsonPath("$.[0].available", is(itemDtoWithAdditionalInfo.getAvailable())));
    }

    @Test
    void searchAll() throws Exception {
        when(itemService.searchAll(anyString()))
                .thenReturn(List.of(itemDto));

        mvc.perform(MockMvcRequestBuilders.get("/items/search")
                        .param("text", "text")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.[0].name", is(itemDto.getName())))
                .andExpect(jsonPath("$.[0].description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.[0].available", is(itemDto.getAvailable())));
    }

    @Test
    void createComment() throws Exception {
        when(itemService.createComment(anyLong(), anyLong(), any()))
                .thenReturn(commentDto);
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        commentCreateDto.setText(commentDto.getText());

        mvc.perform(MockMvcRequestBuilders.post("/items/{itemId}/comment", 1)
                        .content(mapper.writeValueAsString(commentCreateDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(commentDto.getId()), Long.class))
                .andExpect(jsonPath("$.text", is(commentDto.getText())))
                .andExpect(jsonPath("$.authorName", is(commentDto.getAuthorName())));
    }
}
