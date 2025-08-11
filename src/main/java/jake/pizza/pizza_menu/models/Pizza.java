package jake.pizza.pizza_menu.models;

import java.util.List;

import org.bson.types.ObjectId;

public class Pizza {

    private ObjectId id;

    private String name;

    private List<String> toppings;

    private int priceInCents;

    private List<PizzaTag> tags;

    public Pizza() {
    }

    public Pizza(ObjectId objectId, String name, List<String> toppings, int priceInCents, List<PizzaTag> tags) {
        this.id = objectId;
        this.name = name;
        this.toppings = toppings;
        this.priceInCents = priceInCents;
        this.tags = tags;
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

    public int getPriceInCents() {
        return priceInCents;
    }

    public List<PizzaTag> getTags() {
        return tags;
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

    public void setPriceInCents(int priceInCents) {
        this.priceInCents = priceInCents;
    }

    public void setTags(List<PizzaTag> tags) {
        this.tags = tags;
    }
}
