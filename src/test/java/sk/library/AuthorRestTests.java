package sk.library;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import sk.library.model.Author;
import sk.library.repository.AuthorRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthorRestTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void testGetAuthors() throws Exception {
        int count = (int) authorRepository.count();

        MvcResult results = mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(count))
                .andReturn();

        assertThat(results.getResponse().getContentAsString()).contains("\"totalElements\" : " + count);
    }

    @Test
    void testGetAuthorsNamesProjection() throws Exception {
        MvcResult results = mockMvc.perform(
                MockMvcRequestBuilders.get("/authors?projection=AuthorSurnames"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(results.getResponse().getContentAsString())
                .contains("\"surname\" : ")
                .doesNotContain("\"name\" : ");
    }

    @Test
    void testGetAuthor() throws Exception {
        Author author = authorRepository.findAll().iterator().next();

        MvcResult results = mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors/" + author.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(author.getName()))
                .andExpect(jsonPath("$.surname").value(author.getSurname()))
                .andReturn();
    }

    @Test
    void testGetAuthorNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/-1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void postAuthorSuccess() throws Exception {
        mockMvc.perform(post("/authors")
                        .content("{\"name\":\"meno\", \"surname\":\"priezvisko\"}")
                        .accept("application/json"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void postAuthorNoSurname() throws Exception {
        mockMvc.perform(post("/authors")
                        .content("{\"name\":\"meno\"}")
                        .accept("application/json"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message")
                        .value("Prazdne meno alebo priezvisko autora!"))
                .andReturn();
    }

    @Test
    void patchAuthorSuccess() throws Exception {
        mockMvc.perform(patch("/authors/22")
                        .content("{\"name\":\"Zmenene meno\"}")
                        .accept("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Author author = authorRepository.findById(22L).get();

        assertThat(author.getName()).isEqualTo("Zmenene meno");
    }
    @Test
    void deleteAuthorNotAllowed() throws Exception {
        int count = (int) authorRepository.count();

        mockMvc.perform(delete("/authors/1"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed())
                .andReturn();

        assertThat(authorRepository.count()).isEqualTo(count);
    }
}