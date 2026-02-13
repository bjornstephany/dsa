import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

public class ReceiverRosterManager {
    
    // IMPLEMENT YOUR SOLUTION HERE 
	// Helper to store stats for each player
    private class PlayerStats {
        int totalPoints = 0;
        int gameCount = 0;

        double getAvg() {
            return (double) totalPoints / gameCount;
        }
    }

    // key for the ranking tree: sorts by average , then by Jersey(for ties)
    private class RankKey implements Comparable<RankKey> {
        double avg;
        int jersey;

        RankKey(double a, int j) {
            this.avg = a;
            this.jersey = j;
        }

        public int compareTo(RankKey other) {
            if (this.avg != other.avg)
                return Double.compare(this.avg, other.avg);
            return Integer.compare(this.jersey, other.jersey);
        }
    }

    // The Augmentes AVL node:
    private class Node {
        RankKey key;
        int height = 1;
        int subtreeSize = 1;
        Node left, right;

        Node(RankKey k) {
            this.key = k;
        }
    }

    // Trees:
    private TreeMap<String, Integer> gameRecordsTree; // Key:"gameId-jersey"
    private TreeMap<Integer, PlayerStats> playerStatsTree;
    private Node rankingRoot;

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
		gameRecordsTree = new TreeMap<>();
        playerStatsTree = new TreeMap<>();
        rankingRoot = null;
    }

    public void record(int g, int r, int p) {
        // IMPLEMENT YOUR SOLUTION HERE 
		// save the record
        // update receiver average
        // update ranking structure

        String recordKey = g + "-" + r;

        // if game exists, clear it first to avoid double counting
        if (gameRecordsTree.containsKey(recordKey)) {
            clear(g, r);
        }

        PlayerStats stats = playerStatsTree.getOrDefault(r, new PlayerStats());

        // remove from ranking tree before updating (since average will change)
        if (stats.gameCount > 0) {
            rankingRoot = deleteNode(rankingRoot, new RankKey(stats.getAvg(), r));
        }

        stats.totalPoints += p;
        stats.gameCount += 1;
        playerStatsTree.put(r, stats);
        gameRecordsTree.put(recordKey, p);

        // Re-insert into renking tree with new average
        rankingRoot = insertNode(rankingRoot, new RankKey(stats.getAvg(), r));

    }
	
	public void clear(int g, int r) {
        // IMPLEMENT YOUR SOLUTION HERE
		// remove the record
        // update averages
        // update ranking

        String recordKey = g + "-" + r;
        if (!gameRecordsTree.containsKey(recordKey))
            return;

        int points = gameRecordsTree.remove(recordKey);
        PlayerStats stats = playerStatsTree.get(r);

        // remove old position from ranking
        rankingRoot = deleteNode(rankingRoot, new RankKey(stats.getAvg(), r));

        stats.totalPoints -= points;
        stats.gameCount -= 1;

        if (stats.gameCount > 0) {
            rankingRoot = insertNode(rankingRoot, new RankKey(stats.getAvg(), r));
        } else {
            playerStatsTree.remove(r);
        }
    }
	
	public int ranked_receiver(int k) {
        int r = -1; // Variable where you will assign the jersey id with the kth highest performance

        // IMPLEMENT YOUR SOLUTION HERE - DO NOT FORGET TO ASSIGN THE VARIABLE r
		int totalSize = getSubtreeSize(rankingRoot);

        if (k >= 1 && k <= totalSize) {
            r = selectKthLargest(rankingRoot, k);
        }

        // DO NOT MODIFY AFTER THIS POINT
        System.out.println("Jersey with the kth highest performance is "+ r);
        return r;
    }

	// AVL tree helpers
    private int getSubtreeSize(Node n) {
        return (n == null) ? 0 : n.subtreeSize;
    }

    private int getHeight(Node n) {
        return (n == null) ? 0 : n.height;
    }

    private void update(Node n) {
        if (n != null) {
            n.height = 1 + Math.max(getHeight(n.left), getHeight(n.right));
            n.subtreeSize = 1 + getSubtreeSize(n.left) + getSubtreeSize(n.right);
        }
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        update(y);
        update(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        update(x);
        update(y);
        return y;
    }

    private Node balance(Node n) {
        update(n);
        int b = getHeight(n.left) - getHeight(n.right);
        if (b > 1) {
            if (getHeight(n.left.left) < getHeight(n.left.right))
                n.left = rotateLeft(n.left);
            return rotateRight(n);
        }

        if (b < -1) {
            if (getHeight(n.right.right) < getHeight(n.right.left))
                n.right = rotateRight(n.right);
            return rotateLeft(n);
        }
        return n;
    }

    private Node insertNode(Node node, RankKey key) {
        if (node == null)
            return new Node(key);
        if (key.compareTo(node.key) < 0)
            node.left = insertNode(node.left, key);
        else
            node.right = insertNode(node.right, key);
        return balance(node);
    }

    private Node deleteNode(Node node, RankKey key) {
        if (node == null)
            return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0)
            node.left = deleteNode(node.left, key);
        else if (cmp > 0)
            node.right = deleteNode(node.right, key);
        else {
            if (node.left == null || node.right == null)
                return (node.left == null) ? node.right : node.left;
            Node min = node.right;
            while (min.left != null)
                min = min.left;
            node.key = min.key;
            node.right = deleteNode(node.right, min.key);
        }
        return balance(node);
    }

    private int selectKthLargest(Node node, int k) {
        int rightSize = getSubtreeSize(node.right);
        if (k == rightSize + 1)
            return node.key.jersey;
        if (k <= rightSize)
            return selectKthLargest(node.right, k);
        return selectKthLargest(node.left, k - rightSize - 1);
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
