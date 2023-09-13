package sk.library.controller;

import java.io.File;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import sk.library.service.StorageService;

@SpringBootTest
@AutoConfigureMockMvc
class FileControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Value("${adresar}")
    private String directory;

    @MockBean
    StorageService storageService;

    @Test
    void testSaveData() throws Exception {

        File testAdresar = new File(directory);
        int pocetSuborovPred = testAdresar.listFiles().length;

        mockMvc.perform(post("/saveData")
                        .content("test content"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        int pocetSuborovPo = testAdresar.listFiles().length;

        assertThat(pocetSuborovPo).isEqualTo(pocetSuborovPred + 1);
    }

    @Test
    void testSaveDataMock() throws Exception {

        mockMvc.perform(post("/saveData")
                        .content("test content"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
