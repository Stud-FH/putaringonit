package ch.bookoflies.putaringonit.text;

import ch.bookoflies.putaringonit.common.TextReferencable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@Service
public class TextService {

    private final TextRepository textRepository;
    private final TextLineRepository textLineRepository;

    public Text persist(String content, TextReferencable ref) {

        Text text = new Text();
        switch (ref.getContextType()) {
            case Context: text.setContext(ref.getContext()); break;
            case Profile: text.setProfile(ref.getProfile()); break;
        }
        text.setReferenceKey(ref.getReferenceKey());
        text.setContent(content);

        Collection<TextLine> lines = text.getLines();
        text.setLines(new ArrayList<>());
        Text persisted = textRepository.save(text);

        lines.forEach(line -> line.setText(persisted));
        persisted.setLines(textLineRepository.saveAll(text.getLines()));
        return persisted;
    }

    public void clear(TextReferencable ref) {
        textRepository.deleteAllByReferenceKey(ref.getReferenceKey());
    }
}
