package com.example.momgoDB.mongoDB.collection;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection="person") //to make this class as a collection in our DB.
@JsonInclude(JsonInclude.Include.NON_NULL) //only non null fields to be included in the document.
public class Person {

    @Id //to make unique Id.
    private String personId;
    private String firstName;
    private String lastName;
    private Integer age;
    private List<String> hobbies;
    private List<Address> addresses;
}
