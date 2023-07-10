package com.br.accommodation.model.booking;

import com.br.accommodation.dto.booking.CreateBookingDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingMapper {

    BookingMapper MAPPER = Mappers.getMapper(BookingMapper.class);

    CreateBookingDto mapToBookingDto(Booking booking);

    Booking mapToBooking(CreateBookingDto createBookingDto);
}
