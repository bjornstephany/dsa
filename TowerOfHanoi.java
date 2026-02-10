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
        // Implement your code here. You can use additional functions if needed.

        //9-2-26 v.s (aneta) 
        
       //base case 
        if (diskCount < = 2){
            System.out.println("move " + diskCount + " disk(s) from rod " + startingRod + " to rod " + targetRod); 
            return;
        }
        //recursive step
        int helper_rod = 6 - startingRod - targetRod;

        System.out.println(“ Move disk 2 from rod " + startingRod + " to rod " + targetRod);

        printMoves(startingRod, helper_rod, diskCount - 2 );
        }
        




        
        //int startingRod = 3; 
        //int diskCount = 6; 
        int count = 0;
        Boolean isEven;  

        if(diskCount == targetRod){
            return;
        }

        // making an array from the amount of disks
        for(i=0;i<input;i++)
            arrayList.add(i);
            
        printMoves(int startingRod, int targetRod, int diskCount);

        //checking if diskCount is even or odd (how many pairs)
        if (diskCount %2 == 1){ 
            isEven = true;
        }
        else {
            isEven = false;
        }

        //move 2
        for(i= 1;   ; i+2)
            if(isEven){
            use move two pair amount of time
            count ++; 
                if(only one disk left)
                    use move one 
            count ++; 
            }

        //move 1
         for(i=1; i<    ;i++) 
            if(!ifEven)
                use move two until last disk;
            count +=1; 
            go to move 1
     // for loop for checking each rod        
    for(starting rod = 0; ;starting rod++)
        //for loop for checking moving pair/single disks
        for(disks = pair or single ; disks > 0; diskPair-2 diskSingle--)
        //checking for empty rod
         if(check other two rods = null)
            //checking if disks that are there are bigger
            if(check other two > disks)
                move disk to diff rod;
        else{
            find smallest, move to nonlarges 
        }



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

