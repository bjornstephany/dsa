package Part2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TaskTracker {

    // Data structure components:
    // 1. LinkedList to track all tasks in insertion order (for deleteLastTask)
    // 2. HashMap mapping type -> Queue of tasks (for O(1) FIFO access in takeNextTask)
    // 3. HashMap mapping type -> total duration (for O(1) sum in getTotalDuration)
    private LinkedList<Task> allTasks;
    private HashMap<String, Queue<Task>> typeQueues;
    private HashMap<String, Integer> durationTotals;

    public static void main(String[] args) {
        // DO NOT CHANGE ANYTHING IN THIS FUNCTION
        if (args.length == 0) {
            throw new IllegalArgumentException("CSV file path must be provided as a command-line argument");
        }

        TaskTracker taskTracker = new TaskTracker();
        taskTracker.executeFromCSV(args[0]);
        // Example method to call it from the .jar file: "java -jar TaskTracker.jar
        // ExampleInput.csv".
    }

    public TaskTracker() {
        allTasks = new LinkedList<>();
        typeQueues = new HashMap<>();
        durationTotals = new HashMap<>();
    }

    public void insert(Task t) {
        // O(1) operation: add to all data structures
        allTasks.add(t);  // Add to end of list (O(1))
        
        // Add to type queue if not exists
        typeQueues.putIfAbsent(t.type, new LinkedList<>());
        typeQueues.get(t.type).add(t);  // Add to end of queue (O(1))
        
        // Update total duration for this type
        if (!durationTotals.containsKey(t.type)) {
            durationTotals.put(t.type, t.duration);
        } else {
            durationTotals.put(t.type, durationTotals.get(t.type) + t.duration);
        }
    }

    @SuppressWarnings("unused")
    public Task takeNextTask(String type) {
        // O(1) operation: retrieve and remove the oldest task of the given type
        Task returnTask = null;
        
        if (typeQueues.containsKey(type) && !typeQueues.get(type).isEmpty()) {
            returnTask = typeQueues.get(type).poll();  // Remove from front (O(1))
            // Update total duration for this type
            durationTotals.put(type, durationTotals.get(type) - returnTask.duration);
        }
        
        // DO NOT CHANGE ANYTHING IN THIS FUNCTION BELOW HERE
        if (returnTask == null) {
            System.out.println("Return task: null");
        } else {
            System.out.println("Return task: " + returnTask.toString());
        }
        return returnTask;
    }

    public double getTotalDuration(String type) {
        // O(1) operation: return total duration for the given type
        double totalDuration = durationTotals.get(type);

        // DO NOT CHANGE ANYTHING IN THIS FUNCTION BELOW HERE;
        System.out.println("totalDuration: " + totalDuration);
        return totalDuration;
    }

    public void deleteLastTask() {
        // O(n) operation: delete the last inserted task
        if (allTasks.isEmpty()) {
            return;
        }
        
        // Remove the last task from allTasks (O(1) for LinkedList.removeLast())
        Task lastTask = allTasks.removeLast();
        
        // Remove this task from its type queue and update duration total
        // Worst case O(n) since we might need to search through the queue
        if (typeQueues.containsKey(lastTask.type)) {
            typeQueues.get(lastTask.type).remove(lastTask);
            durationTotals.put(lastTask.type, durationTotals.get(lastTask.type) - lastTask.duration);
        }
    }

    public void executeFromCSV(String filePath) {
        // DO NOT CHANGE ANYTHING IN THIS FUNCTION
        // Example csv is available
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;

                String[] tokens = line.split(",");
                String command = tokens[0].trim().toUpperCase();

                switch (command) {
                    case "INSERT":
                        // INSERT,TYPE,DURATION,ID
                        String type = tokens[1].trim();
                        int duration = Integer.parseInt(tokens[2].trim());
                        int id = Integer.parseInt(tokens[3].trim());

                        insert(new Task(type, duration, id));
                        break;

                    case "TAKE_NEXT":
                        // TAKE_NEXT,TYPE
                        takeNextTask(tokens[1].trim());
                        break;

                    case "TOTAL":
                        // Total,TYPE
                        getTotalDuration(tokens[1].trim());
                        break;

                    case "DELETE":
                        // DELETE
                        deleteLastTask();
                        break;

                    default:
                        throw new IllegalArgumentException("Unknown command: " + command);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
