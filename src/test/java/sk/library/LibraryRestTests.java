package sk.library;

import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import sk.library.model.Library;
import sk.library.repository.LibraryRepository;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryRestTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LibraryRepository libraryRepository;

    @Test
    void getLibraries() throws Exception {

        mockMvc.perform(get("/libraries?page=1&size=2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getLibraryNotFound() throws Exception {

        MvcResult result = mockMvc.perform(get("/libraries/99999"))
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getStatus())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    // Test GET library by id
    @Test
    void getLibrarySuccess() throws Exception {

        MvcResult result = mockMvc.perform(get("/libraries/2"))
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentAsString()).contains("Kniznica Ruzinov");
    }

    @Test
    void patchLibrarySuccess() throws Exception {

        MvcResult result = mockMvc.perform(
                patch("/libraries/2")
                        .content("{\"name\":\"Zmenene meno\"}")
                        .accept("application/json"))
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getStatus())
                .isEqualTo(HttpStatus.OK.value());

        Optional<Library> library2 = libraryRepository.findById(2L);

        assertThat(library2).isPresent();
        assertThat(library2.get().getName())
                .isEqualTo("Zmenene meno");
   }

}
