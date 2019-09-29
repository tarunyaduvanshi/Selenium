package page;

import common.WebPage;

public class LandingPage extends WebPage {

    @Override
    public boolean isValid() {
        try{
            WebPage landingPage = browser.navigateToUrl("https://www.spicejet.com/", "LandingPage");

        }
        catch (Exception ex){

        }
        return false;
    }
}
