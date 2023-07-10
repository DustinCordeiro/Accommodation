package com.br.accommodation.service.guest;

import com.br.accommodation.dto.guest.CreateGuestDto;
import com.br.accommodation.model.guest.Guest;
import com.br.accommodation.model.guest.GuestMapper;
import com.br.accommodation.repository.guest.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;


    public Page<Guest> getAll(Pageable pageable) {

        return guestRepository.findAll(pageable);
    }

    public Page<Guest> getByNameAndDocumentAndPhone(String name, String document, String phone,Pageable pageable) {
        return guestRepository.findByNameContainingAndDocumentContainingAndPhoneContaining(name,document,phone,pageable);
    }

    public Guest create(CreateGuestDto guestDto) {
        Guest guest = GuestMapper.MAPPER.mapToGuest(guestDto);
        return guestRepository.save(guest);
    }

}
