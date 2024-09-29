package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.dto.UserDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImplTest {
    private final UserService service;

    @Test
    public void userFindByIdTest() {
        UserDto userDto = new UserDto();
        userDto.setEmail("Email11");
        userDto.setName("Name1");
        long userId = service.create(userDto).getId();

        UserDto userDto1 = service.findById(userId);

        assertThat(userDto1.getId(), notNullValue());
        assertThat(userDto1.getName(), is(equalTo(userDto.getName())));
        assertThat(userDto1.getEmail(), is(equalTo(userDto.getEmail())));
    }
}
