package com.example.momgoDB.mongoDB.repository;

import com.example.momgoDB.mongoDB.collection.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends MongoRepository<Photo , String> {
}
