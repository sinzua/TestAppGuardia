package com.rilevamento.icecat;

/**
 * Created by Jumbo on 30/04/2017.
 */

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;



public class SignedRequestsHelperIcecat {

    private static final String UTF8_CHARSET = "UTF-8";
    private static final String REQUEST_URI = "/xml_s3/xml_server3.cgi?ean_upc=";
    private static final String REQUEST_LOGIN = "https://bo.icecat.biz/index.cgi";
    private static final String REQUEST_URI_FINAL = ";lang=it;output=productxml";
    private static final String REQUEST_METHOD = "GET";

    // https://data.icecat.biz/xml_s3/xml_server3.cgi?ean_upc=0027242855588;lang=it;output=productxml

    static String endpoint = "data.icecat.biz"; // must be lowercase

    public String username = "alessandroporcu.it";
    public String password = "pwd";


    SecretKeySpec secretKeySpec = null;
    Mac mac = null;

    //public String signUrl(String urlStr, String user, String pass, String outFilePath) {
    public String signUrl(String ean) {
        URL url = null;
        URL toSign = null;

        try {
            toSign = new URL(REQUEST_LOGIN);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            url = new URL("https://" + endpoint + REQUEST_URI + "?" + REQUEST_URI_FINAL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            // URL url = new URL ("http://ip:port/download_url");
            String authStr = username + ":" + password;
            String authEncoded = String.valueOf(Base64.encodeBase64(authStr.getBytes()));
            //faccio il signing sul portale
            try {
                signNow(toSign, authEncoded);
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*
            //eseguo le operaizoni di parsing
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + authEncoded);

            File file = new File(outFilePath);
            InputStream in = (InputStream) connection.getInputStream();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            for (int b; (b = in.read()) != -1; ) {
                out.write(b);
            }
            out.close();
            in.close();
        }
         */
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(url);
    }

    private static void signNow(URL toSign, String authEncoded) {
        HttpURLConnection connectionSign = null;
        try {
            connectionSign = (HttpURLConnection) toSign.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connectionSign.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        connectionSign.setDoOutput(true);
        connectionSign.setRequestProperty("Authorization", "Basic " + authEncoded);

    }









}



/*This XML file does not appear to have any style information associated with it. The document tree is shown below.
<!--  source: Icecat.biz 2017  -->
<ICECAT-interface xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="http://data.icecat.biz/xsd/ICECAT-interface_response.xsd">
<Product Code="1" HighPic="http://images.icecat.biz/img/norm/high/15694239-Sony.jpg" HighPicHeight="1000" HighPicSize="376991" HighPicWidth="1000" ID="15694239" LowPic="http://images.icecat.biz/img/norm/low/15694239-Sony.jpg" LowPicHeight="200" LowPicSize="30186" LowPicWidth="200" Name="CD-R 48x, 10" Pic500x500="http://images.icecat.biz/img/norm/medium/15694239-Sony.jpg" Pic500x500Height="500" Pic500x500Size="132606" Pic500x500Width="500" Prod_id="10CDQ80SS" Quality="ICECAT" ReleaseDate="2012-10-07" ThumbPic="http://images.icecat.biz/thumbs/15694239.jpg" ThumbPicSize="7175" Title="Sony CD-R 48x, 10 CD-R 700MB 10pezzo(i)">
<CategoryFeatureGroup ID="2210" No="1">
<FeatureGroup ID="14">
<Name ID="455278" langid="5" Value="Dimensioni e peso"/>
</FeatureGroup>
</CategoryFeatureGroup>
<CategoryFeatureGroup ID="1368" No="2">
<FeatureGroup ID="28">
<Name ID="455291" langid="5" Value="Condizioni ambientali"/>
</FeatureGroup>
</CategoryFeatureGroup>
<CategoryFeatureGroup ID="10750" No="0">
<FeatureGroup ID="0">
<Name ID="455293" langid="5" Value="Dettagli tecnici"/>
</FeatureGroup>
</CategoryFeatureGroup>
<CategoryFeatureGroup ID="3642" No="-10">
<FeatureGroup ID="67">
<Name ID="2576357" langid="5" Value="Dati su imballaggio"/>
</FeatureGroup>
</CategoryFeatureGroup>
<CategoryFeatureGroup ID="10745" No="10">
<FeatureGroup ID="185">
<Name ID="1318116" langid="5" Value="Caratteristiche"/>
</FeatureGroup>
</CategoryFeatureGroup>
<Category ID="292">
<Name ID="454432" langid="5" Value="CD vergini"/>
</Category>
<EANCode/>
<ReasonsToBuy/>
<ProductBundled/>
<ProductDescription/>
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
<Name ID="459966" langid="5" Value="Tipo"/>
</Feature>
</ProductFeature>
<ProductFeature Localized="0" ID="129267550" Local_ID="0" Value="700" CategoryFeature_ID="8774" CategoryFeatureGroup_ID="10745" No="10100400" Presentation_Value="700 MB" Translated="0" Mandatory="1" Searchable="1">
<LocalValue Value="700">
<Measure ID="19">
<Signs>
<Sign ID="6732" langid="5">
<![CDATA[ MB ]]>
</Sign>
</Signs>
</Measure>
</LocalValue>
<Feature ID="1676">
<Measure ID="19" Sign="">
<Signs>
<Sign ID="6732" langid="5">
<![CDATA[ MB ]]>
</Sign>
</Signs>
</Measure>
<Name ID="460835" langid="5" Value="Capacità originaria"/>
</Feature>
</ProductFeature>
<ProductFeature Localized="0" ID="129267552" Local_ID="0" Value="10" CategoryFeature_ID="197474" CategoryFeatureGroup_ID="10745" No="10100007" Presentation_Value="10 pezzo(i)" Translated="0" Mandatory="1" Searchable="1">
<LocalValue Value="10">
<Measure ID="144">
<Signs>
<Sign ID="18799" langid="5">
<![CDATA[ pezzo(i) ]]>
</Sign>
</Signs>
</Measure>
</LocalValue>
<Feature ID="20228">
<Measure ID="144" Sign="">
<Signs>
<Sign ID="18799" langid="5">
<![CDATA[ pezzo(i) ]]>
</Sign>
</Signs>
</Measure>
<Name ID="1510817" langid="5" Value="Quantità per pacco"/>
</Feature>
</ProductFeature>
<ProductFeature Localized="0" ID="129267553" Local_ID="0" Value="48" CategoryFeature_ID="10317" CategoryFeatureGroup_ID="10745" No="10100004" Presentation_Value="48x" Translated="0" Mandatory="1" Searchable="1">
<LocalValue Value="48">
<Measure ID="21">
<Signs>
<Sign ID="6734" langid="5">
<![CDATA[ x ]]>
</Sign>
</Signs>
</Measure>
</LocalValue>
<Feature ID="42">
<Measure ID="21" Sign="">
<Signs>
<Sign ID="6734" langid="5">
<![CDATA[ x ]]>
</Sign>
</Signs>
</Measure>
<Name ID="459971" langid="5" Value="Velocità di scrittura CD"/>
</Feature>
</ProductFeature>
<ProductFeature Localized="0" ID="129267554" Local_ID="0" Value="120" CategoryFeature_ID="15525" CategoryFeatureGroup_ID="10745" No="10100000" Presentation_Value="120 mm" Translated="0" Mandatory="1" Searchable="1">
<LocalValue Value="120">
<Measure ID="24">
<Signs>
<Sign ID="6737" langid="5">
<![CDATA[ mm ]]>
</Sign>
</Signs>
</Measure>
</LocalValue>
<Feature ID="2660">
<Measure ID="24" Sign="">
<Signs>
<Sign ID="6737" langid="5">
<![CDATA[ mm ]]>
</Sign>
</Signs>
</Measure>
<Name ID="455671" langid="5" Value="Diametro disco ottico"/>
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
<ShortSummaryDescription langid="5">
Sony CD-R 48x, 10, CD-R, 700 MB, 10 pezzo(i), 48x, 120 mm
</ShortSummaryDescription>
<LongSummaryDescription langid="5">
Sony CD-R 48x, 10. Tipo: CD-R, Capacità originaria: 700 MB, Quantità per pacco: 10 pezzo(i)
</LongSummaryDescription>
</SummaryDescription>
<Supplier ID="5" Name="Sony"/>
</Product>
</ICECAT-interface>
*/