package jake.pizza.pizza_menu.services;

import java.util.List;

import org.springframework.stereotype.Service;

import jake.pizza.pizza_menu.dtos.MatchedPizzaSpecialDTO;
import jake.pizza.pizza_menu.dtos.PizzaDTO;
import jake.pizza.pizza_menu.models.PizzaTag;

@Service
public interface PizzaMenuService {
    
    List<PizzaDTO> findAll();

    List<MatchedPizzaSpecialDTO> getTodaysPizzaSpecials();

    List<MatchedPizzaSpecialDTO> getAllActiveDailyPizzaSpecials();

    List<MatchedPizzaSpecialDTO> getAllActivePizzaSpecials();

    List<PizzaDTO> getMenuWithFilter(PizzaTag pizzaTag);
}
