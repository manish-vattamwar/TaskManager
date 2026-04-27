import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static final String FILENAME = "tasks.json";
    private static final TaskRepository repository = new TaskRepository(FILENAME);

    public static void main(String[] args) {
        ArrayList<Task> tasks = repository.load();
        
        // Set nextId based on existing tasks
        int maxId = tasks.stream().mapToInt(Task::getId).max().orElse(0);
        Task.setNextId(maxId + 1);

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Task Manager Pro ---");
            System.out.println("1. Add task");
            System.out.println("2. Remove task");
            System.out.println("3. Mark task as done");
            System.out.println("4. View tasks (unsorted)");
            System.out.println("5. View tasks (sorted by deadline)");
            System.out.println("6. Search tasks by name");
            System.out.println("7. Filter tasks by category");
            System.out.println("8. Exit");
            
            int choice = getIntInput(sc, "Enter your choice:");

            switch (choice) {
                case 1:
                    addTask(sc, tasks);
                    repository.save(tasks);
                    break;
                case 2:
                    removeTask(sc, tasks);
                    repository.save(tasks);
                    break;
                case 3:
                    markTaskDone(sc, tasks);
                    repository.save(tasks);
                    break;
                case 4:
                    getTasks(tasks);
                    break;
                case 5:
                    ArrayList<Task> sortedTasks = new ArrayList<>(tasks);
                    sortedTasks.sort(Comparator.comparing(Task::getDeadline));
                    getTasks(sortedTasks);
                    break;
                case 6:
                    searchTasks(sc, tasks);
                    break;
                case 7:
                    filterByCategory(sc, tasks);
                    break;
                case 8:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void addTask(Scanner sc, ArrayList<Task> tasks) {
        System.out.println("Enter task name:");
        String name = sc.nextLine();
        
        System.out.println("Enter task category (or press Enter for 'General'):");
        String category = sc.nextLine();

        System.out.println("Do you want to add a description?[y/n]:");
        String choice2 = sc.nextLine();
        String description = null;
        if (choice2.equalsIgnoreCase("y")) {
            System.out.println("Enter task description:");
            description = sc.nextLine();
        }
        
        LocalDate deadline = getDateInput(sc, "Enter task deadline (yyyy-MM-dd):");

        tasks.add(new Task(name, description, category, deadline));
    }

    private static void removeTask(Scanner sc, ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to remove.");
            return;
        }
        getTasks(tasks);
        int id = getIntInput(sc, "Enter the task ID to be removed:");
        boolean removed = tasks.removeIf(task -> task.getId() == id);
        if (removed) {
            System.out.println("Task removed.");
        } else {
            System.out.println("Task with ID " + id + " not found.");
        }
    }

    private static void markTaskDone(Scanner sc, ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to mark as done.");
            return;
        }
        getTasks(tasks);
        int id = getIntInput(sc, "Enter the task ID that's completed:");
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setStatus();
                System.out.println("Task marked as done.");
                return;
            }
        }
        System.out.println("Task with ID " + id + " not found.");
    }

    private static void searchTasks(Scanner sc, ArrayList<Task> tasks) {
        System.out.println("Enter task name to search:");
        String query = sc.nextLine().toLowerCase();
        List<Task> results = tasks.stream()
                .filter(t -> t.getName().toLowerCase().contains(query))
                .collect(Collectors.toList());
        
        if (results.isEmpty()) {
            System.out.println("No tasks found matching '" + query + "'");
        } else {
            getTasks(new ArrayList<>(results));
        }
    }

    private static void filterByCategory(Scanner sc, ArrayList<Task> tasks) {
        System.out.println("Enter category to filter by:");
        String category = sc.nextLine().toLowerCase();
        List<Task> results = tasks.stream()
                .filter(t -> t.getCategory().toLowerCase().equals(category))
                .collect(Collectors.toList());
        
        if (results.isEmpty()) {
            System.out.println("No tasks found in category '" + category + "'");
        } else {
            getTasks(new ArrayList<>(results));
        }
    }

    public static void getTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks to display.");
        } else {
            System.out.println("\nTASKS LIST:");
            System.out.format(" %-3s | %-10s | %-12s | %-20s | %-10s | %-8s|%n", "ID", "NAME", "CATEGORY", "DESCRIPTION", "DEADLINE", "STATUS");
            System.out.format(" %-3s | %-10s | %-12s | %-20s | %-10s | %-8s|%n", "---", "----------", "------------", "--------------------", "----------", "--------");
            for (Task task : tasks) {
                System.out.println(String.format(" %-3d |", task.getId()) + task.toString());
            }
        }
    }

    private static int getIntInput(Scanner sc, String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = sc.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static LocalDate getDateInput(Scanner sc, String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = sc.nextLine();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Please enter date in format yyyy-MM-dd (e.g., 2023-12-31).");
            }
        }
    }
}
