package ch.bookoflies.putaringonit.wish;

import ch.bookoflies.putaringonit.context.Context;
import ch.bookoflies.putaringonit.common.TextReferencable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Wish implements TextReferencable {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Context.class)
    @JoinColumn(nullable = false)
    private Context context;

    @Column(name = "context_name", insertable = false, updatable = false)
    private String contextName;

    @Column(nullable = false)
    private String title;

    @Column
    private String imageUrl;

    @Column
    private String caption;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContributionUnit unit;

    @Column(nullable = false)
    private Double value;

    @Transient
    private String text;

    @Override
    public String getReferenceKey() {
        return "Wish#" + id;
    }
}
