package ch.bookoflies.putaringonit.account;

import ch.bookoflies.putaringonit.context.ContextResource;
import ch.bookoflies.putaringonit.profile.ProfileResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class AccountResource {

    private long id;
    private ContextResource context;
    private Clearance clearance;
    private String token;
    private Collection<ProfileResource> profiles;

    public AccountResource(Account account) {
        id = account.getId();
        context = new ContextResource(account.getContext());
        clearance = account.getClearance();
        token = account.getToken();
        profiles = account.getProfiles().stream().map(ProfileResource::new).collect(Collectors.toList());
    }
}
