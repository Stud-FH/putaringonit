package ch.bookoflies.putaringonit.program;

import ch.bookoflies.putaringonit.context.Context;
import ch.bookoflies.putaringonit.context.ContextService;
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
    private final ContextService contextService;
    private final TextService textService;

    public Program findById(Long id) {
        return programRepository.findById(id).orElseThrow(ErrorResponse.NotFound("unknown program id"));
    }

    public Program create(String contextName, ProgramResource data) {
        ValidationUtil.rejectEmpty(data.getTitle(), "title");
        Context context = contextService.findByName(contextName);

        Program program = new Program();
        program.setContext(context);
        program.setContextName(contextName);
        program.setTitle(data.getTitle());
        program.setImageUrl(data.getImageUrl());
        program.setCaption(data.getCaption());
        program = programRepository.save(program);
        Text text = textService.persist(data.getDescription(), program);
        program.setText(text.getContent());
        return program;
    }

    public Program update(long id, ProgramResource data, List<String> updates) {
        Program program = findById(id);
        List<BiConsumer<Program, ProgramResource>> handlers = updates.stream().map(this::createUpdateHandler).toList();

        handlers.forEach(handler -> handler.accept(program, data));
        return programRepository.saveAndFlush(program);
    }

    public void delete(Long id) {
        Program program = findById(id);
        this.programRepository.delete(program);
    }

    private BiConsumer<Program, ProgramResource> createUpdateHandler(String attrib) {
        return switch (attrib) {
            case "title" -> (program, data) -> program.setTitle(data.getTitle());
            case "imageUrl" -> (program, data) -> program.setImageUrl(data.getImageUrl());
            case "caption" -> (program, data) -> program.setCaption(data.getCaption());
            case "description" -> (program, data) -> {
                textService.clear(program);
                Text text = textService.persist(data.getDescription(), program);
                program.setText(text.getContent());
            };
            case "startTime" -> (program, data) -> program.setStartTime(data.getStartTime());
            case "endTime" -> (program, data) -> program.setEndTime(data.getEndTime());
            default ->
                    throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("unknown attribute %s", attrib));
        };
    }
}
