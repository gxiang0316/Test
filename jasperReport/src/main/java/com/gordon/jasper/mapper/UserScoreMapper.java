package com.gordon.jasper.mapper;

import com.gordon.jasper.entity.UserScore;

import java.util.List;

/**
 * Created by gordon on 2018/8/31.
 */
public interface UserScoreMapper {

    List<UserScore> findUserById(int id);



}
