import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int nextId = 1;

    private int id;
    private String name;
    private String description;
    private LocalDate deadline;
    private boolean status;

    public Task(String name, String description, LocalDate deadline) {
        this.id = nextId++;
        this.name = name;
        this.description = description;
        this.status = false;
        this.deadline = deadline;
    }

    public Task(String name, LocalDate deadline) {
        this(name, null, deadline);
    }

    public static void setNextId(int id) {
        nextId = id;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        String done = status ? "done" : "pending";
        String desc = (description == null) ? "N/A" : description;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String deadlineStr = deadline.format(formatter);
        return String.format(" %-10s | %-20s | %-10s | %-8s|", name, desc, deadlineStr, done);
    }

    public void setStatus() {
        this.status = true;
    }
}
