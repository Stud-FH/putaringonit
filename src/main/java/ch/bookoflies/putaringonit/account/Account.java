package ch.bookoflies.putaringonit.account;

import ch.bookoflies.putaringonit.context.Context;
import ch.bookoflies.putaringonit.profile.Profile;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    // Account is the only entity to deliver the context
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Context.class)
    @JoinColumn
    private Context context;

    @Column(name = "context_name", insertable = false, updatable = false)
    private String contextName;

    @Enumerated(EnumType.STRING)
    private Clearance clearance = Clearance.Guest;

    @Column
    private String token;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_profiles",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "profile_identifier", referencedColumnName = "identifier"))
    private Set<Profile> profiles = new java.util.LinkedHashSet<>();

    public void matchOrThrow(Clearance clearance, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (this.clearance.ordinal() > clearance.ordinal()) {
            throw exceptionSupplier.get();
        }
    }

    public List<String> getProfileIds() {
        return profiles.stream().map(Profile::getIdentifier).collect(Collectors.toList());
    }


}
