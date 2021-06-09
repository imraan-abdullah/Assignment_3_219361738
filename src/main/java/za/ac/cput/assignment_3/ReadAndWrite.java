/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.assignment_3;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author imraan
 * Student number: 219361738
 */
public class ReadAndWrite 
{
    private ObjectInputStream input;
    ArrayList <Customer> customerList = new ArrayList();
    ArrayList <Supplier> supplierList = new ArrayList();
      
    public void openFile()
    {
        try
        {
           input = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
           System.out.println("Serialized File successfuly opened");     
        }  
        catch(FileNotFoundException f)
        {
            System.out.println("the file was not found");
        }
        catch(IOException e)
        {
            System.out.println("File did not write");
        }
    }
    
    public void sortObjcects()
    {
        try
        {
            System.out.println("Objects are being retrieved from serialized file and stored into ArrayLists...");
            while(true)
            {
               Stakeholder object = (Stakeholder) input.readObject();
               if (object instanceof Customer)
               {
                   customerList.add((Customer) object);
               }
               else if(object instanceof Supplier)
               {
                   supplierList.add((Supplier)object);
               }
               customerList.sort(new CustomerSort());
               supplierList.sort(new SupplierSort());
            }
        }
        catch(EOFException eof)
        {
            System.out.println("End of file has been reached");
        }
        catch(Exception e)
        {
            System.out.println("An error has occured while trying to sort objects");
        }
    }
    
    public void closeFile()
    {
        try
        {
            input.close();
            System.out.println("Serialized file has now been closed");
        }
        catch(IOException ioe)
        {
            System.out.println("error closing file.");
        }
    }     
    
    public int getAge(Customer c)
    {
        try
        {
            LocalDate cDateOfBirth = LocalDate.parse(c.getDateOfBirth());
            LocalDate curDate = LocalDate.now();
            return Period.between(cDateOfBirth, curDate).getYears();
        }
        catch(Exception e)
        {
            System.out.println("Unexpected error while getting customer age");
        }
        return 0;   
    }

    public String dobFormat(Customer c)
    {
        try
        {
            LocalDate formatDOB = LocalDate.parse(c.getDateOfBirth());
            DateTimeFormatter newDateForm = DateTimeFormatter.ofPattern("dd MMM yyyy");
            return newDateForm.format(formatDOB);
        }
        catch(Exception e) 
        {
            System.out.println("Unexpected error while formating customers date of birth");
        }
    return null;
    }
        
    public void writeCustomerToFile()
    {
        int customerCanRent = 0;
        int customerCannotRent = 0;

        try
        {
            FileWriter cfw = new FileWriter("customerOutFile.txt");
            BufferedWriter cbw = new BufferedWriter(cfw);

            System.out.println("Customer text file is open for writing");

            cbw.write("========================= CUSTOMER =========================\n");
            cbw.write(String.format("%-5s\t%-8s\t%-8s\t%-15s\t%-10s\n", "ID", "Name", "Surname", "Date of birth", "Age"));
            cbw.write("============================================================\n");
            
            for (int i = 0; i < customerList.size(); i++) 
            {
                cbw.write(String.format("%-5s\t%-8s\t%-8s\t%-15s\t%-10s\n", customerList.get(i).getStHolderId(), customerList.get(i).getFirstName(), 
                                                                            customerList.get(i).getSurName(), dobFormat(customerList.get(i)), getAge(customerList.get(i))));
                if(customerList.get(i).getCanRent() == true)
                {
                    customerCanRent++;
                }
                else
                {
                    customerCannotRent++;
                }
            }
            cbw.write("\n");
            cbw.write(String.format("%-10s\t%-10s\n", "Number of customers who can rent: ", customerCanRent, "\n"));
            cbw.write(String.format("%-10s\t%-10s\n", "Number of customers who cannot rent: ", customerCannotRent, "\n"));
            cbw.close();
        }
        catch(IOException ioe)
        {
            System.out.println("An error occured while writing to the Customer text file");
        }
        finally
        {
            System.out.println("Customer text file is now closed");
        }
    }

    public void writeSupplierToFile()
    {
        try
        {
            FileWriter sfw = new FileWriter("SupplierOutFile.txt");
            BufferedWriter sbw = new BufferedWriter(sfw);

            System.out.println("Supplier text file is open for writing");

            sbw.write("=========================== SUPPLIERS ===========================\n");
            sbw.write(String.format("%-5s\t%-20s\t%-10s\t%-15s\n", "ID", "Name", "Prod Type", "Description"));
            sbw.write("=================================================================\n");

            for (int i = 0; i < supplierList.size(); i++) 
            {
                sbw.write(String.format("%-5s\t%-20s\t%-10s\t%-15s\n", supplierList.get(i).getStHolderId(), supplierList.get(i).getName(), 
                                                                       supplierList.get(i).getProductType(), supplierList.get(i).getProductDescription()));
            }
            sbw.close();
        }
        catch(IOException ioe)
        {
            System.out.println("An error occured while writing to the Supplier text file");
        }
        finally
        {
            System.out.println("Supplier text file is now closed");
        }

    }
 
    public static void main(String[] args) 
    {
        ReadAndWrite rnw = new ReadAndWrite();
        rnw.openFile();
        rnw.sortObjcects();
        rnw.closeFile();
        rnw.writeCustomerToFile();
        rnw.writeSupplierToFile();
    }
}
