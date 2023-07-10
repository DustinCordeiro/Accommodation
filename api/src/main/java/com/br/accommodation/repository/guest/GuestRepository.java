package com.br.accommodation.repository.guest;

import com.br.accommodation.model.guest.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    Page<Guest> findAll(Pageable pageable);

    Page<Guest> findByNameContainingAndDocumentContainingAndPhoneContaining(String name, String document, String phone,Pageable pageable);

}
