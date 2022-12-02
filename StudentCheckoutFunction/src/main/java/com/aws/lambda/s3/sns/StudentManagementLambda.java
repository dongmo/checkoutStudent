package com.aws.lambda.s3.sns;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StudentManagementLambda {

    ObjectMapper mapper = new ObjectMapper();

    public void handler(SNSEvent event){
        event.getRecords().forEach(snsRecord -> {
            StudentCheckout studentCheckout = null;

            try{
                studentCheckout = mapper.readValue(snsRecord.getSNS().getMessage(), StudentCheckout.class);
                System.out.println(studentCheckout);
            }catch (JsonProcessingException e){
                throw new RuntimeException(e);
            }
        });
    }
}
