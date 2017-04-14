package com.kvprasad.zbarbarcodescanner;

/**
 * Created by JUMBO on 01/04/2017.
 *
 * AWSAccessKeyId=AKIAICCADS4P3LEJAHTA
 AWSSecretKey=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

 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

public class Parser {
    /** ---------------------  Search TAG --------------------- */
    private static final String KEY_ROOT="Items";
    private static final String KEY_REQUEST_ROOT="Request";
    private static final String KEY_REQUEST_CONTAINER="IsValid";
    private static final String KEY_ITEM="Item";
    private static final String KEY_ID="ASIN";
    private static final String KEY_ITEM_URL="DetailPageURL";
    private static final String KEY_IMAGE_ROOT="MediumImage";
    private static final String KEY_IMAGE_CONTAINER="URL";
    private static final String KEY_ITEM_ATTR_CONTAINER="ItemAttributes";
    private static final String KEY_ITEM_ATTR_TITLE="Title";

    private static final String VALUE_VALID_RESPONCE="True";

    //Tags
    //Items,Request,IsValid,Item,ASIN,DetailPageURL,MediumImage,URL,ItemAttributes,Title


    public NodeList getResponceNodeList(String service_url) {
        String searchResponce = this.getUrlContents(service_url);
        Log.i("url",""+service_url);
        Log.i("responce",""+searchResponce);
        Document doc;
        NodeList items = null;
        if (searchResponce != null) {
            try {
                doc = this.getDomElement(service_url);
                items = doc.getElementsByTagName(KEY_ROOT);
                Element element=(Element)items.item(0);
                if(isResponceValid(element)){
                    items=doc.getElementsByTagName(KEY_ITEM);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return items;
    }

    public SearchObject getSearchObject(NodeList list,int position){
        SearchObject object=new SearchObject();
        Element e=(Element)list.item(position);
        object.setUrl(this.getValue(e, KEY_ITEM_URL));
        object.setId(this.getValue(e, KEY_ID));
        object.setImageUrl(this.getValue((Element)(e.getElementsByTagName(KEY_IMAGE_ROOT).item(0))
                , KEY_IMAGE_CONTAINER));
        object.setTitle(this.getValue((Element)(e.getElementsByTagName(KEY_ITEM_ATTR_CONTAINER).item(0))
                , KEY_ITEM_ATTR_TITLE));
        return object;
    }

    public boolean isResponceValid(Element element){
        NodeList nList=element.getElementsByTagName(KEY_REQUEST_ROOT);
        Element e=(Element)nList.item(0);
        if(getValue(e, KEY_REQUEST_CONTAINER).equals(VALUE_VALID_RESPONCE)){
            return true;
        }
        return false;
    }


    public SearchObject getObject(String service_url) {
        String searchResponce = this.getUrlContents(service_url);
        Log.i("getObject url",""+service_url);
        Log.i("getObject responce",""+searchResponce);
        Document doc;
        SearchObject item = null;
        if (searchResponce != null) {
            try {
                doc = this.getDomElement(service_url);
                item = (SearchObject) doc.getElementsByTagName(KEY_ITEM); //inserisco solo un oggetto.. xml mi restituisce solo q
                Log.d("getObject>>>>>>" , item.getTitle());
            } catch (Exception e) {
                Log.e("getObject Exception",String.valueOf(e));
                e.printStackTrace();
            }
        }else{
            Log.d("getObject", "searchResponce null");
        }

        return item;
    }



    /** In app reused functions */
    private String getUrlContents(String theUrl) {
        StringBuilder content = new StringBuilder();
        //int BUFFER_SIZE = 8192;
        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()), 8);
                    //new InputStreamReader(urlConnection.getInputStream()), BUFFER_SIZE);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public Document getDomElement(String xml) {
        Document doc = null;
       // Log.e("getDomElement URL--->: ", xml);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            /*DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = (Document) db.parse(is);*/

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.parse(xml);
        } catch (ParserConfigurationException e) {
            Log.e("Errore ParserConfig: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error SAXException : ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error IOException URL: ", xml);
            Log.e("Error IOException: ", e.getMessage());
            return null;
        }

        return doc;
    }

    public final String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child
                        .getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE
                            || (child.getNodeType() == Node.CDATA_SECTION_NODE)) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }
}