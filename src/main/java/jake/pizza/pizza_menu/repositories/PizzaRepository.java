package jake.pizza.pizza_menu.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import jake.pizza.pizza_menu.models.Pizza;

@Repository
public interface PizzaRepository {

    public List<Pizza> findAll();
}