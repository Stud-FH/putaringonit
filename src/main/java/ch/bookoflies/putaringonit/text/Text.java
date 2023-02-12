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
    private Context context;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Profile.class)
    private Profile profile;

    @Column(nullable = false)
    private String referenceKey;

    @JsonIgnore
    @OneToMany(mappedBy = "text", fetch = FetchType.EAGER, orphanRemoval = true)
    private Collection<TextLine> lines;

    public String getContent() {
        return lines.stream()
                .sorted(Comparator.comparingInt(TextLine::getOrdinal))
                .map(line -> line.getBreakAfter() ? line.getLine() +"\n" : line.getLine())
                .collect(Collectors.joining());
    }
}
