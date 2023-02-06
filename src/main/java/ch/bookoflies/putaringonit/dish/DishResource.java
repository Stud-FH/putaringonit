package ch.bookoflies.putaringonit.dish;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DishResource {

    private long id;
    private long mealId;
    private String title;
    private String imageUrl;
    private String caption;
    private String description;

    public DishResource(Dish dish) {
        id = dish.getId();
        mealId = dish.getMealId();
        title = dish.getTitle();
        imageUrl = dish.getImageUrl();
        caption = dish.getCaption();
        description = dish.getText();
    }
}
