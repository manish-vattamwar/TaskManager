import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int nextId = 1;

    private int id;
    private String name;
    private String description;
    private String category;
    private LocalDate deadline;
    private boolean status;

    public Task(String name, String description, String category, LocalDate deadline) {
        this.id = nextId++;
        this.name = name;
        this.description = description;
        this.category = (category == null || category.isEmpty()) ? "General" : category;
        this.status = false;
        this.deadline = deadline;
    }

    public Task(String name, LocalDate deadline) {
        this(name, null, null, deadline);
    }

    // Constructor for manual JSON parsing
    public Task(int id, String name, String description, String category, LocalDate deadline, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.deadline = deadline;
        this.status = status;
    }

    public static void setNextId(int id) {
        nextId = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        String done = status ? "done" : "pending";
        String desc = (description == null || description.isEmpty()) ? "N/A" : description;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String deadlineStr = deadline.format(formatter);
        return String.format(" %-10s | %-12s | %-20s | %-10s | %-8s|", name, category, desc, deadlineStr, done);
    }

    public void setStatus() {
        this.status = true;
    }

    // Manual JSON-like serialization
    public String toJson() {
        return String.format(
            "{\"id\":%d,\"name\":\"%s\",\"description\":\"%s\",\"category\":\"%s\",\"deadline\":\"%s\",\"status\":%b}",
            id, escapeJson(name), escapeJson(description), escapeJson(category), deadline.toString(), status
        );
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\"", "\\\"");
    }

    public static Task fromJson(String json) {
        // Very basic manual parsing for "key":"value" or "key":boolean/int
        int id = Integer.parseInt(extractValue(json, "id"));
        String name = extractValue(json, "name");
        String description = extractValue(json, "description");
        String category = extractValue(json, "category");
        LocalDate deadline = LocalDate.parse(extractValue(json, "deadline"));
        boolean status = Boolean.parseBoolean(extractValue(json, "status"));
        
        return new Task(id, name, description, category, deadline, status);
    }

    private static String extractValue(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int start = json.indexOf(searchKey) + searchKey.length();
        if (json.charAt(start) == '"') {
            start++; // skip opening quote
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } else {
            int end = json.indexOf(",", start);
            if (end == -1) end = json.indexOf("}", start);
            return json.substring(start, end).trim();
        }
    }
}
