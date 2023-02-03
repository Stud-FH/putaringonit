package ch.bookoflies.putaringonit.context;

import ch.bookoflies.putaringonit.common.ErrorResponse;
import ch.bookoflies.putaringonit.common.TextReferencable;
import ch.bookoflies.putaringonit.profile.Profile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class ContextService {

    private final ContextRepository contextRepository;

    public Context findByName(String name) {
        return contextRepository.findByName(name).orElseThrow(ErrorResponse.NotFound("not found"));
    }

    public Context findAndDressUp(String contextName) {
        Context context = findByName(contextName);
        Map<String, TextReferencable> dictionary = new HashMap<>();
        context.getGifts().forEach(ref -> dictionary.put(ref.getReferenceKey(), ref));
        context.getPrograms().forEach(ref -> dictionary.put(ref.getReferenceKey(), ref));
        context.getDishes().forEach(ref -> dictionary.put(ref.getReferenceKey(), ref));
        context.getWishes().forEach(ref -> dictionary.put(ref.getReferenceKey(), ref));

        context.getTexts().forEach(text -> dictionary.get(text.getReferenceKey()).setText(text.getContent()));

        return context;
    }

    public void dressUp(Profile profile) {
        Map<String, TextReferencable> dictionary = new HashMap<>();
        profile.getDishSelections().forEach(ref -> dictionary.put(ref.getReferenceKey(), ref));

        profile.getTexts().forEach(text -> dictionary.get(text.getReferenceKey()).setText(text.getContent()));
    }
}
