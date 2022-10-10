package com.wandoo.hotel.repository;

import com.wandoo.hotel.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select r from Reservation r where (cast(r.startDate as LocalDate) >= :fromDate and cast(r.startDate as LocalDate) <= :toDate)" +
            " or (cast(r.endDate as LocalDate) > :fromDate and cast(r.endDate as LocalDate) < :toDate)")
    List<Reservation> findAllWithinDateInterval(LocalDate fromDate, LocalDate toDate);
}
