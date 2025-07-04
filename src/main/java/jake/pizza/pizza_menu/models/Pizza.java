package jake.pizza.pizza_menu.models;

import java.util.List;

import org.bson.types.ObjectId;

public class Pizza {

    private ObjectId id;

    private String name;

    private List<String> toppings;

    public Pizza() {
    }

    public Pizza(ObjectId objectId, String name, List<String> toppings) {
        this.id = objectId;
        this.name = name;
        this.toppings = toppings;
    }

    @Override
    public String toString() {
        return String.format("%s pizza! Comes with %s", name, String.join(", ", toppings));
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToppings(List<String> toppings) {
        this.toppings = toppings;
    }
}
