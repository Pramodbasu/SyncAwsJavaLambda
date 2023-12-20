
package com.pramod.aws.lambda.api.dto;
public class Orders
{
public int id;
public String itemName;
public int quantity;

public Orders(int id,String itemName,int quantity)
{
    this.id=id;
    this.itemName=itemName;
    this.quantity=quantity;
}

    public Orders() {

    }
}