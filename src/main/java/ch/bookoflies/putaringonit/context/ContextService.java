package ch.bookoflies.putaringonit.context;

import ch.bookoflies.putaringonit.common.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class ContextService {

    private final ContextRepository contextRepository;

    public Context findByName(String name) {
        return contextRepository.findByName(name).orElseThrow(ErrorResponse.NotFound("not found"));
    }
}
