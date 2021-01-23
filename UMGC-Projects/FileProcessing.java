/*
NAME: Dmitry Landy
DATE: September 20, 2020
FILE: FileProcessing.java
DESCRIPTION: This program focused on performing seven functions regarding 
file directories and file manipulation to include the following: selecting 
directory, listing contents of the directory, listing contents of the 
directories within the directory recursively, deleting files, viewing a file 
in hexadecimal format, encrypting a file with a password using XOR, and 
decrypting a file with a password using XOR.
 */

package Week5;

//imports
import java.util.Scanner;
import java.io.*;
import javax.swing.JFileChooser;
import java.util.ArrayList;

public class FileProcessing {
    
    public static ArrayList<File> allDirectories = new ArrayList<>();
    public static ArrayList<File> allFiles = new ArrayList<>();
    
    
    public static int menu(){
        int option;
        Scanner input = new Scanner(System.in);
        
        System.out.println("\nMENU:\n\n"                
                + "0-Exit\n"
                + "1 – Select directory\n"
                + "2 – List directory content (first level)\n"
                + "3 – List directory content (all levels)\n"
                + "4 – Delete file\n"
                + "5 – Display file (hexadecimal view)\n"
                + "6 – Encrypt file (XOR with password)\n"
                + "7 – Decrypt file (XOR with password)\n"
                + "\nSelect Option: ");
        option = input.nextInt();
        return option;
    }
    
    public static String checkDirectory(String currentDirectory){
        while(currentDirectory.equals("")){
            System.out.print("ERROR: ");
            currentDirectory = selectDirectory();
        }
        
        return currentDirectory;
    }
    
    public static String selectDirectory(){
        String directoryPath="";
        JFileChooser fc = new JFileChooser();
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("Please input the absolute path of the desired directory: ");
        directoryPath = input.nextLine();
        fc.setCurrentDirectory(new java.io.File(directoryPath)); 
        return directoryPath;
    }
    
    public static void printCurrentDirectory(String currentDirectory){
        File directoryPath = new File(currentDirectory);
        File directoryContents[] = directoryPath.listFiles();
        ArrayList<File> directories = new ArrayList<>();
        
        System.out.println("\nFILES:");
        for(File item:directoryContents){            
            if(item.isFile()){
                System.out.println(item);
            }
            if(item.isDirectory()){
                directories.add(item);
            }
        }
        
        System.out.println("\nDIRECTORIES:");
        for(File item:directories){
            System.out.println(item);
        }
    }
    
    public static void getAllRecursive(String currentDirectory){
        File path = new File(currentDirectory);
        File[] list = path.listFiles();           
        
        for (File item : list) {
            if (item.isDirectory()) {
                getAllRecursive(item.getAbsolutePath());
                allDirectories.add(item);
            } else {
                allFiles.add(item);
            }
        }        
    }
    
    public static void printAll(){
        
        System.out.println("\nFILES:");
        for(File item:allFiles){
            System.out.println(item);
        }
        
        System.out.println("\nDIRECTORIES:");
        for(File item:allDirectories){
            System.out.println(item);
        }        
    }
    
    public static void deleteFile(String currentDirectory){
                
        String fileName=""; 
        File file= new File(fileName);
        Scanner input = new Scanner(System.in);
        int counter = 0;
        
        while(!file.delete()){
            if(counter>0){
                System.out.print("ERROR: ");
            }
            System.out.println("Please input a file in current directory to delete: ");
            fileName = input.nextLine();
            file= new File(currentDirectory+"\\"+fileName);     
            counter++;
        }
        System.out.println(fileName+" deleted!");        
    }   
    
    public static void hexView(String currentDirectory){
        String fileName=""; 
        File file= new File(fileName);
        Scanner input = new Scanner(System.in);
        int counter = 0;    
        int offsetCounter = 0;
        
            while(!file.exists()||fileName.equals("")){
                if(counter>0){
                    System.out.print("ERROR: ");
                }
                System.out.println("Please input a file in current directory to view in hexadecimal format: ");
                fileName = input.nextLine();
                file= new File(currentDirectory+"\\"+fileName);     
                counter++;
            }         
        
        FileInputStream fis = null;
        try {            
            fis = new FileInputStream(currentDirectory+"\\"+fileName);            
            int currentByte = 0;
            int count = 0;
            
            try {
                System.out.print(String.format("\n%08X: ", offsetCounter));
                while ((currentByte = fis.read()) != -1) {
                    
                    if (currentByte != -1) {                        
                        System.out.printf("%02X ", currentByte);
                        count++;
                    }

                    if (count ==16) {
                        offsetCounter+=count;
                        System.out.print(String.format("\n%08X :", offsetCounter));                        
                        count = 0;
                    }
                }
            }catch (IOException e){
                System.out.println("Couldn't Read File: "+e);                
            }
                
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        System.out.println();
    }
    
    /*
    This option prompts the user for a password (max 256 bytes long, may 
    contain letters, digits, other characters) and then prompts the user 
    for a filename and encrypts the content of the selected file using 
    that password. 
    
    The encryption method is very simple: just XOR the password
    with the file content byte after byte; the password being shorter than the 
    file content, you must repeat the password as needed.
    */
    public static void encryptFile(String currentDirectory){
        String password="";
        int maxLength = 256;
        String fileName=""; 
        String encryptedFileName="";
        File file= new File(fileName);
        File encryptedFile;
        int counter =0;
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("\nPlease input a password for a file (<256 characters): ");
        password = input.nextLine();
        
        while(password.length()>maxLength){
            System.out.println("\nERROR: Password too long!");
            System.out.println("\nPlease input a password for a file (<256 characters): ");
            password = input.nextLine();
        }        
        
        while (!file.exists() || fileName.equals("")) {
            if (counter > 0) {
                System.out.print("ERROR: ");
            }
            System.out.println("\nPlease input a filename in current directory to encrypt: ");
            fileName = input.nextLine();
            file = new File(currentDirectory + "\\" + fileName);            
            counter++;
        }
        
        System.out.println("\nPlease input a file name to store encrypted file: ");
        encryptedFileName = input.nextLine();
        encryptedFile = new File(currentDirectory + "\\" + encryptedFileName);
                
        
        //read file byte by byte
        FileInputStream fis = null;
        FileOutputStream fos = null;
        
        try {            
            fis = new FileInputStream(currentDirectory+"\\"+fileName); 
            fos = new FileOutputStream(currentDirectory+"\\"+encryptedFileName, true);            
            
            int currentByte = 0;
            int count = 0;
            
            try {                
                while ((currentByte = fis.read()) != -1) {                     
                    int xorByte = (currentByte)^Integer.valueOf(password.charAt(count%(password.length()-1)));    
                    fos.write(xorByte);
                }
            }catch (IOException e){
                System.out.println("Couldn't Read File: "+e);                
            }
                
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        
    }
    
    public static void decryptFile(String currentDirectory){
        String password="";
        int maxLength = 256;
        String encryptedFileName=""; 
        String decryptedFileName="";
        File encryptedFile= new File(encryptedFileName);
        File decryptedFile;
        int counter =0;
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("\nPlease input a password for a file to decrypt(<256 characters): ");
        password = input.nextLine();
        
        while(password.length()>maxLength){
            System.out.println("\nERROR: Password too long!");
            System.out.println("\nPlease input a password for a file to decrypt (<256 characters): ");
            password = input.nextLine();
        }        
        
        while (!encryptedFile.exists() || encryptedFileName.equals("")) {
            if (counter > 0) {
                System.out.print("ERROR: ");
            }
            System.out.println("\nPlease input a filename in current directory to decrypt: ");
            encryptedFileName = input.nextLine();
            encryptedFile = new File(currentDirectory + "\\" + encryptedFileName);            
            counter++;
        }
        
        System.out.println("\nPlease input a file name to store decrypted file: ");
        decryptedFileName = input.nextLine();
        decryptedFile = new File(currentDirectory + "\\" + decryptedFileName);
                
        
        //read file byte by byte
        FileInputStream fis = null;
        FileOutputStream fos = null;
        
        try {            
            fis = new FileInputStream(currentDirectory+"\\"+encryptedFileName); 
            fos = new FileOutputStream(currentDirectory+"\\"+decryptedFileName, true);            
            
            int currentByte = 0;
            int count = 0;
            
            try {                
                while ((currentByte = fis.read()) != -1) {                     
                    int xorByte = (currentByte)^Integer.valueOf(password.charAt(count%(password.length()-1)));    
                    fos.write(xorByte);
                }
            }catch (IOException e){
                System.out.println("Couldn't Read File: "+e);                
            }
                
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        
    }
    
    public static void main(String args[]){
        int option;
        String currentDirectory="";              
        
        while(true){
            option = menu();
            switch (option){
                case 1:
                    currentDirectory = selectDirectory();
                    break;
                case 2:
                    currentDirectory = checkDirectory(currentDirectory);
                    printCurrentDirectory(currentDirectory);
                    break;
                case 3:
                    currentDirectory = checkDirectory(currentDirectory);
                    getAllRecursive(currentDirectory);
                    printAll();
                    break;
                case 4:
                    currentDirectory = checkDirectory(currentDirectory);
                    deleteFile(currentDirectory);
                    break;
                case 5:
                    currentDirectory = checkDirectory(currentDirectory);
                    hexView(currentDirectory);
                    break;   
                case 6:
                    currentDirectory = checkDirectory(currentDirectory);
                    encryptFile(currentDirectory);
                    break;
                case 7:
                    currentDirectory = checkDirectory(currentDirectory);
                    decryptFile(currentDirectory);
                    break;
                default:
                    System.exit(0);   
            }
        }
       
    }
    
}
