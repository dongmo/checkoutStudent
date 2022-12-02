package com.aws.lambda.s3.sns;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StudentCheckoutLambda {

    AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
    ObjectMapper mapper = new ObjectMapper();
    AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();

    public void handler(S3Event s3Event){
        s3Event.getRecords().forEach(record ->{
            S3ObjectInputStream s3ObjectInputStream = s3
                    .getObject(record.getS3().getBucket().getName(),record.getS3().getObject().getKey())
                    .getObjectContent();
            try {
                List<StudentCheckout> students = Arrays.asList(mapper.readValue(s3ObjectInputStream, StudentCheckout[].class));
                System.out.println(students);
                students.forEach(checkoutEvent -> {
                   int score = Integer.parseInt(checkoutEvent.testScore);
                    if( score> 90 || score == 90 ){
                        checkoutEvent.grade = "A";
                    } else if (score < 90 && score > 50) {
                        checkoutEvent.grade = "B";
                    } else if (score < 50) {
                        checkoutEvent.grade = "C";
                    }
                    try {
                        sns.publish(System.getenv("STUDENT_CHECKOUT_TOPIC"), mapper.writeValueAsString(checkoutEvent));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}
