package ch.bookoflies.putaringonit.text;

import ch.bookoflies.putaringonit.common.TextReferencable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@Service
@Transactional
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

        text = textRepository.save(text);
        text.setLines(textLineRepository.saveAll(parseContent(content, text)));
        return text;
    }

    public Collection<TextLine> parseContent(String content, Text text) {
        Collection<TextLine> lines = new ArrayList<>();
        int ordinal = 0;
        while (!content.isEmpty()) {
            int i = ordinal++;
            String head = content.length() >= 255? content.substring(0, 255) : content;
            int newlineIdx = head.indexOf("\n");
            boolean hasLinebreak = newlineIdx >= 0;

            TextLine textLine = new TextLine();
            textLine.setText(text);
            textLine.setOrdinal(i);
            textLine.setBreakAfter(hasLinebreak);
            textLine.setLine(hasLinebreak? head.substring(0, newlineIdx) : head);
            lines.add(textLine);
            content = content.substring(hasLinebreak? newlineIdx + "\n".length() : head.length());
        }
        return lines;
    }

    public void clear(TextReferencable ref) {
        textRepository.deleteAllByReferenceKey(ref.getReferenceKey());
    }
}
