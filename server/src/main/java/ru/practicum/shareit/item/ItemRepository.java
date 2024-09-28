package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByOwnerIdAndId(long userId, long itemId);

    List<Item> findAllByOwnerId(long userId);

    @Query(value = """
            select item from Item as item
            where (lower(item.name) like lower(%?1%) or
            lower(item.description) like lower(%?1%)) and
            item.available = true
            """)
    List<Item> findAllAvailableByText(String text);

    List<Item> findAllByRequestId(long requestId);
}
