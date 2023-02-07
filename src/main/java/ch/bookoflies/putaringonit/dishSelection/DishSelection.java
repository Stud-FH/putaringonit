package ch.bookoflies.putaringonit.dishSelection;

import ch.bookoflies.putaringonit.common.TextReferencable;
import ch.bookoflies.putaringonit.context.Context;
import ch.bookoflies.putaringonit.context.ContextType;
import ch.bookoflies.putaringonit.profile.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class DishSelection implements TextReferencable {

    @Id
    @GeneratedValue
    private Long id;

    // parent
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Profile profile;

    @Column(name = "profile_identifier", insertable = false, updatable = false)
    private String profileId;

    @Column(name = "meal_id")
    private Long mealId;

    @Column(name = "dish_id")
    private Long dishId;

    @Transient
    private String text;

    @Override
    public String getReferenceKey() {
        return "DishSelection#" + id;
    }

    @Override
    public String getContextName() {
        return profileId;
    }

    @Override
    public ContextType getContextType() {
        return ContextType.Profile;
    }

    @Override
    public Context getContext() {
        throw new UnsupportedOperationException();
    }
}
