package com.br.accommodation.controller.guest;

import com.br.accommodation.dto.guest.CreateGuestDto;
import com.br.accommodation.model.guest.Guest;
import com.br.accommodation.service.guest.GuestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guest")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @GetMapping
    public ResponseEntity<Page<Guest>> getAll(Pageable pageable) {
        return ResponseEntity.ok(guestService.getAll(pageable));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Guest>> getByNameAndDocumentAndPhone(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String document,
            @RequestParam(defaultValue = "") String phone,
            Pageable pageable
    ) {
        return ResponseEntity.ok(guestService.getByNameAndDocumentAndPhone(
                name,
                document,
                phone,
                pageable
        ));
    }

    @PostMapping
    public ResponseEntity<Guest> create(@Valid @RequestBody  CreateGuestDto guestDto) {
        Guest newGuest = guestService.create(guestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGuest);
    }
}
