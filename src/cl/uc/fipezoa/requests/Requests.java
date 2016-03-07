package cl.uc.fipezoa.requests;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Requests {


    public static Response get(String url) throws IOException {
        return get(url, new HashMap<String, String>());
    }

    public static Response get(String url, Map<String, String> headers) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        // Headers
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
        for (String key : headers.keySet()) {
            httpGet.addHeader(key, headers.get(key));
        }
        HttpResponse response = client.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == 302){
            String redirectUrl = response.getFirstHeader("Location").toString();
            return get(redirectUrl);
        } else {
            return new Response(response);
        }
    }

    public static Response get(String url, UrlParameters urlParameters, Map<String, String> headers) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGetWithEntity httpGet = new HttpGetWithEntity(url);

        // Headers
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
        for (String key : headers.keySet()) {
            httpGet.addHeader(key, headers.get(key));
        }

        httpGet.setEntity(urlParameters.toEntity());

        HttpResponse response = client.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == 302){
            String redirectUrl = response.getFirstHeader("Location").toString();
            return get(redirectUrl);
        } else {
            return new Response(response);
        }
    }

    public static Response get(String url, UrlParameters urlParameters) throws IOException {
        return get(url, urlParameters, new HashMap<String, String>());
    }

    public static Response post(String url, Map<String, String> parameters, Map<String, String> headers) throws IOException {
        // Parameters
        UrlParameters urlParameters = new UrlParameters();
        for (String key : parameters.keySet()) {
            urlParameters.addParameter(key, parameters.get(key));
        }
        return post(url, urlParameters, headers);
    }

    public static Response post(String url, UrlParameters parameters, Map<String, String> headers) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        // Headers
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        for (String key : headers.keySet()) {
            httpPost.addHeader(key, headers.get(key));
        }

        // Parameters
        httpPost.setEntity(parameters.toEntity());

        HttpResponse response = client.execute(httpPost);

        if (response.getStatusLine().getStatusCode() == 302){
            String redirectUrl = response.getFirstHeader("Location").getValue();
            return get(redirectUrl);
        } else {
            return new Response(response);
        }
    }

    public static Response post(String url, UrlParameters urlParameters) throws IOException {
        return post(url, urlParameters, new HashMap<String, String>());
    }

    public static Response post(String url) throws IOException {
        return post(url, new UrlParameters(), new HashMap<String, String>());
    }

}
