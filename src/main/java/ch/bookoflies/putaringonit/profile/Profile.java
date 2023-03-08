package ch.bookoflies.putaringonit.profile;

import ch.bookoflies.putaringonit.invitation.Invitation;
import ch.bookoflies.putaringonit.dishSelection.DishSelection;
import ch.bookoflies.putaringonit.text.Text;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Profile {

    @Id@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_generator")
    @SequenceGenerator(name = "profile_generator", sequenceName = "profile_seq", allocationSize = 1)
    private String identifier;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String familyName;

    @Column(length = 50)
    private String nickname;

    @Column(length = 50)
    private String email;

    @Column
    private Boolean blockEmail;

    @Column(length = 50)
    private String phoneNumber;

    @Column
    private Boolean blockPhoneNumber;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "profile", orphanRemoval = true)
    private Set<DishSelection> dishSelections = new java.util.LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "profile", orphanRemoval = true)
    private Set<Invitation> invitations = new java.util.LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy ="profile", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Text> texts = new java.util.LinkedHashSet<>();

}
