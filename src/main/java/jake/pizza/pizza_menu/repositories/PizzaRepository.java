package jake.pizza.pizza_menu.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import jake.pizza.pizza_menu.models.Pizza;
import jake.pizza.pizza_menu.models.PizzaTag;

@Repository
public interface PizzaRepository {

    public List<Pizza> findAll();

    public Pizza findById(final String id);

    public List<Pizza> saveAll(List<Pizza> pizzaList);

    public long deleteAll();

    public Map<String, List<Pizza>> getWeeklyPizzaSpecials();
    
    public List<Pizza> getDailyPizzaSpecials();

    public List<Pizza> getMenuWithFilter(PizzaTag pizzaTag);
}