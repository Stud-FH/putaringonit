package ch.bookoflies.putaringonit.gift;

import ch.bookoflies.putaringonit.context.Context;
import ch.bookoflies.putaringonit.common.TextReferencable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class Gift implements TextReferencable {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Context.class)
    @JoinColumn(nullable = false)
    private Context context;

    @Column(name = "context_name", insertable = false, updatable = false)
    private String contextName;

    @Column(name = "donor_id", insertable = false, updatable = false)
    private String donorId;

    @Column(name = "wish_id", insertable = false, updatable = false)
    private Long wishId;

    @Column(nullable = false)
    private Double value;

    @Column
    private LocalDateTime created = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GiftStatus status = GiftStatus.Reserved;

    @Transient
    private String text;

    @Override
    public String getReferenceKey() {
        return "Gift#" + id;
    }
}
