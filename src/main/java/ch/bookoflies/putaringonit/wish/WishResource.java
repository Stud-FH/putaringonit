package ch.bookoflies.putaringonit.wish;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WishResource {

    private long id;
    private String title;
    private String imageUrl;
    private String caption;
    private ContributionUnit unit;
    private Double value;
    private String description;

    public WishResource(Wish wish) {
        id = wish.getId();
        title = wish.getTitle();
        imageUrl = wish.getImageUrl();
        caption = wish.getCaption();
        unit = wish.getUnit();
        value = wish.getValue();
        description = wish.getText();
    }
}
