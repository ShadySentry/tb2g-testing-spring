package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

    @Mock(lenient = true)
    ClinicService clinicService;

    @InjectMocks
    VetController vetController;
    private Map<String, Object> model;

    private Vet vetUnderTest;

    private List<Vet> collectionOfVets = new ArrayList<>();


    //    @Mock
    private Vets vets;

    @BeforeEach
    void setUp() {
        model = new HashMap<>();

        vetUnderTest = new Vet();
        vetUnderTest.setFirstName("John");
        vetUnderTest.setLastName("Boon");
        collectionOfVets.add(vetUnderTest);

        when(clinicService.findVets()).thenReturn(collectionOfVets);
    }

    @Test
    void showVetList() {

        String response = vetController.showVetList(model);
        assertThat(response).isEqualToIgnoringCase("vets/vetList");

        assertThat(model).hasSize(1);

        List<Vet> retrievedVetsList = new ArrayList<>();
        if (model.get("vets") instanceof Vets) {
            Vets retrievedVets = (Vets) model.get("vets");
            retrievedVetsList = retrievedVets.getVetList();
        }
        assertThat(retrievedVetsList).contains(vetUnderTest);
    }

    @Test
    void showResourcesVetList() {
        vets=null;
        vets= vetController.showResourcesVetList();
        List<Vet> vetsList=vets.getVetList();
        assertThat(vetsList).contains(vetUnderTest);
    }
}