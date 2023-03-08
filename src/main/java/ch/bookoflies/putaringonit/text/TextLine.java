package ch.bookoflies.putaringonit.text;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class TextLine {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Text.class)
    @JoinColumn(nullable = false)
    private Text text;

    @Column(nullable = false)
    private Integer ordinal;

    @Column
    private String line;

    @Column(nullable = false)
    private Boolean breakAfter = false;

}
