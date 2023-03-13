package ch.bookoflies.putaringonit.invitation;

import ch.bookoflies.putaringonit.context.Context;
import ch.bookoflies.putaringonit.profile.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class Invitation {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Context.class)
    @JoinColumn
    private Context context;

    @Column(name = "context_name", insertable = false, updatable = false)
    private String contextName;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Profile profile;

    @Column(name = "profile_identifier", insertable = false, updatable = false)
    private String profileId;

    @Column(name = "program_id")
    private Long programId;

    @Column
    private Boolean accepted;
}
