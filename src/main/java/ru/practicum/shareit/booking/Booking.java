package ru.practicum.shareit.booking;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.Instant;

/**
 * TODO Sprint add-bookings.
 */
@Entity
@Table(name = "bookings")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant start_date;
    private Instant end_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booker_id")
    private User booker;

    @Enumerated(value = EnumType.STRING)
    private BookingStatus status;
}
