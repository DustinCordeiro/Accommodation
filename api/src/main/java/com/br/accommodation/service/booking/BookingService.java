package com.br.accommodation.service.booking;

import com.br.accommodation.dto.booking.CheckoutDetailsDto;
import com.br.accommodation.dto.booking.CheckoutResponseDto;
import com.br.accommodation.dto.booking.CreateBookingDto;
import com.br.accommodation.model.booking.AmountDetails;
import com.br.accommodation.model.booking.Booking;
import com.br.accommodation.model.booking.BookingMapper;
import com.br.accommodation.model.guest.Guest;
import com.br.accommodation.repository.booking.BookingRepository;
import com.br.accommodation.repository.guest.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    public static final BigDecimal DAILY_VALUE = BigDecimal.valueOf(120);
    public static final BigDecimal WEEKEND_DAILY_VALUE = BigDecimal.valueOf(180);
    public static final BigDecimal VEHICLE_VALUE = BigDecimal.valueOf(15);
    public static final BigDecimal WEEKEND_VEHICLE_VALUE = BigDecimal.valueOf(20);


    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GuestRepository guestRepository;


    public Page<Booking> getAll(Pageable pageable) {

        return bookingRepository.findAll(pageable);
    }

    public Page<Booking> getAllBookingWithoutCheckout(boolean hasCheckin,boolean hasCheckout, Pageable pageable) {

        return bookingRepository.findByHasCheckinAndHasCheckout(hasCheckin,hasCheckout,pageable);
    }

    public Page<Booking> getAllBookingWithoutCheckin(boolean hasCheckin, Pageable pageable) {

        return bookingRepository.findByHasCheckin(hasCheckin,pageable);
    }

    public Booking create(CreateBookingDto bookingDto, Long guestId) {

        Optional<Guest> savedGuest = guestRepository.findById(guestId);

        if(!savedGuest.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hóspede não encontrado");
        }

        Booking booking = BookingMapper.MAPPER.mapToBooking(bookingDto);

        booking.setInitialValue(calculateBookingAmount(booking));
        booking.setGuest(savedGuest.get());
        return bookingRepository.save(booking);
    }

    public Booking checkIn(Long bookingId) {

        Optional<Booking> savedBooking = bookingRepository.findById(bookingId);

        if(!savedBooking.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada");
        }

        Booking booking = savedBooking.get();

        LocalDateTime currentDatetime = LocalDateTime.now();
        LocalTime validateCheckinTime = LocalTime.parse( "14:00:00");

        if(!currentDatetime.toLocalDate().isEqual(booking.getCheckInDate().toLocalDate()) ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data para check-in inválida");
        }

        if(currentDatetime.toLocalTime().isBefore(validateCheckinTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Check-in só pode ser feito a partir das 14h");
        }

        booking.setHasCheckin(true);
        return bookingRepository.save(booking);
    }
    public CheckoutResponseDto checkOut(Long bookingId) {

        Optional<Booking> savedBooking = bookingRepository.findById(bookingId);

        if(!savedBooking.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada");
        }

        Booking booking = savedBooking.get();

        if(!booking.isHasCheckin()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível realizar check-out antes do check-in");
        }

        LocalTime currentTime = LocalTime.now();
        LocalTime validateCheckoutTime = LocalTime.parse( "12:00:00");
        BigDecimal initialValue = booking.getInitialValue();

        List<CheckoutDetailsDto> details = detailsBookingAmount(booking);

        if(currentTime.isAfter(validateCheckoutTime)) {
            Double tax = 0.5 * initialValue.doubleValue();
            booking.setFinalValue(initialValue.add(BigDecimal.valueOf(tax)));
            details.add(new CheckoutDetailsDto(LocalDate.now(),AmountDetails.CHECKOUT_AFTER_12H.name(),booking.getFinalValue() ));
        }

        CheckoutResponseDto response = new CheckoutResponseDto(details,booking.getFinalValue());


        booking.setHasCheckout(true);
        bookingRepository.save(booking);
        return response;
    }

    public static boolean isSaturdayOrSunday(LocalDate date) {

        DayOfWeek d = date.getDayOfWeek();
        return d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY;
    }

    public static List<LocalDate> getDatesBetweenInterval(
            LocalDate startDate, LocalDate endDate) {

        return startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toList());
    }

    private static BigDecimal calculateBookingAmount(Booking booking) {

        BigDecimal totalAmount = BigDecimal.ZERO;

        List<LocalDate> bookingDays = getDatesBetweenInterval(
                booking.getCheckInDate().toLocalDate(),
                booking.getCheckOutDate().toLocalDate()
        );

        for (LocalDate daily : bookingDays) {
            if(isSaturdayOrSunday(daily)) {
                if(booking.isHasVehicle()) {
                    totalAmount = totalAmount.add(WEEKEND_VEHICLE_VALUE);
                }
                totalAmount =  totalAmount.add(WEEKEND_DAILY_VALUE);
            }else{
                if(booking.isHasVehicle()) {
                    totalAmount = totalAmount.add(VEHICLE_VALUE);
                }
                totalAmount = totalAmount.add(DAILY_VALUE);
            }
        }

        return totalAmount;
    }

    private static List<CheckoutDetailsDto> detailsBookingAmount(Booking booking) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        List<CheckoutDetailsDto> details = new ArrayList<>();

        List<LocalDate> bookingDays = getDatesBetweenInterval(
                booking.getCheckInDate().toLocalDate(),
                booking.getCheckOutDate().toLocalDate()
        );

        for (LocalDate daily : bookingDays) {
            if(isSaturdayOrSunday(daily)) {
                if(booking.isHasVehicle()) {
                    totalAmount = totalAmount.add(WEEKEND_VEHICLE_VALUE);
                    details.add(new CheckoutDetailsDto(daily , AmountDetails.WEEKEND_VEHICLE_VALUE.name(),WEEKEND_VEHICLE_VALUE));
                }
                totalAmount =  totalAmount.add(WEEKEND_DAILY_VALUE);
                details.add(new CheckoutDetailsDto(daily , AmountDetails.WEEKEND_DAILY_VALUE.name(),WEEKEND_DAILY_VALUE));

            }else{
                if(booking.isHasVehicle()) {
                    totalAmount = totalAmount.add(VEHICLE_VALUE);
                    details.add(new CheckoutDetailsDto(daily , AmountDetails.VEHICLE_VALUE.name(),VEHICLE_VALUE));
                }
                totalAmount = totalAmount.add(DAILY_VALUE);
                details.add(new CheckoutDetailsDto(daily , AmountDetails.DAILY_VALUE.name(),DAILY_VALUE));
            }
        }
        return details;
    }
}
