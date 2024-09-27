package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;

    @Override
    public ItemRequestDto addRequest(long userId, ItemRequestCreateDto request) {
        return null;
    }

    @Override
    public List<ItemRequestDto> getRequestsOfUser(long userId) {
        return List.of();
    }

    @Override
    public List<ItemRequestDto> getAllRequests(long userId) {
        return List.of();
    }

    @Override
    public ItemRequestDto getRequestById(long userId, long requestId) {
        return null;
    }
}
