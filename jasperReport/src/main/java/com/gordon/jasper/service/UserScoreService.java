package com.gordon.jasper.service;

import com.gordon.jasper.entity.UserScore;

import java.util.List;

/**
 * Created by gordon on 2018/8/19.
 */
public interface UserScoreService {

    List<UserScore> findUserById(int id);
}
