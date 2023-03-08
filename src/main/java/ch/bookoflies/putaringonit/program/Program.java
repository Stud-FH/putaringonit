package ch.bookoflies.putaringonit.program;

import ch.bookoflies.putaringonit.context.Context;
import ch.bookoflies.putaringonit.common.TextReferencable;
import ch.bookoflies.putaringonit.context.ContextType;
import ch.bookoflies.putaringonit.profile.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class Program implements TextReferencable {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Context.class)
    @JoinColumn
    private Context context;

    @Column(name = "context_name", insertable = false, updatable = false)
    private String contextName;

    @Column(nullable = false)
    protected String title;

    @Column
    protected String imageUrl;

    @Column
    protected String caption;

    @Column
    private String startTime;

    @Column
    private String endTime;

    @Transient
    private String text;

    @Override
    public String getReferenceKey() {
        return "Program#" + id;
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
