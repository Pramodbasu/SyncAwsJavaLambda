
package com.pramod.aws.lambda.api;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pramod.aws.lambda.api.dto.Orders;

public class CreateOrdersLambdas {
    //making mapper objects as final so that they can reused
    private final  ObjectMapper  mapper = new ObjectMapper();
    private final  DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
public APIGatewayProxyResponseEvent createOrder(APIGatewayProxyRequestEvent request) throws JsonProcessingException {


   Orders order= mapper.readValue(request.getBody(), Orders.class);

    Table table =dynamoDB.getTable(System.getenv("ORDERS_TABLE"));
    Item item = new Item()
            .withPrimaryKey("id",order.id)
            .withString("itemName",order.itemName)
            .withInt("quantity",order.quantity);
    table.putItem(item);
    return new APIGatewayProxyResponseEvent().withStatusCode(200)
            .withBody("Order placed successfully");
}
}