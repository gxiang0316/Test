package com.gordon.jasper.controller;

import ch.qos.logback.classic.servlet.LogbackServletContextListener;
import com.gordon.jasper.BeanUtil.BeanFactory;
import com.gordon.jasper.JDBCUtil.JDBCUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.servlet.HttpServletBean;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gordon on 2018/8/19.
 */
@Controller
@RequestMapping("jasper")
public class JasperController {

    private final Logger logger = LoggerFactory.getLogger(JasperController.class);

    /**
     * 测试Bean作为数据源
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "testBean", method = RequestMethod.GET)
    public String createReportByBean(Model model) {
        // 创建报表数据源
        JRDataSource dataSource = new JRBeanCollectionDataSource(BeanFactory.getListUser());
        // 设置报表数据源
        model.addAttribute("jrDatasource", dataSource);
        // 设置spring-mvc.xml报表视图中定义的value=jrDatasource数据源的值

        // 指定format格式，告诉视图控制器该渲染成何种视图
        model.addAttribute("format", "pdf");
        /**
         * public JasperReportsMultiFormatView() {
         this.formatMappings.put("csv", JasperReportsCsvView.class);
         this.formatMappings.put("html", JasperReportsHtmlView.class);
         this.formatMappings.put("pdf", JasperReportsPdfView.class);
         this.formatMappings.put("xls", JasperReportsXlsView.class);
         this.formatMappings.put("xlsx", JasperReportsXlsxView.class);
         }
         */
        return "testReport";// 返回哪个视图  testReport.jasper
    }

    /**
     * 测试map作为数据源
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "testMap", method = RequestMethod.GET)
    public void createReportByMap(HttpServletRequest request, HttpServletResponse response, Model
            model) {
        String fileName = "testMapInColumnFooterReport";
        // 创建报表数据源填充feild字段
        JRDataSource dataSource = new JRBeanCollectionDataSource(BeanFactory.getListUser());
        // 创建数据源填充parameters字段
        Map<String, Object> dataMap = new HashMap();
        dataMap.put("developer", "科比");
        dataMap.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        try {
            ServletContext servletContext = request.getServletContext();
            System.out.println("realPath: " + servletContext.getRealPath("/WEB-INF/jasper/" +
                    fileName + ".jrxml"));
            System.out.println("contextPath: " + servletContext.getContextPath());
            // 获取源文件路径
            String filePath = servletContext.getRealPath("/WEB-INF/jasper/" + fileName + ".jrxml");
            System.out.println("filePath : " + filePath);
            // 编译成.jasper文件
            String jasperFilePath = JasperCompileManager.compileReportToFile(filePath);
            System.out.println("调用代码手动编译成.jasper文件后的路径： " + jasperFilePath);
            // 两种方式：
//            // 第一种：数据填充生成.jrprint文件，返回该文件路径
//            String printFilePath =
//                    JasperFillManager.
//                            fillReportToFile(jasperFilePath,dataMap,dataSource);
//            System.out.println("printFilePath : " + printFilePath);
//            // 预览文件  false表示不使用xml文件
//            JasperViewer jasperViewer = new JasperViewer(printFilePath,false);
//            jasperViewer.pack();
//            jasperViewer.setVisible(true);
//            // 导出文件,注意这里的传入的参数是.jrprint文件路径
//            JasperExportManager.exportReportToHtmlFile(printFilePath);
//            // runReportToHtmlFile方法执行完后会再项目的部署路径下生产一个跟.jrprint文件名相同的.html文件
//            String htmlFilePath = servletContext.getRealPath("/WEB-INF/jasper/") + fileName + "
// .html";
//            System.out.println("htmlFilePath : " + htmlFilePath);

            // 第二种：填充数据生成一个.jasper文件并返回一个JasperPrint对象
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFilePath, dataMap,
                    dataSource);
            String jasperPrintFileName = jasperPrint.getName();
            System.out.println("jasperPrintFileName : " + jasperPrintFileName);
            //预览文件  false表示不使用xml文件
            JasperViewer jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.pack();
            jasperViewer.setVisible(true);
            //导出文件，传入一个JasperPrint对象并指定导出后文件的路径
            JasperExportManager.exportReportToHtmlFile(jasperPrint,
                    "F:\\WorkSpace\\IDEA_Space\\Test\\jasperReport\\target\\com.gordon" +
                            ".jasper\\WEB-INF\\jasper\\" + fileName + ".html");
            String htmlFilePath = servletContext.getRealPath("/WEB-INF/jasper/") + fileName + "" +
                    ".html";
            System.out.println("htmlFilePath : " + htmlFilePath);

            //第三种方式：这种方式导出文件最方便，缺点是：没有生成.jrprint文件，生成JasperPrint对象也是不可见的，无法预览
//            String runReportToHtmlFile = JasperRunManager.runReportToHtmlFile(jasperFilePath,
// dataMap,dataSource);
////          // runReportToHtmlFile方法执行完后会再项目的部署路径下生产一个跟.jasper文件名相同的.html文件
//            System.out.println("htmlFilePath : " + runReportToHtmlFile);
            /**
             * *用到重定向和转发时要注意的问题：

             在做增删改请求操作的时候不可用转发，只能用重定向
             原因：由于转发之后地址栏不变，请求（request）内容不变，再次刷新页面的时候，
             请求将再次发送，造成重复操作执行，造成错误。

             在做查询操作时，只能转发，不能使用重定向。
             原因：重定向后请求会将原本的查询请求覆盖，刷新之后将得不到要查询的数据。
             */
            //response.sendRedirect(htmlFilePath);
        } catch (JRException e) {
            e.printStackTrace();
        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
        //JRDataSource dataSource = new JRMapCollectionDataSource(new List<dataMap>());
        //JRDataSource dataSource1 = new JRMapArrayDataSource(new Object[3]); 数组类型
    }

    /**
     * 测试分组，参考：https://blog.csdn.net/cswhale/article/details/21396439
     * 这里有个问题：如果void方法参数中不加response，
     * springmvc @RequestMapping注解中指定的路径是要返回视图的，就会经过视图解析器，
     * 最后报错说找不到testGroup.jasper文件，以下代码确实没有生成testGroup.jasper文件。
     * 而上面那个方法是生成了.jasper文件因此没有报错，所以在void方法中最好加入response参数。
     * 详细解析参考：https://blog.csdn.net/yh_zeng2/article/details/75136614
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "testGroup", method = RequestMethod.GET)
    public void createReportGroup(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "testGroup";
        JRDataSource dataSource = new JRBeanCollectionDataSource(BeanFactory.getUserScoreList());
        ServletContext servletContext = request.getServletContext();
        String xmlPath = servletContext.getRealPath("/WEB-INF/jasper/") + fileName + ".jrxml";
        System.out.println("xmlPath : " + xmlPath);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("class", "五年级三班");
        dataMap.put("createDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        try {

            JasperReport jasperReport = JasperCompileManager.compileReport(xmlPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, dataMap,
                    dataSource);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.pack();
            jasperViewer.setVisible(true);
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    "F:\\WorkSpace\\IDEA_Space\\Test\\jasperReport\\target\\com.gordon" +
                            ".jasper\\WEB-INF\\jasper\\" + "学生成绩表" + ".pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试导出HTML，附图片
     * 1、在ireport中添加一个Image组件放到任意位置
     * 2、在ireport的Paramters中添加一个imageURL的参数
     * 3、设置组件的属性：
     * Image Expression : $P{imageURL}
     * Expression Class : java.lang.String
     * Is Lazy 打勾
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "testHtmlImage", method = RequestMethod.GET)
    public void createReportGroupAddImage(HttpServletRequest request, HttpServletResponse
            response) {
        String fileName = "testGroup";
        JRDataSource dataSource = new JRBeanCollectionDataSource(BeanFactory.getUserScoreList());
        ServletContext servletContext = request.getServletContext();
        String xmlPath = servletContext.getRealPath("/WEB-INF/jasper/") + fileName + ".jrxml";
        System.out.println("xmlPath : " + xmlPath);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("class", "五年级三班");
        dataMap.put("createDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        String imagePath = servletContext.getRealPath("/WEB-INF/image/icon.png");
        dataMap.put("imageURL", imagePath);
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(xmlPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, dataMap,
                    dataSource);

            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.pack();
            jasperViewer.setVisible(true);
            // jasperViewer和导出的pdf文件中都有图片，但导出的html文件图片不显示
            String reportHtmlPath = "F:\\WorkSpace\\IDEA_Space\\Test\\jasperReport\\target\\com" +
                    ".gordon" +
                    ".jasper\\WEB-INF\\jasper\\" + "学生成绩表";
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportHtmlPath + ".html");
            JasperExportManager.exportReportToPdfFile(jasperPrint, reportHtmlPath + ".pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    /**
     * 优化上一个方法，解决HTML不显示图片问题
     * 在ireport中将image组件的类型设置成InputStream类型
     */
    @RequestMapping(value = "testHtmlImage2", method = RequestMethod.GET)
    public void createReportGroupAddImageInHtml(HttpServletRequest request, HttpServletResponse
            response)
            throws FileNotFoundException {
        String fileName = "testImageInHtml";
        JRDataSource dataSource = new JRBeanCollectionDataSource(BeanFactory.getUserScoreList());
        ServletContext servletContext = request.getServletContext();
        String xmlPath = servletContext.getRealPath("/WEB-INF/jasper/") + fileName + ".jrxml";
        System.out.println("xmlPath : " + xmlPath);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("class", "五年级三班");
        dataMap.put("createDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        String imagePath = servletContext.getRealPath("/WEB-INF/image/icon.png");
        dataMap.put("imageURL", imagePath);
        InputStream is = new FileInputStream(imagePath);
        dataMap.put("img", is);
        // 这种方式搞完后，两张图片都显示了，也可以试试Image类型
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(xmlPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, dataMap,
                    dataSource);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.pack();
            jasperViewer.setVisible(true);
            String reportHtmlPath = "F:\\WorkSpace\\IDEA_Space\\Test\\jasperReport\\target\\com" +
                    ".gordon" +
                    ".jasper\\WEB-INF\\jasper\\" + "学生成绩表";
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportHtmlPath + ".html");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接数据库
     */
    @RequestMapping(value = "testJDBC", method = RequestMethod.GET)
    public void testConnectDB(HttpServletRequest request, HttpServletResponse response) {

        try {
            ServletContext servletContext = request.getServletContext();
            String fileName = "testDB";
            String xmlPath = servletContext.getRealPath("/WEB-INF/jasper/") + fileName + ".jrxml";
            Connection connection = JDBCUtil.getConnection();
            Map<String, Object> params = new HashedMap();
            params.put("id", 123);
            JasperReport jasperReport = JasperCompileManager.compileReport(xmlPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,
                    connection);

            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.pack();
            jasperViewer.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private DataSource dataSource;
    /* 不能直接使用JasperFillManager.fillReport传入dataSource，jasper中没有sql的dataSource类型。
        但是使用model却可以，如下面的方法
    @RequestMapping(value="testMybatis" ,method = RequestMethod.GET)
    public void createJasperToMybatis(HttpServletRequest request,HttpServletResponse response){
        try {
            ServletContext servletContext = request.getServletContext();
            String fileName = "testDB";
            String xmlPath = servletContext.getRealPath("/WEB-INF/jasper/") + fileName + ".jrxml";
            Connection connection = JDBCUtil.getConnection();
            Map<String,Object> params = new HashedMap();
            params.put("id",123);
            JasperReport jasperReport = JasperCompileManager.compileReport(xmlPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,params,dataSource);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint,false);
            jasperViewer.pack();
            jasperViewer.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 为什么这段代码就可以呢？？因为在spring-mvc.xml中定义的
     * JasperReportsMultiFormatView类中就有一个针对jdbc的数据源
     * 其实这跟mybatis没半毛钱关系，它只要一个数据源而已，完全可以将mybatis的一些配置删除
     * @param request
     * @param response
     * @param model
     * @return
     * @throws JRException
     */
    @RequestMapping(value = "testMybatis", method = RequestMethod.GET)
    public String createJasperToMybatis(HttpServletRequest request,
                                        HttpServletResponse response, Model model) throws
            JRException {

        ServletContext servletContext = request.getServletContext();
        String fileName = "testDB";
        String xmlPath = servletContext.getRealPath("/WEB-INF/jasper/") + fileName + ".jrxml";

        // 先生存.jasper文件用于返回
        String jasperFilePath = JasperCompileManager.compileReportToFile(xmlPath);

        model.addAttribute("id", 123);// sql中的参数
        model.addAttribute("format", "html");// 导出的文件格式
        model.addAttribute("jrDatasource", dataSource);// druid数据源

        // 这句代码报错：ClassCastException: net.sf.jasperreports.engine.JasperReport
        // cannot be cast to net.sf.jasperreports.engine.JasperPrint]
        // JRLoader只能加载.jrprint文件
//        JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(new File(jasperFilePath));

//        JasperViewer jasperViewer = new JasperViewer(jasperPrint,false);
//            jasperViewer.pack();
//            jasperViewer.setVisible(true);

        return fileName;
    }

}
