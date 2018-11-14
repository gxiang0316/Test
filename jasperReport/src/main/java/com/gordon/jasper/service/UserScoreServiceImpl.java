package com.gordon.jasper.service;

import com.gordon.jasper.entity.UserScore;
import com.gordon.jasper.mapper.UserScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gordon on 2018/9/1.
 */
@Service
public class UserScoreServiceImpl implements UserScoreService {

    @Autowired
    private UserScoreMapper userScoreMapper;

    @Override
    public List<UserScore> findUserById(int id) {
        return userScoreMapper.findUserById(id);
    }
}
