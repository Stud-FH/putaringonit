package ch.bookoflies.putaringonit.account;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class AccountLoginCode {

    @Id
    private String code;

    @ManyToOne(optional = false)
    private Account account;
}
