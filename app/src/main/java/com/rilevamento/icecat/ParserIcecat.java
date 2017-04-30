package com.rilevamento.icecat;

/**
 * Created by JUMBO on 01/04/2017.
 *

 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;
import com.rilevamento.*;

public class ParserIcecat{

    /** ---------------------  Search TAG --------------------- */
    private static final String KEY_ROOT="Product"; //ok
    private static final String KEY_REQUEST_ROOT="Request";
    private static final String KEY_REQUEST_CONTAINER="IsValid"; //<<<<<<<<<<<<<<<<<<< qui non esiste valid ma verifica se accetta il 200..
    private static final String KEY_ITEM="SummaryDescription"; //ok
    private static final String KEY_ID="ID"; //ok?
    private static final String KEY_ITEM_URL="DetailPageURL";
    private static final String KEY_IMAGE_ROOT="Pic500x500"; //ok
    private static final String KEY_IMAGE_CONTAINER="URL";
    private static final String KEY_ITEM_ATTR_CONTAINER="ProductFeature "; //ok
    private static final String KEY_ITEM_ATTR_TITLE="LongSummaryDescription"; //ok
    private static final String KEY_ITEM_ATTR_MANIFACTURER="Supplier"; //ok
    private static final String KEY_ITEM_ATTR_PRODUCTGROUP="Category"; //si trova dentro value

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
        Element el=(Element)list.item(position);//non va bene... il position va a cazzi suoi
        Element e=(Element)list.item(0);
        String url, id, img, title,manufacturer,productGroup;
        if(list==null){
            Log.e("getSearchObject LISTA", "VUOTA");
            Log.d("getSearchObject pos", String.valueOf(position));
            return null;
        }else
            Log.d("getSearchObject LISTA", "VALIDA");

        if(e!=null) {
            url = this.getValue(e, KEY_ITEM_URL);
            Log.d("getSearchObject URL", url);
            object.setUrl(url);
            id = this.getValue(e, KEY_ID);
            Log.d("getSearchObject ID", id);
            object.setId(id);
            img = this.getValue((Element) (e.getElementsByTagName(KEY_IMAGE_ROOT).item(0))
                    , KEY_IMAGE_CONTAINER);
            Log.d("getSearchObject IMG", img);
            object.setImageUrl(img);
            title = this.getValue((Element) (e.getElementsByTagName(KEY_ITEM_ATTR_CONTAINER).item(0))
                    , KEY_ITEM_ATTR_TITLE);
            object.setTitle(title);
            Log.d("getSearchObject Title", title);
            manufacturer = this.getValue((Element) (e.getElementsByTagName(KEY_ITEM_ATTR_CONTAINER).item(0))
                    , KEY_ITEM_ATTR_MANIFACTURER);
            object.setManufacturer(manufacturer);
            Log.d("getSearchObject manufac", manufacturer);
            productGroup = this.getValue((Element) (e.getElementsByTagName(KEY_ITEM_ATTR_CONTAINER).item(0))
                    , KEY_ITEM_ATTR_PRODUCTGROUP);
            object.setProductGroup(productGroup);
            Log.d("getSearchObject product", productGroup);

            return object;
        }
        Log.e("getSearchObject", "NULLO");
        return null;
    }

    public boolean isResponceValid(Element element){
        NodeList nList=element.getElementsByTagName(KEY_REQUEST_ROOT);
        Element e=(Element)nList.item(0);
        if(getValue(e, KEY_REQUEST_CONTAINER).equals(VALUE_VALID_RESPONCE)){
            return true;
        }
        return false;
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
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(xml);
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
        Log.d(">>>>>getValue", str);
        if(item!=null && str != null) {
            NodeList n = item.getElementsByTagName(str);
            return this.getElementValue(n.item(0));
        }
        return null;
    }


}



/*
    <ICECAT-interface xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="http://data.icecat.biz/xsd/ICECAT-interface_response.xsd">
<Product Code="1" HighPic="http://images.icecat.biz/img/norm/high/15694239-Sony.jpg" HighPicHeight="1000" HighPicSize="376991" HighPicWidth="1000" ID="15694239" LowPic="http://images.icecat.biz/img/norm/low/15694239-Sony.jpg" LowPicHeight="200" LowPicSize="30186" LowPicWidth="200" Name="10CDQ80SS" Pic500x500="http://images.icecat.biz/img/norm/medium/15694239-Sony.jpg" Pic500x500Height="500" Pic500x500Size="132606" Pic500x500Width="500" Prod_id="10CDQ80SS" Quality="ICECAT" ReleaseDate="2012-10-07" ThumbPic="http://images.icecat.biz/thumbs/15694239.jpg" ThumbPicSize="7175" Title="Sony 10CDQ80SS">
<CategoryFeatureGroup ID="2210" No="1">
<FeatureGroup ID="14">
<Name ID="5023" langid="1" Value="Weight & dimensions"/>
</FeatureGroup>
</CategoryFeatureGroup>
<CategoryFeatureGroup ID="1368" No="2">
<FeatureGroup ID="28">
<Name ID="5067" langid="1" Value="Operational conditions"/>
</FeatureGroup>
</CategoryFeatureGroup>
<CategoryFeatureGroup ID="10750" No="0">
<FeatureGroup ID="0">
<Name ID="5073" langid="1" Value="Technical details"/>
</FeatureGroup>
</CategoryFeatureGroup>
<CategoryFeatureGroup ID="3642" No="-10">
<FeatureGroup ID="67">
<Name ID="27423" langid="1" Value="Packaging data"/>
</FeatureGroup>
</CategoryFeatureGroup>
<CategoryFeatureGroup ID="10745" No="10">
<FeatureGroup ID="185">
<Name ID="1219853" langid="1" Value="Features"/>
</FeatureGroup>
</CategoryFeatureGroup>
<Category ID="292">
<Name ID="585" langid="1" Value="blank CDs"/>
</Category>
<EANCode/>
<ReasonsToBuy>
<ReasonToBuy ID="5232213" Value="700MB total capacity" HighPic="" HighPicSize="0" No="0" langid="1" origin="RTB"/>
</ReasonsToBuy>
<ProductBundled/>
<ProductDescription ID="36931945" LongDesc="Strict quality control and superior manufacturing processes enable Sony CD-R discs to deliver the mechanical precision needed to meet today's high-speed recording requirements. They provide 700MB of permanent data storage or 80 minutes of digital audio recording." ManualPDFSize="0" ManualPDFURL="" PDFSize="0" PDFURL="" ShortDesc="CD-R 48x recordable storage, 10-pack" URL="http://store.sony.com/p/CDR/en/p/100CDQ80RS" WarrantyInfo="" langid="1"/>
<ProductFeature Localized="0" ID="129267551" Local_ID="0" Value="CD-R" CategoryFeature_ID="23344" CategoryFeatureGroup_ID="10745" No="10300001" Presentation_Value="CD-R" Translated="0" Mandatory="1" Searchable="1">
<LocalValue Value="CD-R">
<Measure ID="29">
<Signs/>
</Measure>
</LocalValue>
<Feature ID="31">
<Measure ID="29" Sign="">
<Signs/>
</Measure>
<Name ID="1375" langid="1" Value="Type"/>
</Feature>
</ProductFeature>
<ProductFeature Localized="0" ID="129267550" Local_ID="0" Value="700" CategoryFeature_ID="8774" CategoryFeatureGroup_ID="10745" No="10100400" Presentation_Value="700 MB" Translated="0" Mandatory="1" Searchable="1">
<LocalValue Value="700">
<Measure ID="19">
<Signs>
<Sign ID="15506" langid="1">
<![CDATA[ MB ]]>
</Sign>
</Signs>
</Measure>
</LocalValue>
<Feature ID="1676">
<Measure ID="19" Sign="">
<Signs>
<Sign ID="15506" langid="1">
<![CDATA[ MB ]]>
</Sign>
</Signs>
</Measure>
<Name ID="5225" langid="1" Value="CD storage capacity"/>
</Feature>
</ProductFeature>
<ProductFeature Localized="0" ID="129267552" Local_ID="0" Value="10" CategoryFeature_ID="197474" CategoryFeatureGroup_ID="10745" No="10100007" Presentation_Value="10 pc(s)" Translated="0" Mandatory="1" Searchable="1">
<LocalValue Value="10">
<Measure ID="144">
<Signs>
<Sign ID="125" langid="1">
<![CDATA[ pc(s) ]]>
</Sign>
</Signs>
</Measure>
</LocalValue>
<Feature ID="20228">
<Measure ID="144" Sign="">
<Signs>
<Sign ID="125" langid="1">
<![CDATA[ pc(s) ]]>
</Sign>
</Signs>
</Measure>
<Name ID="1498423" langid="1" Value="Quantity per pack"/>
</Feature>
</ProductFeature>
<ProductFeature Localized="0" ID="129267553" Local_ID="0" Value="48" CategoryFeature_ID="10317" CategoryFeatureGroup_ID="10745" No="10100004" Presentation_Value="48x" Translated="0" Mandatory="1" Searchable="1">
<LocalValue Value="48">
<Measure ID="21">
<Signs>
<Sign ID="6" langid="1">
<![CDATA[ x ]]>
</Sign>
</Signs>
</Measure>
</LocalValue>
<Feature ID="42">
<Measure ID="21" Sign="">
<Signs>
<Sign ID="6" langid="1">
<![CDATA[ x ]]>
</Sign>
</Signs>
</Measure>
<Name ID="1421" langid="1" Value="CD write speed"/>
</Feature>
</ProductFeature>
<ProductFeature Localized="0" ID="129267554" Local_ID="0" Value="120" CategoryFeature_ID="15525" CategoryFeatureGroup_ID="10745" No="10100000" Presentation_Value="120 mm" Translated="0" Mandatory="1" Searchable="1">
<LocalValue Value="120">
<Measure ID="24">
<Signs>
<Sign ID="3" langid="1">
<![CDATA[ mm ]]>
</Sign>
</Signs>
</Measure>
</LocalValue>
<Feature ID="2660">
<Measure ID="24" Sign="">
<Signs>
<Sign ID="3" langid="1">
<![CDATA[ mm ]]>
</Sign>
</Signs>
</Measure>
<Name ID="13482" langid="1" Value="Optical disc diameter"/>
</Feature>
</ProductFeature>
<ProductGallery>
<ProductPicture IsMain="Y" LowPic="http://images.icecat.biz/img/norm/low/15694239-Sony.jpg" LowPicHeight="200" LowPicWidth="200" LowSize="30186" No="1" Original="http://images.icecat.biz/img/norm/high/15694239-Sony.jpg" OriginalSize="633516" Pic="http://images.icecat.biz/img/norm/high/15694239-Sony.jpg" Pic500x500="http://images.icecat.biz/img/norm/medium/15694239-Sony.jpg" Pic500x500Height="500" Pic500x500Size="132606" Pic500x500Width="500" PicHeight="1000" PicWidth="1000" ProductPicture_ID="14844056" Size="376991" ThumbPic="http://images.icecat.biz/thumbs/15694239.jpg" ThumbSize="7175" Type="ProductImage"/>
<ProductPicture LowPic="http://images.icecat.biz/img/gallery_lows/15694239-Sony-10CDQ80SS-15611.jpg" LowPicHeight="78" LowPicWidth="200" LowSize="10125" No="2" Original="http://images.icecat.biz/img/gallery/15694239-Sony-10CDQ80SS-15611.jpg" OriginalSize="162355" Pic="http://images.icecat.biz/img/gallery/15694239-Sony-10CDQ80SS-15611.jpg" Pic500x500="http://images.icecat.biz/img/gallery_mediums/15694239-Sony-10CDQ80SS-15611.jpg" Pic500x500Height="196" Pic500x500Size="39500" Pic500x500Width="500" PicHeight="391" PicWidth="1000" ProductPicture_ID="4658191" Size="109080" ThumbPic="http://images.icecat.biz/img/gallery_thumbs/15694239-Sony-10CDQ80SS-15611.jpg" ThumbSize="2853" Type="ProductImage"/>
<ProductPicture LowPic="http://images.icecat.biz/img/gallery_lows/15694239-Sony-10CDQ80SS-15612.jpg" LowPicHeight="78" LowPicWidth="200" LowSize="9269" No="3" Original="http://images.icecat.biz/img/gallery/15694239-Sony-10CDQ80SS-15612.jpg" OriginalSize="168215" Pic="http://images.icecat.biz/img/gallery/15694239-Sony-10CDQ80SS-15612.jpg" Pic500x500="http://images.icecat.biz/img/gallery_mediums/15694239-Sony-10CDQ80SS-15612.jpg" Pic500x500Height="196" Pic500x500Size="36258" Pic500x500Width="500" PicHeight="391" PicWidth="1000" ProductPicture_ID="4658192" Size="99022" ThumbPic="http://images.icecat.biz/img/gallery_thumbs/15694239-Sony-10CDQ80SS-15612.jpg" ThumbSize="2650" Type="ProductImage"/>
</ProductGallery>
<ProductMultimediaObject/>
<ProductRelated/>
<SummaryDescription>
<ShortSummaryDescription langid="1">
Sony 10CDQ80SS, CD-R, 700 MB, 10 pc(s), 48x, 120 mm
</ShortSummaryDescription>
<LongSummaryDescription langid="1">
Sony 10CDQ80SS. Type: CD-R, CD storage capacity: 700 MB, Quantity per pack: 10 pc(s)
</LongSummaryDescription>
</SummaryDescription>
<Supplier ID="5" Name="Sony"/>

*/