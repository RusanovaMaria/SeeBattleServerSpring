package com.seebattleserver.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {
    private MockMvc mockMvc;
    private String nameJson;

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private UserController userController;

    @Before
    public void setUp() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userEntityRepository)).build();
    }

    @Test
    public void addNewUserEntity_whenParamsAreValid_returnStatusOkAndString200() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/users/new?name=Mary");
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userEntityRepository)).build();
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk()).andExpect(content().string(containsString("200"))).andReturn();
    }

    @Test
    public void addNewUserEntity_whenParamsDoNotExist_returnStatusBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/users/new");
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userEntityRepository)).build();
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void getAllUserEntity_whenRequestIsValid_returnStatusOk() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/users/all");
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userEntityRepository)).build();
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void changeUserEntityName_whenUserEntityWithOldNameDoNotExist_returnStatusOkAndString404() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = put("/users/newName?oldName=Mary&newName=Kate");
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userEntityRepository)).build();
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk()).andExpect(content().string(containsString("404"))).andReturn();
    }

    @Test
    public void deleteUserEntity_whenUserEntityWithThisNameDoNotExist_returnStatusOkAndString404() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = delete("/users/remove?name=Mary");
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userEntityRepository)).build();
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk()).andExpect(content().string(containsString("404"))).andReturn();
    }

    @Test
    public void getUserEntitiesByName_whenUserEntityWithThisNameDoNotExist_returnStatusOk() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/users/choiceByName?names=Mary,Ann");
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userEntityRepository)).build();
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk()).andReturn();
    }
}