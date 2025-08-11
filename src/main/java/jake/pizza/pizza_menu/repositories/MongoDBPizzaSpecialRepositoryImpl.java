package jake.pizza.pizza_menu.repositories;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.BsonDocument;
import org.bson.types.ObjectId;
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
import jake.pizza.pizza_menu.models.PizzaSpecial;

@Repository
public class MongoDBPizzaSpecialRepositoryImpl implements PizzaSpecialRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();
    private final MongoClient client;
    private MongoCollection<PizzaSpecial> pizzaSpecialCollection;

    public MongoDBPizzaSpecialRepositoryImpl(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        pizzaSpecialCollection = client.getDatabase("test").getCollection("pizzaSpecials", PizzaSpecial.class);
    }

    @Override
    public List<PizzaSpecial> findAll() {
        return pizzaSpecialCollection.find().into(new ArrayList<>());
    }

    @Override
    public List<PizzaSpecial> saveAll(List<PizzaSpecial> pizzaSpecialList) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(() -> {
                pizzaSpecialList.forEach(p -> p.setId(new ObjectId()));
                pizzaSpecialCollection.insertMany(clientSession, pizzaSpecialList);
                return pizzaSpecialList;
            }, txnOptions);
        }
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> pizzaSpecialCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }
    }

    @Override
    public List<PizzaSpecial> getAllDailyPizzaSpecials() {
        return pizzaSpecialCollection.find(Filters.exists("specialDay")).into(new ArrayList<>());
    }

    @Override
    public List<PizzaSpecial> getPizzaSpecialsByDay(DayOfWeek dayOfWeek) {
        return pizzaSpecialCollection.find(Filters.eq("specialDay", dayOfWeek)).into(new ArrayList<>());
    }

    @Override
    public List<PizzaSpecial> getAllActivePizzaSpecials() {
        return pizzaSpecialCollection.find(Filters.or(Filters.exists("expirationDate", false), Filters.eq("expirationDate", null), Filters.lt("expirationDate", new Date()))).into(new ArrayList<>());
    }

}
