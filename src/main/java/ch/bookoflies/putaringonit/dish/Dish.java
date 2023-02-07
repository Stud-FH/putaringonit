package ch.bookoflies.putaringonit.dish;

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
public class Dish implements TextReferencable {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Context.class)
    @JoinColumn
    private Context context;

    @Column(name = "context_name", insertable = false, updatable = false)
    private String contextName;

    @Column(name = "meal_id")
    private Long mealId;

    @Column(nullable = false)
    protected String title;

    @Column
    protected String imageUrl;

    @Column
    protected String caption;

    @Transient
    private String text;

    @Override
    public String getReferenceKey() {
        return "Dish#" + id;
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
