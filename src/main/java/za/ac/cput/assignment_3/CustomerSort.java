/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.assignment_3;

import java.util.Comparator;

/**
 *
 * @author imraan
 * Student number: 219361738
 */
public class CustomerSort implements Comparator<Customer>
{
    @Override
    public int compare(Customer o1, Customer o2)
    {
        return o1.getStHolderId().compareTo(o2.getStHolderId());
    }
}
