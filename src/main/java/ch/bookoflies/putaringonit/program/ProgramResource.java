package ch.bookoflies.putaringonit.program;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProgramResource {

    private long id;
    private String title;
    private String imageUrl;
    private String caption;
    private String startTime;
    private String endTime;
    private String description;

    public ProgramResource(Program program) {
        id = program.getId();
        title = program.getTitle();
        imageUrl = program.getImageUrl();
        caption = program.getCaption();
        startTime = program.getStartTime();
        endTime = program.getEndTime();
        description = program.getText();
    }
}
