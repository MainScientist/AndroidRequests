package cl.uc.fipezoa.requests;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Session {

    HttpContext clientContext;
    private CookieStore cookieStore;

    public Session(){
        clientContext = new BasicHttpContext();
        cookieStore = new BasicCookieStore();
        clientContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
    }

    public Response get(String url) throws IOException {
        return get(url, new HashMap<String, String>());
    }

    public Response get(String url, UrlParameters urlParameters) throws IOException {
        return get(url, urlParameters, new HashMap<String, String>());
    }

    public Response get(String url, UrlParameters urlParameters, Map<String, String> headers) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGetWithEntity httpGet = new HttpGetWithEntity(url);

        client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);

        // Headers
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded; text/html");
        for (String key : headers.keySet()) {
            httpGet.addHeader(key, headers.get(key));
        }

        httpGet.setEntity(urlParameters.toEntity());

        HttpResponse response = client.execute(httpGet, clientContext);
        handleCookies(response);

        return handleRedirection(response);
    }

    public Response get(String url, Map<String, String> headers) throws IOException {
        return get(url, new UrlParameters(), headers);
    }

    public Response post(String url) throws IOException {
        return post(url, new UrlParameters());
    }

    public Response post(String url, UrlParameters urlParameters) throws IOException {
        return post(url, urlParameters, new HashMap<String, String>());
    }

    public Response post(String url, UrlParameters parameters, Map<String, String> headers) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);

        // Headers
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; text/html");
        for (String key : headers.keySet()) {
            httpPost.addHeader(key, headers.get(key));
        }

        // Parameters
        httpPost.setEntity(parameters.toEntity());

        HttpResponse response = client.execute(httpPost, clientContext);
        handleCookies(response);

        return handleRedirection(response);
    }

    public void handleCookies(HttpResponse response){
        Header[] setCookieHeaders = response.getHeaders("Set-Cookie");
        for (Header header : setCookieHeaders){
            String stringCookie = header.getValue();
            String[] fields = stringCookie.split("; ");
            String firstField = fields[0];
            String key = firstField.split("=")[0];
            String value = firstField.split("=").length > 1 ? firstField.split("=")[1] : "";
            BasicClientCookie cookie = new BasicClientCookie(key, value);
            DateFormat format = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss zzz", Locale.ENGLISH);
            for (String field : fields){
                if (field.contains("=")){
                    key = field.split("=")[0];
                    value = field.split("=").length > 1 ? field.split("=")[1] : "";
                    if (key.contains("Path")){
                        cookie.setPath(value);
                    }else if (key.contains("Expires")){
                        try {
                            cookie.setExpiryDate(format.parse(value));
                        } catch (ParseException e) {
                            // Do nothing.
                        }
                    }
                }else{
                    if (field.contains("Secure")){
                        cookie.setSecure(true);
                    }
                }
            }
            cookieStore.addCookie(cookie);
        }
    }

    public CookieStore getCookieStore(){
        return cookieStore;
    }

    public String getCookiesHeader(){
        String cookieHeader = "";
        for (Cookie cookie : cookieStore.getCookies()){
            cookieHeader += cookie.getName() + "=" + cookie.getValue() + "; ";
        }
        return cookieHeader.trim();
    }

    public Response handleRedirection(HttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() == 302){
            String redirectUrl = response.getFirstHeader("Location").getValue();
            return get(redirectUrl);

        } else {
            return new Response(response);
        }
    }
}
