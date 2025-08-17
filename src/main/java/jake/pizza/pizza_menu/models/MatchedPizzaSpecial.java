package jake.pizza.pizza_menu.models;

public class MatchedPizzaSpecial {

    PizzaSpecial pizzaSpecial;

    Pizza pizza;


    public MatchedPizzaSpecial() {
    }

    public MatchedPizzaSpecial(PizzaSpecial pizzaSpecial, Pizza pizza) {
        this.pizzaSpecial = pizzaSpecial;
        this.pizza = pizza;
    }


    @Override
    public String toString() {
        // TODO: Add a formatter to include decimal and consider a more useful implementation in general
        return String.format("%s pizza, On sale for %s on %s", pizza.getName(), String.valueOf(pizzaSpecial.getNewPriceInCents()), pizzaSpecial.getSpecialDay().toString());
    }

    public PizzaSpecial getPizzaSpecial() {
        return pizzaSpecial;
    }

    public void setPizzaSpecial(PizzaSpecial pizzaSpecial) {
        this.pizzaSpecial = pizzaSpecial;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }



}
