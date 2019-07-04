package com.btkj.webdeiver;

import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by slh on 2019/5/24.
 */
public class demo1 {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/chromedriver_win32/chromedriver.exe");
        WebDriver  driver = new ChromeDriver();
//        System.out.println("The testing page title is: " + driver.getTitle());
//        driver.close();

        //必须先访问
        driver.get("http://192.168.100.85:811/Index.aspx");

        Cookie c1 =new Cookie("ASP.NET_SessionId","ecio34hsnbtxagewnqln4yck");
        Cookie c2 =new Cookie(".ASPXEvjenWebApplication","JhSQXa0y7xCreUnHLGUpoMZSfJ3ck24OhYRYr-6obeV58FGPEDi1YUoRwSZTFQ9c3sLXYc1drwhzI2yZEMfLTLYbT32fJfMTXL5grIvhvEMlJSZM1qgf7CF_CeDRT63Alx35PhoP6oohDYSCtnvCOQ2");

        driver.manage().addCookie(c1);
        driver.manage().addCookie(c2);

        driver.get("http://192.168.100.85:811/Index.aspx");


//        List<WebElement> cheeses = driver.findElements(By.className("J_menuItem"));
//
//        cheeses.get(2).click();

        driver.findElement(By.xpath("//*[@id=\"navigation\"]/div[2]/ul/ul/li[1]/ul/li[2]/ul/li[1]/ul/li[1]/a")).click();


        /* iframe使用后必须跳出来，否则无法定位其它元素*/
        driver.switchTo().frame("iframe2");
        driver.findElement(By.id("Add")).click();


        //进入下一个iframe
        driver.switchTo().defaultContent();
        driver.switchTo().frame("TB_iframeContent");


        //表单填写
        driver.findElement(By.id("txtFPersonName")).sendKeys("杨松");
        driver.findElement(By.id("txtFPersonIDCard")).sendKeys("513001196208241417");
        driver.findElement(By.id("txtFPersonMobile")).sendKeys("18398865888");
        driver.findElement(By.id("txtFPlateNo")).sendKeys("浙C88888");
        driver.findElement(By.id("txtFFactoryCard")).sendKeys("舒适型");
        driver.findElement(By.id("txtFEngineNo")).sendKeys("PSARFN10LH3X");
        driver.findElement(By.id("txtFVIN")).sendKeys("1A8GYB4R96Y125867");
        driver.findElement(By.id("txtFColor")).sendKeys("白");
        driver.findElement(By.id("txtFKMNum")).sendKeys("6000.00");
        driver.findElement(By.id("txtFCarEmissions")).sendKeys("1.4");
        driver.findElement(By.id("txtFProductionDate")).sendKeys("2018-02-22");
        driver.findElement(By.id("txtFLicensedPeriod")).sendKeys("2018-02-22");
        driver.findElement(By.id("txtFMotorRegNo")).sendKeys("123456");
        driver.findElement(By.id("txtFBuyCarDate")).sendKeys("2018-04-19");



        //下拉选择框(Select)
        Select select106  = new Select(driver.findElement(By.id("selF_106")));
        select106.selectByValue("1");


        Select select107  = new Select(driver.findElement(By.id("selF_107")));
        select107.selectByValue("1");


        Select txtFLicenseProvince  = new Select(driver.findElement(By.id("txtFLicenseProvince")));
        txtFLicenseProvince.selectByValue("安徽");



        driver.findElement(By.id("txtFCarName")).click();

        Thread.sleep(1000);


        //车辆名称iframe  大坑，要用xpath的绝对路径
        WebElement frame=driver.findElement(By.xpath( "/html[1]/body[1]/div[5]/iframe[1]" ));
        driver.switchTo().frame(frame);




        Select selFCarBrand  = new Select(driver.findElement(By.id("selFCarBrand")));
        selFCarBrand.selectByValue("35");

        Select selFCarModel  = new Select(driver.findElement(By.id("selFCarModel")));
        selFCarModel.selectByValue("24530");

        driver.findElement(By.id("Btn_Ok")).click();




        driver.switchTo().defaultContent();////坑
        driver.switchTo().frame("TB_iframeContent");



        Thread.sleep(1000);//坑
        driver.findElement(By.id("txtFEmpName")).click();



        //进入业务员选择的iframe
        WebElement frame1=driver.findElement(By.xpath( "/html[1]/body[1]/div[5]/iframe[1]" ));
        driver.switchTo().frame(frame1);


        Actions action = new Actions(driver);
        action.doubleClick(driver.findElement(By.xpath("//body//tr[7]"))).perform();



        driver.switchTo().defaultContent();////坑
        driver.switchTo().frame("TB_iframeContent");


        Thread.sleep(1000);//坑

        ////点击保存
        driver.findElement(By.id("btnsave")).click();

        driver.switchTo().alert().accept();
        driver.switchTo().alert().accept();


        //点击提交
        driver.findElement(By.id("btnsubmit")).click();
        driver.switchTo().alert().accept();

        //点击确认
        driver.findElement(By.id("btncheck")).click();
        driver.switchTo().alert().accept();



        WebElement framebtncheck=driver.findElement(By.xpath( "/html[1]/body[1]/div[5]/iframe[1]" ));
        driver.switchTo().frame(framebtncheck);



        driver.findElement(By.id("txtFEvaluatePrice")).sendKeys("500000");
        driver.findElement(By.id("FlowSave")).click();

        //最后切回主文档
        driver.switchTo().defaultContent();





    }

}
