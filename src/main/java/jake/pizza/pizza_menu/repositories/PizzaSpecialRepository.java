package jake.pizza.pizza_menu.repositories;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.stereotype.Repository;

import jake.pizza.pizza_menu.models.PizzaSpecial;

@Repository
public interface PizzaSpecialRepository {

    public List<PizzaSpecial> findAll();

    public List<PizzaSpecial> saveAll(List<PizzaSpecial> pizzaSpecialList);

    public long deleteAll();
    
    public List<PizzaSpecial> getAllActiveDailyPizzaSpecials();

    public List<PizzaSpecial> getPizzaSpecialsByDay(DayOfWeek dayOfWeek);

    public List<PizzaSpecial> getAllActivePizzaSpecials();

}