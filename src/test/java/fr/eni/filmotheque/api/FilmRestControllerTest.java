package fr.eni.filmotheque.api;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
//pas besoin de script sql finalement car nos service JpaImpl s'occupent de remplir la base h2 : @Sql(scripts = {"/creation_films.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class FilmRestControllerTest {

    @Autowired // ca marche car on a utilisé l'annotation : @AutoConfigureMockMvc
    private MockMvc mockMvc;

    @Test
    void films() throws Exception {
        // TEST requête sans paramètres
        mockMvc.perform(get("/api/films"))
                // 1 - je vérifie que la requête me renvoie une réponse 200
                .andExpect(status().is2xxSuccessful())
                // 2 - je vérifie que la requête me renvoie une liste qui a une taille de 4 éléménts
                .andExpect(jsonPath("$", hasSize(4)))
                // 3 - je vérifie que le titre du premier élément de ma liste est : "Jurassic Park"
                .andExpect(jsonPath("$[0].titre", Matchers.is("Jurassic Park")))
                // 4 - je vérifie que l'année du premier élément de ma liste est : 1993
                .andExpect(jsonPath("$[0].annee", Matchers.is(1993)));

        // TEST requête avec recherche
        mockMvc.perform(get("/api/films?recherche=Ju"))
                // 1 - je vérifie que la requête me renvoie une réponse 200
                .andExpect(status().is2xxSuccessful())
                // 2 - je vérifie que la requête me renvoie une liste qui a une taille de 1 élémént
                .andExpect(jsonPath("$", hasSize(1)))
                // 3 - je vérifie que le titre du premier élément de ma liste est : "Jurassic Park"
                .andExpect(jsonPath("$[0].titre", Matchers.is("Jurassic Park")));

        // TEST requête avec filtre par genre
        mockMvc.perform(get("/api/films?genre=1"))
                // 1 - je vérifie que la requête me renvoie une réponse 200
                .andExpect(status().is2xxSuccessful())
                // 2 - je vérifie que la requête me renvoie une liste qui a une taille de 2 éléménts
                .andExpect(jsonPath("$", hasSize(2)))
                // 3 - je vérifie que le titre du premier élément de ma liste est : "Jurassic Park"
                .andExpect(jsonPath("$[0].titre", Matchers.is("Jurassic Park")))
                // 4 - je vérifie que le titre du deuxième élément de ma liste est : "The Fly"
                .andExpect(jsonPath("$[1].titre", Matchers.is("The Fly")));

    }
}