package sk.library;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import sk.library.model.Author;
import sk.library.model.Book;
import sk.library.repository.BookRepository;

@SpringBootTest
@AutoConfigureMockMvc
class BookRestTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BookRepository bookRepository;

    @Test
    void testListBooks() throws Exception {
        int count = (int) bookRepository.count();

        MvcResult results = mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(count))
                .andExpect(jsonPath("$.page.totalElements").value(count))
                .andReturn();

        assertThat(results.getResponse().getContentAsString()).contains("\"totalElements\" : " + count);
    }

    @Test
    void testGetBook() throws Exception {
        Book book = bookRepository.findAll().iterator().next();

        MvcResult results = mockMvc.perform(
                        MockMvcRequestBuilders.get("/books/" + book.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(book.getName()))
                .andExpect(jsonPath("$.count").value(book.getCount()))
                .andReturn();
    }
    @Test
    void testGetBookNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateBook() throws Exception {
        mockMvc.perform(post("/books")
                        .content("{\"name\":\"meno\", \"id_author\":\"/authors/2\"}")
                        .accept("application/json"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }
    @Test
    void testUpdateBook() throws Exception {
        Book book = bookRepository.findAll().iterator().next();

        mockMvc.perform(patch("/books/" + book.getId())
                        .content("{\"count\": 100 }")
                        .accept("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertThat(bookRepository.findById(book.getId()).get().getCount()).isEqualTo(100);
    }
    @Test
    void testDeleteBook() throws Exception {
        Book book = bookRepository.findAll().iterator().next();

        MvcResult results = mockMvc.perform(
                        MockMvcRequestBuilders.delete("/books/" + book.getId()))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        assertThat(bookRepository.findById(book.getId())).isEmpty();
    }
}
