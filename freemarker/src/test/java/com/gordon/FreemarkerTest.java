package com.gordon;

import com.gordon.freemarker.TestBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

/**
 * freemarker基本用法
 * Created by gordon on 2018/8/11.
 */
public class FreemarkerTest {

    @Test
    public void testFreemarker() throws Exception{
        // 1、创建一个模板文件 ，可以由jsp改造
        // 2、创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 3、设置需要加载的模板文件的目录
        configuration.setDirectoryForTemplateLoading(new File("F:\\WorkSpace\\IDEA_Space\\Test" +
                "\\freemarker\\src\\main\\webapp\\WEB-INF\\ftl"));
        // 4、设置模板文件的编码格式
        configuration.setDefaultEncoding("utf-8");
        // 5、加载一个模板文件
        Template template = configuration.getTemplate("hello.ftl");
        // 6、创建一个数据集，可以使pojo也可以是map.推荐使用map
        Map dataMap = new HashMap();
        dataMap.put("hello","hello freemarker");
        // 创建一个pojo对象
        TestBean bean = new TestBean(1,"小明",19,"广州");
        dataMap.put("bean",bean);
        // 添加一个list
        List<TestBean> list = new ArrayList<>();
        list.add(new TestBean(1,"小明",19,"广州"));
        list.add(new TestBean(2,"小明2",20,"广州"));
        list.add(new TestBean(3,"小明3",21,"广州"));
        list.add(new TestBean(4,"小明4",22,"广州"));
        list.add(new TestBean(5,"小明5",23,"广州"));
        list.add(new TestBean(6,"小明6",24,"广州"));
        list.add(new TestBean(7,"小明7",25,"广州"));
        dataMap.put("list",list);
        // 添加日期类型
        dataMap.put("date",new Date());
        // null值测试
        dataMap.put("value",null);
        // 7、创建一个Writer对象，指定生成文件的路径及文件名
        Writer out = new FileWriter(new File("F:\\WorkSpace\\IDEA_Space\\Test" +
                "\\freemarker\\src\\main\\webapp\\WEB-INF\\html\\hello.html"));
        // 不一定要生成在项目目录下，也可以使磁盘任意位置，一般都生成在磁盘上，
        // 因为tomcat访问静态页面的能力不是那么好，使用nginx来访问静态页面更快

        // 8、生成静态页面
        template.process(dataMap,out);
        // 9、关闭流
        out.close();

    }








}
