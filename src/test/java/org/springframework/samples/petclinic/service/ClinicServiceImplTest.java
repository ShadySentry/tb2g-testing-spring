package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {

    private List<PetType> petTypes;

    private PetType petUnderTest1;
    private PetType petUnderTest2;

    @InjectMocks
    ClinicServiceImpl clinicService;

    @Mock
    PetRepository petRepository;

    @BeforeEach
    void initialize(){
        petUnderTest1 = new PetType();
        petUnderTest1.setName("Dog");
        petUnderTest1.setId(1);

        petUnderTest2 = new PetType();
        petUnderTest2.setName("Cat");
        petUnderTest2.setId(2);

        petTypes = new ArrayList<>();
        petTypes.add(petUnderTest1);
        petTypes.add(petUnderTest2);
    }

    @Test
    void findPetTypes() {
        when(petRepository.findPetTypes()).thenReturn(petTypes);

        List<PetType> returnedPets;
        returnedPets = new ArrayList<>(clinicService.findPetTypes());
        assertThat(returnedPets).isNotNull();
        assertThat(returnedPets).size().isEqualTo(2);
        assertThat(returnedPets).contains(petUnderTest1, petUnderTest2);


    }
}