package jake.pizza.pizza_menu.dtos;

import java.time.DayOfWeek;
import java.util.Date;

import org.bson.types.ObjectId;

import jake.pizza.pizza_menu.models.PizzaSpecial;

public record PizzaSpecialDTO(
        String id,
        String pizzaId,
        int newPriceInCents,
        DayOfWeek specialDay,
        Date expirationDate) {

    public PizzaSpecialDTO(PizzaSpecial pizzaSpecial) {
        this(pizzaSpecial.getId().toHexString(), pizzaSpecial.getPizzaId().toHexString(), pizzaSpecial.getNewPriceInCents(), pizzaSpecial.getSpecialDay(), pizzaSpecial.getExpirationDate());
    }

    public PizzaSpecial toPizzaSpecial() {
        ObjectId objectId = id == null ? new ObjectId() : new ObjectId(id);
        ObjectId pizzaObjectId = new ObjectId(pizzaId);
        return new PizzaSpecial(objectId, pizzaObjectId, newPriceInCents, specialDay, expirationDate);
    }

}
