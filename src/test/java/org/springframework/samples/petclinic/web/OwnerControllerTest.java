package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {

    MockMvc mockMvc;

    @Autowired
    OwnerController ownerController;

    @Autowired
    ClinicService clinicService;


    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    Owner testOwner;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();

        testOwner = new Owner();
        testOwner.setLastName("FindJustOne");
        testOwner.setId(60001);
    }

    @AfterEach
    void tearDown() {
        reset(clinicService);
    }

    @Test
    void testNewOwnerPostValid() throws Exception {
        mockMvc.perform(post("/owners/new")
            .param("firstName","Jimmy")
            .param("lastName","Buffet")
            .param("Address","Rabocha st 3")
            .param("city","Dnipro")
            .param("telephone","3151231234"))
        .andExpect(status().is3xxRedirection());
    }

    @Test
    void testNewOwnerPostNotValid() throws Exception {
        mockMvc.perform(post("/owners/new")
                .param("firstName","Jimmy")
                .param("lastName","Buffet")
                .param("city","Dnipro"))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("owner"))
            .andExpect(model().attributeHasFieldErrors("owner","address"))
            .andExpect(model().attributeHasFieldErrors("owner","telephone"))
            .andExpect(view().name(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void testUpdateOwnerValid() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit",1)
                .param("firstName","Jimmy")
                .param("lastName","Buffet")
                .param("Address","Rabocha st 3")
                .param("city","Dnipro")
                .param("telephone","3151231234"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/{ownerId}"));
    }
    @Test
    void testUpdateOwnerNotValid() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit",1)
                .param("firstName","Jimmy")
                .param("lastName","Buffet")
                .param("city","Dnipro"))
            .andExpect(status().isOk())
            .andExpect(view().name(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM))
        .andExpect(model().attributeHasErrors("owner"))
        .andExpect(model().attributeHasFieldErrors("owner","address"))
        .andExpect(model().attributeHasFieldErrors("owner","telephone"));
    }

    @Test
    void testFindByNameNotFound() throws Exception {
        mockMvc.perform(get("/owners")
                .param("lastName", "Dont find me"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void testFindByNullLastName() throws Exception {
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void testReturnOwnerList() throws Exception {
        given(clinicService.findOwnerByLastName("")).willReturn(Lists.newArrayList(new Owner(), new Owner()));

        mockMvc.perform(get("/owners")
                /*.param("lastName","Dave")*/)
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().size(2));

        then(clinicService).should().findOwnerByLastName(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("");
    }

    @Test
    void testFindOwnerOneResult() throws Exception {
        String findJustOne = "FindJustOne";
        given(clinicService.findOwnerByLastName(findJustOne)).willReturn(Lists.newArrayList(testOwner));

        mockMvc.perform(get("/owners")
                .param("lastName", findJustOne))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/60001"));

        then(clinicService).should().findOwnerByLastName(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase(findJustOne);
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }
}