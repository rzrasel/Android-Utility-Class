/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fbapi;

import com.restfb.FacebookClient;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author Rz Rasel 2017-08-23.
 */
public class FBAPI {

    private FacebookClient facebookClient;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //https://developers.facebook.com/apps/YOUR_APP_ID/fb-login/
        //https://www.facebook.com/v2.5/dialog/oauth?client_id=XXXXXXXXXXXXXXX&redirect_uri=http%3A%2F%2Fphotovote.dev%2Fauth%2Ffacebook%2Fcallback&scope=email&response_type=code&state=0ztcKhmWwFLtj72TWE8uOKTcf65JmePtG95MZLDD
        //https://www.facebook.com/v2.5/dialog/oauth?client_id=723262031081656&redirect_uri=http://taiwannoc.net/cgione&scope=email&response_type=code&state=0ztcKhmWwFLtj72TWE8uOKTcf65JmePtG95MZLDD
        //https://developers.facebook.com/apps/723262031081656/fb-login/
        /*"http://www.facebook.com/dialog/oauth?" + "client_id="
                    + FB_APP_ID + "&redirect_uri="
                    + URLEncoder.encode(REDIRECT_URI, "UTF-8")
                    + "&scope=public_profile";*/
        //http://www.facebook.com/dialog/oauth?client_id=723262031081656&redirect_uri=http://taiwannoc.net/cgione&scope=public_profile
        String strAPPID = "723262031081656";
        String strRedirectURL = "http://taiwannoc.net/cgione";
        String strAuthUrl = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + strAPPID + "&redirect_uri=" + strRedirectURL + "&scope=user_about_me,"
                + "user_actions.books,user_actions.fitness,user_actions.music,user_actions.news,user_actions.video,user_birthday,user_education_history,"
                + "user_events,user_photos,user_friends,user_games_activity,user_hometown,user_likes,user_location,user_photos,user_relationship_details,"
                + "user_relationships,user_religion_politics,user_status,user_tagged_places,user_videos,user_website,user_work_history,ads_management,ads_read,email,"
                + "manage_pages,publish_actions,read_insights,user_friends,read_page_mailboxes,rsvp_event";
        System.out.println(strAuthUrl);
        //System.setProperty("webdriver.chrome.driver", "chromedriver");
        System.setProperty("webdriver.chrome.driver", "geckodriver");
        //WebDriver driver = new ChromeDriver();
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(strRedirectURL);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("CURRENT_URL: " + driver.getCurrentUrl());
            if (driver.getCurrentUrl().contains("facebook.com")) {
                String strURL = driver.getCurrentUrl();
                System.out.println("STR_URL: " + strURL);
                driver.quit();
            }
            isRunning = false;
        }
    }

}
/*
Pattern p = Pattern.compile("(?<=access_token=).+?(?=&)");
Matcher m = p.matcher(input);
if (m.find()) {
    System.out.println(m.group());
}
Pattern p = Pattern.compile("access_token=(.*?)&expires=\\d+");

https://stackoverflow.com/questions/32244865/selenium-test-case-for-facebook-login-and-logout

https://seleniumjava.com/2016/07/13/how-does-webdriver-driver-new-firefoxdriver-work/

https://stackoverflow.com/questions/32244865/selenium-test-case-for-facebook-login-and-logout
 */
