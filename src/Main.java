import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

class Task implements Serializable {
    String name;
    String description;
    String deadline;
    boolean status;
    public Task(String name, String description, String deadline) {
        this.name = name;
        this.description = description;
        status = false;
        this.deadline = deadline;
    }
    public Task(String name,String deadline) {
        this.name = name;
        this.description = null;
        status = false;
        this.deadline = deadline;
    }
    public String toString(){
        return name+" "+description+" "+deadline+" Status:"+status;
    }
    public void setStatus(){
        this.status=true;
    }
}
public class Main {
    public static void main(String[] args) {
        LinkedList<Task> tasks = new LinkedList<Task>();
        Scanner sc = new Scanner(System.in);
        String filename = "file.ser";
        File f = new File(filename);
        if(f.exists()){
        tasks = load(filename, tasks);
        }
        while(true){
            System.out.println("1. Add task\n" +
                    "2. Remove task\n" +
                    "3. Mark task as done\n" +
                    "4. View tasks\n" +
                    "5. Exit");
            System.out.println("Enter your choice:");
            int choice = sc.nextInt();
            sc.nextLine();
            if(choice == 5){
                break;
            } else if (choice == 4) {
                getTasks(tasks);
            } else if (choice == 3) {
                getTasks(tasks);
                System.out.println("Enter the task number thats completed:");
                int taskNumber = sc.nextInt();
                sc.nextLine();
                tasks.get(taskNumber-1).setStatus();
                save(filename,tasks);
            } else if (choice == 2) {
                getTasks(tasks);
                System.out.println("Enter the task number to be removed:");
                int choice2 = sc.nextInt();
                sc.nextLine();
                tasks.remove(choice2-1);
                save(filename,tasks);
            } else if (choice == 1) {
                String name;
                String description = "";
                String deadline;
                System.out.println("Enter task name:");
                name = sc.nextLine();
                System.out.println("Do you want to add a description?[y/n]:");
                String choice2 = sc.nextLine();
                if(choice2.equals("y")){
                    System.out.println("Enter task description:");
                    description = sc.nextLine();
                }
                System.out.println("Enter task deadline:");
                deadline = sc.nextLine();
                if(choice2.equals("y")){
                    tasks.add(new Task(name,description,deadline));
                }
                else{
                    tasks.add(new Task(name,deadline));
                }
                save(filename,tasks);
            }else {
                System.out.println("Invalid choice");
            }
        }

    }
    public static void getTasks(LinkedList<Task> tasks){
        if(tasks.isEmpty()){
            System.out.println("There are no tasks in the system");
        }
        else{
            System.out.println("TASKS :");
            for(Task task : tasks){
                System.out.println((tasks.indexOf(task)+1)+": "+task.toString());
            }
        }
    }
    public static void save (String filename,LinkedList<Task> tasks){
        try{
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tasks);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static LinkedList<Task> load(String filename,LinkedList<Task> tasks){
        try{
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            tasks = (LinkedList<Task>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }
}