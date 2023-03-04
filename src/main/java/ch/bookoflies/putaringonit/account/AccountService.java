package ch.bookoflies.putaringonit.account;

import ch.bookoflies.putaringonit.profile.Profile;
import ch.bookoflies.putaringonit.profile.ProfileService;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ProfileService profileService;
    private final AccountLoginCodeRepository accountLoginCodeRepository;
    private final AccountLoginPasswordRepository accountLoginPasswordRepository;

    public Account loginWithCode(AccountLoginCode data) {
        Account account = this.accountLoginCodeRepository.findByCode(data.getCode()).orElseThrow(ErrorResponse.Unauthorized("invalid code")).getAccount();
        String token = account.getToken();
        if (token == null || token.isEmpty()) {
            account.setToken(generateToken());
            account = accountRepository.save(account);
        }
        return account;
    }

    public Account loginWithToken(String token) {
        return this.accountRepository.findByToken(token).orElseThrow(ErrorResponse.Unauthorized("invalid token"));
    }

    public Account loginWithPassword(AccountLoginPassword data) {
        AccountLoginPassword login = this.accountLoginPasswordRepository.findByUsername(data.getUsername()).orElseThrow(ErrorResponse.NotFound(String.format("account with username %s not found", data.getUsername())));
        login.matchOrThrow(data.getPassword(), ErrorResponse.Unauthorized("password mismatch"));
        Account account = login.getAccount();String token = account.getToken();
        if (token == null || token.isEmpty()) {
            account.setToken(generateToken());
            account = accountRepository.save(account);
        }
        return account;
    }

    public Profile profileLogin(String token, String profileId) {
        Account account = loginWithToken(token);
        if (account.getClearance().equals(Clearance.Admin)) {
            return profileService.findById(profileId);
        }
        return account.getProfiles().stream().filter(p -> p.getIdentifier().equals(profileId)).findAny().orElseThrow(ErrorResponse.Unauthorized("invalid token"));
    }

    public Boolean existsUsername(String username) {
        return this.accountLoginPasswordRepository.existsByUsername(username);
    }

    public String generateToken() {
        SecureRandom r = new SecureRandom();
        return String.format("%05d-%05d-%05d-%05d", Math.abs(r.nextInt() % 100000), Math.abs(r.nextInt() % 100000), Math.abs(r.nextInt() % 100000), Math.abs(r.nextInt() % 100000));
    }

    public void invalidateToken(String token) {
        this.accountRepository.findByToken(token).ifPresent(account -> account.setToken(null));
    }

}
