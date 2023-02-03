package ch.bookoflies.putaringonit.dish;

public class DishResource {

    public final long id;
    public final long mealId;
    public final String title;
    public final String imageUrl;
    public final String caption;
    public final String description;

    public DishResource(Dish dish) {
        id = dish.getId();
        mealId = dish.getMealId();
        title = dish.getTitle();
        imageUrl = dish.getImageUrl();
        caption = dish.getCaption();
        description = dish.getText();
    }
}
