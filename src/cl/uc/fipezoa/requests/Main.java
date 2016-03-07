package cl.uc.fipezoa.requests;

import java.io.IOException;

/**
 * Created by fipezoa on 1/29/2016.
 */
public class Main {

    public static void main(String[] args){
        Session session = new Session();
        try {
            session.get("https://ssb.uc.cl/ERPUC/twbkwbis.P_WWWLogin");

            UrlParameters data = new UrlParameters();
            data.addParameter("sid", "usuario");
            data.addParameter("PIN", "password");
            session.post("https://ssb.uc.cl/ERPUC/twbkwbis.P_ValLogin", data);

            UrlParameters data2 = new UrlParameters();
            data2.addParameter("levl", "");
            data2.addParameter("tprt", "FAA");
            Response response = session.post("https://ssb.uc.cl/ERPUC/bwskotrn.P_ViewTran", data2);
            System.out.println(response.getContent().toString());
            System.out.println(session.getCookieStore().getCookies().size());
            System.out.println(session.getCookiesHeader());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
