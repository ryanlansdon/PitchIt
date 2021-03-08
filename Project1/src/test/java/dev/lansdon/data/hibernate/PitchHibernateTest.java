package dev.lansdon.data.hibernate;

import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.lansdon.models.Genre;
import dev.lansdon.models.Pitch;
import dev.lansdon.models.Story;
import dev.lansdon.models.StoryType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class PitchHibernateTest {
    public static PitchHibernate pitchHibernate;
    public static GenreHibernate genreHibernate;
    public static StoryTypeHibernate storyTypeHibernate;

    @BeforeAll
    public static void setup() {
        pitchHibernate = new PitchHibernate();
        genreHibernate = new GenreHibernate();
        storyTypeHibernate = new StoryTypeHibernate();
    }

    @Test
    public void testAddPitch() {
        Story s = new Story();
        s.setAuthorInfo("This is a promising new author by the name of Ryan Lansdon");
        s.setDescription("A thrilling crime drama");
        Genre g = genreHibernate.getByName("Horror");
        s.setGenre(g);
        s.setTagline("Scary Crime Drama");
        s.setTitle("Crime City");
        StoryType st = storyTypeHibernate.getByName("Article");
        s.setType(st);
        Pitch p = new Pitch();
        p.setStory(s);
        p = pitchHibernate.add(p);
        assertTrue(p.equals(pitchHibernate.getById(p.getId())));
    }

    @Test
    public void testGetByGenre() {
        Genre g = genreHibernate.getByName("Horror");
        Set<Pitch> pitches = pitchHibernate.getPitchesByGenre(g);

        for (Pitch p : pitches) {
            assertTrue(g.equals(p.getStory().getGenre()));
        }
    }
}
