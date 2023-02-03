package ch.bookoflies.putaringonit.gift;

import java.time.LocalDateTime;

public class GiftResource {

    public final long id;
    public final String donorId;
    public final long wishId;
    public final double value;
    public final LocalDateTime created;
    public final GiftStatus status;
    public final String comment;

    public GiftResource(Gift gift) {
        id = gift.getId();
        donorId = gift.getDonorId();
        wishId = gift.getWishId();
        value = gift.getValue();
        created = gift.getCreated();
        status = gift.getStatus();
        comment = gift.getText();
    }
}
