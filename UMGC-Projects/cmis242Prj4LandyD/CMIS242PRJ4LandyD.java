/*
Filename: CMIS242PRJ4LandyD.java
Author: Dmitry Landy
Date: July 14, 2020

Description: This program creates a database of property listings (PRJ4Property.txt) that include 
the Parcel ID, property address, number of bedrooms, square footage, property price,
and property status. This database is output in the IDE output window. A GUI is presented 
to the user, who can find, insert, and delete properties in the database. 
Additionally, property status can also be changed. After the program is exited,
the transactions from the program as well as the final modified database are
output in the IDE output window.
*/
package cmis242Prj4LandyD;

//imports
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/*
This class contains an enumeration named status that holds the property status, and an 
interface that has an abstract method to change the status. The second part of the
program are two classes. The first class named Property that constructs 
the property objects. The second class is the program core that contains the GUI
as well as how the buttons function. The program core also contains the main method
That reads the input file and displays the GUI.
*/
public class CMIS242PRJ4LandyD {
    /*
    This is the status for the property
    */
    public enum Status {
        FOR_SALE, UNDER_CONTRACT, SOLD;
    }//end Status
    
    /*
    This is used to change the status of the property
    */
    public interface StatusChangeable<E extends Enum <E>>{
        abstract void changeStatus(Enum E);        
    }//end StatusChangeable
    
    /*
    This class contains the constructor that creates the property object, a
    changeStatus method to change the status, and a toString method to output
    the object in a string.
    */
    public static class Property implements StatusChangeable{
        int parcelID;
        String propertyAddr;
        int numBedrooms;
        int sqrFootage;
        int propertyPrice;
        Status status;        
        
        /*
        This is the constructor and has 6 parameters that initialize the instance variables
        */
        public Property(int parcelID, String propertyAddr, int numBedrooms, int sqrFootage, int propertyPrice, Status status){
            this.status = status;
            this.parcelID = parcelID;
            this.propertyAddr = propertyAddr;
            this.numBedrooms = numBedrooms;
            this.sqrFootage = sqrFootage;
            this.propertyPrice = propertyPrice;            
        }//end Property
        
        /*
        This method overrides the changeStatus method to the given parameter status.
        */
       @Override
        public void changeStatus(Enum newStatus){
           this.status=(Status) newStatus;
        }//end changeStatus
        
        /*
        This method outputs the variables of the property in an appropriate way
        */
        @Override        
        public String toString(){            
            String output = "Parcel ID: "+parcelID+""
                    + "\nProperty address: "+propertyAddr+""
                    + "\nNumber of bedrooms: "+numBedrooms+""
                    + "\nSquare footage: "+sqrFootage+""
                    + "\nProperty Price: "+propertyPrice+""
                    + "\nCurrent status: "+status;            
            return output;
        }//end toString
    }//end Property
    
    /*
    This class contains the hashmap of the parcel ID (key) and their respective 
    property (value). This class also contains the array lists of transactions 
    that keep track of everything the user does to the database. The GUI elements
    are declared and initialized. The addComponents method creates the GUI frame
    and then adds the components to it. It also gives functions to the buttons to
    ensure they work properly. Lastly, it contains the main method that reads in
    the database text file and then displays the GUI for the user.
    */
    public static class ProgramCore{
        //This hashmap contains the database of properties
        public static HashMap<Integer, Property> database = new HashMap<Integer, Property>();        
        
        /*
        These array lists hold the processing implemented during the running 
        of the program. The are tags for each transaction followed by the 
        appropriate information about the transaction.  
        */
        ArrayList<String> transaction = new ArrayList<String>();
        ArrayList<Property> transactionProperty = new ArrayList<Property>();
        
        //GUI Elements
               
        //Labels
        private JLabel parcelIDLbl = new JLabel("Parcel ID:");
        private JLabel addressLbl = new JLabel("Address:");
        private JLabel bedroomsLbl = new JLabel("Bedrooms:");
        private JLabel sqrFootageLbl = new JLabel("Square Footage:");
        private JLabel propertyPriceLbl = new JLabel("Property Price:");

        //Text Fields
        private JTextField parcelIDTxt = new JTextField("");
        private JTextField addressTxt = new JTextField("");
        private JTextField bedroomsTxt = new JTextField("");
        private JTextField sqrFootageTxt = new JTextField("");
        private JTextField propertyPriceTxt = new JTextField("");
        
        //Buttons
        private JButton processBtn = new JButton("Process");
        private JButton changeStatusBtn = new JButton("Change Status");
        private JButton clearBtn = new JButton("Clear");
        private JButton exitBtn = new JButton("Exit");
         
        //Combo Box
        String[] processes = {"Find", "Delete", "Insert"};
        JComboBox processCB = new JComboBox(processes);
        String[] currentStatus = {"FOR_SALE", "UNDER_CONTRACT", "SOLD"};
        JComboBox statusCB = new JComboBox(currentStatus);        
        
        /*
        This method creates the GUI frame, adds the components to it, and ensures
        that the elements (buttons, text fields, combo buttons) work appropriately.
        */
        public void addComponents() {
            int frameHeight = 300;
            int frameWidth = 400;
            int border = 20;            
            int spacingInterval = 30;            
            int compHeight = 25;
            int compWidth = 150;
            
            //add frame
            JFrame frame = new JFrame("Real Estate Database"); 
            frame.setSize(frameWidth, frameHeight);
            frame.setLayout(null); //no layout manager
            
            // Function to set the Default Close Operation of JFrame
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            //add labels            
            frame.add(parcelIDLbl);
            frame.add(addressLbl);
            frame.add(bedroomsLbl);
            frame.add(sqrFootageLbl);
            frame.add(propertyPriceLbl); 
            
            //add text fields
            frame.add(parcelIDTxt);
            frame.add(addressTxt);
            frame.add(bedroomsTxt);
            frame.add(sqrFootageTxt);
            frame.add(propertyPriceTxt);            
            
            //add buttons
            frame.add(processBtn);
            frame.add(changeStatusBtn);
            frame.add(clearBtn);
            frame.add(exitBtn);
            
            //add combobuttons
            frame.add(processCB);
            frame.add(statusCB);
            
            //left side
            parcelIDLbl.setBounds(border, spacingInterval * 0 + border, compWidth, compHeight);
            addressLbl.setBounds(border, spacingInterval * 1 + border, compWidth, compHeight);
            bedroomsLbl.setBounds(border, spacingInterval * 2 + border, compWidth, compHeight);
            sqrFootageLbl.setBounds(border, spacingInterval * 3 + border, compWidth, compHeight);
            propertyPriceLbl.setBounds(border, spacingInterval * 4 + border, compWidth, compHeight);
            processBtn.setBounds(border, spacingInterval * 5 + border, compWidth, compHeight);
            changeStatusBtn.setBounds(border, spacingInterval * 6 + border, compWidth, compHeight);
            clearBtn.setBounds(border, spacingInterval * 7 + border, compWidth, compHeight);
            
            //right side
            parcelIDTxt.setBounds((frameWidth/2)+border, spacingInterval * 0 + border, compWidth, compHeight);
            addressTxt.setBounds((frameWidth/2)+border, spacingInterval * 1 + border, compWidth, compHeight);
            bedroomsTxt.setBounds((frameWidth/2)+border, spacingInterval * 2 + border, compWidth, compHeight);
            sqrFootageTxt.setBounds((frameWidth/2)+border, spacingInterval * 3 + border, compWidth, compHeight);
            propertyPriceTxt.setBounds((frameWidth/2)+border, spacingInterval * 4 + border, compWidth, compHeight);
            processCB.setBounds((frameWidth/2)+border, spacingInterval * 5 + border, compWidth, compHeight);
            statusCB.setBounds((frameWidth/2)+border, spacingInterval * 6 + border, compWidth, compHeight);
            exitBtn.setBounds((frameWidth/2)+border, spacingInterval * 7 + border, compWidth, compHeight);
            
            /*
            Add action listener to the process button. This creates a switch case
            each process: find, insert, and delete.
            */
            processBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    String selectedProcess = (String) processCB.getSelectedItem();                    
                                        
                    //switch case                    
                    switch(selectedProcess){
                        case "Find":
                            if (parcelIDTxt.getText().equals("")) {
                                JOptionPane.showMessageDialog(frame, "Parcel ID Text Field Must Be Filled!");
                            }//end if (parcelIDTxt.getText().equals("")) {
                            else{
                                try {
                                    Integer.parseInt(parcelIDTxt.getText());
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(frame, "Parcel ID Text Field Must Only Have Integers!");
                                    break;
                                }//end catch
                                if(database.get(Integer.parseInt(parcelIDTxt.getText()))==null){
                                    JOptionPane.showMessageDialog(frame,"No Parcel ID Exists");
                                }//end if(database.get(Integer.parseInt(parcelIDTxt.getText()))==null){
                                else{
                                    JOptionPane.showMessageDialog(frame, database.get(Integer.parseInt(parcelIDTxt.getText())));
                                    transaction.add("Find");
                                    transactionProperty.add(database.get(Integer.parseInt(parcelIDTxt.getText())));
                                }//end else                             
                            }//end else
                            break;
                            
                        case "Insert":                            
                            //check first for all fields
                            if (parcelIDTxt.getText().equals("")) {
                                JOptionPane.showMessageDialog(frame, "Parcel ID Text Field Must Be Filled!");
                                break;
                            }//end if statement
                            
                            try {
                                Integer.parseInt(parcelIDTxt.getText());
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(frame, "Parcel ID Text Field Must Only Have Integers!");
                                break;
                            }//end catch

                            try {
                                Integer.parseInt(bedroomsTxt.getText());
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(frame, "Bedrooms Text Field Must Only Have Integers!");
                                break;
                            }//end catch

                            try {
                                Integer.parseInt(sqrFootageTxt.getText());
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(frame, "Square Footage Text Field Must Only Have Integers!");
                                break;
                            }//end catch

                            try {
                                Integer.parseInt(propertyPriceTxt.getText());
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(frame, "Property Price Text Field Must Only Have Integers!");
                                break;
                            }//end catch
                            
                            //check if parcel ID exists
                            if(database.get(Integer.parseInt(parcelIDTxt.getText()))!=null){
                                JOptionPane.showMessageDialog(frame, "Parcel ID already Exists");
                            }
                            else{
                                Status currentStatus;
                                switch ((String) statusCB.getSelectedItem()) {
                                    case "FOR_SALE":
                                        currentStatus = Status.FOR_SALE;
                                        break;
                                    case "SOLD":
                                        currentStatus = Status.SOLD;
                                        break;
                                    default:
                                        currentStatus = Status.UNDER_CONTRACT;
                                        break;
                                }//end switch case
                                Property currentInsert = new Property(
                                        Integer.parseInt(parcelIDTxt.getText()),
                                        addressTxt.getText(),
                                        Integer.parseInt(bedroomsTxt.getText()), 
                                        Integer.parseInt(sqrFootageTxt.getText()), 
                                        Integer.parseInt(propertyPriceTxt.getText()), 
                                        currentStatus);
                                
                                database.put(Integer.parseInt(parcelIDTxt.getText()), currentInsert);                                                               
                                transaction.add("Insert");
                                transactionProperty.add(currentInsert);                                
                                JOptionPane.showMessageDialog(frame, "Parcel ID: "+parcelIDTxt.getText() + " Added!");
                                JOptionPane.showMessageDialog(frame, currentInsert);                                
                            }//end else                            
                            break;
                        case "Delete":
                            if (parcelIDTxt.getText().equals("")) {
                                JOptionPane.showMessageDialog(frame, "Parcel ID Text Field Must Be Filled!");
                            }//end if (parcelIDTxt.getText().equals("")) 
                            else {
                                if (database.get(Integer.parseInt(parcelIDTxt.getText())) == null) {
                                    JOptionPane.showMessageDialog(frame, "No Parcel ID Exists");
                                }//end if (database.get(Integer.parseInt(parcelIDTxt.getText())) == null) 
                                else {                                     
                                    transaction.add("Delete");
                                    transactionProperty.add(database.get(Integer.parseInt(parcelIDTxt.getText())));
                                    database.remove(Integer.parseInt(parcelIDTxt.getText()));
                                    JOptionPane.showMessageDialog(frame, "Parcel ID: "+parcelIDTxt.getText()+" Removed!");
                                }//end else
                            }//end else
                            break;
                    }//end switch                    
                }//end action performed
            });//end action listener
            
            /*
            Add action listener to the change status button. It checks the parcel
            ID is present and correct. Then it changes the status by calling the
            changeStatus method of the property.
             */
            changeStatusBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    //check Parcel ID is filled with an Integer                    
                    if (parcelIDTxt.getText().equals("")) {
                        JOptionPane.showMessageDialog(frame, "Parcel ID Text Field Must Be Filled!");
                    }// end if (parcelIDTxt.getText().equals(""))
                    else {
                        try {
                            Integer.parseInt(parcelIDTxt.getText());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Parcel ID Text Field Must Only Have Integers!");
                        }//end catch
                        if (database.get(Integer.parseInt(parcelIDTxt.getText())) == null) {
                            JOptionPane.showMessageDialog(frame, "No Parcel ID Exists");
                        }// end if (database.get(Integer.parseInt(parcelIDTxt.getText())) == null)
                        else {
                            Status currentStatus;
                            switch ((String) statusCB.getSelectedItem()) {
                                case "FOR_SALE":
                                    currentStatus = Status.FOR_SALE;
                                    break;
                                case "SOLD":
                                    currentStatus = Status.SOLD;
                                    break;
                                default:
                                    currentStatus = Status.UNDER_CONTRACT;
                                    break;
                            }//end switch case
                            database.get(Integer.parseInt(parcelIDTxt.getText())).changeStatus(currentStatus);
                            JOptionPane.showMessageDialog(frame, Integer.parseInt(parcelIDTxt.getText())+" Changed Status");
                            transaction.add("Change Status");
                            transactionProperty.add(database.get(Integer.parseInt(parcelIDTxt.getText())));
                        }//end else
                    }//end else                    
                }//end actionPerformed
            });//end action Listener
            
            /*
            Add action listener to clear button to reset all the text boxes 
            */
            clearBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    parcelIDTxt.setText("");
                    addressTxt.setText("");
                    bedroomsTxt.setText("");
                    sqrFootageTxt.setText("");
                    propertyPriceTxt.setText("");
                }//end actionPerformed
            });//end action Listener

            /*
            Add action listener to exit button to show a message box and then exit. 
            This also prints the transaction array lists and the modified database.
            */
            exitBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    //print transactions
                    System.out.println("TRANSACTIONS");
                    for (int iterator = 0; iterator < transaction.size(); iterator++) {
                        System.out.println(transaction.get(iterator));
                        System.out.println(transactionProperty.get(iterator)+"\n");                        
                    }// end for (int iterator = 0; iterator < transaction.size(); iterator++) 
                    //print final database
                    System.out.println("MODIFIED DATABASE\n");
                    for (Property value : database.values()) {
                        System.out.println(value+"\n");                        
                    }//end for (Property value : database.values())

                    JOptionPane.showMessageDialog(frame, "EXITING PROGRAM");
                    System.exit(0);
                }//end actionPerformed
            });//end action Listener
            
            // make GUI appear
            frame.setVisible(true);            
        }// end addComponents

        /*
        This main method creates a program core object so it can use the addcomponents
        method. The text file is read and then saved to the hashmap.
        */
        public static void main(String[] args) throws IOException {
            ProgramCore core = new ProgramCore();
            
            Property currentProperty;
            int currentParcelID;
            String currentAddress;
            int currentRooms;
            int currentFootage;
            int currentPrice;
            Status currentStatus;
            
            try{
                BufferedReader myReader = new BufferedReader(new FileReader("C:\\Users\\Dmitry\\Documents\\NetBeansProjects\\CMIS242\\src\\cmis242Prj4LandyD\\PRJ4Property.txt"));
                String read;
                System.out.println("DATAFIELD RECORDS:\n");
                
                while((read = myReader.readLine())!=null)
                {                    
                    String[] line = read.split(",|:");                     
                    currentParcelID = Integer.parseInt(line[0].trim());
                    currentAddress = line[1].trim();
                    currentRooms = Integer.parseInt(line[2].trim());
                    currentFootage = Integer.parseInt(line[3].trim());
                    currentPrice = Integer.parseInt(line[4].trim());
                    
                    switch (Integer.parseInt(line[5].trim())) {
                        case 0:
                            currentStatus = Status.FOR_SALE;
                            break;
                        case 1:
                            currentStatus = Status.SOLD;
                            break;
                        default:
                            currentStatus = Status.UNDER_CONTRACT;
                            break;
                    }//end switch case
                    
                    currentProperty = new Property(currentParcelID, currentAddress, currentRooms, currentFootage, currentPrice, currentStatus);
                    database.put(currentParcelID, currentProperty);//Add each property into the hashmap
                    
                    System.out.println(currentProperty+"\n");//print each property out in IDE output window
                }//end while ((read = myReader.readLine())!=null)
                
            } catch (FileNotFoundException e) {
                System.out.println("An exception occurred: File not found!");
                e.printStackTrace();
            }//end catch
                        
            core.addComponents();
        }//end main method
    }// end program core   
    
}//end class CMIS242PRJ4LandyD




    

