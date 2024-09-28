package ru.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.shareit.user.User;

import java.time.Instant;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@Entity
@Table(name = "requests")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Instant created = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
