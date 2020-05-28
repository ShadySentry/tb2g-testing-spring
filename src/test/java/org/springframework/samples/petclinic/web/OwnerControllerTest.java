package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml","classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {

    MockMvc mockMvc;

    @Autowired
    OwnerController ownerController;

    @Autowired
    ClinicService clinicService;

    @BeforeEach
    void setUp(){
        Object[] controllers;
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();

    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new")).
                andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }
}