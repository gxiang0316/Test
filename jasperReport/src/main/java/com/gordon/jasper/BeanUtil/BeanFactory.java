package com.gordon.jasper.BeanUtil;

import com.gordon.jasper.entity.UserScore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gordon on 2018/8/19.
 */
public class BeanFactory {

    public static List<User> getListUser(){
        List<User> userList = new ArrayList<>();
        User user = new User("丽丽","女",18,"北京","1523645",new Date() + "1000");
        User user2 = new User("明明","男",21,"上海","12566548",new Date() + "103300");
        User user3 = new User("海海","男",25,"广州","1365897526",new Date() + "15635000");
        User user4 = new User("莎莎","女",34,"深圳","145268",new Date() + "10500");
        User user5 = new User("毅毅","男",16,"重庆","1758965432",new Date() + "106200");
        userList.add(user);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        return userList;
    }

    public static List<UserScore> getUserScoreList(){
        List<UserScore> scoreList = new ArrayList<>();
        UserScore xiaoming = new UserScore(123,"小明","语文",78);
        UserScore xiaoming1 = new UserScore(123,"小明","数学",85.5);
        UserScore xiaoming2 = new UserScore(123,"小明","英语",45);

        UserScore xiaoqiang = new UserScore(12352,"小强","语文",89);
        UserScore xiaoqiang1 = new UserScore(12352,"小强","数学",68);
        UserScore xiaoqiang2 = new UserScore(12352,"小强","英语",94);

        UserScore xiaoli = new UserScore(16223,"小丽","语文",68);
        UserScore xiaoli1 = new UserScore(16223,"小丽","数学",98);
        UserScore xiaoli2 = new UserScore(16223,"小丽","英语",87);

        scoreList.add(xiaoming);
        scoreList.add(xiaoming1);
        scoreList.add(xiaoming2);
        scoreList.add(xiaoqiang);
        scoreList.add(xiaoqiang1);
        scoreList.add(xiaoqiang2);
        scoreList.add(xiaoli);
        scoreList.add(xiaoli1);
        scoreList.add(xiaoli2);

        return scoreList;
    }


}
