package ch.bookoflies.putaringonit.context;

import ch.bookoflies.putaringonit.dish.Dish;
import ch.bookoflies.putaringonit.gift.Gift;
import ch.bookoflies.putaringonit.meal.Meal;
import ch.bookoflies.putaringonit.program.Program;
import ch.bookoflies.putaringonit.text.Text;
import ch.bookoflies.putaringonit.wish.Wish;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Context {

    @Id
    private String name;

    @OneToMany(mappedBy = "context", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Gift> gifts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "context", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Program> programs = new java.util.LinkedHashSet<>();

    @OneToMany(mappedBy = "context", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Meal> meals = new java.util.LinkedHashSet<>();

    @OneToMany(mappedBy = "context", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Dish> dishes = new java.util.LinkedHashSet<>();

    @OneToMany(mappedBy = "context", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Wish> wishes = new java.util.LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy ="context", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Text> texts = new java.util.LinkedHashSet<>();
}
