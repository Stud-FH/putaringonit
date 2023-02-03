package ch.bookoflies.putaringonit.program;

public class ProgramResource {

    public final long id;
    public final String title;
    public final String imageUrl;
    public final String caption;
    public final String startTime;
    public final String endTime;
    public final String description;

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
