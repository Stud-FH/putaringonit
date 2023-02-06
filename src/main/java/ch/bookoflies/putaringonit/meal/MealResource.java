package ch.bookoflies.putaringonit.meal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MealResource {

    private long id;
    private long programId;
    private String title;
    private String imageUrl;
    private String caption;
    private String description;

    public MealResource(Meal meal) {
        id = meal.getId();
        programId = meal.getProgramId();
        title = meal.getTitle();
        imageUrl = meal.getImageUrl();
        caption = meal.getCaption();
        description = meal.getText();
    }
}
