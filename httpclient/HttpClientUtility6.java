package httpclient;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class HttpClientUtility {

	public static String get(String url) throws Exception{
		return getExecute(url,null,null);
	}
	
	public static String get(String url,NameValuePair nameValuePair) throws Exception{
		List<NameValuePair> nameValuePairs = new ArrayList<>();
		nameValuePairs.add(nameValuePair);
		return getExecute(url,nameValuePairs,null);
	}
	
	public static String post(String url) throws Exception{
		return postExecute(url,null,null,null);
	}
	
	public static String post4JSON(String url, Object jsonObj, Map<String,String> headers) throws Exception{
		return postExecute(url,null,jsonObj,null);
	}
	
	public static String post4Form(String url, List<NameValuePair> nvps, Map<String,String> headers) throws Exception{
		return postExecute(url,nvps,null,null);
	}
	
	public static String getExecute(String url, List<NameValuePair> nvps, Map<String,String> headers) throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		URIBuilder uriBuilder = new URIBuilder(url);
		
		//set parameters
		if(nvps != null && !nvps.isEmpty()) uriBuilder.setParameters(nvps);
		
		HttpGet httpGet = new HttpGet(uriBuilder.build());
		
		//set headers
		if(headers != null && !headers.isEmpty()) {
			Header[] rHeaders = new Header[headers.size()];
			int i = 0;
			for(Map.Entry<String,String> entry : headers.entrySet()) {
				rHeaders[i++] = new BasicHeader(entry.getKey(),entry.getValue());
			}
			httpGet.setHeaders(rHeaders);
		}
		CloseableHttpResponse response = httpClient.execute(httpGet);
		HttpEntity responseEntity = response.getEntity();
		return responseEntity != null?EntityUtils.toString(responseEntity):null;
	}
	
	public static String postExecute(String url,List<NameValuePair> nvps, Object jsonObj, Map<String,String> headers) throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		URIBuilder uriBuilder = new URIBuilder(url);
		
		//set parameters
		if(nvps != null && !nvps.isEmpty()) uriBuilder.setParameters(nvps);
		
		HttpPost httpPost = new HttpPost(uriBuilder.build());
		
		//set json parameter
		if(jsonObj != null) {
			headers.put("Content-Type", "application/json;charset=utf-8");
			httpPost.setEntity(new StringEntity(JSON.toJSONString(jsonObj), "utf-8"));
		}
		
		//set headers
		if(headers != null && !headers.isEmpty()) {
			Header[] rHeaders = new Header[headers.size()];
			int i = 0;
			for(Map.Entry<String,String> entry : headers.entrySet()) {
				rHeaders[i++] = new BasicHeader(entry.getKey(),entry.getValue());
			}
			httpPost.setHeaders(rHeaders);
		}
		
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity responseEntity = response.getEntity();
		return responseEntity != null?EntityUtils.toString(responseEntity):null;
	}
	
	public static void simpleGet(String url, String path) throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(httpGet);
		HttpEntity responseEntity = response.getEntity();
		byte[] bs= EntityUtils.toByteArray(responseEntity);
		ByteBuffer bb = ByteBuffer.wrap(bs);
		FileChannel fc = new FileOutputStream(path).getChannel();
		fc.write(bb);
		fc.close();
	}
}
