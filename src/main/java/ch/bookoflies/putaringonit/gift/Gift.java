package ch.bookoflies.putaringonit.gift;

import ch.bookoflies.putaringonit.context.Context;
import ch.bookoflies.putaringonit.common.TextReferencable;
import ch.bookoflies.putaringonit.context.ContextType;
import ch.bookoflies.putaringonit.profile.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class Gift implements TextReferencable {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Context.class)
    @JoinColumn
    private Context context;

    @Column(name = "context_name", insertable = false, updatable = false)
    private String contextName;

    @Column(name = "donor_id")
    private String donorId;

    @Column(name = "wish_id")
    private Long wishId;

    @Column(nullable = false)
    private Double value;

    @Column
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HandoverOption handoverOption;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GiftStatus status;

    @Transient
    private String text;

    @Override
    public String getReferenceKey() {
        return "Gift#" + id;
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
