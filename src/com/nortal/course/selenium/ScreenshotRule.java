package com.nortal.course.selenium;

import com.google.common.base.Supplier;
import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;

public class ScreenshotRule extends TestWatcher {
    private static final Logger log = LoggerFactory.getLogger(ScreenshotRule.class);
    private static long screenshotId = 0;
    private Supplier<WebDriver> driverSup;

    public ScreenshotRule(Supplier<WebDriver> driverSup){
        this.driverSup = driverSup;
    }

    @Override
    protected void failed(Throwable e, Description description) {
        makeScreenshot(driverSup.get(), description.getClassName());
    }

    public static void makeScreenshot(WebDriver driver, String filenamePrefix) {
        final String screenShotName =
                filenamePrefix + "-" + getTimestamp()+ "[" + (screenshotId++) + "].png";

        String fileName = "build" + File.separator + "reports" + File.separator + "tests" + File.separator + screenShotName;
        fileName = fileName.replaceAll("\\s", "-");
        log.info("saving screenshot: " + fileName + " , current url: " + driver.getCurrentUrl());
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(fileName));
        } catch (Exception ex) {
            log.warn("Could not make snapshot", ex);
        }
    }

    private static String getTimestamp(){
        String t = new Date().toString();
        t = t.replaceAll(":","_");
        return t;
    }
}
