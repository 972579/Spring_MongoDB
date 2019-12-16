package com.mongo.test;

import com.mongo.bean.User;

import com.mongo.util.MongoQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration({"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String collectionName = "user";


    @Test
    public void add() {
        for (int i = 0; i < 20; i++) {
            User user = new User("测试" + i, i);
            mongoTemplate.save(user);
        }

    }

    @Test
    public void delete() {
        //只删除查询到的第一条
        //System.out.println(mongoTemplate.findAndRemove(new Query(Criteria.where("age").is(1)),User.class));


        //删除查询到的所有记录
        //System.out.println(mongoTemplate.findAllAndRemove(new Query(Criteria.where("age").gte(0)),User.class));


        //直接删除所有
        mongoTemplate.remove(new Query(Criteria.where("age").gte(0)), User.class);
    }


    @Test
    public void update() {
        //mongoTemplate.updateFirst() 删除符合条件的第一个
        mongoTemplate.upsert(new Query(Criteria.where("age").gte(100)),
                Update.update("name", "修改后的").set("title", "新增"), User.class); //删除符合条件的记录，没有符合条件的则添加

       /* mongoTemplate.updateMulti(new Query(Criteria.where("age").gte(0)),
                Update.update("name","修改后的").set("title","新增"),User.class);*/

        //查询并删除
       /* System.out.println(mongoTemplate.findAndModify(new Query(Criteria.where("age").is(0)),
                new Update().set("name","修改后的"),User.class));*/

    }

    @Test
    public void select() {

        //查询符合条件的第一个
        System.out.println(mongoTemplate.findOne(new Query(Criteria.where("age").gte(0)), User.class));

        //查询user集合所有数据
        System.out.println(mongoTemplate.findAll(User.class));

        //查询age >= 0 的所有数据
        System.out.println(mongoTemplate.find(new Query(Criteria.where("age").gte(0)), User.class));
    }


    @Test
    public void utilTest() {
        // 大于查询
        MongoQueryBuilder<User> queryBuilder = MongoQueryBuilder.build();
        queryBuilder.ge(User::getAge, 0);
        System.out.println(mongoTemplate.findOne(queryBuilder.getQuery(), User.class));
        System.out.println("----------------------");
        // 列表查询
        queryBuilder.pageList(1, 5);
        System.out.println(mongoTemplate.find(queryBuilder.getQuery(), User.class));
        System.out.println("----------------------");
        // 排序
        queryBuilder.orderByDesc(User::getAge);
        System.out.println(mongoTemplate.find(queryBuilder.getQuery(), User.class));
    }

}
