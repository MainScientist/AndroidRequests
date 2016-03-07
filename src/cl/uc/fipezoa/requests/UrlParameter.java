package cl.uc.fipezoa.requests;


import org.apache.http.NameValuePair;

/**
 * Created by fipezoa on 1/25/2016.
 */
public class UrlParameter implements NameValuePair {

    String key;
    String value;


    public UrlParameter(String key, String value){
        this.key = key;
        this.value = value;
    }

    @Override
    public String getName() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
