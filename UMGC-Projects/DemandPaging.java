/*
NAME: Dmitry Landy
DATE: October 9, 2020
FILE: DemandPaging.java
DESCRIPTION: This program perform various page-replacement algorithms. The user 
is promted with a menu when starting the program that allows them to do the following:

0-Exit the program
1 – Read Reference String
2 – Generate Reference String
3 – Display Current Reference String
4 – Simulate FIFO (first in first out)
5 – Simulate OPT (optimal)
6 – Simulate LRU (least recently used)
7 – Simulate LFU (least frequently used)

This program requires an argument from the command line for setting the amount of 
physical frames. After getting the to menu, the user will need to create a reference
string manually or automatically, otherwise the simulations will give an error. 

After the reference string is created, the user can simulate the various page-replacement
algorithms. 
 */

package FinalProject;

//imports
import java.util.Scanner;
import java.nio.IntBuffer;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Random;


public class DemandPaging{    
    
    public static int physicalFrames;//stores number of physical frames    
    public static IntBuffer referenceString;//stores reference string in a Integer buffer
    public static Scanner input = new Scanner(System.in);
    
    /*
    This method displays the menu for the user
    */
    public static int menu(){
        int option;
        Scanner input = new Scanner(System.in);
        
        System.out.println("\nMENU:\n\n"                
                + "0-Exit\n"
                + "1 – Read Reference String\n"
                + "2 – Generate Reference String\n"
                + "3 – Display Current Reference String\n"
                + "4 – Simulate FIFO\n"
                + "5 – Simulate OPT\n"
                + "6 – Simulate LRU\n"
                + "7 – Simulate LFU\n"
                + "\nSelect Option: ");
        option = input.nextInt();
        return option;
    }
    
    /*
    This method reads the reference string from the user one integer at a time. 
    It first determines the size of the reference string to allocate that space
    for the buffer. it then takes input from the user and checks to make
    sure it is a number 0-9 (10 virtual frames).
    */
    public static IntBuffer readReferenceString(){       
       int tempIn;
       int capacity;       
       IntBuffer intBuff;
       
       System.out.println("Input the length of the reference string: ");
       capacity = input.nextInt();
       intBuff = IntBuffer.allocate(capacity);//
       
       System.out.println("Input the reference numbers (0-9) one at a time, pressing ENTER after each input ");
              
       for(int count = 0; count<capacity;count++){   
           try{
               tempIn = input.nextInt();
               if(tempIn>=0&&tempIn<10){
                   intBuff.put(tempIn);
               }else{
                   System.out.println("Input only numbers 0-9!");
                   count--;
               }
           }catch(InputMismatchException exception){
               System.out.println("Input only numbers 0-9!");
               input.next();
               count--;
           }
       }      
       return intBuff;
    }
   
    /*
    This method generates a reference string, size determined by the user
    with random numbers 0-9 with the first few being unique from one another. 
    */
    public static IntBuffer generateReferenceString(){
       
       int tempIn;
       int capacity=0; 
       int checked=0;
       IntBuffer intBuff;
       int[] firstFew = new int[physicalFrames];
       Random rand = new Random();
              
       while(capacity<10){
           System.out.println("Input the length of the reference string(>=10): ");
           capacity = input.nextInt();
       }       
       intBuff = IntBuffer.allocate(capacity);//
       
       //ensures the first frew numbers are unique
       for(int count = 0; count<capacity;count++){
           tempIn = rand.nextInt(10);
           
           while(count<physicalFrames){
               for(int index=0;index<count;index++){
                    if(tempIn!=firstFew[index]){
                        checked++;                        
                    }
                }
               if(checked==count){                   
                   intBuff.put(tempIn);
                   firstFew[count]=tempIn;
                   checked=0;
                   count++;
               }else{
                   tempIn = rand.nextInt(10);
                   checked=0;
               }
           }
           
            intBuff.put(rand.nextInt(10));
           
       }
       return intBuff;
    }
   
    /*
    Checks to make sure the reference string isn't empty
    */
    public static boolean fullReferenceString(IntBuffer refString){
       return refString!=null;
    }    
   
    /*
    Prints the reference String
    */
    public static void printReferenceString(IntBuffer refString){
        
        System.out.print("\nReference String: ");
        for(int count =0;count<refString.limit();count++){
            System.out.print(refString.get(count)+" ");
        }       
    }
    
    /*
    FIFO method has one parameter to retrieve the buffer with reference string.
    It uses a linked list to store the current elements of the reference string
    into the physical frames. It checks for faults and counts them. If its a fault,
    it removes the last element in the last frame, pushes the elements in the current frame up
    one, and then adds the new element. Each step awaits for user input to continue.
    */
    public static void FIFO(IntBuffer refString){
        LinkedList<Integer> framesQueue = new LinkedList<>();        
        int pageFaults=0;
        int victimFrame=0;
        boolean currentPageFault=false;
        int currentElement;              
        
        System.out.println("\nFIFO: (Press 1 then ENTER to continue after each step)");
 
        
        for(int virtualIndex = 0; virtualIndex<refString.limit();virtualIndex++){
            currentElement = refString.get(virtualIndex);
            currentPageFault=false;
            
            if(virtualIndex<physicalFrames){                
                framesQueue.push(currentElement);    
                currentPageFault=true;
                pageFaults++;
            }else{
                currentPageFault = true;
                for(int element:framesQueue){
                    if(currentElement == element){ 
                        currentPageFault=false;
                        break;
                    }                  
                }
                if(currentPageFault){
                    framesQueue.push(currentElement);
                    victimFrame = framesQueue.removeLast();
                    currentPageFault = true;
                    pageFaults++;                    
                }                
            }
            
            //print 
            System.out.println("\nReference String| "+currentElement);
            for(int elementIndex =0; elementIndex<framesQueue.size();elementIndex++){
                System.out.println("Physical Frame "+elementIndex+"| "+framesQueue.get(elementIndex));
            }
            System.out.println("Page Fault: "+currentPageFault);
            if(currentPageFault&&virtualIndex>physicalFrames-1){
                System.out.println("Victim Frame: "+victimFrame);
            }            
            input.next();
            
        }        
        System.out.println("\nTotal Page Faults: "+pageFaults+"\n");        
        
    }
    
    /*
    OPT method has one parameter to get the reference string buffer. It keeps
    track of elements in the physical frames into linked lists. It has to calculate
    the future occurances of the virtual frames. Every time there is a fault, it finds
    the current element in the physical frame that occurs the least in the future,
    and replaces it. Each step awaits user input before proceeding.
    */
    public static void OPT(IntBuffer refString) {
        LinkedList<Integer> framesQueue = new LinkedList<>();
        int pageFaults = 0;
        int victimFrame = 0;
        boolean currentPageFault = false;
        int currentElement;
        int[] futureOccurances = new int[10];
        int[] currentFrameOccurances = new int[physicalFrames];//keeps track of future occurances of the current frames
        int leastOccured=0;
        int leastOccuredFrame=0;
        
        //calculate future occurances
        for(int index = 0; index<refString.limit();index++){
            futureOccurances[refString.get(index)]++;
        }

        System.out.println("\nOPT: (Press 1 then ENTER to continue after each step)");

        for (int virtualIndex = 0; virtualIndex < refString.limit(); virtualIndex++) {
            currentElement = refString.get(virtualIndex);
            futureOccurances[currentElement]--;
            currentPageFault = false;

            if (virtualIndex < physicalFrames) {
                framesQueue.add(virtualIndex, currentElement);                
                currentPageFault = true;
                pageFaults++;
            } else {
                currentPageFault = true;
                for (int element : framesQueue) {
                    if (currentElement == element) {
                        currentPageFault = false;
                        break;
                    }
                }
                
                //if a page fault occurs
                if (currentPageFault) {
                    //determines how many future occuances of each element in the physical frames
                    for(int count = 0; count < physicalFrames;count++){
                        currentFrameOccurances[count]= futureOccurances[framesQueue.get(count)];//return the number of occurances of the element in the physical frame
                    }                    
                    
                    //find the least occuring element in the physical frames                       
                    for(int frameNumber = physicalFrames-1; frameNumber>0;frameNumber--){                        
                        if(frameNumber ==physicalFrames-1){                             
                            leastOccured= currentFrameOccurances[frameNumber];//last frame 
                            leastOccuredFrame = frameNumber;                            
                        }
                        //iterate through physical frames
                        if(currentFrameOccurances[frameNumber]<=leastOccured){                             
                            leastOccured= currentFrameOccurances[frameNumber]; 
                            leastOccuredFrame = frameNumber;
                        }                       
                    }                      
                    
                    victimFrame = framesQueue.remove(leastOccuredFrame);
                    framesQueue.add(leastOccuredFrame, currentElement);
                    currentPageFault = true;
                    pageFaults++;
                }
            }

            //print 
            System.out.println("\nReference String| " + currentElement);
            for (int elementIndex = 0; elementIndex < framesQueue.size(); elementIndex++) {
                System.out.println("Physical Frame " + elementIndex + "| " + framesQueue.get(elementIndex));
            }
            System.out.println("Page Fault: " + currentPageFault);
            if (currentPageFault && virtualIndex > physicalFrames-1) {
                System.out.println("Victim Frame: " + victimFrame);
            }

            input.next();

        }
        System.out.println("\nTotal Page Faults: " + pageFaults + "\n");

    }
    
    /*
    LRU method has one parameter to get the reference string buffer. It keeps
    track of elements in the physical frames into linked lists. It keeps track
    of referenced units in an array as well as the referended numbers of the current
    elements in the physical frames. Every time there is a fault, it finds
    the current element in the physical frame that has the fewests references 
    and replaces it with the newe element. Each step awaits user input before proceeding.
    */
    public static void LRU(IntBuffer refString) {
        LinkedList<Integer> framesQueue = new LinkedList<>();
        int pageFaults = 0;
        int victimFrame = 0;
        boolean currentPageFault = false;
        int currentElement;
        int[] pageUseTracker = new int[10];//stores how long its been since used
        int[] currentFrameTracker = new int[physicalFrames]; //hold tracker for current elements in physical frames
        int leastUsedFrame =0;
        int leastUsedNumber=0;
        
        System.out.println("\nLRU: (Press 1 then ENTER to continue after each step)");

        for (int virtualIndex = 0; virtualIndex < refString.limit(); virtualIndex++) {
            currentElement = refString.get(virtualIndex);            
            currentPageFault = false;
            
            //updates tracker
            for(int trackerIndex = 0; trackerIndex<pageUseTracker.length;trackerIndex++){
                if(currentElement!=trackerIndex){
                    pageUseTracker[trackerIndex]++;
                }else{
                    pageUseTracker[trackerIndex]=0;
                }
            }
            
            if (virtualIndex < physicalFrames) {
                framesQueue.add(virtualIndex, currentElement);                
                currentPageFault = true;
                pageFaults++;
            } else {
                currentPageFault = true;
                for (int element : framesQueue) {
                    if (currentElement == element) {
                        currentPageFault = false;
                        break;
                    }
                }
                
                //if a page fault occurs
                if (currentPageFault) {                    
                            
                    //update current physical frames tracker with use info from total tracker
                    for(int frameElement = 0; frameElement<physicalFrames;frameElement++){
                        currentFrameTracker[frameElement] = pageUseTracker[framesQueue.get(frameElement)];
                    }
                    
                    //compare frame use to find least recently used frame
                    for(int useNumberIndex = 0; useNumberIndex<physicalFrames;useNumberIndex++){
                        if(useNumberIndex == 0){
                            leastUsedNumber = currentFrameTracker[useNumberIndex];
                            leastUsedFrame = useNumberIndex;
                        }else{
                            if(currentFrameTracker[useNumberIndex]>leastUsedNumber){
                               leastUsedNumber = currentFrameTracker[useNumberIndex];
                               leastUsedFrame = useNumberIndex; 
                            }
                        }
                    }
                    victimFrame = framesQueue.remove(leastUsedFrame);
                    framesQueue.add(leastUsedFrame, currentElement);
                    currentPageFault = true;
                    pageFaults++;
                }
            }

            //print 
            System.out.println("\nReference String| " + currentElement);
            for (int elementIndex = 0; elementIndex < framesQueue.size(); elementIndex++) {
                System.out.println("Physical Frame " + elementIndex + "| " + framesQueue.get(elementIndex));
            }
            System.out.println("Page Fault: " + currentPageFault);
            if (currentPageFault && virtualIndex > physicalFrames-1) {
                System.out.println("Victim Frame: " + victimFrame);
            }

            input.next();

        }
        System.out.println("\nTotal Page Faults: " + pageFaults + "\n");

    }
    
    /*
    LFU method has one parameter to get the reference string buffer. It keeps
    track of elements in the physical frames into linked lists. It keeps track
    of the number of times an element is referenced units in an array as well 
    as the ones that are currently in the physical frames.
    
    Every time there is a fault, it finds
    the current element in the physical frame that has not been referenced the longest
    and replaces it with the newe element. Each step awaits user input before proceeding.
    */
    public static void LFU(IntBuffer refString) {
        LinkedList<Integer> framesQueue = new LinkedList<>();
        int pageFaults = 0;
        int victimFrame = 0;
        boolean currentPageFault = false;
        int currentElement;
        int[] pageUseTracker = new int[10];//stores how often virtual frames have been used
        int[] currentFrameTracker = new int[physicalFrames]; //hold tracker for current elements in physical frames
        int leastFrequentFrame =0;
        int leastFrequentNumber=0;
        
        System.out.println("\nLFU: (Press 1 then ENTER to continue after each step)");

        for (int virtualIndex = 0; virtualIndex < refString.limit(); virtualIndex++) {
            currentElement = refString.get(virtualIndex);            
            currentPageFault = false;
            
            //updates tracker
            for(int trackerIndex = 0; trackerIndex<pageUseTracker.length;trackerIndex++){
                if(currentElement==trackerIndex){
                    pageUseTracker[trackerIndex]++;
                }
            }
            
            if (virtualIndex < physicalFrames) {
                framesQueue.add(virtualIndex, currentElement);                
                currentPageFault = true;
                pageFaults++;
            } else {
                currentPageFault = true;
                for (int element : framesQueue) {
                    if (currentElement == element) {
                        currentPageFault = false;
                        break;
                    }
                }
                
                //if a page fault occurs
                if (currentPageFault) {                    
                            
                    //update current physical frames tracker with use info from total tracker
                    for(int frameElement = 0; frameElement<physicalFrames;frameElement++){
                        currentFrameTracker[frameElement] = pageUseTracker[framesQueue.get(frameElement)];
                    }
                    
                    //compare frame use to find least recently used frame
                    for(int useNumberIndex = 0; useNumberIndex<physicalFrames;useNumberIndex++){
                        if(useNumberIndex == 0){
                            leastFrequentNumber = currentFrameTracker[useNumberIndex];
                            leastFrequentFrame = useNumberIndex;
                        }else{
                            if(currentFrameTracker[useNumberIndex]<leastFrequentNumber){
                               leastFrequentNumber = currentFrameTracker[useNumberIndex];
                               leastFrequentFrame = useNumberIndex; 
                            }
                        }
                    }
                    victimFrame = framesQueue.remove(leastFrequentFrame);
                    framesQueue.add(leastFrequentFrame, currentElement);
                    currentPageFault = true;
                    pageFaults++;
                }
            }

            //print 
            System.out.println("\nReference String| " + currentElement);
            for (int elementIndex = 0; elementIndex < framesQueue.size(); elementIndex++) {
                System.out.println("Physical Frame " + elementIndex + "| " + framesQueue.get(elementIndex));
            }
            System.out.println("Page Fault: " + currentPageFault);
            if (currentPageFault && virtualIndex > physicalFrames-1) {
                System.out.println("Victim Frame: " + victimFrame);
            }

            input.next();

        }
        System.out.println("\nTotal Page Faults: " + pageFaults + "\n");

    }
    
    /*
    The main method assigns the first argument from the command line to the physical frames. 
    It has the menu method on loop until the program is exited using option 0. A switch
    case is used to call the options of the menu appropriately.
    */
    public static void main(String args[]){
        physicalFrames = Integer.parseInt(args[0]);//stores the number of physical frames 0-6
        
        int option;                  
        
        while(true){
            option = menu();
            switch (option){
                case 1:
                    //Read Reference String
                    referenceString = readReferenceString();
                    break;
                case 2:
                    referenceString = generateReferenceString();
                    break;
                case 3:
                    if(fullReferenceString(referenceString)){
                        printReferenceString(referenceString); //print current reference string
                    }else{
                        System.out.println("ERROR: Reference String is Empty!");
                    }                    
                    break;
                case 4:
                    if(fullReferenceString(referenceString)){
                        FIFO(referenceString);
                    }else{
                        System.out.println("ERROR: Reference String is Empty!");
                    }      
                    break;
                case 5:
                    if(fullReferenceString(referenceString)){
                        OPT(referenceString);
                    }else{
                        System.out.println("ERROR: Reference String is Empty!");
                    } 
                    break;   
                case 6:
                    if(fullReferenceString(referenceString)){
                        LRU(referenceString);
                    }else{
                        System.out.println("ERROR: Reference String is Empty!");
                    }
                    break;
                case 7:
                    if (fullReferenceString(referenceString)) {
                        LFU(referenceString);
                    } else {
                        System.out.println("ERROR: Reference String is Empty!");
                    }
                    break;
                default:
                    System.exit(0);   
            }
        }
       
    }
    
}
