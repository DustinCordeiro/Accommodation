package com.br.accommodation.model.guest;

import com.br.accommodation.dto.guest.CreateGuestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GuestMapper {

    GuestMapper MAPPER = Mappers.getMapper(GuestMapper.class);

    CreateGuestDto mapToGuestDto(Guest guest);

    Guest mapToGuest(CreateGuestDto createGuestDto);
}
