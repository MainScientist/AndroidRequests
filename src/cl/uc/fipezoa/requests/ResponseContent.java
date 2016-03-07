package cl.uc.fipezoa.requests;


import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * Created by fipezoa on 1/26/2016.
 */
public class ResponseContent{

    private byte[] content;

    protected ResponseContent(byte[] content){
        this.content = content;
    }

    public String toString(){
        try {
            return IOUtils.toString(content, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public byte[] toByteArray(){
        return content;
    }
}
