import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRepository {
    private final String filename;

    public TaskRepository(String filename) {
        this.filename = filename;
    }

    public void save(ArrayList<Task> tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("[");
            for (int i = 0; i < tasks.size(); i++) {
                writer.print("  " + tasks.get(i).toJson());
                if (i < tasks.size() - 1) {
                    writer.println(",");
                } else {
                    writer.println();
                }
            }
            writer.println("]");
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    public ArrayList<Task> load() {
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        ArrayList<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("{")) {
                    // Remove trailing comma if exists
                    if (line.endsWith(",")) {
                        line = line.substring(0, line.length() - 1);
                    }
                    tasks.add(Task.fromJson(line));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }
}
