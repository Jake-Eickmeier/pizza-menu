package jake.pizza.pizza_menu.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jake.pizza.pizza_menu.dtos.MatchedPizzaSpecialDTO;
import jake.pizza.pizza_menu.dtos.PizzaDTO;
import jake.pizza.pizza_menu.models.PizzaTag;
import jake.pizza.pizza_menu.services.PizzaMenuService;

@RestController
public class MenuController {

    @Autowired
    PizzaMenuService pizzaMenuService;

    MenuController() {
    };


    @GetMapping("/hello")
    public String getHello() {
        return new String("Hello ");
    }

    @GetMapping("/pizza/menu")
    public List<PizzaDTO> getAllPizzas() {
        return pizzaMenuService.findAll();
    }

    @GetMapping("/pizza/specials/daily")
    public List<MatchedPizzaSpecialDTO> getAllActiveDailyPizzaSpecials() {
        return pizzaMenuService.getAllActiveDailyPizzaSpecials();
    }

    @GetMapping("/pizza/specials/today")
    public List<MatchedPizzaSpecialDTO> getTodaysPizzaSpecials() {
        return pizzaMenuService.getTodaysPizzaSpecials();
    }

    @GetMapping("pizza/specials/active") 
    public List<MatchedPizzaSpecialDTO> getAllActiveSpecials() {
        return pizzaMenuService.getAllActivePizzaSpecials();
    }

    @GetMapping("/pizza/menu/{tag}")
    public List<PizzaDTO> getMenuWithFilter(@PathVariable("tag") PizzaTag pizzaTag) {
        return pizzaMenuService.getMenuWithFilter(pizzaTag);
    }

}
