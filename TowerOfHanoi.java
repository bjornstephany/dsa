public class TowerOfHanoi {

    public static void main(
        //DO NOT CHANGE ANYTHING IN THIS FUNCTION
        String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java TowerOfHanoi <startingRod> <targetRod> <diskCount>");
            System.out.println("Example: java TowerOfHanoi 1 3 10");
            return;
        }

        int startingRod = Integer.parseInt(args[0]);
        int targetRod = Integer.parseInt(args[1]);
        int diskCount = Integer.parseInt(args[2]);

        TowerOfHanoi t = new TowerOfHanoi();
        t.printMoves(startingRod, targetRod, diskCount);
    }

    /**
     * Prints all moves required to transfer a stack of disks from one rod to
     * another following the rules of the Towers of Hanoi.
     *
     * @param startingRod the rod number where the disks start (1 to 3)
     * @param targetRod   the rod number where the disks should end up (1 to 3)
     * @param diskCount   the number of disks on the starting rod (must be >= 1)
     */
     public void printMoves(int startingRod, int targetRod, int diskCount) {

        //base case 
        if (diskCount <= 2) {
        System.out.println("moved " + diskCount + " disk(s) from rod " + startingRod + " to rod " + targetRod);
        return;
        }  
        // Recursive step
        int helperRod = 6 - startingRod - targetRod;

        printMoves(startingRod, helperRod, diskCount - 2);

        printMoves(startingRod, targetRod, 2);

        printMoves(helperRod, targetRod, diskCount - 2);
    }

    /**
     * Prints a single move of 1-2 disks from one rod to another.
     *
     * @param from the starting rod number (1 to 3)
     * @param to   the target rod number (1 to 3)
     * @param n    How many disks we are moving (1 to 2)
     */
    private void printMove(int from, int to, int n) {
        //DO NOT CHANGE ANYTHING IN THIS FUNCTION
        System.out.printf("Moving %d disks from rod %d to rod %d%n",n, from, to);
    }
}


