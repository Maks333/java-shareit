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
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto addRequest(long userId, ItemRequestCreateDto request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(""));
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(request);
        itemRequest.setUser(user);
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.save(itemRequest));
    }

    @Override
    public List<ItemRequestDto> getRequestsOfUser(long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user with id " + userId + " is not found"));
        Sort sort = Sort.by("created").ascending();
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByUserId(userId, sort);
        //return getListOfItemRequestDtoWithResponses(itemRequests);
        return  itemRequests.stream()
                .map(this::formRequestDtoWithResponses)
                .toList();
    }

    @Override
    public List<ItemRequestDto> getAllRequests(long userId) {
        return List.of();
    }

    @Override
    public ItemRequestDto getRequestById(long userId, long requestId) {
        return null;
    }

//    private List<ItemRequestDto> getListOfItemRequestDtoWithResponses(List<ItemRequest> requests) {
//        List<ItemRequestDto> dtos = new ArrayList<>();
//        for(ItemRequest request: requests) {
//            List<Item> items = itemRepository.findAllByRequestId(request.getId());
//            dtos.add(ItemRequestMapper.toItemRequestDto(request, items));
//        }
//        return dtos;
//    }

    private ItemRequestDto formRequestDtoWithResponses(ItemRequest request) {
        List<Item> items = itemRepository.findAllByRequestId(request.getId());
        return ItemRequestMapper.toItemRequestDto(request, items);
    }
}
