package common;

public abstract class WebPage {
        protected static WebBrowser browser;

    protected final int WEB_ELEMENT_WAIT_TIMEOUT = 20;

    // protected ColorFormatConverter colorFormatConverter = new ColorFormatConverter();
    public abstract boolean isValid();

    public void setBrowser(WebBrowser webBrowser) {
        this.browser = webBrowser;
    }
}
