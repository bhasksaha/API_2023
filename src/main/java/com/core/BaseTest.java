package com.core;

import com.utils.ExtentReport;
import com.utils.Helper;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.Properties;

public class BaseTest {

    protected Helper helper;
    protected Properties propObj;
    protected String url;
    private final String reportPath;


    public BaseTest(){
        helper=new Helper();
        propObj=helper.properReader("/src/test/resources/config.properties");
        url=propObj.getProperty("url");
        reportPath=helper.CreateFolder(helper.getCurrentTimestamp());
        ExtentReport.initialize(reportPath);
    }

    @BeforeMethod
    public void extentStartTest(ITestResult result,Method method){
        ExtentReport.test=ExtentReport.report.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());

    }

    @AfterMethod
    public void getTestStatus(ITestResult result){

        if(result.getStatus()==ITestResult.SUCCESS){
            ExtentReport.test.pass("Test Case : "+result.getName()+" is passed");
        }else if(result.getStatus()==ITestResult.FAILURE){
            ExtentReport.test.fail("Test Case : "+result.getName()+" is failed");
            ExtentReport.test.fail(result.getThrowable());
        }else if(result.getStatus()==ITestResult.SKIP){
            ExtentReport.test.skip("Test Case : "+result.getName()+" is skipped");
        }
    }

    @AfterSuite
    public void enReport(){
       ExtentReport.report.flush();
    }
}
