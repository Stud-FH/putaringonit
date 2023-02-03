package ch.bookoflies.putaringonit.account;

import ch.bookoflies.putaringonit.context.ContextResource;
import ch.bookoflies.putaringonit.profile.ProfileResource;

import java.util.Collection;
import java.util.stream.Collectors;

public class AccountResource {

    public final long id;
    public final String contextName;
    public final ContextResource context;
    public final Clearance clearance;
    public final String token;
    public final Collection<ProfileResource> profiles;

    public AccountResource(Account account) {
        id = account.getId();
        contextName = account.getContextName();
        context = new ContextResource(account.getContext());
        clearance = account.getClearance();
        token = account.getToken();
        profiles = account.getProfiles().stream().map(ProfileResource::new).collect(Collectors.toList());
    }
}
