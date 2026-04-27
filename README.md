# Task Manager Pro CLI

A robust, command-line based Task Management application built with Java. This tool allows you to efficiently track your daily tasks with features like JSON persistence, deadline sorting, categories, and search functionality.

## 🚀 Features

- **Unique Task IDs**: Every task is assigned a permanent ID, making it easy to manage even after deletions.
- **Date Management**: Built-in support for deadlines using `java.time.LocalDate`.
- **Categories**: Organize your tasks by category (e.g., Work, Personal, Shopping).
- **Search & Filter**: Find tasks quickly by name or filter by category.
- **Sorting**: View your tasks either in the order they were created or sorted by their deadlines.
- **JSON Persistence**: Tasks are saved in a human-readable `tasks.json` format, making data easy to inspect and edit.
- **Robust Input**: Built-in validation to handle invalid dates and non-numeric menu choices without crashing.

## 🛠️ Architecture

The project follows a clean separation of concerns:
- **`Main.java`**: Handles the CLI interface and user interaction.
- **`Task.java`**: The data model representing a single task with manual JSON serialization logic.
- **`TaskRepository.java`**: Manages data persistence using the JSON format.

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
1. **Add Task**: Enter name, category, optional description, and deadline (YYYY-MM-DD).
2. **Remove Task**: Provide the unique ID of the task to delete.
3. **Mark as Done**: Toggle the status of a task using its ID.
4. **View Tasks**: See your current list in table format.
5. **Search/Filter**: Find specific tasks by name or category.

## 🛣️ Roadmap (Completed!)

- [x] Switch from Java Serialization to JSON format for human-readable data.
- [x] Add categories/tags for tasks.
- [x] Implement a search feature to find tasks by name.
