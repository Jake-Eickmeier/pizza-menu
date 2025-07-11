package jake.pizza.pizza_menu;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import jake.pizza.pizza_menu.dtos.PizzaDTO;
import jake.pizza.pizza_menu.models.Pizza;

@Component
public class MenuTestHelper {
    Pizza getPepperoniPizza() {
        return new Pizza(new ObjectId(), "pepperoni", List.of("cheese", "pepperoni"));
    }

    Pizza getCheesePizza() {
        return new Pizza(new ObjectId(), "cheese", List.of("cheese"));
    }

    List<Pizza> getListOfPizzas() {
        return List.of(getPepperoniPizza(), getCheesePizza());
    }


    PizzaDTO getPepperoniPizzaDTO() {
        return new PizzaDTO(getPepperoniPizza());
    }

    PizzaDTO getCheesePizzaDTO() {
        return new PizzaDTO(getCheesePizza());
    }

    PizzaDTO getPepperoniPizzaDTOWithId(ObjectId id) {
        Pizza pepperonPizza = getPepperoniPizza();
        pepperonPizza.setId(id);
        return new PizzaDTO(pepperonPizza);
    }

    PizzaDTO getCheesePizzaDTOWithId(ObjectId id) {
        Pizza cheesePizza = getCheesePizza();
        cheesePizza.setId(id);
        return new PizzaDTO(cheesePizza);
    }

}
