
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;



public class customerSystem {
    static Scanner in = new Scanner(System.in); 
    public static void main(String[] args) throws Exception {

        String[][]customer = new String[100][4]; //Name, ID, Address, Email
        String[][]orders = new String[100][4]; //ID, restaurant, Date, Amount Paid
        boolean exit = false;
        int custCount = 0; //The total current amount of customers registered
        int orderCount = 0; //The total current amount of orders made


        //---------------------------------------------------Main menu Loop---------------------------------------------------
        do{
        int choice;
        BORDER();
        System.out.println("What function would you like to access?");
        System.out.println("Customer registration - 1");
        System.out.println("Add Customer Order    - 2");
        System.out.println("Display Past Orders   - 3");
        System.out.println("Edit Customer         - 4");
        System.out.println("List Customer         - 5");
        System.out.println("Exit Program          - 6");
        BORDER();
        System.out.print("Please enter the digit (1-6): ");
        choice = in.nextInt();
        BORDER();

        //---------------------------------------------------Choices of methods---------------------------------------------------
            switch(choice){
                case 1: custCount = custReg(customer, custCount); break;
                case 2: orderCount = addOrder(orders, orderCount, customer, custCount);
                    break;
                case 3: pastOrder(orders, orderCount); break;
                case 4: editCust(customer, custCount); break;
                case 5: listCust(customer, custCount, orders, orderCount); break;
                case 6: System.out.println("Goodbye~!");
                        BORDER();
                        exit = true; 
                        break;
                default: System.out.println(choice + " is not a function, try again.");
            }
        }while(!exit);
    }

    //---------------------------------------------------CUSTOMER REGISTRATION METHOD---------------------------------------------------
    public static int custReg(String[][]customer, int custCount) {
        System.out.println("You have chosen Customer Registration!");

        boolean IDTaken = false;

        //Check if the array is full
        if(custCount<customer.length){
            System.out.print("Enter customer name> ");
            in.nextLine();
            String nameBuffer = in.nextLine();

            System.out.print("Enter customer ID (6-digits only)> ");
            String IDBuffer = in.next();
            in.nextLine();

            System.out.print("Enter customer address> ");
            String addressBuffer = in.nextLine();

            System.out.print("Enter customer email> ");
            String emailBuffer = in.nextLine();

            System.out.println();

            for(int i = 0; i<custCount; i++ ){
                if(IDBuffer.equalsIgnoreCase(customer[i][1])){
                    IDTaken = true;
                }
            }

            if(!IDTaken){
                if(IDBuffer.length() == 6){
                    customer[custCount][0] = nameBuffer;
                    customer[custCount][1] = IDBuffer;
                    customer[custCount][2] = addressBuffer;
                    customer[custCount][3] = emailBuffer;
                    custCount++;
                    System.out.println("Registration of customer ID "+ IDBuffer + " complete");
                }else{
                    System.out.println("ID must be 6 digits long!");
                }
            }else{
                System.out.println("The ID : " + IDBuffer + " has already been taken!");
            }

        }else{
            System.out.println("I'm sorry, but the registration is full!");
        }
        return(custCount);
    }

    //---------------------------------------------------ADD ORDER METHOD---------------------------------------------------
    public static int addOrder(String[][]order, int orderCount, String[][]customer, int custCount) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();
        boolean IDexist = false;

        System.out.println("You have chosen Add Customer Order!");
        System.out.print("Enter an ID> ");
        String ID = in.next();
        System.out.print("How many Orders would you like to make? > ");
        int numOrder = in.nextInt();
        in.nextLine();

        //Checks if the ID exist in the customer array
        for(int c = 0; c<custCount; c++){
            if(ID.equals(customer[c][1])){
                IDexist = true;
            }
        }

        //Input of details if ID exist. If not, output error message
        if(IDexist){
            //Repeat as many orders as needed
            for(int i = 0; i<numOrder; i++){
                orderCount++;
                System.out.println("------------------------------");
                order[(orderCount-1)][0] = ID;
                System.out.print("Enter name of restaurant> ");
                order[(orderCount-1)][1] = in.nextLine();
                order[(orderCount-1)][2] = dtf.format(now);
                System.out.print("Enter the amount paid> RM");
                order[(orderCount-1)][3] = String.valueOf(in.nextDouble());
                in.nextLine();

            }  
            System.out.println("--- "+ numOrder +" orders have been recorded under the ID: "+ ID +"! ---");
        }else{
            System.out.println("ID does not exist!");
        }
        return(orderCount);
    }

    //---------------------------------------------------VIEW PAST ORDER BY AMOUNT METHOD---------------------------------------------------
    public static void pastOrder(String[][]order, int orderCount) {

        int errorCount = 0;
        int dispCount = 1;

        System.out.println("You have chosen Display Past Orders!");    
        System.out.print("Enter an ID> ");
        String ID = in.next();
    
        //Check if the ID has made an order
        for(int i = 0; i<orderCount; i++){
            if(ID.equals(order[i][0])){
            }else{
                errorCount++;
            }

        }

        //If there is an ID, go to if...... If no ID, go to else
        if(errorCount!=orderCount){
            for(int j = 0; j<orderCount; j++){
                if(ID.equals(order[j][0])){
                    System.out.println("--------------Order "+dispCount+"-------------------------");
                    System.out.println("Restaurant> "+ order[j][1]);
                    System.out.println("Date> "+ order[j][2]);
                    System.out.println("Amount Paid> " + order[j][3]);
                    System.out.println();
                    dispCount++;

                }
            }
        }else{
            System.out.println("Sorry, ID has not made any order yet!");
        }
    }

    //---------------------------------------------------EDIT CUSTOMER METHOD---------------------------------------------------
    public static void editCust(String[][]customer, int custCount) {
        boolean noID = true;
        System.out.println("You have chosen Edit Customer!");
        System.out.println("Please enter your ID : ");
        String targetID = in.next();

        //Search for the ID in the customer array
        for(int i= 0; i<custCount; i++){
            //If-elseif-else for different executions
            if(customer[i][1].equals(targetID)){
                noID = false;
                BORDER();
                System.out.println("What would you like to edit?");
                System.out.println("Name --------------------- 1");
                System.out.println("Address ------------------ 2");
                System.out.println("Email -------------------- 3");
                System.out.println("Cancel ------------------- 4");
                System.out.print("Your choice : ");
                int choice = in.nextInt();
                in.nextLine(); 
                BORDER();

                if(choice == 1){
                    System.out.print("Enter your new name: ");
                    customer[i][0] = in.nextLine(); 
                    System.out.println("--- New name edited! ---");
                }else if(choice == 2){
                    System.out.print("Enter your new Address: ");
                    customer[i][2] = in.nextLine(); 
                    System.out.println("--- New address edited! ---");
                } else if(choice == 3){
                    System.out.print("Enter your new Email: ");
                    customer[i][3] = in.next(); 
                    System.out.println("--- New Email edited! ---");
                }else{
                    System.out.println("Editing cancelled!");
                }

            }

        }
        if(noID){
            System.out.println("The ID : " + targetID + " does not exist!");
        }

    }

    //---------------------------------------------------LIST CUSTOMER METHOD---------------------------------------------------
    public static void listCust(String[][]customer, int custCount, String[][]order, int orderCount) {
        System.out.println("You have chosen List Customer!");

        //If user enters 3, it would display any customer with more than 2 orders
        System.out.print("Enter the minimum amount of orders> ");
        int orderAmnt = in.nextInt();

        boolean noID = true;
    
        //Take every customer ID
        for(int i=0; i<custCount; i++){
            int CustOrdAmnt = 0;
            //Compare customer ID with the ID in the orders
            for(int j = 0; j<orderCount; j++){
                if(customer[i][1].equalsIgnoreCase(order[j][0])){
                    CustOrdAmnt++;
                }
            }
            
            //Prints customer information if they have made more than x orders
            if(CustOrdAmnt>=orderAmnt){
                System.out.println("--------------Customer "+(i+1)+"-------------------------");
                System.out.println("Name = " + customer[i][0]);
                System.out.println("ID = " + customer[i][1]);
                System.out.println("e-mail = " + customer[i][3]);
                noID = false;
            }
        }
        
        //noID will only be true if there is no ID that has more than x orders
        if(noID){
            System.out.println("There is no ID that has the same or more than " + orderAmnt);
        }
    }

    //---------------------------------------------------PRINT BORDER METHOD---------------------------------------------------
    public static void BORDER(){
        System.out.println("------------------------------------------------");
    }
}