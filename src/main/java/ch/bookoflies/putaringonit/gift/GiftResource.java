package ch.bookoflies.putaringonit.gift;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GiftResource {

    private long id;
    private String donorId;
    private long wishId;
    private double value;
    private LocalDateTime created;
    private GiftStatus status;
    private String comment;

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
