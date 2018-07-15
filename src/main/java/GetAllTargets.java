import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;


//See the Vuforia Web Services Developer API Specification - https://developer.vuforia.com/resources/dev-guide/listing-targets-cloud-database

public class GetAllTargets extends BaseTarget{

	protected JSONArray getTargets() throws URISyntaxException, ClientProtocolException, IOException, JSONException {
		HttpGet getRequest = new HttpGet();
		HttpClient client = new DefaultHttpClient();
		getRequest.setURI(new URI(url + "/targets"));
		setHeaders(getRequest);
		
		HttpResponse response = client.execute(getRequest);
		HttpEntity entity = response.getEntity();
		String tmp = EntityUtils.toString(response.getEntity());
		JSONObject jsonObj = new JSONObject(tmp);
		JSONArray jsonArray= (JSONArray) jsonObj.get("results");
//		System.out.println(EntityUtils.toString(response.getEntity()));

		return jsonArray;
	}
	
	private void setHeaders(HttpUriRequest request) {
		SignatureBuilder sb = new SignatureBuilder();
		request.setHeader(new BasicHeader("Date", DateUtils.formatDate(new Date()).replaceFirst("[+]00:00$", "")));
		request.setHeader("Authorization", "VWS " + accessKey + ":" + sb.tmsSignature(request, secretKey));
	}

//	public static void get() throws URISyntaxException, ClientProtocolException, IOException {
//		GetAllTargets g = new GetAllTargets();
//		g.getTargets();
//	}
}
