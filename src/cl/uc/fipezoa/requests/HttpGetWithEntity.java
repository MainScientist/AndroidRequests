package cl.uc.fipezoa.requests;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * Created by fipezoa on 1/26/2016.
 */
public class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {

    public HttpGetWithEntity(URI uri) {
        this.setURI(uri);
    }

    public HttpGetWithEntity(String uri) {
        this.setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return "GET";
    }
}
