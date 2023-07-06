package com.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.Properties;

public class ExtentReport {

    public static ExtentReports report=null;
    public static ExtentTest test=null;
//    private static Helper helpobj;

    public static void initialize(String reportPath) {
        if (report == null) {
            //exetent test
            Properties prop=new Helper().properReader("/src/test/resources/config.properties");
            report = new ExtentReports();
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath + "/extent-report.html");//add to perties file
            reporter.config().setReportName("Books API Automation Report");
            reporter.config().setTheme(Theme.STANDARD);
            report.attachReporter(reporter);
            report.setSystemInfo("OS", prop.getProperty("OS"));
            report.setSystemInfo("Author", prop.getProperty("Author"));
            report.setSystemInfo("Environment", prop.getProperty("Environment"));
//
//            test = report.createTest("test1");
//            test.pass("test1 passed");
//            test.info("test1 info");
//
//            ExtentTest test1 = report.createTest("test2");
//            test1.pass("test2 passed");
//            test1.info("test2 info");
//
//
//            report.flush();
        }
    }
}
