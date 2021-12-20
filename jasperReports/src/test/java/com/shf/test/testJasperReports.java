package com.shf.test;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class testJasperReports {

    //    基于JDBC填充数据
    @Test
    public void test3() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/health", "root", "root");
        String jrxmlPath = "D:\\DEMO\\ItcastHealth_Demo01\\jasperReports\\src\\main\\resources\\public\\demo1.jrxml";
        String jasperPath = "D:\\DEMO\\ItcastHealth_Demo01\\jasperReports\\src\\main\\resources\\public\\demo1.jasper";

        //编译模板,生成后缀为jasper的二进制文件
        JasperCompileManager.compileReportToFile(jrxmlPath,jasperPath);

        //构造数据
        Map paramters = new HashMap();
        paramters.put("company","舒洪凡");

        //填充数据---使用JDBC数据源方式填充
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, paramters, connection);
        //输出文件
        String pdfPath = "D:\\DEMO\\ItcastHealth_Demo01\\jasperReports\\src\\main\\resources\\public\\demo1.pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint,pdfPath);
    }

//    基于JavaBean数据源方式填充数据
    @Test
    public void test2() throws Exception {
        String jrxmlPath = "D:\\DEMO\\ItcastHealth_Demo01\\jasperReports\\src\\main\\resources\\public\\demo2.jrxml";
        String jasperPath = "D:\\DEMO\\ItcastHealth_Demo01\\jasperReports\\src\\main\\resources\\public\\demo2.jasper";

        //编译模板,生成后缀为jasper的二进制文件
        JasperCompileManager.compileReportToFile(jrxmlPath,jasperPath);

        //构造数据
        Map paramters = new HashMap();
        paramters.put("company","舒洪凡");

        List<Map> list = new ArrayList();
        Map map1 = new HashMap();
        map1.put("name","入职体检套餐");
        map1.put("code","RZTJ");
        map1.put("age","18-60");
        map1.put("sex","男");

        Map map2 = new HashMap();
        map2.put("name","阳光爸妈老年健康体检");
        map2.put("code","YGBM");
        map2.put("age","55-60");
        map2.put("sex","男");
        list.add(map1);
        list.add(map2);

        //填充数据---使用JDBC数据源方式填充
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, paramters, new JRBeanCollectionDataSource(list));
        //输出文件
        String pdfPath = "D:\\DEMO\\ItcastHealth_Demo01\\jasperReports\\src\\main\\resources\\public\\demo2.pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint,pdfPath);
    }

    @Test
    public void test1() throws JRException {
        String jrxmlPath =
                "D:\\DEMO\\ItcastHealth_Demo01\\jasperReports\\src\\main\\resources\\public\\demo.jrxml";
        String jasperPath =
                "D:\\DEMO\\ItcastHealth_Demo01\\jasperReports\\src\\main\\resources\\public\\demo.jasper";

        //编译模板
        JasperCompileManager.compileReportToFile(jrxmlPath,jasperPath);

        //构造数据
        Map paramters = new HashMap();
        paramters.put("reportDate","2019-10-10");
        paramters.put("company","itcast");
        List<Map> list = new ArrayList();
        Map map1 = new HashMap();
        map1.put("name","xiaoming");
        map1.put("address","beijing");
        map1.put("email","xiaoming@itcast.cn");
        Map map2 = new HashMap();
        map2.put("name","xiaoli");
        map2.put("address","nanjing");
        map2.put("email","xiaoli@itcast.cn");
        list.add(map1);
        list.add(map2);

        //填充数据
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperPath,
                        paramters,
                        new JRBeanCollectionDataSource(list));

        //输出文件
        String pdfPath = "D:\\DEMO\\ItcastHealth_Demo01\\jasperReports\\src\\main\\resources\\public\\test.pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint,pdfPath);
    }
}
