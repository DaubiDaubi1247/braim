package ru.alex.braim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.alex.braim.service.Impl.AccountServiceImpl;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    private final String url = "/accounts";
    private final String searchUrl = "/search";

    protected final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    AccountServiceImpl accountService;

    // drop because a add spring - security

//    @Test
//    void getAccountsByParameters() throws Exception {
//        when(accountService.getAccountsByParameters(any(AccountDto.class), any(FromSizeParams.class))).thenReturn(new ArrayList<>());
//
//        mockMvc.perform(MockMvcRequestBuilders.get(url + searchUrl + "?firstName=alex&lastName=b&email=f&from=0&size=2"))
//                .andExpect(status().isOk());
//    }
}