package jake.pizza.pizza_menu.dtos;

import jake.pizza.pizza_menu.models.MatchedPizzaSpecial;

public record MatchedPizzaSpecialDTO (
    PizzaSpecialDTO pizzaSpecialDTO,
    PizzaDTO pizzaDTO) {

        public MatchedPizzaSpecialDTO(MatchedPizzaSpecial matchedPizzaSpecial) {
            this(new PizzaSpecialDTO(matchedPizzaSpecial.getPizzaSpecial()), new PizzaDTO(matchedPizzaSpecial.getPizza()));
        }

        public MatchedPizzaSpecial toMatchedPizzaSpecial() {
            return new MatchedPizzaSpecial(pizzaSpecialDTO.toPizzaSpecial(), pizzaDTO.toPizza());
        }
    }
