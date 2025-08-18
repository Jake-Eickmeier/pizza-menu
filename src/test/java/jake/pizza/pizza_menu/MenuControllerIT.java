package jake.pizza.pizza_menu;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import jakarta.annotation.PostConstruct;
import jake.pizza.pizza_menu.dtos.MatchedPizzaSpecialDTO;
import jake.pizza.pizza_menu.dtos.PizzaDTO;
import jake.pizza.pizza_menu.models.Pizza;
import jake.pizza.pizza_menu.models.PizzaSpecial;
import jake.pizza.pizza_menu.repositories.PizzaRepository;
import jake.pizza.pizza_menu.repositories.PizzaSpecialRepository;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MenuControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaSpecialRepository pizzaSpecialRepository;

    @Autowired
    private MenuTestHelper menuTestHelper;

    private String serviceURL;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    MenuControllerIT(MongoClient mongoClient) {
        createPizzaMenuCollectionsIfNotPresent(mongoClient);
    }

    @PostConstruct
    void setUp() {
        serviceURL = "http://localhost:" + port;
    }

    @AfterEach
    void tearDown() {
        pizzaRepository.deleteAll();
        pizzaSpecialRepository.deleteAll();
    }

    @DisplayName("GET /pizza/menu with 2 pizzas")
    @Test
    void getPizzas_twoPizzasPresent_returnsTwoPizzas() {
        // GIVEN
        List<Pizza> pizzas = pizzaRepository.saveAll(menuTestHelper.getListOfPizzas());
        // WHEN
        ResponseEntity<List<PizzaDTO>> result = rest.exchange(serviceURL + "/pizza/menu", HttpMethod.GET, null,
                                                               new ParameterizedTypeReference<>() {
                                                               });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<PizzaDTO> expected = List.of(menuTestHelper.getPepperoniPizzaDTOWithId(pizzas.get(0).getId()),
                                           menuTestHelper.getCheesePizzaDTOWithId(pizzas.get(1).getId()));
        assertThat(result.getBody()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "createdAt")
                                    .containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("GET /pizza/specials/daily with 2 daily specials")
    @Test
    void getDailySpecials_twoActiveDailySpecialsPresent_returnsTwoSpecials() {
        // GIVEN
        List<Pizza> pizzas = pizzaRepository.saveAll(menuTestHelper.getListOfPizzas());
        List<PizzaSpecial> pizzaSpecials = pizzaSpecialRepository.saveAll(menuTestHelper.getListOfAllPizzaSpecials());

        // WHEN
        ResponseEntity<List<MatchedPizzaSpecialDTO>> result = rest.exchange(serviceURL + "/pizza/specials/daily", HttpMethod.GET, null,
                                                               new ParameterizedTypeReference<>() {
                                                               });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<MatchedPizzaSpecialDTO> expected = List.of(
            menuTestHelper.getMatchedCheesePizzaSpecialDTO(),
            menuTestHelper.getMatchedPepperoniPizzaSpecialDTO()
        );
        assertThat(result.getBody()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "createdAt")
                                    .containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("GET /pizza/specials/active")
    @Test
    void getActiveSpecials_oneExpiredPresent_returnsOnlyActive() {
        // GIVEN
        List<Pizza> pizzas = pizzaRepository.saveAll(menuTestHelper.getListOfPizzas());
        List<PizzaSpecial> pizzaSpecials = pizzaSpecialRepository.saveAll(menuTestHelper.getListOfActivePizzaSpecials());

        // WHEN
        ResponseEntity<List<MatchedPizzaSpecialDTO>> result = rest.exchange(serviceURL + "/pizza/specials/daily", HttpMethod.GET, null,
                                                               new ParameterizedTypeReference<>() {
                                                               });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<MatchedPizzaSpecialDTO> expected = List.of(
            menuTestHelper.getMatchedCheesePizzaSpecialDTO(),
            menuTestHelper.getMatchedPepperoniPizzaSpecialDTO()
        );
        assertThat(result.getBody()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "createdAt")
                                    .containsExactlyInAnyOrderElementsOf(expected);

    }

    @DisplayName("GET /pizza/menu/{tag}")
    @Test
    void getWithVegetarianFilter_twoPizzasPresent_returnsOnlyVegetarian() {
        // GIVEN
        List<Pizza> pizzas = pizzaRepository.saveAll(menuTestHelper.getListOfPizzas());

        // WHEN
        ResponseEntity<List<PizzaDTO>> result = rest.exchange(serviceURL + "/pizza/menu/VEGETARIAN", HttpMethod.GET, null,
                                                               new ParameterizedTypeReference<>() {
                                                               });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<PizzaDTO> expected = List.of(
            menuTestHelper.getCheesePizzaDTO()
        );
        assertThat(result.getBody()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "createdAt")
                                    .containsExactlyInAnyOrderElementsOf(expected);

    }

    private void createPizzaMenuCollectionsIfNotPresent(MongoClient mongoClient) {
        // This is required because it is not possible to create a new collection within a multi-documents transaction.
        // Some tests start by inserting 2 documents with a transaction.
        MongoDatabase pizzaMenu = mongoClient.getDatabase("test");
        if (!pizzaMenu.listCollectionNames().into(new ArrayList<>()).contains("pizzaMenu")) {
            pizzaMenu.createCollection("pizzaMenu");
        }
        MongoDatabase pizzaSpecials = mongoClient.getDatabase("test");
        if (!pizzaSpecials.listCollectionNames().into(new ArrayList<>()).contains("pizzaSpecials")) {
            pizzaSpecials.createCollection("pizzaSpecials");
        }

    }
    
}