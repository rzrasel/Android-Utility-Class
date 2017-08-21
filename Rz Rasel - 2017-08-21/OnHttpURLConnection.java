
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rz Rasel 2017-08-21.
 */
public class OnHttpURLConnection {

    private String strDomainURL;
    private final String userAgent = "Mozilla/5.0";
    private REQUEST_METHODS requestMethods;
    private boolean isAllowRedirects;
    HashMap<String, String> urlHeaderList = new HashMap<String, String>();
    HashMap<String, String> urlRequestParameterList = new HashMap<String, String>();

    public enum REQUEST_METHODS {
        GET("GET"), POST("POST");
        private final String methodName;

        REQUEST_METHODS(String argMethodName) {
            this.methodName = argMethodName;
        }

        public String getMethodName() {
            return this.methodName;
        }
    };

    public OnHttpURLConnection() {
    }

    public OnHttpURLConnection onPrepareConnection(String argStrDomainURL, REQUEST_METHODS argRequestMethods, boolean argIsAllowRedirects) {
        this.strDomainURL = argStrDomainURL;
        this.requestMethods = argRequestMethods;
        this.isAllowRedirects = argIsAllowRedirects;
        return this;
    }

    public OnHttpURLConnection setURLParameters(HashMap<String, String> argRequestParameterList) {
        this.urlRequestParameterList = argRequestParameterList;
        return this;
    }

    public OnHttpURLConnection setUrlHeader(HashMap<String, String> argUrlHeaderList) {
        this.urlHeaderList = argUrlHeaderList;
        return this;
    }

    public void onOpenConnection() {
        try {
            URL domainURL = new URL(strDomainURL);
            //Get url protocol: domainURL.getProtocol()
            System.out.println("HTTP_REQUESTED_URL: " + strDomainURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) domainURL.openConnection();
            httpURLConnection.setRequestMethod(requestMethods.getMethodName());
            System.out.println("HTTP_REQUESTED_METHODS: " + requestMethods.getMethodName());
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setUseCaches(false);
            //httpURLConnection.setRequestProperty("User-Agent", userAgent);
            httpURLConnection.setInstanceFollowRedirects(isAllowRedirects);
            httpURLConnection.setRequestProperty("charset", "utf-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            if (urlHeaderList.size() > 0) {
                //JSONObject jsonObject = new JSONObject(urlHeaderList);
                //LogWriter.Log("PRINT_HEADER_LIST:- " + jsonObject.toString());
                for (Map.Entry<String, String> entry : urlHeaderList.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpURLConnection.setRequestProperty(key, value);
                }
            }
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            if (REQUEST_METHODS.POST == requestMethods && urlRequestParameterList.size() > 0) {
                onWriteHttpUrlData(httpURLConnection);
            }
            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("HTTP_RESPONSE_CODE: " + responseCode);
            String httpURLData = onReadHttpUrlData(httpURLConnection);
            httpURLConnection.disconnect();
            if (httpURLData != null) {
                System.out.println("URL_DATA: " + httpURLData);
            }
        } catch (MalformedURLException e) {
            System.out.println("PRINT_ERROR_MalformedURLException: " + e.toString());
        } catch (IOException e) {
            System.out.println("PRINT_ERROR_IOException: " + e.toString());
        }
    }

    private String getURLRequestParameters(Map<String, String> argUrlParameters) {
        //byte[] retVal;
        String retVal = null;
        StringBuilder stringBuilderUrlParams = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = argUrlParameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> urlParameters = iterator.next();
            stringBuilderUrlParams.append(urlParameters.getKey()).append('=').append(urlParameters.getValue());
            if (iterator.hasNext()) {
                stringBuilderUrlParams.append('&');
            }
        }
        //String strUrlParams = stringBuilderUrlParams.toString();
        //retVal = strUrlParams.getBytes();
        retVal = stringBuilderUrlParams.toString();
        System.out.println("HTTP_REQUESTED_PARAMETERS: " + retVal);
        return retVal;
    }

    private void onWriteHttpUrlData(HttpURLConnection argHttpURLConnection) {
        String urlParams = getURLRequestParameters(urlRequestParameterList);
        try {
            OutputStream outputStream = argHttpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(urlParams);
            writer.flush();
            writer.close();
            outputStream.close();
        } catch (IOException e) {
            System.out.println("PRINT_ERROR_IOException:- " + e.getMessage().toString());
        }
    }

    private String onReadHttpUrlData(HttpURLConnection argHttpURLConnection) {
        String retVal = null;
        try {
            InputStream inputStream = null;
            StringBuilder stringBuilder = null;
            if (argHttpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = argHttpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    //sb.append(line + "\n");
                    stringBuilder.append(line);
                }
                inputStream.close();
                //argHttpURLConnection.disconnect();
                retVal = stringBuilder.toString();
            } else {
                inputStream = argHttpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    //sb.append(line + "\n");
                    stringBuilder.append(line);
                }
                inputStream.close();
                //argHttpURLConnection.disconnect();
                System.out.println("PRINT_ERROR_STRING: " + stringBuilder.toString());
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("PRINT_ERROR_IOException:- " + e.getMessage().toString());
        }
        return retVal;
    }

    public static void main(String... args) {
        /*new OnHttpURLConnection()
                .onPrepareConnection("https://stackoverflow.com", REQUEST_METHODS.POST, true)
                .onOpenConnection();*/
        HashMap<String, String> mapHttpParameter = new HashMap<String, String>();
        mapHttpParameter.clear();
        mapHttpParameter.put("auth_key", "F3:DE:B3:32:11:1D:E1:AB:26:5D:D8:D8:B2:15:EF:E3:BF:04:CA:2C");
        mapHttpParameter.put("package_name", "com.sm.cmdss");
        mapHttpParameter.put("app_version", "1.0.0");

        mapHttpParameter.put("fcm_token", "The fcm token field is required");
        mapHttpParameter.put("user_lat", "41.40338");
        mapHttpParameter.put("user_lng", "2.17403");
        //httpParameter.put(HTTP_PARAM_USER_TYPE, userType + "");
        mapHttpParameter.put("user_mob_num", "8801756200700");
        mapHttpParameter.put("user_password", "agent14");
        new OnHttpURLConnection()
                .onPrepareConnection("http://cmdss.tblbd.com/cmdss/public/index.php/user_login_check", REQUEST_METHODS.POST, true)
                .setURLParameters(mapHttpParameter)
                .onOpenConnection();
        /*int someNumber = 42;
        String someString = "foobar";
        Object[] args = {new Long(someNumber), someString};
        MessageFormat fmt = new MessageFormat("String is \"{1}\", number is {0}.");
        System.out.println(fmt.format(args));*/
        String formatString = "there were {0} {0,choice,0#objects|1#object|1<objects}";
        MessageFormat fmt = new MessageFormat(formatString);
        fmt.format(new Object[]{new Long(8733)});
        System.out.println(fmt.toString());
        getFormatedPhone("8801812166155");
    }

    public static String getFormatedPhone(String argPhoneNumber) {
        String phoneNumber = new StringBuilder(argPhoneNumber).reverse().toString();
        StringBuilder formatPhoneNumber = new StringBuilder(phoneNumber)
                .insert(4, " ")
                .insert(8, " ")
                .insert(13, " ");
        //88{13}01812{8}166{4}155
        phoneNumber = formatPhoneNumber.toString();
        phoneNumber = new StringBuilder(phoneNumber).reverse().toString();
        System.out.println("PHONE_NUMBER: " + phoneNumber);
        return phoneNumber;
    }

    public interface OnEventsListenerHandler {
    }

    public static String toTitleCase(String argInput) {
        //LogWriter.Log(argInput.substring(0, 1).toUpperCase() + argInput.substring(1).toLowerCase());
        argInput = argInput.toLowerCase();
        char charZero = argInput.charAt(0);
        String charToStr = new String("" + charZero);
        String strFull = charToStr.toUpperCase();
        return strFull + argInput.substring(1);
    }
}
/*
https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html
https://stackoverflow.com/questions/4205980/java-sending-http-parameters-via-post-method-easily
https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/

https://stackoverflow.com/questions/8196771/format-a-string-using-regex-in-java


https://stackoverflow.com/questions/11746442/java-mask-formatting-strings
https://www.dotnetperls.com/format-java
https://stackoverflow.com/questions/4920228/android-format-string-with-mask

 */
