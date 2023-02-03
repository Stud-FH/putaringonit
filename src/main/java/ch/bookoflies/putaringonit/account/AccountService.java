package ch.bookoflies.putaringonit.account;

import ch.bookoflies.putaringonit.profile.Profile;
import ch.bookoflies.putaringonit.profile.ProfileService;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ProfileService profileService;
    private final AccountLoginCodeRepository accountLoginCodeRepository;
    private final AccountLoginPasswordRepository accountLoginPasswordRepository;

    public Account loginWithCode(AccountLoginCode data) {
        return this.accountLoginCodeRepository.findByCode(data.getCode()).orElseThrow(ErrorResponse.Unauthorized("invalid code")).getAccount();
    }

    public Account loginWithToken(String token) {
        return this.accountRepository.findByToken(token).orElseThrow(ErrorResponse.Unauthorized("invalid token"));
    }

    public Account loginWithPassword(AccountLoginPassword data) {
        AccountLoginPassword login = this.accountLoginPasswordRepository.findByUsername(data.getUsername()).orElseThrow(ErrorResponse.NotFound(String.format("account with username %s not found", data.getUsername())));
        login.matchOrThrow(data.getPassword(), ErrorResponse.Unauthorized("password mismatch"));
        return login.getAccount();
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

    public void invalidateToken(String token) {
        this.accountRepository.findByToken(token).ifPresent(account -> account.setToken(null));
    }

}
