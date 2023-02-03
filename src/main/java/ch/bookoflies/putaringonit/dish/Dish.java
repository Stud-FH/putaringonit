package ch.bookoflies.putaringonit.dish;

import ch.bookoflies.putaringonit.context.Context;
import ch.bookoflies.putaringonit.common.TextReferencable;
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
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Context.class)
    @JoinColumn(nullable = false)
    private Context context;

    @Column(name = "context_name", insertable = false, updatable = false)
    private String contextName;

    @Column(name = "meal_id", insertable = false, updatable = false)
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

}
