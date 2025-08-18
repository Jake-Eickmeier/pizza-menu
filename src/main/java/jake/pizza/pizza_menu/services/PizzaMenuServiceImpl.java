package jake.pizza.pizza_menu.services;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jake.pizza.pizza_menu.dtos.MatchedPizzaSpecialDTO;
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
    public List<MatchedPizzaSpecialDTO> getTodaysPizzaSpecials() {
        // TODO: I am sure there is a better way of streaming these results together into a List<MatchedPizzaSpecialDTO>. Perhaps an aggregate query.
        List<MatchedPizzaSpecialDTO> result = new ArrayList<>();
        List<PizzaSpecialDTO> pizzaSpecialDTOList = pizzaSpecialRepository.getPizzaSpecialsByDay(LocalDate.now().getDayOfWeek())
            .stream()
            .map(PizzaSpecialDTO::new)
            .toList();
        pizzaSpecialDTOList
            .forEach((pizzaSpecialDTO) -> {
                result.add(new MatchedPizzaSpecialDTO(pizzaSpecialDTO, new PizzaDTO(pizzaRepository.findById(pizzaSpecialDTO.pizzaId()))));
            });
        return result;
    }

    @Override
    public List<MatchedPizzaSpecialDTO> getAllActiveDailyPizzaSpecials() {
        // TODO: I am sure there is a better way of streaming these results together into a List<MatchedPizzaSpecialDTO>. Perhaps an aggregate query.
        List<MatchedPizzaSpecialDTO> result = new ArrayList<>();
        List<PizzaSpecialDTO> pizzaSpecialDTOList = pizzaSpecialRepository.getAllActiveDailyPizzaSpecials()
            .stream()
            .map(PizzaSpecialDTO::new)
            .toList();
        pizzaSpecialDTOList
            .forEach((pizzaSpecialDTO) -> {
                result.add(new MatchedPizzaSpecialDTO(pizzaSpecialDTO, new PizzaDTO(pizzaRepository.findById(pizzaSpecialDTO.pizzaId()))));
            });
        return result;
    }

    @Override
    public List<MatchedPizzaSpecialDTO> getAllActivePizzaSpecials() {
        // TODO: I am sure there is a better way of streaming these results together into a List<MatchedPizzaSpecialDTO>. Perhaps an aggregate query.
        List<MatchedPizzaSpecialDTO> result = new ArrayList<>();
        List<PizzaSpecialDTO> pizzaSpecialDTOList = pizzaSpecialRepository.getAllActivePizzaSpecials()
            .stream()
            .map(PizzaSpecialDTO::new)
            .toList();
        pizzaSpecialDTOList
            .forEach((pizzaSpecialDTO) -> {
                result.add(new MatchedPizzaSpecialDTO(pizzaSpecialDTO, new PizzaDTO(pizzaRepository.findById(pizzaSpecialDTO.pizzaId()))));
            });
        return result;
    }
}
