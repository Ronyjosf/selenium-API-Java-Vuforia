import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

public class Target extends BaseTarget{

    public Target(){
        super();
    }
    public String addTarget() throws URISyntaxException, IOException, JSONException, NotFound {
        HttpPost postRequest = new HttpPost();
        HttpClient client = new DefaultHttpClient();
        postRequest.setURI(new URI(url + "/targets"));
        JSONObject requestBody = new JSONObject();

        setRequestBody(requestBody);
        postRequest.setEntity(new StringEntity(requestBody.toString()));
        setHeaders(postRequest); // Must be done after setting the body

        HttpResponse response = client.execute(postRequest);
//        if (response.getStatusLine().getStatusCode()!=200) {
//            throw new NotFoundException("got http error: "+response.getStatusLine().getStatusCode() );
//        }
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println(responseBody);

        JSONObject jobj = new JSONObject(responseBody);

        String uniqueTargetId = jobj.has("target_id") ? jobj.getString("target_id") : "";
        System.out.println("\nCreated target with id: " + uniqueTargetId);
//        Log.debug("\nCreated target with id: " + uniqueTargetId);

        return uniqueTargetId;

    }

    private void setHeaders(HttpUriRequest request) {
        SignatureBuilder sb = new SignatureBuilder();
        request.setHeader(new BasicHeader("Date", DateUtils.formatDate(new Date()).replaceFirst("[+]00:00$", "")));
        request.setHeader("Authorization", "VWS " + accessKey + ":" + sb.tmsSignature(request, secretKey));
        request.setHeader("Content-Type", "application/json");
    }
    private void setRequestBody(JSONObject requestBody) throws IOException, JSONException {
        File imageFile = new File(imageLocation);
        if(!imageFile.exists()) {
            System.out.println("File location does not exist!");
            System.exit(1);
        }
        byte[] image = FileUtils.readFileToByteArray(imageFile);
        requestBody.put("name", targetName); // Mandatory
        requestBody.put("width", 320.0); // Mandatory
        requestBody.put("image", Base64.encodeBase64String(image)); // Mandatory
        requestBody.put("active_flag", 1); // Optional
        requestBody.put("application_metadata", Base64.encodeBase64String("Vuforia test metadata".getBytes())); // Optional
    }

}
