package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto addRequest(long userId, ItemRequestCreateDto request) {
        User user = getUser(userId);
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(request);
        itemRequest.setUser(user);
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.save(itemRequest));
    }

    private User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " is not found"));
    }

    @Override
    public List<ItemRequestDto> getRequestsOfUser(long userId) {
        checkUserExistence(userId);
        Sort sort = Sort.by("created").descending();
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByUserId(userId, sort);

        return  itemRequests.stream()
                .map(this::formRequestDtoWithResponses)
                .toList();
    }

    @Override
    public List<ItemRequestDto> getAllRequests(long userId) {
        checkUserExistence(userId);
        Sort sort = Sort.by("created").descending();
        return itemRequestRepository.findAll(sort)
                .stream()
                .map(this::formRequestDtoWithResponses)
                .toList();
    }

    @Override
    public ItemRequestDto getRequestById(long userId, long requestId) {
        checkUserExistence(userId);
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Item request with id " + requestId + " is not found"));
        return formRequestDtoWithResponses(itemRequest);
    }

    private void checkUserExistence(long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user with id " + userId + " is not found"));
    }

    private ItemRequestDto formRequestDtoWithResponses(ItemRequest request) {
        List<Item> items = itemRepository.findAllByRequestId(request.getId());
        return ItemRequestMapper.toItemRequestDto(request, items);
    }
}
