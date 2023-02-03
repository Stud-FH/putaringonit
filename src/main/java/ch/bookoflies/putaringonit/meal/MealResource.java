package ch.bookoflies.putaringonit.meal;

public class MealResource {

    public final long id;
    public final long programId;
    public final String title;
    public final String imageUrl;
    public final String caption;
    public final String description;

    public MealResource(Meal meal) {
        id = meal.getId();
        programId = meal.getProgramId();
        title = meal.getTitle();
        imageUrl = meal.getImageUrl();
        caption = meal.getCaption();
        description = meal.getText();
    }
}
