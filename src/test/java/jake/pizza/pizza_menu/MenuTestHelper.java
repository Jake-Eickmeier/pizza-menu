package jake.pizza.pizza_menu;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import jake.pizza.pizza_menu.dtos.MatchedPizzaSpecialDTO;
import jake.pizza.pizza_menu.dtos.PizzaDTO;
import jake.pizza.pizza_menu.dtos.PizzaSpecialDTO;
import jake.pizza.pizza_menu.models.Pizza;
import jake.pizza.pizza_menu.models.PizzaSpecial;
import jake.pizza.pizza_menu.models.PizzaTag;

@Component
public class MenuTestHelper {
    Pizza getPepperoniPizza() {
        return new Pizza(new ObjectId("68715674e36ec57a5dba8a7d"), "pepperoni", List.of("cheese", "pepperoni"), 1199, List.of(PizzaTag.MEATLOVER));
    }

    Pizza getCheesePizza() {
        return new Pizza(new ObjectId("68715674e36ec57a5dba8a7e"), "cheese", List.of("cheese"), 1099, List.of(PizzaTag.VEGETARIAN));
    }

    List<Pizza> getListOfPizzas() {
        return List.of(getPepperoniPizza(), getCheesePizza());
    }

    PizzaSpecial getPepperoniPizzaSpecial() {
        return new PizzaSpecial(new ObjectId("68a21bf3544d7e6b03dc5346"), new ObjectId("68715674e36ec57a5dba8a7d"), 999, DayOfWeek.THURSDAY, null);
    }

    PizzaSpecial getExpiredPepperoniPizzaSpecial() {
        return new PizzaSpecial(new ObjectId("68a21bf3544d7e6b03dc5348"), new ObjectId("68715674e36ec57a5dba8a7d"), 999, DayOfWeek.THURSDAY, Date.from(LocalDate.now().minusDays(7).atStartOfDay().toInstant(ZoneOffset.UTC)));
    }

    PizzaSpecial getCheesePizzaSpecial() {
        return new PizzaSpecial(new ObjectId("68a21bf3544d7e6b03dc5347"), new ObjectId("68715674e36ec57a5dba8a7e"), 899, DayOfWeek.FRIDAY, null);
    }

    List<PizzaSpecial> getListOfActivePizzaSpecials() {
        return List.of(getPepperoniPizzaSpecial(), getCheesePizzaSpecial());
    }

    List<PizzaSpecial> getListOfAllPizzaSpecials() {
        return List.of(getPepperoniPizzaSpecial(), getCheesePizzaSpecial(), getExpiredPepperoniPizzaSpecial());

    }

    PizzaSpecialDTO getPepperoniPizzaSpecialDTO() {
        return new PizzaSpecialDTO(getPepperoniPizzaSpecial());
    }

    PizzaSpecialDTO getExpiredPepperoniPizzaSpecialDTO() {
        return new PizzaSpecialDTO(getExpiredPepperoniPizzaSpecial());
    }

    PizzaSpecialDTO getCheesePizzaSpecialDTO() {
        return new PizzaSpecialDTO(getCheesePizzaSpecial());
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

    MatchedPizzaSpecialDTO getMatchedPepperoniPizzaSpecialDTO() {
        return new MatchedPizzaSpecialDTO(getPepperoniPizzaSpecialDTO(), getPepperoniPizzaDTO());
    }

    MatchedPizzaSpecialDTO getMatchedCheesePizzaSpecialDTO() {
        return new MatchedPizzaSpecialDTO(getCheesePizzaSpecialDTO(), getCheesePizzaDTO());
    }
}
