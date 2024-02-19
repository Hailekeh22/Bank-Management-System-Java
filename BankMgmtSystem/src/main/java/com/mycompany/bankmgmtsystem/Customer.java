package com.mycompany.bankmgmtsystem;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class Customer {
    
    private IntegerProperty accountnumberproperty;
    private StringProperty firstnameproperty;
    private StringProperty lastnameproperty;
    private StringProperty addressproperty;
    private IntegerProperty amountproperty;
    
    public Customer() {
    
        this.accountnumberproperty = new SimpleIntegerProperty();
        this.firstnameproperty = new SimpleStringProperty();
        this.lastnameproperty = new SimpleStringProperty();
        this.addressproperty = new SimpleStringProperty();
        this.amountproperty = new SimpleIntegerProperty();
    
    }
    
    //account number
    public int getcustaccount() {
        return accountnumberproperty.get();
    }
    
    public void setcustaccount(int accountnumber) {
        this.accountnumberproperty.set(accountnumber);
    }
    
    public IntegerProperty getcustomeraccount() {
        return accountnumberproperty;
    }
    //accountnumber ends
    
    
    //first name  
    public String getcustlname() {
        return firstnameproperty.get();
    }
    
    public void setcustlname(String firstname) {
        this.firstnameproperty.set(firstname);
    }
    
    public StringProperty getcustomerlname(){
        return firstnameproperty;
    }  
    //first name ends
    
    
    //last name  
    public String getcustfname() {
        return lastnameproperty.get();
    }
    
    public void setcustfname(String lastname) {
        this.lastnameproperty.set(lastname);
    }
    
    public StringProperty getcustomerfname(){
        return lastnameproperty;
    }
    //lastname ends
    
    
    //address  
    public String getcustaddress() {
        return addressproperty.get();
    }
    
    public void setcustaddress(String address) {
        this.addressproperty.set(address);
    }
    
    public StringProperty getcustomeraddress(){
        return addressproperty;
    }
    //address ends
    
    
    //amount 
    public int getcustamount() {
        return amountproperty.get();
    }
    
    public void setcustamount(int amount) {
        this.amountproperty.set(amount);
    }
    
    public IntegerProperty getcustomeramount() {
        return amountproperty;
    }
    //amount ends
    


}