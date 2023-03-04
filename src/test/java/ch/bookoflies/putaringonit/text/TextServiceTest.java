package ch.bookoflies.putaringonit.text;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TextServiceTest {

    TextService textService;

    @BeforeEach
    void setUp() {
    textService = new TextService(null, null);
    }

    @Test
    void parseContent() {
        String s = "Für ein reibungsfreies Eheleben wünschen wir uns ein bequemes Entsorgungssystem mit Grünkompost-, Altglas- und Blechbehälter. Zusätzlich bietet es Platz für Reinigungsutensilien.\\n\\nDies wär der Link für die Müllex Küchenschublade:\\n\\nhttps://www.doitgarden.ch/de/p/674453700000/muellex-abfall-auszugsystem-comfort-35-5-4-4-l?gclid=Cj0KCQiArsefBhCbARIsAP98hXS6xW0LsGXTgeExyw-Buw0r3oQ0BdzzJUk9nxt4injC9pvx0eGqjXIaAgxIEALw_wcB&gclsrc=aw.ds";
        Collection<TextLine> lines = textService.parseContent(s, new Text());
    }
}