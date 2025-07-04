package jake.pizza.pizza_menu.services;

import java.util.List;

import org.springframework.stereotype.Service;

import jake.pizza.pizza_menu.dtos.PizzaDTO;
import jake.pizza.pizza_menu.repositories.PizzaRepository;

@Service
public class PizzaMenuServiceImpl implements PizzaMenuService {

    private final PizzaRepository pizzaRepository;

    public PizzaMenuServiceImpl(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @Override
    public List<PizzaDTO> findAll() {
        return pizzaRepository.findAll()
            .stream()
            .map(PizzaDTO::new)
            .toList();
    }
}
