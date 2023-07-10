package com.br.accommodation.service.guest;

import com.br.accommodation.dto.guest.CreateGuestDto;
import com.br.accommodation.model.guest.Guest;
import com.br.accommodation.model.guest.GuestMapper;
import com.br.accommodation.repository.guest.GuestRepository;
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

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class GuestServiceTest {

    @Mock
    private GuestRepository repository;

    @Mock
    private Page<Guest> guestPage;

    @Mock
    private Pageable pageableMock;

    @InjectMocks
    private GuestService service;

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

        Mockito.when(repository.save(Mockito.any(Guest.class))).thenReturn(guest);

        Guest saved = service.create(dto);

        Assertions.assertThat(saved.getName()).isNotNull();
        Assertions.assertThat(saved.getDocument()).isNotNull();
        Assertions.assertThat(saved.getPhone()).isNotNull();

    }

    @Test
    void testGetByNameAndDocumentAndPhone() {
        Mockito.when(repository.findByNameContainingAndDocumentContainingAndPhoneContaining("", "","", pageableMock)).thenReturn(guestPage);

        Page<Guest> response = service.getByNameAndDocumentAndPhone("","","",pageableMock);

        Assertions.assertThat(response).isNotNull();
    }


    @Test
    void testGetAllGuest() {
        Mockito.when(repository.findAll(pageableMock)).thenReturn(guestPage);

        Page<Guest> response = service.getAll(pageableMock);

        Assertions.assertThat(response).isNotNull();
    }
}