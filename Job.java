import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

/**
 * Describes a sampling job with successive notes.
 */
public class Job {
    /**
     * Interval between notes in semitones.
     */
    public enum Interval {
        ONE(1),
        THREE(3),
        SIX(6),
        TWELVE(12);

        private final int value;

        private Interval(int value) {
            this.value = value;
        }

        /**
         * Gets the ordinal value of this enum instance.
         */
        public int getValue() {
            return value;
        }
    }

    /**
     * Constructs a job with default values.
     */
    public Job(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.fromNote = 40;
        this.toNote = 120;
        this.interval = Interval.SIX;
    }

    /**
     * Gets the name of the job.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the unique identifier of the job.
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Gets the first note of the range.
     */
    public int getFromNote() {
        return this.fromNote;
    }

    /**
     * Sets the first note of the range.
     */
    public void setFromNote(int note) {
        if (note < 0 || note > 127) {
            throw new IllegalArgumentException("Note must be 0...127");
        }
        this.fromNote = note;
    }

    /**
     * Gets the last note of the range.
     */
    public int getToNote() {
        return this.toNote;
    }

    /**
     * Sets the last note of the range.
     */
    public void setToNote(int note) {
        if (note < 0 || note > 127) {
            throw new IllegalArgumentException("Note must be 0...127");
        }
        this.toNote = note;
    }

    /**
     * Gets the interval between the notes.
     */
    public Interval getInterval() {
        return this.interval;
    }

    /**
     * Sets the interval between the notes.
     */
    public void setInterval(Interval i) {
        this.interval = i;
    }

    public List<Integer> getNotes() {
        List<Integer> notes = new ArrayList<>();

        int note = this.fromNote;
        while (note <= this.toNote) {
            notes.add(note);
            note += this.interval.getValue();
        }

        return notes;
    }

    /**
     * Gets a string representation of the job.
     */
    @Override
    public String toString() {
        return String.format(
            "%s: from %d to %d by %d semitones",
            this.name,
            this.fromNote,
            this.toNote,
            this.interval.getValue()
        );
    }

    //
    // Private fields
    //

    private String name;
    private UUID id;
    private int fromNote;
    private int toNote;
    private Interval interval;
}