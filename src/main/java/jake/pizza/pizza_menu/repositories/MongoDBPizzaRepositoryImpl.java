package jake.pizza.pizza_menu.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import jakarta.annotation.PostConstruct;
import jake.pizza.pizza_menu.models.Pizza;
import jake.pizza.pizza_menu.models.PizzaTag;

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
        pizzaCollection = client.getDatabase("test").getCollection("pizzaMenu", Pizza.class);
    }

    @Override
    public List<Pizza> findAll() {
        return pizzaCollection.find().into(new ArrayList<>());
    }

    @Override
    public Pizza findById(final String id) {
        return pizzaCollection.find(Filters.eq("_id", new ObjectId(id))).first();
    }

    @Override
    public List<Pizza> saveAll(List<Pizza> pizzaList) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(() -> {
                pizzaList.forEach(p -> p.setId(new ObjectId()));
                pizzaCollection.insertMany(clientSession, pizzaList);
                return pizzaList;
            }, txnOptions);
        }
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> pizzaCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }
    }

    @Override
    public Map<String, List<Pizza>> getWeeklyPizzaSpecials() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWeeklyPizzaSpecials'");
    }

    @Override
    public List<Pizza> getDailyPizzaSpecials() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDailyPizzaSpecials'");
    }

    @Override
    public List<Pizza> getMenuWithFilter(PizzaTag pizzaTag) {
        return pizzaCollection.find(Filters.in("tags", pizzaTag)).into(new ArrayList<>());
    }

}
