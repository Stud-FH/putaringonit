package ch.bookoflies.putaringonit.text;

import ch.bookoflies.putaringonit.common.TextReferencable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TextService {

    private final TextRepository textRepository;
    private final TextLineRepository textLineRepository;

    public Text persist(String content, TextReferencable ref) {
        Text text = new Text();
        // TODO set context?
        text.setContextName(ref.getContextName());
        text.setReferenceKey(ref.getReferenceKey());
        text.setContent(content);

        text.setLines(textLineRepository.saveAll(text.getLines()));
        return textRepository.save(text);
    }

    public void clear(TextReferencable ref) {
        textRepository.deleteAllByReferenceKey(ref.getReferenceKey());
    }
}
