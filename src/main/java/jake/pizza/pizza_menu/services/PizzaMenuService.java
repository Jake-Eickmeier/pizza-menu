package jake.pizza.pizza_menu.services;

import org.springframework.stereotype.Service;

import jake.pizza.pizza_menu.dtos.PizzaDTO;

import java.util.List;

@Service
public interface PizzaMenuService {
    
    List<PizzaDTO> findAll();
}
