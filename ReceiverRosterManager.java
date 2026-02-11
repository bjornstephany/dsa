import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReceiverRosterManager {

    
    // IMPLEMENT YOUR SOLUTION HERE 

    public static void main(String[] args) {
        // DO NOT CHANGE ANYTHING IN THIS FUNCTION
        if (args.length == 0) {
            throw new IllegalArgumentException("CSV file path must be provided as a command-line argument");
        }

        ReceiverRosterManager receiverRosterManager = new ReceiverRosterManager();
        receiverRosterManager.executeFromCSV(args[0]);
        // Example method to call it from the .jar file: "java -jar ReceiverRosterManager.jar ExampleInput.csv".

    }

    public ReceiverRosterManager() {
        // IMPLEMENT YOUR SOLUTION HERE 
    }

    public void record(int g, int r, int p) {
        // IMPLEMENT YOUR SOLUTION HERE 
    }
	
	public void clear(int g, int r) {
        // IMPLEMENT YOUR SOLUTION HERE 
    }
	
	public int ranked_receiver(int k) {
        int r = 0; // Variable where you will assign the jersey id with the kth highest performance

        // IMPLEMENT YOUR SOLUTION HERE - DO NOT FORGET TO ASSIGN THE VARIABLE r

        // DO NOT MODIFY AFTER THIS POINT
        System.out.println("Jersey with the kth highest performance is "+ r);
        return r;
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

                int game, jersey, points, rank = 0;

                switch (command) {
                    case "RECORD":
                        // RECORD,GAME,JERSEY,POINTS
                        game = Integer.parseInt(tokens[1].trim());
                        jersey = Integer.parseInt(tokens[2].trim());
                        points = Integer.parseInt(tokens[3].trim());

                        record(game,jersey,points);
                        break;

                    case "CLEAR":
                        // CLEAR,GAME,JERSEY
                        game = Integer.parseInt(tokens[1].trim());
                        jersey = Integer.parseInt(tokens[2].trim());
						
						clear(game,jersey);
                        break;

                    case "RANKED_RECEIVER":
                        // RANKED_RECEIVER,RANK
                        rank = Integer.parseInt(tokens[1].trim());
						
						ranked_receiver(rank);
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