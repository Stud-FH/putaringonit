package ch.bookoflies.putaringonit.profile;

import ch.bookoflies.putaringonit.common.StringPrefixedSequenceIdGenerator;
import ch.bookoflies.putaringonit.invitation.Invitation;
import ch.bookoflies.putaringonit.dishSelection.DishSelection;
import ch.bookoflies.putaringonit.text.Text;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @GenericGenerator(
            name = "profile_seq",
            strategy = "ch.bookoflies.putaringonit.common.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "Profile_"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d") })
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "profile", orphanRemoval = true)
    private Set<DishSelection> dishSelections = new java.util.LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "profile", orphanRemoval = true)
    private Set<Invitation> invitations = new java.util.LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy ="profile", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Text> texts = new java.util.LinkedHashSet<>();

}
