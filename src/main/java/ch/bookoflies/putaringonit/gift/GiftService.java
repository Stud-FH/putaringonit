package ch.bookoflies.putaringonit.gift;

import ch.bookoflies.putaringonit.profile.Profile;
import ch.bookoflies.putaringonit.text.Text;
import ch.bookoflies.putaringonit.wish.Wish;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import ch.bookoflies.putaringonit.common.ValidationUtil;
import ch.bookoflies.putaringonit.text.TextService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@AllArgsConstructor
@Service
public class GiftService {

    private final GiftRepository giftRepository;
    private final TextService textService;

    public Gift findByWishIdAndDonorId(long wishId, String donorId) {
        return giftRepository.findByWishIdAndDonorId(wishId, donorId).orElseThrow(ErrorResponse.NotFound("gift not found"));
    }

    public Gift create(GiftResource data, Wish wish, Profile donor) {
        if (giftRepository.existsByWishIdAndDonorId(wish.getId(), donor.getIdentifier()))
            throw ErrorResponse.Conflict("gift already exists").get();
        ValidationUtil.rejectNonPositive(data.getValue(), "value");

        Gift gift = new Gift();
        gift.setContext(wish.getContext());
        gift.setContextName(wish.getContextName());
        gift.setDonorId(donor.getIdentifier());
        gift.setWishId(wish.getId());
        gift.setCreated(LocalDateTime.now());
        gift.setHandoverOption(wish.getIsPhysical()? data.getHandoverOption() : HandoverOption.Monetary);
        gift.setStatus(GiftStatus.Reserved);
        gift.setValue(data.getValue());
        gift = giftRepository.save(gift);
        Text text = textService.persist(data.getComment(), gift);
        gift.setText(text.getContent());
        return gift;
    }

    public Gift update(Wish wish, Profile donor, GiftResource data, List<String> updates) {
        Gift gift = findByWishIdAndDonorId(wish.getId(), donor.getIdentifier());
        List<BiConsumer<Gift, GiftResource>> handlers = updates.stream().map(this::createUpdateHandler).toList();

        handlers.forEach(handler -> handler.accept(gift, data));
        return giftRepository.save(gift);
    }

    public Gift delete(Wish wish, Profile donor) {
        Gift gift = findByWishIdAndDonorId(wish.getId(), donor.getIdentifier());
        this.textService.clear(gift);
        this.giftRepository.delete(gift);
        return gift;
    }

    private BiConsumer<Gift, GiftResource> createUpdateHandler(String attrib) {
        return switch (attrib) {
//            case "value": return (gift, data) -> gift.setValue(data.getValue());
            case "handoverOption" -> (gift, data) -> gift.setHandoverOption(data.getHandoverOption());
            case "status" -> (gift, data) -> gift.setStatus(data.getStatus());
            case "comment" -> (gift, data) -> {
                textService.clear(gift);
                Text text = textService.persist(data.getComment(), gift);
                gift.setText(text.getContent());
            };
            default ->
                    throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("unknown attribute %s", attrib));
        };
    }
}
