package com.kvprasad.zbarbarcodescanner;

/**
 * Created by JUMBO on 01/04/2017.
 */
import java.util.HashMap;
import java.util.Map;

public class UrlParameterHandler {

    public static UrlParameterHandler paramHandler;
    private UrlParameterHandler() {}


    public static synchronized UrlParameterHandler getInstance(){
        if(paramHandler==null){
            paramHandler=new UrlParameterHandler();
            return paramHandler;
        }
        return paramHandler;
    }


    public  Map<String,String> buildMapForItemSearch(String ean){
        Map<String, String> myparams = new HashMap<String, String>();
        myparams.put("Service", "AWSECommerceService");
        myparams.put("Operation", "ItemSearch");
        myparams.put("Version", "2009-10-01");
        myparams.put("ContentType", "text/xml");
        //myparams.put("SearchIndex", "MobileApps");//for searching mobile apps
        myparams.put("SearchIndex", "All");//ricerca completa
        myparams.put("Keywords", ean); //parametro da passare
        myparams.put("AssociateTag", "lapaginadella-21"); // "app"
        myparams.put("MaximumPrice","10000");
        myparams.put("Sort","price");
        myparams.put("ResponseGroup", "Images,Small");
        return myparams;
    }

}