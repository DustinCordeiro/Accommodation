package com.br.accommodation.repository.booking;

import com.br.accommodation.model.booking.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findAll(Pageable pageable);

    Page<Booking> findByHasCheckinAndHasCheckout(boolean hasCheckin,boolean hasCheckout, Pageable pageable);

    Page<Booking> findByHasCheckin(boolean hasCheckin, Pageable pageable);


}
