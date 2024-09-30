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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        return formRequestDtoWithResponses(itemRequests);
    }

    @Override
    public List<ItemRequestDto> getAllRequests(long userId) {
        checkUserExistence(userId);
        Sort sort = Sort.by("created").descending();

        return formRequestDtoWithResponses(itemRequestRepository.findAll(sort));
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

    private List<ItemRequestDto> formRequestDtoWithResponses(List<ItemRequest> requests) {
        Map<Long, List<Item>> requestIdToItem = mapItemsForEveryRequest(requests);

        return requests.stream()
                .map(request -> ItemRequestMapper.toItemRequestDto(request, requestIdToItem.get(request.getId())))
                .toList();
    }

    private Map<Long, List<Item>> mapItemsForEveryRequest(List<ItemRequest> requests) {
        //map requests to requests id
        List<Long> requestsId = requests.stream().map(ItemRequest::getId).toList();
        //get all items by requests id
        List<Item> requestItems = itemRepository.findAllByRequestIdIn(requestsId);

        //create map and map new empty list to each request id
        Map<Long, List<Item>> requestIdToItem = new HashMap<>();
        requestsId.forEach(id -> requestIdToItem.put(id, new ArrayList<>()));

        //populate map with items
        requestItems.forEach(item -> {
            List<Item> items = requestIdToItem.get(item.getRequest().getId());
            items.add(item);
        });

        return requestIdToItem;
    }
}
