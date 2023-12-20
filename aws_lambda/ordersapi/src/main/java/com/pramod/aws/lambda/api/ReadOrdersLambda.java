package com.pramod.aws.lambda.api;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pramod.aws.lambda.api.dto.Orders;

import java.util.List;
import java.util.stream.Collectors;

public class ReadOrdersLambda {

    private final ObjectMapper mapper = new ObjectMapper();

    private final AmazonDynamoDB DynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
    public APIGatewayProxyResponseEvent readOrder(APIGatewayProxyRequestEvent request) throws JsonProcessingException {



        //reason this is diff grom createOrder class is we need get table from object but here we don't need it

        ScanResult scan = DynamoDB.scan(new ScanRequest().withTableName(System.getenv("ORDERS_TABLE")));

        List<Orders> orders=  scan
                .getItems()
                .stream()
                .map(item->new Orders(Integer.parseInt(item.get("id").getN()),
                        item.get("itemName").getS(),
                        Integer.parseInt(item.get("quantity").getN())))
                .collect(Collectors.toList());
       String output =  mapper.writeValueAsString(orders);

        return new APIGatewayProxyResponseEvent().withStatusCode(200)
                .withBody(output);
    }
}