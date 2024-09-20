package ru.practicum.shareit.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.dto.ItemBookingDateProjection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookerId(long userId, Sort sort);

    List<Booking> findAllByBookerIdAndEndDateBefore(long userId, LocalDateTime now, Sort sort);

    @Query(value = """
            select b from Booking as b
            join b.booker as u
            where u.id = ?1 and ?2 between b.startDate and b.endDate
            """)
    List<Booking> findAllCurrentBookingsOfUser(long userId, LocalDateTime now, Sort sort);

    List<Booking> findAllByBookerIdAndStartDateAfter(long userId, LocalDateTime now, Sort sort);

    List<Booking> findAllByBookerIdAndStatusIs(long userId, BookingStatus bookingStatus, Sort sort);


    List<Booking> findAllByItemOwnerId(long userId, Sort sort);

    @Query(value = """
            select b from Booking as b
            join b.item as i
            where i.id = ?1
            order by b.startDate asc
            """)
    Page<ItemBookingDateProjection> findItemBookingDates(long itemId, Pageable page);

    List<Booking> findAllByItemOwnerIdAndEndDateBefore(long userId, LocalDateTime now, Sort sort);

    List<Booking> findAllByItemOwnerIdAndStatusIs(long userId, BookingStatus bookingStatus, Sort sort);

    List<Booking> findAllByItemOwnerIdAndStartDateAfter(long userId, LocalDateTime now, Sort sort);

    @Query(value = """
            select b from Booking as b
            join b.item as i
            join i.owner as o
            where o.id = ?1 and ?2 between b.startDate and b.endDate
            """)
    List<Booking> findAllCurrentBookingsOfSharer(long userId, LocalDateTime now, Sort sort);

    Optional<Booking> findByBookerIdAndItemIdAndEndDateBeforeAndStatusIs(long userId, long itemId, LocalDateTime now, BookingStatus status);

    @Query(value = """
            select b from Booking as b
            join b.item as i
            where i.id = ?1 and ?2 between b.startDate and b.endDate
            order by b.startDate desc
            """)
    List<ItemBookingDateProjection> findAllCurrentBookingsOfItem(long itemId, LocalDateTime now);

    @Query(value = """
            select b from Booking as b
            join b.item as i
            where i.id = ?1 and b.startDate > ?2
            order by b.startDate asc
            """)
    List<ItemBookingDateProjection> findAllFutureBookingsOfItem(long itemId, LocalDateTime now);
}
