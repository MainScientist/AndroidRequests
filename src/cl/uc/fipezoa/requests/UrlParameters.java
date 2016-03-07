package cl.uc.fipezoa.requests;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fipezoa on 1/25/2016.
 */
public class UrlParameters {

    private List<UrlParameter> parameters;

    public UrlParameters(){
        parameters = new ArrayList<>();
    }

    public void addParameter(String key, String value){
        parameters.add(new UrlParameter(key, value));
    }

    public String toString(){
        return URLEncodedUtils.format(parameters, Charset.defaultCharset().displayName());
    }

    public UrlEncodedFormEntity toEntity(){
        try {
            return new UrlEncodedFormEntity(this.parameters, Charset.defaultCharset().displayName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int size(){
        return parameters.size();
    }
}
