package ch.bookoflies.putaringonit.dishSelection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DishSelectionResource {

    private long mealId;
    private long dishId;
    private String comment;

    public DishSelectionResource(DishSelection dishSelection) {
        mealId = dishSelection.getMealId();
        dishId = dishSelection.getDishId();
        comment = dishSelection.getText();
    }
}
