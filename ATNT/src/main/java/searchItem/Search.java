package searchItem;

import common.WebAPI;
import dataDriven.DataSource;
import databases.ConnectToSqlDB;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

import java.util.List;

import static searchItem.SearchItemWebElement.*;

public class Search extends WebAPI {


    String textVerificationPreMessage = "Showing results for ";

    @FindBy(how = How.XPATH, using = searchBoxXpathWebElement)
    public WebElement searchBox;
    @FindBy(how = How.XPATH, using = searchTextWebElement)
    public WebElement searchText;

    //login
    @FindBy(how = How.XPATH, using = accountXpathWebElement)
    public WebElement account;
     @FindBy(how = How.XPATH, using = signInXpathWebElement)
    public WebElement signIn;

    @FindBy(how = How.XPATH, using = userIdXpathWebElement)
    public WebElement userId;
    @FindBy(how = How.XPATH, using = passwordXpathWebElement)
    public WebElement password;
    @FindBy(how = How.XPATH, using = doubleCheckMessageForSignInXpathWebElement)
    public WebElement doubleCheckMessageForSignIn;
    @FindBy(how = How.CSS, using = signInButtonCSSWebElement)
    public WebElement signInButton;

    //login


    //action method
    public void searchItem() {
        clickOnElement(searchBoxXpathWebElement);
        typeByXpath(searchBoxXpathWebElement, "iphone 8 plus");
        searchBox.submit();

    }

    //validate method
    public void validateSearchItem() throws InterruptedException {
        waitUntilClickAble(By.xpath(searchBoxXpathWebElement));
        sleepFor(5);
        String actualText = searchText.getText();

        String expectedText = textVerificationPreMessage + "\"iphone 8 plus\"" + ".";
        Assert.assertEquals(actualText, expectedText);

    }


    //get the value from data source class and search on atnt

    public void searchBoxCheckUsingGetItemValue() throws InterruptedException {
        List<String> itemList = DataSource.getItemValue();
        for (String st : itemList) {
            typeOnElementNEnter(searchBoxXpathWebElement,st,driver);

//            searchBox.sendKeys(st);
//            searchBox.submit();
            String expectedResult = textVerificationPreMessage + "\"" + st + "\"" + ".";
            sleepFor(3);
            String actualResult = searchText.getText();
            System.out.println("Actual Result : " + actualResult);
            Assert.assertEquals(actualResult, expectedResult, "Search Item not match");
            sleepFor(3);
            searchBox.clear();
        }
    }

    //search items form db list
    public void searchItemsFromDBList() throws Exception {
        //connect to db
        DataSource.insertDataIntoDB();
        //get the
        List<String> itemList = DataSource.getItemsListFromDB();
        for (String st : itemList) {
            typeOnElementNEnter(searchBoxXpathWebElement,st,driver);

//            searchBox.sendKeys(st);
//            searchBox.submit();
            String expectedResult = textVerificationPreMessage + "\"" + st + "\"" + ".";
            sleepFor(3);
            String actualResult = searchText.getText();
            System.out.println("Actual Result : " + actualResult);
            Assert.assertEquals(actualResult, expectedResult, "Search Item not match");
            sleepFor(3);
            searchBox.clear();
        }
    }


    //search items from the excel sheet
    public void SearchItemsFromExcel() throws Exception {
        List<String> itemList = DataSource.getItemsListFromExcel();
        for (int i = 1; i < itemList.size(); i++) {
            String item = itemList.get(i);
            typeOnElementNEnter(searchBoxXpathWebElement,item,driver);

//            searchBox.sendKeys(item);
//            searchBox.submit();

            String expectedResult = textVerificationPreMessage + "\"" + item + "\"" + ".";
            System.out.println("Expected Result : " + expectedResult);
            String actualResult = searchText.getText();
            System.out.println("Actual Result : " + actualResult);
            Assert.assertEquals(actualResult, expectedResult, "Search Item not match");
            sleepFor(3);
            searchBox.clear();
        }

    }

    public void Login(String UN, String PWD) {

        userId.sendKeys(UN);
        password.sendKeys(PWD);

    }

    public void navigateToSignInPage() throws InterruptedException {
        clickOnElement(accountXpathWebElement);
        clickOnElement(signInXpathWebElement);
        loginInfoFromExcel();

    }

    public void loginInfoFromExcel() throws InterruptedException {
        String path = "../ATNT/DataTest/mentoringATnT.xlsx";
        String sheet = "loginInfo";
        int rowCount = DataSource.getRowCount(path, sheet);

        for (int i = 0; i <= rowCount; i++) {
            String userName = DataSource.getCellValue(path, sheet, i, 0);
            System.out.println(userName);
            String Pwd = DataSource.getCellValue(path, sheet, i, 1);
            System.out.println(Pwd);
            typeOnElement(userIdXpathWebElement,userName);
            sleepFor(5);
            typeOnElementNEnter(passwordXpathWebElement,Pwd,driver);
            sleepFor(5);


        }

    }




}

