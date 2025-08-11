package jake.pizza.pizza_menu.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jake.pizza.pizza_menu.dtos.PizzaDTO;
import jake.pizza.pizza_menu.dtos.PizzaSpecialDTO;
import jake.pizza.pizza_menu.models.PizzaTag;
import jake.pizza.pizza_menu.repositories.PizzaRepository;
import jake.pizza.pizza_menu.repositories.PizzaSpecialRepository;

@Service
public class PizzaMenuServiceImpl implements PizzaMenuService {

    private final PizzaRepository pizzaRepository;

    private final PizzaSpecialRepository pizzaSpecialRepository;

    public PizzaMenuServiceImpl(PizzaRepository pizzaRepository, PizzaSpecialRepository pizzaSpecialRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaSpecialRepository = pizzaSpecialRepository;
    }

    @Override
    public List<PizzaDTO> findAll() {
        return pizzaRepository.findAll()
            .stream()
            .map(PizzaDTO::new)
            .toList();
    }

    @Override
    public List<PizzaDTO> getMenuWithFilter(PizzaTag pizzaTag) {
        return pizzaRepository.getMenuWithFilter(pizzaTag)
            .stream()
            .map(PizzaDTO::new)
            .toList();
    }

    @Override
    public Map<PizzaSpecialDTO, PizzaDTO> getTodaysPizzaSpecials() {
        // TODO: I am sure there is a better way of streaming these results together into a map. Perhaps an aggregate query.
        Map<PizzaSpecialDTO, PizzaDTO> result = new HashMap<>();
        List<PizzaSpecialDTO> pizzaSpecialDTOList = pizzaSpecialRepository.getPizzaSpecialsByDay(LocalDate.now().getDayOfWeek())
            .stream()
            .map(PizzaSpecialDTO::new)
            .toList();
        pizzaSpecialDTOList
            .forEach((pizzaSpecialDTO) -> {
                result.put(pizzaSpecialDTO, new PizzaDTO(pizzaRepository.findById(pizzaSpecialDTO.id())));
            });
        return result;
    }

    @Override
    public Map<PizzaSpecialDTO, PizzaDTO> getAllDailyPizzaSpecials() {
        // TODO: I am sure there is a better way of streaming these results together into a map. Perhaps an aggregate query.
        Map<PizzaSpecialDTO, PizzaDTO> result = new HashMap<>();
        List<PizzaSpecialDTO> pizzaSpecialDTOList = pizzaSpecialRepository.getAllDailyPizzaSpecials()
            .stream()
            .map(PizzaSpecialDTO::new)
            .toList();
        pizzaSpecialDTOList
            .forEach((pizzaSpecialDTO) -> {
                result.put(pizzaSpecialDTO, new PizzaDTO(pizzaRepository.findById(pizzaSpecialDTO.pizzaId())));
            });
        return result;
    }

    @Override
    public Map<PizzaSpecialDTO, PizzaDTO> getAllActivePizzaSpecials() {
        // TODO: I am sure there is a better way of streaming these results together into a map. Perhaps an aggregate query.
        Map<PizzaSpecialDTO, PizzaDTO> result = new HashMap<>();
        List<PizzaSpecialDTO> pizzaSpecialDTOList = pizzaSpecialRepository.getAllActivePizzaSpecials()
            .stream()
            .map(PizzaSpecialDTO::new)
            .toList();
        pizzaSpecialDTOList
            .forEach((pizzaSpecialDTO) -> {
                result.put(pizzaSpecialDTO, new PizzaDTO(pizzaRepository.findById(pizzaSpecialDTO.pizzaId())));
            });
        return result;
    }
}
