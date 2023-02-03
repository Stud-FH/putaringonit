package ch.bookoflies.putaringonit.wish;

public class WishResource {

    public final long id;
    public final String title;
    public final String imageUrl;
    public final String caption;
    public final ContributionUnit unit;
    public final Double value;
    public final String description;

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
