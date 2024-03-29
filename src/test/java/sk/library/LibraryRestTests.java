package sk.library;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import sk.library.model.Library;
import sk.library.repository.LibraryRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LibraryRestTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getLibraries() throws Exception {

        MvcResult result = mockMvc.perform(get("/libraries?page=1&size=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        // mvcresult map json from embedded list of libraries to list of library objects
        Collection<Library> libraries = objectMapper.readValue(
                response, new TypeReference<CollectionModel<Library>>() {
        }).getContent();

        assertThat(libraries.size()).isEqualTo(2);
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

        MvcResult result = mockMvc.perform(get("/libraries/7"))
                .andDo(print())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        System.out.println("------------------------------------");
        System.out.println(response);
        System.out.println("------------------------------------");

        Library library = objectMapper.readValue(response, Library.class);

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentAsString()).contains("Kniznica Stare Mesto");
        assertThat(library.getName()).isEqualTo("Kniznica Stare Mesto");
    }

    @Test
    void patchLibrarySuccess() throws Exception {

        MvcResult result = mockMvc.perform(
                patch("/libraries/3")
                        .content("{\"name\":\"Zmenene meno\"}")
                        .accept("application/json"))
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getStatus())
                .isEqualTo(HttpStatus.OK.value());

        Optional<Library> library2 = libraryRepository.findById(3L);

        assertThat(library2).isPresent();
        assertThat(library2.get().getName())
                .isEqualTo("Zmenene meno");
    }

    @Test
    void patchLibraryInvalidName() throws Exception {

        Library library = libraryRepository.findAll().iterator().next();

        MvcResult result = mockMvc.perform(
                        patch("/libraries/" + library.getId())
                                .content("{\"name\":\"test\"}")
                                .accept("application/json"))
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getStatus())
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

        Optional<Library> library2 = libraryRepository.findById(library.getId());

        assertThat(library2).isPresent();
        assertThat(library2.get().getName())
                .isEqualTo(library.getName());
    }

    @Test
    void testLibraryNameProjection() throws Exception {
        Library library = libraryRepository.findAll().iterator().next();

        MvcResult result = mockMvc.perform(get("/libraries?projection=LibraryName"))
               .andDo(print())
               .andReturn();

        assertThat(result.getResponse().getStatus())
               .isEqualTo(HttpStatus.OK.value());

        assertThat(result.getResponse().getContentAsString())
               .contains(library.getName())
               .doesNotContain("address")
               .doesNotContain("city");
    }

    @Test
    void testExportBooks() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/v1/libraries/exportAllBooks"))
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getStatus())
                .isEqualTo(HttpStatus.OK.value());

        assertThat(result.getResponse().getContentAsString()).contains("Java Programming");
    }

    @Test
    void testExportAuthors() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/v1/libraries/exportAllAuthors"))
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getStatus())
                .isEqualTo(HttpStatus.OK.value());

        assertThat(result.getResponse().getContentAsString())
                .contains("\"Ernest Hemingway\",\"bookCount\":1");
    }
}
