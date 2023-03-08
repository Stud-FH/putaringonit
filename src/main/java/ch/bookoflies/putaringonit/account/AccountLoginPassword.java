package ch.bookoflies.putaringonit.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.*;
import java.util.function.Supplier;

@Getter
@Setter
@Entity
public class AccountLoginPassword {

//    private static PasswordEncoder passwordEncoder;

    @Id
    private String username;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private transient String password;

    @Column(length = 50)
    @JsonIgnore
    private String passwordEncoded;

    @ManyToOne(optional = false)
    private Account account;

    public String getPasswordEncoded() {
//        if (this.passwordEncoded == null && this.password != null) {
//            this.passwordEncoded = getPasswordEncoder().encode(this.password);
//        }
        return this.passwordEncoded;
    }

    public void matchOrThrow(String password, Supplier<? extends RuntimeException> exceptionSupplier) {
//        if (!getPasswordEncoder().matches(password, getPasswordEncoded())) {
//            throw exceptionSupplier.get();
//        }
    }

//    protected static PasswordEncoder getPasswordEncoder() {
//        if (passwordEncoder == null) {
//            passwordEncoder = new BCryptPasswordEncoder(16);
//        }
//        return passwordEncoder;
//    }
}
