package jake.pizza.pizza_menu.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import jakarta.annotation.PostConstruct;
import jake.pizza.pizza_menu.models.Pizza;

@Repository
public class MongoDBPizzaRepositoryImpl implements PizzaRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();
    private final MongoClient client;
    private MongoCollection<Pizza> pizzaCollection;

    public MongoDBPizzaRepositoryImpl(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        pizzaCollection = client.getDatabase("test").getCollection("pizzamenu", Pizza.class);
    }

    @Override
    public List<Pizza> findAll() {
        return pizzaCollection.find().into(new ArrayList<>());
    }

}
