package ch.bookoflies.putaringonit.meal;

import ch.bookoflies.putaringonit.common.ErrorResponse;
import ch.bookoflies.putaringonit.common.ValidationUtil;
import ch.bookoflies.putaringonit.program.Program;
import ch.bookoflies.putaringonit.text.Text;
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
public class MealService {

    private final MealRepository mealRepository;
    private final TextService textService;

    public Meal findById(Long id) {
        return mealRepository.findById(id).orElseThrow(ErrorResponse.NotFound("unknown program id"));
    }

    public Meal create(Program program, MealResource data) {
        ValidationUtil.rejectEmpty(data.getTitle(), "title");

        Meal meal = new Meal();
        meal.setContext(program.getContext());
        meal.setContextName(program.getContextName());
        meal.setProgramId(program.getId());
        meal.setTitle(data.getTitle());
        meal.setImageUrl(data.getImageUrl());
        meal.setCaption(data.getCaption());
        meal = mealRepository.save(meal);
        Text text = textService.persist(data.getDescription(), meal);
        meal.setText(text.getContent());
        return meal;
    }

    public Meal update(long id, MealResource data, List<String> updates) {
        Meal meal = findById(id);
        List<BiConsumer<Meal, MealResource>> handlers = updates.stream().map(this::createUpdateHandler).collect(Collectors.toList());

        handlers.forEach(handler -> handler.accept(meal, data));
        return mealRepository.saveAndFlush(meal);
    }

    public void delete(Long id) {
        Meal meal = findById(id);
        this.mealRepository.delete(meal);
    }

    private BiConsumer<Meal, MealResource> createUpdateHandler(String attrib) {
        switch(attrib) {
            case "title": return (meal, data) -> meal.setTitle(data.getTitle());
            case "imageUrl": return (meal, data) -> meal.setImageUrl(data.getImageUrl());
            case "caption": return (meal, data) -> meal.setCaption(data.getCaption());
            case "description": return (meal, data) -> {
                textService.clear(meal);
                Text text = textService.persist(data.getDescription(), meal);
                meal.setText(text.getContent());
            };
            default: throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("unknown attribute %s", attrib));
        }
    }
}
