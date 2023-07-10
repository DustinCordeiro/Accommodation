package com.br.accommodation.controller.booking;

import com.br.accommodation.dto.booking.CheckoutResponseDto;
import com.br.accommodation.dto.booking.CreateBookingDto;
import com.br.accommodation.model.booking.Booking;
import com.br.accommodation.service.booking.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseEntity<Page<Booking>> getAll(Pageable pageable) {
        return ResponseEntity.ok(bookingService.getAll(pageable));
    }

    @GetMapping("/without-checkout")
    public ResponseEntity<Page<Booking>> getAllWithoutCheckout(Pageable pageable) {
        return ResponseEntity.ok(bookingService.getAllBookingWithoutCheckout(true,false,pageable));
    }

    @GetMapping("/without-checkin")
    public ResponseEntity<Page<Booking>> getAllWithoutCheckIn(Pageable pageable) {
        return ResponseEntity.ok(bookingService.getAllBookingWithoutCheckin(false,pageable));
    }

    @PostMapping("/guest/{guestId}")
    public ResponseEntity<Booking> create(
            @Valid @RequestBody CreateBookingDto bookingDto,
            @PathVariable Long guestId
    ) {
        Booking newBooking = bookingService.create(bookingDto,guestId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
    }

    @PutMapping("/{bookingId}/checkin")
    public ResponseEntity<Booking> checkIn(@PathVariable Long bookingId) {
        Booking savedBooking = bookingService.checkIn(bookingId);
        return ResponseEntity.ok(savedBooking);
    }

    @PutMapping("/{bookingId}/checkout")
    public ResponseEntity<CheckoutResponseDto> checkOut(@PathVariable Long bookingId) {
        CheckoutResponseDto response = bookingService.checkOut(bookingId);
        return ResponseEntity.ok(response);
    }
}
