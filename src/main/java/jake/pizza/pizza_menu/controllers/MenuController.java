package jake.pizza.pizza_menu.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jake.pizza.pizza_menu.dtos.PizzaDTO;
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

}
