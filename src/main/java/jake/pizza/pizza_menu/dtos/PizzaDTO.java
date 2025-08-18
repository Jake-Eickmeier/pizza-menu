package jake.pizza.pizza_menu.dtos;

import java.util.List;

import org.bson.types.ObjectId;

import jake.pizza.pizza_menu.models.Pizza;
import jake.pizza.pizza_menu.models.PizzaTag;

public record PizzaDTO(
        String id,
        String name,
        List<String> toppings,
        int priceInCents,
        List<PizzaTag> tags) {

    public PizzaDTO(Pizza pizza) {
        this(pizza.getId().toHexString(), pizza.getName(), pizza.getToppings(), pizza.getPriceInCents(), pizza.getTags());
    }

    public Pizza toPizza() {
        ObjectId objectId = id == null ? new ObjectId() : new ObjectId(id);
        return new Pizza(objectId, name, toppings, priceInCents, tags);
    }

}
