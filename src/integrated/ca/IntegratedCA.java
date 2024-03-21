/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package integrated.ca;

/**
 *
 * @author Capitania
 */
public class IntegratedCA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // making sure that the console is working
        System.out.println("just checking");
        
        // create an instance of the DBConnector class
        DBConnector db = new DBConnector();
        
        //create a fake table name to test the connection and call the method of DBConnector to creat this table.
        String tableName = "test_table";
        db.createTable(tableName);
       
    }
    
}
