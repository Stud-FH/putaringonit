package ch.bookoflies.putaringonit.dish;

import ch.bookoflies.putaringonit.meal.Meal;
import ch.bookoflies.putaringonit.text.Text;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import ch.bookoflies.putaringonit.common.ValidationUtil;
import ch.bookoflies.putaringonit.text.TextService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DishService {

    private final DishRepository dishRepository;
    private final TextService textService;

    public Dish findById(Long id) {
        return dishRepository.findById(id).orElseThrow(ErrorResponse.NotFound("option not found"));
    }

    public Dish create(Meal meal, DishResource data) {
        ValidationUtil.rejectEmpty(data.getTitle(), "title");

        Dish dish = new Dish();
        dish.setContext(meal.getContext());
        dish.setContextName(meal.getContextName());
        dish.setMealId(meal.getId());
        dish.setTitle(data.getTitle());
        dish.setImageUrl(data.getImageUrl());
        dish.setCaption(data.getCaption());
        dish = dishRepository.save(dish);
        Text text = textService.persist(data.getDescription(), dish);
        dish.setText(text.getContent());
        return dish;
    }

    public Dish update(Long id, DishResource data, List<String> updates) {
        Dish dish = findById(id);
        List<BiConsumer<Dish, DishResource>> handlers = updates.stream().map(this::createUpdateHandler).toList();

        handlers.forEach(handler -> handler.accept(dish, data));
        return dishRepository.saveAndFlush(dish);
    }

    public void delete(Long id) {
        this.dishRepository.deleteById(id);
    }

    private BiConsumer<Dish, DishResource> createUpdateHandler(String attrib) {
        return switch (attrib) {
            case "title" -> (dish, data) -> dish.setTitle(data.getTitle());
            case "imageUrl" -> (dish, data) -> dish.setImageUrl(data.getImageUrl());
            case "caption" -> (dish, data) -> dish.setCaption(data.getCaption());
            case "description" -> (dish, data) -> {
                textService.clear(dish);
                Text text = textService.persist(data.getDescription(), dish);
                dish.setText(text.getContent());
            };
            default ->
                    throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("unknown attribute %s", attrib));
        };
    }
}
