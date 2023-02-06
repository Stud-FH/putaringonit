package ch.bookoflies.putaringonit.profile;

import ch.bookoflies.putaringonit.common.ErrorResponse;
import ch.bookoflies.putaringonit.common.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public Profile findById(String id) {
        return profileRepository.findById(id).orElseThrow(ErrorResponse.NotFound(String.format("profile with id %s not found", id)));
    }

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public Profile create(ProfileResource data) {
        ValidationUtil.rejectEmpty(data.getFirstName(), "firstName");
        ValidationUtil.rejectEmpty(data.getFamilyName(), "familyName");
        // TODO validate
        Profile profile = new Profile();
        profile.setFirstName(profile.getFirstName());
        profile.setFamilyName(data.getFamilyName());
        profile.setNickname(data.getNickname());
        profile.setBlockEmail(data.getBlockEmail());
        profile.setEmail(data.getEmail());
        return this.profileRepository.save(profile);
    }

    public Profile update(String id, ProfileResource data, List<String> updates) {
        Profile profile = findById(id);
        List<BiConsumer<Profile, ProfileResource>> handlers = updates.stream().map(this::createUpdateHandler).collect(Collectors.toList());

        // run if no errors
        handlers.forEach(handler -> handler.accept(profile, data));
        return profileRepository.saveAndFlush(profile);
    }


    public void delete(String id) {
        this.profileRepository.deleteById(id);
    }

    private BiConsumer<Profile, ProfileResource> createUpdateHandler(String attrib) {
        switch(attrib) {
            case "firstName": return (profile, data) -> profile.setFirstName(data.getFirstName());
            case "familyName": return (profile, data) -> profile.setFamilyName(data.getFamilyName());
            case "nickname": return (profile, data) -> profile.setNickname(data.getNickname());
            case "email": return (profile, data) -> profile.setEmail(data.getEmail());
            case "blockEmail": return (profile, data) -> profile.setBlockEmail(data.getBlockEmail());
            default: throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("unknown attribute %s", attrib));
        }
    }

}
