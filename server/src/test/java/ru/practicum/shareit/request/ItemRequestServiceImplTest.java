package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestServiceImplTest {
    private final ItemRequestService itemRequestService;
    private final UserService userService;


    @Test
    public void findRequestByIdTest() {
        UserDto userToCreate = new UserDto();
        userToCreate.setName("name");
        userToCreate.setEmail("email");
        long userId = userService.create(userToCreate).getId();

        ItemRequestCreateDto requestCreateDto = new ItemRequestCreateDto();
        requestCreateDto.setDescription("description");
        long itemRequestId = itemRequestService.addRequest(userId, requestCreateDto).getId();

        ItemRequestDto itemRequestDto = itemRequestService.getRequestById(userId, itemRequestId);
        assertThat(itemRequestDto.getId(), notNullValue());
        assertThat(itemRequestDto.getDescription(), is(equalTo(requestCreateDto.getDescription())));
        assertThat(itemRequestDto.getItems(), equalTo(Collections.emptyList()));
        assertThat(itemRequestDto.getCreated(), notNullValue());
    }
}