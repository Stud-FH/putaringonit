package ch.bookoflies.putaringonit.account;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class AccountLoginCode {

    @Id
    private String code;

    @ManyToOne(optional = false)
    private Account account;
}
