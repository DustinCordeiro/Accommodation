package com.br.accommodation.service.booking;

import com.br.accommodation.dto.booking.CreateBookingDto;
import com.br.accommodation.dto.guest.CreateGuestDto;
import com.br.accommodation.model.booking.Booking;
import com.br.accommodation.model.booking.BookingMapper;
import com.br.accommodation.model.guest.Guest;
import com.br.accommodation.model.guest.GuestMapper;
import com.br.accommodation.repository.booking.BookingRepository;
import com.br.accommodation.repository.guest.GuestRepository;
import com.br.accommodation.service.guest.GuestService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BookingServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private Page<Booking> bookingPage;

    @Mock
    private Pageable pageableMock;

    @InjectMocks
    private BookingService bookingService;

    @InjectMocks
    private GuestService guestService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCreate() {
        CreateGuestDto dto = new CreateGuestDto("Dustin","1234567899","8130124545");
        Guest guest = GuestMapper.MAPPER.mapToGuest(dto);
        CreateBookingDto bookingDto = new CreateBookingDto(LocalDateTime.now(),LocalDateTime.now(),false);

        Mockito.when(guestRepository.findById(1L)).thenReturn(Optional.ofNullable(guest));

        Booking booking = BookingMapper.MAPPER.mapToBooking(bookingDto);
        booking.setGuest(guest);

        Mockito.when(bookingRepository.save(Mockito.any(Booking.class))).thenReturn(booking);

        Booking savedBooking = bookingService.create(bookingDto,1L);

        Assertions.assertThat(savedBooking.getCheckInDate()).isNotNull();
        Assertions.assertThat(savedBooking.getCheckOutDate()).isNotNull();
        Assertions.assertThat(savedBooking.getGuest()).isNotNull();

    }

    @Test
    void testGetAll() {
        Mockito.when(bookingRepository.findAll(pageableMock)).thenReturn(bookingPage);

        Page<Booking> response = bookingService.getAll(pageableMock);

        Assertions.assertThat(response).isNotNull();
    }

}