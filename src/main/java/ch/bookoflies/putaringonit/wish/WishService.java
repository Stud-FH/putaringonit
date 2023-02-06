package ch.bookoflies.putaringonit.wish;

import ch.bookoflies.putaringonit.context.Context;
import ch.bookoflies.putaringonit.context.ContextService;
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
    private final ContextService contextService;
    private final TextService textService;

    public Wish findById(Long id) {
        return wishRepository.findById(id).orElseThrow(ErrorResponse.NotFound("unknown wish id"));
    }

    public Wish create(String contextName, WishResource data) {
        ValidationUtil.rejectEmpty(data.getTitle(), "title");
        ValidationUtil.rejectNull(data.getUnit(), "unit");
        ValidationUtil.rejectNonPositive(data.getValue(), "value");
        Context context = contextService.findByName(contextName);

        Wish wish = new Wish();
        wish.setContext(context);
        wish.setContextName(contextName);
        wish.setTitle(data.getTitle());
        wish.setImageUrl(data.getImageUrl());
        wish.setCaption(data.getCaption());
        wish.setUnit(data.getUnit());
        wish.setValue(data.getValue());
        wish = wishRepository.save(wish);
        Text text = textService.persist(data.getDescription(), wish);
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
            case "title": return (wish, data) -> wish.setTitle(data.getTitle());
            case "imageUrl": return (wish, data) -> wish.setImageUrl(data.getImageUrl());
            case "caption": return (wish, data) -> wish.setCaption(data.getCaption());
            case "unit": return (wish, data) -> wish.setUnit(data.getUnit());
            case "value": return (wish, data) -> wish.setValue(data.getValue());
            case "description": return (wish, data) -> {
                textService.clear(wish);
                Text text = textService.persist(data.getDescription(), wish);
                wish.setText(text.getContent());
            };
            default: throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("unknown attribute %s", attrib));
        }
    }
}
