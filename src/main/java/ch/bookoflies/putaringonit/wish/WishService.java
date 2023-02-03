package ch.bookoflies.putaringonit.wish;

import ch.bookoflies.putaringonit.text.Text;
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
public class WishService {

    private final WishRepository wishRepository;
    private final TextService textService;

    public Wish findById(Long id) {
        return wishRepository.findById(id).orElseThrow(ErrorResponse.NotFound("unknown wish id"));
    }

    public Wish create(String contextName, WishResource data) {
        ValidationUtil.rejectEmpty(data.title, "title");
        ValidationUtil.rejectNull(data.unit, "unit");
        ValidationUtil.rejectNonPositive(data.value, "value");

        Wish wish = new Wish();
        wish.setContextName(contextName);
        wish.setTitle(data.title);
        wish.setImageUrl(data.imageUrl);
        wish.setCaption(data.caption);
        wish.setUnit(data.unit);
        wish.setValue(data.value);
        wish = wishRepository.save(wish);
        Text text = textService.persist(data.description, wish);
        wish.setText(text.getContent());
        return wish;
    }

    public Wish update(long id, WishResource data, List<String> updates) {
        Wish wish = findById(id);
        List<BiConsumer<Wish, WishResource>> handlers = updates.stream().map(this::createUpdateHandler).collect(Collectors.toList());

        handlers.forEach(handler -> handler.accept(wish, data));
        return wishRepository.saveAndFlush(wish);
    }

    public void delete(long id) {
        this.wishRepository.deleteById(id);
    }

    private BiConsumer<Wish, WishResource> createUpdateHandler(String attrib) {
        switch(attrib) {
            case "title": return (wish, data) -> wish.setTitle(data.title);
            case "imageUrl": return (wish, data) -> wish.setImageUrl(data.imageUrl);
            case "caption": return (wish, data) -> wish.setCaption(data.caption);
            case "unit": return (wish, data) -> wish.setUnit(data.unit);
            case "value": return (wish, data) -> wish.setValue(data.value);
            case "description": return (wish, data) -> {
                textService.clear(wish);
                Text text = textService.persist(data.description, wish);
                wish.setText(text.getContent());
            };
            default: throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("unknown attribute %s", attrib));
        }
    }
}
