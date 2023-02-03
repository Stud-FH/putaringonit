package ch.bookoflies.putaringonit.text;

import ch.bookoflies.putaringonit.context.Context;
import ch.bookoflies.putaringonit.profile.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class Text {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Context.class)
    @JoinColumn(name = "context_name", insertable = false, updatable = false)
    private Context context;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Profile.class)
    @JoinColumn(name = "context_name", insertable = false, updatable = false, referencedColumnName = "identifier")
    private Profile profile;

    @Column(name = "context_name", insertable = false, updatable = false)
    private String contextName; // refers to context or profile

    @Column(nullable = false)
    private String referenceKey;

    @JsonIgnore
    @OneToMany(mappedBy = "text", fetch = FetchType.EAGER, orphanRemoval = true)
    protected Collection<TextLine> lines = new ArrayList<>();

    public String getContent() {
        return lines.stream()
                .sorted(Comparator.comparingInt(TextLine::getOrdinal))
                .map(line -> line.getBreakAfter() ? line.getLine() +"\n" : line.getLine())
                .collect(Collectors.joining());
    }

    public void setContent(String content) {
        lines = new ArrayList<>();
        int ordinal = 0;
        while (!content.isEmpty()) {
            int i = ordinal++;
            String head = content.length() >= 256? content.substring(0, 256) : content;
            int newlineIdx = head.indexOf("\n");
            boolean hasLinebreak = newlineIdx >= 0;

            TextLine textLine = new TextLine();
            textLine.setOrdinal(i);
            textLine.setBreakAfter(hasLinebreak);
            textLine.setLine(hasLinebreak? head.substring(0, newlineIdx) : head);
            lines.add(textLine);
            content = content.substring(hasLinebreak? newlineIdx + "\n".length() : head.length());
        }
    }

}
