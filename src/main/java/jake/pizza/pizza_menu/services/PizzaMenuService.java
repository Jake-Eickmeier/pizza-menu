package jake.pizza.pizza_menu.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jake.pizza.pizza_menu.dtos.PizzaDTO;
import jake.pizza.pizza_menu.dtos.PizzaSpecialDTO;
import jake.pizza.pizza_menu.models.PizzaTag;

@Service
public interface PizzaMenuService {
    
    List<PizzaDTO> findAll();

    Map<PizzaSpecialDTO, PizzaDTO> getTodaysPizzaSpecials();

    Map<PizzaSpecialDTO, PizzaDTO> getAllDailyPizzaSpecials();

    Map<PizzaSpecialDTO, PizzaDTO> getAllActivePizzaSpecials();

    List<PizzaDTO> getMenuWithFilter(PizzaTag pizzaTag);
}
