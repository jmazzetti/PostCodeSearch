package com.postcodesearch;

import com.postcodesearch.config.AppBoot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppBoot.class})
public class PostCodeControllerTest {

    private final static String CONTENT_TYPE_TEXT_UTF8 = "text/plain;charset=UTF-8";
    private final static String CONTENT_TYPE_UTF8 = "application/json;charset=UTF-8";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void test_postCodeService() throws Exception {
        mvc.perform(get("/postcode/N19JA"))
                .andDo(print())
                .andExpect(status().isOk());
    }



}
