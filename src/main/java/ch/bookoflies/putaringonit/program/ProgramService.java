package ch.bookoflies.putaringonit.program;

import ch.bookoflies.putaringonit.text.Text;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import ch.bookoflies.putaringonit.common.ValidationUtil;
import ch.bookoflies.putaringonit.text.TextService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProgramService {

    private final ProgramRepository programRepository;
    private final TextService textService;

    public Program findById(Long id) {
        return programRepository.findById(id).orElseThrow(ErrorResponse.NotFound("unknown program id"));
    }

    public Program create(String contextName, ProgramResource data) {
        ValidationUtil.rejectEmpty(data.title, "title");

        Program program = new Program();
        program.setContextName(contextName);
        program.setTitle(data.title);
        program.setImageUrl(data.imageUrl);
        program.setCaption(data.caption);
        program = programRepository.save(program);
        Text text = textService.persist(data.description, program);
        program.setText(text.getContent());
        return program;
    }

    public Program update(long id, ProgramResource data, List<String> updates) {
        Program program = findById(id);
        List<BiConsumer<Program, ProgramResource>> handlers = updates.stream().map(this::createUpdateHandler).collect(Collectors.toList());

        handlers.forEach(handler -> handler.accept(program, data));
        return programRepository.saveAndFlush(program);
    }

    public void delete(Long id) {
        Program program = findById(id);
        this.programRepository.delete(program);
    }

    private BiConsumer<Program, ProgramResource> createUpdateHandler(String attrib) {
        switch(attrib) {
            case "title": return (program, data) -> program.setTitle(data.title);
            case "imageUrl": return (program, data) -> program.setImageUrl(data.imageUrl);
            case "caption": return (program, data) -> program.setCaption(data.caption);
            case "description": return (program, data) -> {
                textService.clear(program);
                Text text = textService.persist(data.description, program);
                program.setText(text.getContent());
            };
            default: throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("unknown attribute %s", attrib));
        }
    }
}
