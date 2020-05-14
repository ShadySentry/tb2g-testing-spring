package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VetControllerTest {

    private Map<String, Object> model;

    private Vet vetUnderTest;

    private final List<Vet> collectionOfVets = new ArrayList<>();
    @Mock
    ClinicService clinicService;

    @InjectMocks
    VetController vetController;


//    @Mock
    private Vets vets;

    @BeforeAll
    void setUp(){
        model = new HashMap<>();

        vetUnderTest = new Vet();
        vetUnderTest.setFirstName("John");
        vetUnderTest.setLastName("Boon");
        collectionOfVets.add(vetUnderTest);
    }

    @Test
    void showVetList() {
        when(clinicService.findVets()).thenReturn(collectionOfVets);

        String response = vetController.showVetList(model);
        assertThat(response).isEqualToIgnoringCase("vets/vetList");

        assertThat(model).hasSize(1);

        List<Vet> retrievedVetsList=new ArrayList<>();
        if(model.get("vets") instanceof Vets){
            Vets retrievedVets = (Vets)model.get("vets");
            retrievedVetsList = retrievedVets.getVetList();
        }
        assertThat(retrievedVetsList).containsExactly(vetUnderTest);
    }

    @Test
    void showResourcesVetList() {
    }
}