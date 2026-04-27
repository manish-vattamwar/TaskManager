# Task Manager CLI

A robust, command-line based Task Management application built with Java. This tool allows you to efficiently track your daily tasks with features like persistence, deadline sorting, and unique task identification.

## 🚀 Features

- **Unique Task IDs**: Every task is assigned a permanent ID, making it easy to manage even after deletions.
- **Date Management**: Built-in support for deadlines using `java.time.LocalDate`.
- **Sorting**: View your tasks either in the order they were created or sorted by their deadlines.
- **Persistence**: Tasks are automatically saved to `file.ser` and reloaded when you restart the application.
- **Robust Input**: Built-in validation to handle invalid dates and non-numeric menu choices without crashing.

## 🛠️ Architecture

The project follows a clean separation of concerns:
- **`Main.java`**: Handles the CLI interface and user interaction.
- **`Task.java`**: The data model representing a single task.
- **`TaskRepository.java`**: Manages data persistence (Saving/Loading).

## 📋 How to Run

1. **Prerequisites**: Ensure you have Java JDK 8 or higher installed.
2. **Clone the repository**:
   ```bash
   git clone https://github.com/manish-vattamwar/TaskManager.git
   cd TaskManager
   ```
3. **Compile**:
   ```bash
   javac -d out src/*.java
   ```
4. **Run**:
   ```bash
   java -cp out Main
   ```

## 📝 Usage

Follow the on-screen menu to:
1. **Add Task**: Enter name, optional description, and deadline (YYYY-MM-DD).
2. **Remove Task**: Provide the unique ID of the task to delete.
3. **Mark as Done**: Toggle the status of a task using its ID.
4. **View Tasks**: See your current list in table format.

## 🛣️ Future Roadmap

- [ ] Switch from Java Serialization to JSON format for human-readable data.
- [ ] Add categories/tags for tasks.
- [ ] Implement a search feature to find tasks by name.
