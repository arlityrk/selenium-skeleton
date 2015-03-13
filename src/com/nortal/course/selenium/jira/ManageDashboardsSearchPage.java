package com.nortal.course.selenium.jira;

import com.google.common.base.Function;
import com.nortal.course.selenium.BasePageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertEquals;

/**
 * Created by arli on 13.03.2015.
 */
public class ManageDashboardsSearchPage extends BasePageObject {

    @FindBy(name = "searchName")
    private WebElement searchNameFilterProxy;

    @FindBy(id = "searchOwnerUserName")
    private WebElement ownerFilterProxy;

    @FindBy(name = "Search")
    private WebElement searchButtonProxy;

    @FindBy(xpath = "//div[@id='filter_search_results']/div[contains(@class,'aui-message')]")
    private WebElement searchResultMessageProxy;

    @FindBy(id = "pp_browse")
    private WebElement searchResultListProxy;



    private JiraSeleniumTestBase testContext;

    public ManageDashboardsSearchPage(JiraSeleniumTestBase testContext){
        super(testContext.getDriver());
        this.testContext = testContext;
    }

    public void searchByNameAndOwner(String name, String owner){
        searchNameFilterProxy.sendKeys(name);
        ownerFilterProxy.sendKeys(owner);
        searchButtonProxy.click();
        Integer result = testContext.waits().getWait().until(new Function<WebDriver, Integer>() {
            @Override
            public Integer apply(WebDriver input) {
                boolean messageDisplayed = searchResultMessageProxy.isDisplayed();
                boolean resultListDisplayed = searchResultListProxy.isDisplayed();
                if (messageDisplayed) return 0;
                if (resultListDisplayed) return 1;
                return null;
            }
        });
        if (result == 1) {
            //TODO:Otsing tagasta tulemused ja on vaja midagi teha
        }
    }
}
