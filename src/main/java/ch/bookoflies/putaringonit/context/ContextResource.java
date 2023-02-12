package ch.bookoflies.putaringonit.context;

import ch.bookoflies.putaringonit.common.TextReferencable;
import ch.bookoflies.putaringonit.dish.DishResource;
import ch.bookoflies.putaringonit.gift.GiftResource;
import ch.bookoflies.putaringonit.meal.MealResource;
import ch.bookoflies.putaringonit.program.ProgramResource;
import ch.bookoflies.putaringonit.wish.WishResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ContextResource {

    private String name;
    private Collection<ProgramResource> programs;
    private Collection<MealResource> meals;
    private Collection<DishResource> dishes;
    private Collection<WishResource> wishes;
    private Collection<GiftResource> gifts;
    public ContextResource(Context context) {
        Map<String, TextReferencable> dictionary = new HashMap<>();
        context.getGifts().forEach(ref -> dictionary.put(ref.getReferenceKey(), ref));
        context.getPrograms().forEach(ref -> dictionary.put(ref.getReferenceKey(), ref));
        context.getDishes().forEach(ref -> dictionary.put(ref.getReferenceKey(), ref));
        context.getWishes().forEach(ref -> dictionary.put(ref.getReferenceKey(), ref));

        context.getTexts().forEach(text -> dictionary.get(text.getReferenceKey()).setText(text.getContent()));

        name = context.getName();
        programs = context.getPrograms().stream().map(ProgramResource::new).collect(Collectors.toList());
        meals = context.getMeals().stream().map(MealResource::new).collect(Collectors.toList());
        dishes = context.getDishes().stream().map(DishResource::new).collect(Collectors.toList());
        wishes = context.getWishes().stream().map(WishResource::new).collect(Collectors.toList());
        gifts = context.getGifts().stream().map(GiftResource::new).collect(Collectors.toList());
    }
}
