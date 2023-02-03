package ch.bookoflies.putaringonit.context;

import ch.bookoflies.putaringonit.dish.DishResource;
import ch.bookoflies.putaringonit.gift.GiftResource;
import ch.bookoflies.putaringonit.meal.MealResource;
import ch.bookoflies.putaringonit.program.ProgramResource;
import ch.bookoflies.putaringonit.wish.WishResource;

import java.util.Collection;
import java.util.stream.Collectors;

public class ContextResource {

    public final String name;
    public final Collection<ProgramResource> programs;
    public final Collection<MealResource> meals;
    public final Collection<DishResource> dishes;
    public final Collection<WishResource> wishes;
    public final Collection<GiftResource> gifts;
    public ContextResource(Context context) {
        name = context.getName();
        programs = context.getPrograms().stream().map(ProgramResource::new).collect(Collectors.toList());
        meals = context.getMeals().stream().map(MealResource::new).collect(Collectors.toList());
        dishes = context.getDishes().stream().map(DishResource::new).collect(Collectors.toList());
        wishes = context.getWishes().stream().map(WishResource::new).collect(Collectors.toList());
        gifts = context.getGifts().stream().map(GiftResource::new).collect(Collectors.toList());
    }
}
