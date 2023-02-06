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

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

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
        gift.setValue(data.getValue());
        gift = giftRepository.save(gift);
        Text text = textService.persist(data.getComment(), gift);
        gift.setText(text.getContent());
        return gift;
    }

    public Gift update(Wish wish, Profile donor, GiftResource data, List<String> updates) {
        Gift gift = findByWishIdAndDonorId(wish.getId(), donor.getIdentifier());
        List<BiConsumer<Gift, GiftResource>> handlers = updates.stream().map(this::createUpdateHandler).collect(Collectors.toList());

        handlers.forEach(handler -> handler.accept(gift, data));
        return giftRepository.save(gift);
    }

    public void delete(Wish wish, Profile donor) {
        Gift gift = findByWishIdAndDonorId(wish.getId(), donor.getIdentifier());
        gift.setStatus(GiftStatus.Cancelled);
        this.giftRepository.saveAndFlush(gift);
    }

    private BiConsumer<Gift, GiftResource> createUpdateHandler(String attrib) {
        switch(attrib) {
            case "value": return (gift, data) -> gift.setValue(data.getValue());
            case "status": return (gift, data) -> gift.setStatus(data.getStatus());
            case "comment": return (gift, data) -> {
                textService.clear(gift);
                Text text = textService.persist(data.getComment(), gift);
                gift.setText(text.getContent());
            };
            default: throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("unknown attribute %s", attrib));
        }
    }
}
