package ch.bookoflies.putaringonit.dishSelection;

public class DishSelectionResource {

    public final long mealId;
    public final long dishId;
    public final String comment;

    public DishSelectionResource(DishSelection dishSelection) {
        mealId = dishSelection.getMealId();
        dishId = dishSelection.getDishId();
        comment = dishSelection.getText();
    }
}
