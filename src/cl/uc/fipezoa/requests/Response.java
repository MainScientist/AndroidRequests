package cl.uc.fipezoa.requests;

import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;


public class Response implements HttpResponse {

    private HttpResponse response;

    protected Response(HttpResponse response){
        this.response = response;
    }

    public ResponseContent getContent() throws IOException {
        HttpEntity entity = this.response.getEntity();
        InputStream is = entity.getContent();
        return new ResponseContent(IOUtils.toByteArray(is));
    }

    @Override
    public StatusLine getStatusLine() {
        return response.getStatusLine();
    }

    @Override
    public void setStatusLine(StatusLine statusLine) {
        response.setStatusLine(statusLine);
    }

    @Override
    public void setStatusLine(ProtocolVersion protocolVersion, int i) {
        response.setStatusLine(protocolVersion, i);
    }

    @Override
    public void setStatusLine(ProtocolVersion protocolVersion, int i, String s) {
        response.setStatusLine(protocolVersion, i, s);
    }

    @Override
    public void setStatusCode(int i) throws IllegalStateException {
        response.setStatusCode(i);
    }

    @Override
    public void setReasonPhrase(String s) throws IllegalStateException {
        response.setReasonPhrase(s);
    }

    @Override
    public HttpEntity getEntity() {
        return response.getEntity();
    }

    @Override
    public void setEntity(HttpEntity httpEntity) {
        response.setEntity(httpEntity);
    }

    @Override
    public Locale getLocale() {
        return response.getLocale();
    }

    @Override
    public void setLocale(Locale locale) {
        response.setLocale(locale);
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return response.getProtocolVersion();
    }

    @Override
    public boolean containsHeader(String s) {
        return response.containsHeader(s);
    }

    @Override
    public Header[] getHeaders(String s) {
        return response.getHeaders(s);
    }

    @Override
    public Header getFirstHeader(String s) {
        return response.getFirstHeader(s);
    }

    @Override
    public Header getLastHeader(String s) {
        return response.getLastHeader(s);
    }

    @Override
    public Header[] getAllHeaders() {
        return response.getAllHeaders();
    }

    @Override
    public void addHeader(Header header) {
        response.addHeader(header);
    }

    @Override
    public void addHeader(String s, String s1) {
        response.addHeader(s, s1);
    }

    @Override
    public void setHeader(Header header) {
        response.setHeader(header);
    }

    @Override
    public void setHeader(String s, String s1) {
        response.setHeader(s, s1);
    }

    @Override
    public void setHeaders(Header[] headers) {
        response.setHeaders(headers);
    }

    @Override
    public void removeHeader(Header header) {
        response.removeHeader(header);
    }

    @Override
    public void removeHeaders(String s) {
        response.removeHeaders(s);
    }

    @Override
    public HeaderIterator headerIterator() {
        return response.headerIterator();
    }

    @Override
    public HeaderIterator headerIterator(String s) {
        return response.headerIterator(s);
    }

    @Override
    public HttpParams getParams() {
        return response.getParams();
    }

    @Override
    public void setParams(HttpParams httpParams) {
        response.setParams(httpParams);
    }
}
