package ch.bookoflies.putaringonit.invitation;

import ch.bookoflies.putaringonit.profile.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Invitation {

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

    @Column(name = "program_id", insertable = false, updatable = false)
    private Long programId;

    @Column
    private Boolean accepted;
}
