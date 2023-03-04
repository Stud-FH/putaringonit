package ch.bookoflies.putaringonit.wish;

import ch.bookoflies.putaringonit.context.Context;
import ch.bookoflies.putaringonit.common.TextReferencable;
import ch.bookoflies.putaringonit.context.ContextType;
import ch.bookoflies.putaringonit.profile.Profile;
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
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Context.class)
    @JoinColumn
    private Context context;

    @Column(name = "context_name", insertable = false, updatable = false)
    private String contextName;

    @Column(nullable = false)
    private String title;

    @Column
    private String imageUrl;

    @Column
    private String productUrl;

    @Column
    private String caption;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContributionUnit unit;

    @Column
    private Double value;

    @Column
    private Boolean hideProgress;

    @Transient
    private String text;

    @Override
    public String getReferenceKey() {
        return "Wish#" + id;
    }

    @Override
    public ContextType getContextType() {
        return ContextType.Context;
    }

    @Override
    public Profile getProfile() {
        throw new UnsupportedOperationException();
    }
}
