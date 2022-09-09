package com.aptech.duongquocvietexam.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.aptech.duongquocvietexam.entity.FeedBack;

import java.util.List;

@Dao
public interface FeedBackDao {
    @Insert
    long insertFeedBack(FeedBack feedBack);

    @Query("SELECT * FROM feedbacks")
    List<FeedBack> getAllFeedBack();
}
