package com.btkj.webdeiver.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by slh on 2019/6/9.
 */
public class WebdeiverUtil {
    public static void formFill(WebDriver driver, Map<String,String> formData) {
        Iterator<Map.Entry<String, String>> iterator = formData.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry) iterator.next();
            String key = entry.getKey();
            String val = entry.getValue();
            driver.findElement(By.id(key)).sendKeys(val);
        }

    }

    //每次切换frame，先回到主的iframe,再切入对应的主表单的iframe
    public static  void checkIframe(WebDriver driver, String iframe) throws InterruptedException {
        driver.switchTo().defaultContent();
        driver.switchTo().frame(iframe);
        //切完后最后等待一下，不然找不到元素报错
        Thread.sleep(1000);
    }


    //选择车辆共有组件
    public static  void choseCar(WebDriver driver, String element) {
        driver.findElement(By.id(element)).click();

        WebElement frame=driver.findElement(By.xpath( "/html[1]/body[1]/div[5]/iframe[1]" ));
        driver.switchTo().frame(frame);

        new Select(driver.findElement(By.id("selFCarBrand"))).selectByValue("35");

        new Select(driver.findElement(By.id("selFCarModel"))).selectByValue("24530");

        driver.findElement(By.id("Btn_Ok")).click();

    }

    //选择业务员共有组件
    public static  void choseEmp(WebDriver driver, String element){
        driver.findElement(By.id(element)).click();

        //进入业务员选择的iframe
        WebElement frame=driver.findElement(By.xpath( "/html[1]/body[1]/div[5]/iframe[1]" ));
        driver.switchTo().frame(frame);

        driver.findElement(By.id("txtFName")).sendKeys("合伙人");
        driver.findElement(By.id("txtFDeptName")).sendKeys("很好贷一");

        driver.findElement(By.id("Search")).click();

        //选择第一个符合名字 包含：合伙人  并且是很好贷一团队的
        Actions action = new Actions(driver);
        action.doubleClick(driver.findElement(By.xpath("/html[1]/body[1]/form[1]/div[4]/table[1]/tbody[1]/tr[1]/td[3]"))).perform();

    }

    //重新填写  input的值
    public static void  rewriteInput(WebDriver driver, String key, String value){
        WebElement Input = driver.findElement(By.id(key));
        Input.clear();
        Input.sendKeys(value);
    }


}

