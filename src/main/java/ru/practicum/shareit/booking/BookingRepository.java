package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookerId(long userId);

    List<Booking> findAllByItemOwnerId(long userId);

    List<Booking> findAllByBookerIdAndEndDateBefore(long userId, LocalDateTime now);

    @Query(value = """
            select b from Booking as b
            join b.booker as u
            where u.id = ?1 and ?2 between b.startDate and b.endDate
            """)
    List<Booking> findAllCurrentBookingsOfUser(long userId, LocalDateTime now);

    List<Booking> findAllByBookerIdAndStartDateAfter(long userId, LocalDateTime now);

    List<Booking> findAllByBookerIdAndStatusIs(long userId, BookingStatus bookingStatus);
}
