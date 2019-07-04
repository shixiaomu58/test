package com.btkj.webdeiver;

import com.btkj.webdeiver.util.WebdeiverUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Driver;
import java.util.*;

/**
 * Created by slh on 2019/5/29.
 */
public class HHD {
     /*
    设置变量cookie及一些用户信息
    */
   private static final String NET_SessionId= "sbvhlhjhxnfs4wid1tolxqhb";
   private static final  String ASPXEvjenWebApplication= "Zkct0aSNkAIZHLS28WWc4H8lQ3xVPUs_u5edWyLqw-na06iPtP5XBaNYrTCVX-2glpWNmXPG6EVLVfm93pYT2rHucTz1Cn-hewvxBa2Q2eiM7m7_MrCCXue9LuYw4BU0VrO4Gvf6PIp_mgFfMJZccA2";
   private static final String  txtFPersonName = "方爱亮";
   private static final String  txtFPersonIDCard = "330127196408013614";
   private static final String  txtFPersonMobile = "18258868093";
   private static final String txtFContractSignDate = "2019-06-17"; //改为当前时间
   private static final String txtFPeriod = "6"; //贷款期数
    /*
    100:金华工商银行   101：艮山工商银行      102：南昌工商银行    201：阳光小贷新网银行
     */
   private static  final String selFPayType = "201";



    //设置类变量
    WebDriver  driver;

    /*
    执行之前先登录很好贷后台
    */
    @BeforeClass
    public void login(){
        System.setProperty("webdriver.chrome.driver", "D:/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();

        //必须先访问
        driver.get("http://192.168.100.85:811/Index.aspx");

        Cookie c1 =new Cookie("ASP.NET_SessionId",NET_SessionId);
        Cookie c2 =new Cookie(".ASPXEvjenWebApplication",ASPXEvjenWebApplication);

        driver.manage().addCookie(c1);
        driver.manage().addCookie(c2);

        driver.get("http://192.168.100.85:811/Index.aspx");
        driver.manage().window().maximize();
    }

    @Test
    public void HLoan_Evaluation() throws InterruptedException {
        Thread.sleep(300);

        //找到评估单的菜单，点击
        driver.findElement(By.xpath("//*[@id=\"navigation\"]/div[2]/ul/ul/li[1]/ul/li[2]/ul/li[1]/ul/li[1]/a")).click();


        //进入对应的iframe并且操作
        driver.switchTo().frame("iframe2");
        driver.findElement(By.id("Add")).click();


        //切换下一个iframe先回到主文档
        driver.switchTo().defaultContent();
        driver.switchTo().frame("TB_iframeContent");


        //下拉选择框选择(Select)
        new Select(driver.findElement(By.id("selF_106"))).selectByValue("1");

        new Select(driver.findElement(By.id("selF_107"))).selectByValue("1");

        new Select(driver.findElement(By.id("txtFLicenseProvince"))).selectByValue("安徽");


        //表单填写
        Map<String,String> evaluatioForm = new HashMap<String, String>();
        evaluatioForm.put("txtFPersonName",txtFPersonName);
        evaluatioForm.put("txtFPersonIDCard",txtFPersonIDCard);
        evaluatioForm.put("txtFPersonMobile",txtFPersonMobile);
        evaluatioForm.put("txtFPlateNo","浙C88888");
        evaluatioForm.put("txtFFactoryCard","舒适型");
        evaluatioForm.put("txtFEngineNo","PSARFN10LH3X");
        evaluatioForm.put("txtFVIN","1A8GYB4R96Y125867");
        evaluatioForm.put("txtFColor","白色");
        evaluatioForm.put("txtFKMNum","6000");
        evaluatioForm.put("txtFCarEmissions","1.4");
        evaluatioForm.put("txtFProductionDate","2018-02-22");
        evaluatioForm.put("txtFLicensedPeriod","2018-02-22");
        evaluatioForm.put("txtFMotorRegNo","123456");
        evaluatioForm.put("txtFBuyCarDate","2018-04-19");

        WebdeiverUtil.formFill(driver, evaluatioForm);

        WebdeiverUtil.choseCar(driver, "txtFCarName");

        WebdeiverUtil.checkIframe(driver,"TB_iframeContent");

        WebdeiverUtil.choseEmp(driver, "txtFEmpName");

        WebdeiverUtil.checkIframe(driver,"TB_iframeContent");

        //点击保存
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

        //输入评估价，点击确定
        driver.findElement(By.id("txtFEvaluatePrice")).sendKeys("500000");
        driver.findElement(By.id("FlowSave")).click();

        //关闭弹窗
        driver.findElement(By.id("TB_closeWindowButton")).click();
      //  driver.findElement(By.className("fa-times-circle")).click(); //关闭不掉就不关闭了！！
        driver.switchTo().defaultContent();

        driver.navigate().refresh();
    }




    @Test(dependsOnMethods = "HLoan_Evaluation")
    public void HLoan_Apply() throws InterruptedException {
        Thread.sleep(300);
        driver.findElement(By.xpath("//li[2]//ul[1]//li[6]//ul[1]//li[3]//a[1]")).click();
        //进入对应的iframe并且操作
        driver.switchTo().frame("iframe36");

        //最好搜索下
        driver.findElement(By.id("txtFPersonName")).sendKeys(txtFPersonName);
        new Select(driver.findElement(By.id("txtFStatus"))).selectByValue("-1");
        driver.findElement(By.id("Search")).click();

        Actions action = new Actions(driver);
        action.doubleClick(driver.findElement(By.xpath("//div[@class='lister_v2']//tbody//tr[1]"))).perform();

        driver.switchTo().defaultContent();
        driver.switchTo().frame("TB_iframeContent");



        //车辆信息
        driver.findElement(By.linkText("车辆信息")).click();
        new Select(driver.findElement(By.id("SelFCarType"))).selectByValue("1");

        driver.findElement(By.id("txtFCarYearCheckDate")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath("//input[@id='txtFCarYearCheckDate']")).sendKeys("2018-02-22");

        Thread.sleep(2000);

        driver.findElement(By.id("txtFCarInsuranceDate")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath("//input[@id='txtFCarInsuranceDate']")).sendKeys("2018-02-22");

        Thread.sleep(300);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("var content = document.getElementsByClassName(\"content-tabs\")[0];content.getElementsByTagName(\"a\")[0].click()");

        //下拉框选择
        new Select(driver.findElement(By.id("selFPersonMarriage"))).selectByValue("2");//性别
        new Select(driver.findElement(By.id("selFPersonEducation"))).selectByValue("1");//教育程度
        new Select(driver.findElement(By.id("txtFHomeAddrProvince"))).selectByValue("安徽");//现住地址
        new Select(driver.findElement(By.id("selFIsHouse"))).selectByValue("1"); //有无房产
        new Select(driver.findElement(By.id("selFPersonHouse"))).selectByValue("1");//居住情况
        new Select(driver.findElement(By.id("selFComType"))).selectByValue("1");//公司类型
        new Select(driver.findElement(By.id("selFPersonPosition"))).selectByValue("1");//职位
        new Select(driver.findElement(By.id("txtFCompanyProvince"))).selectByValue("安徽");//公司地址
        new Select(driver.findElement(By.id("selFEmergency2Map"))).selectByValue("1");
        new Select(driver.findElement(By.id("selFEmergency3Map"))).selectByValue("1");
        new Select(driver.findElement(By.id("selFEmergencyMap"))).selectByValue("1");


        Thread.sleep(300);
        //时间控件输入
        driver.findElement(By.id("txtFCertificateTime")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath("//input[@id='txtFCertificateTime']")).sendKeys("2021-06-19");

        //表单输入
        Map<String,String> applyForm = new HashMap<String, String>();
//        applyForm.put("txtFCertificateTime","2021-06-19");
        applyForm.put("txtFPersonEmail","1468663110@qq.com");
        applyForm.put("txtFPersonIDCardAddress","户籍地址");
        applyForm.put("txtFPersonComName","公司名称");
        applyForm.put("txtFPostCode","310000");
        applyForm.put("txtFPersonFamilyAddress","详细现住地址");
        applyForm.put("txtFPersonComInDate","2018-02-22");//入职时间
        applyForm.put("txtFPersonComIncome","200000");//年收入
        applyForm.put("txtFPersonComTel","18000000000");//公司电话
        applyForm.put("txtFPersonComAddrss","公司详细地址");
        applyForm.put("txtFEmergency2Contact","工作证明人");
        applyForm.put("txtFEmergency3Contact","其它联系人");
        applyForm.put("txtFEmergencyContact","家庭联系人");
        applyForm.put("txtFEmergency2Mobile","18000000000");
        applyForm.put("txtFEmergency3Mobile","18000000000");
        applyForm.put("txtFEmergencyMobile","18000000000");


        WebdeiverUtil.formFill(driver,applyForm);


        //绑卡信息
        driver.findElement(By.id("txtFPersonBankName")).sendKeys("农业银行");
        new Select(driver.findElement(By.id("txtFRepayPersonBankName"))).selectByValue("农业银行");
        driver.findElement(By.id("txtFPersonBankAccount")).sendKeys("农业银行");
        driver.findElement(By.id("txtFPersonBankAccount")).sendKeys("62222222222");


        //融资信息
        new Select(driver.findElement(By.id("SelFPayType"))).selectByValue(selFPayType);
        driver.findElement(By.id("txtFLoanUsage")).sendKeys("信保贷款");
        driver.findElement(By.id("txtFApplyAmount")).sendKeys("250000");


            //随便点击一个元素，为了让下面的alert弹窗出来
        driver.findElement(By.xpath("//label[@class='col-md-1 search-label'][contains(text(),'成交状态')]")).click();
        Thread.sleep(1000);
        driver.switchTo().alert().accept();

        driver.findElement(By.id("txtFContractSignDate")).sendKeys(txtFContractSignDate);
        new Select(driver.findElement(By.id("selFContractType"))).selectByIndex(1);
        new Select(driver.findElement(By.id("txtFPeriod"))).selectByValue(txtFPeriod);


        //抵押公司
        driver.findElement(By.id("txtFMortgageDeptName")).click();
        WebElement txtFMortgageDeptName=driver.findElement(By.xpath( "/html[1]/body[1]/div[5]/iframe[1]" ));
        driver.switchTo().frame(txtFMortgageDeptName);

        action.doubleClick(driver.findElement(By.xpath("//div[@class='lister_v2']//tbody//tr[1]"))).perform();
        WebdeiverUtil.checkIframe(driver,"TB_iframeContent");



        //渠道姓名
        driver.findElement(By.id("txtFIntermediary")).click();
        WebElement txtFIntermediary=driver.findElement(By.xpath( "/html[1]/body[1]/div[5]/iframe[1]" ));
        driver.switchTo().frame(txtFIntermediary);
        action.doubleClick(driver.findElement(By.xpath("//div[@class='lister_v2']//tbody//tr[1]"))).perform();
        WebdeiverUtil.checkIframe(driver,"TB_iframeContent");


        new Select(driver.findElement(By.id("SelFLoanExplainType"))).selectByValue("1");
        driver.findElement(By.id("txtFServiceorganName")).sendKeys("测试服务商");

        driver.findElement(By.id("btncompute")).click(); //点击计算按钮


        //重新填写下身份证，和个别手机号 带*号的，不然没法保存
        WebdeiverUtil.rewriteInput(driver,"txtFPersonIDCard",txtFPersonIDCard);
        WebdeiverUtil.rewriteInput(driver,"txtFPersonMobile",txtFPersonMobile);
        WebdeiverUtil.rewriteInput(driver,"txtFPersonMobile2",txtFPersonMobile);


        driver.findElement(By.id("btnsave")).click(); //点击保存按钮


        Thread.sleep(1000);
        driver.switchTo().defaultContent();
        driver.switchTo().frame(4); //我的隔天，居然用索引解决了
        driver.findElement(By.id("btnSaveValueID")).click();


        driver.switchTo().defaultContent();
        Thread.sleep(1000);

        driver.switchTo().frame(2); //我的隔天，居然用索引解决了
        Thread.sleep(1000);

        WebElement actionbotton = driver.findElement(By.xpath("/html[1]/body[1]/div[5]/iframe[1]"));
        driver.switchTo().frame(actionbotton);
        Thread.sleep(500);

        driver.findElement(By.xpath("/html[1]/body[1]/div[4]/button[1]")).click();

        driver.switchTo().alert().accept();


        //关闭弹窗
        driver.findElement(By.id("TB_closeWindowButton")).click();
        driver.switchTo().defaultContent();
        driver.navigate().refresh();
    }


    @Test
    public void HLoan_Credit() throws InterruptedException {
        //找到征信单的菜单，点击
        driver.findElement(By.xpath("//a[contains(text(),'征信管理')]")).click();

        //进入对应的iframe并且操作
        driver.switchTo().frame("iframe11");
        driver.findElement(By.id("Add")).click();

        driver.switchTo().defaultContent();
        driver.switchTo().frame("TB_iframeContent");

        //表单填写
        Map<String,String> creditForm = new HashMap<String, String>();
        creditForm.put("txtFPersonName",txtFPersonName);
        creditForm.put("txtFPersonIDCard",txtFPersonIDCard);
        creditForm.put("txtFPersonMobile",txtFPersonMobile);
        creditForm.put("txtFPresaleEvaluatePrice","110000");
        new Select(driver.findElement(By.id("selFPayType"))).selectByValue(selFPayType);


        WebdeiverUtil.formFill(driver, creditForm);
        WebdeiverUtil.choseEmp(driver, "txtFEmpName");

        WebdeiverUtil.checkIframe(driver,"TB_iframeContent");

        driver.findElement(By.id("btnsave")).click();
        driver.switchTo().alert().accept();
        driver.navigate().refresh();

    }

}
