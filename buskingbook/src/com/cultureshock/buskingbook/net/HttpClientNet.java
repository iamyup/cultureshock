package com.cultureshock.buskingbook.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;

import com.cultureshock.buskingbook.service.ServiceType;

public class HttpClientNet {

	private String domainAddress = ""; // 도메인 어드레스
	private List<NameValuePair> params; // 파라미터 모
	private HttpClient httpClient = new DefaultHttpClient(); // 클라이언트 연결

	/**
	 * 생성자
	 */
	public HttpClientNet() {
		params = new ArrayList<NameValuePair>();
	}

	public HttpClientNet(int serviceType) {
		this.domainAddress = ServiceType.getInstance().getUrl(serviceType);
		params = new ArrayList<NameValuePair>();
	}

	public HttpClientNet(String domainAddress) {
		this.domainAddress = domainAddress;
		params = new ArrayList<NameValuePair>();
	}

	public HttpClientNet(String domainAddress, List<NameValuePair> params) {
		this.domainAddress = domainAddress;
		this.params = params;
	}

	// post방식 실제 데이터 이동 HttpPost 메소드 사용
	public String httpToPost() {
		StringBuffer strbuffer = new StringBuffer();
		BufferedReader inreader = null;
		StatusLine status = null;
		HttpResponse response = null;

		try {
			HttpPost request = new HttpPost(domainAddress);

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					"utf-8");

			request.setEntity(entity);
			response = httpClient.execute(request);

			inreader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent(), "utf-8"));
			// inreader = new BufferedReader(new
			// InputStreamReader(response.getEntity().getContent()));
			{
				String strline = new String();

				while ((strline = inreader.readLine()) != null) {
					strbuffer.append(strline);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strbuffer.toString();
	}

	public String httpToPost(int serviceType) {
		StringBuffer strbuffer = new StringBuffer();
		BufferedReader inreader = null;
		StatusLine status = null;
		HttpResponse response = null;

		try {
			HttpPost request = new HttpPost(ServiceType.getInstance().getUrl(
					serviceType));

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					"utf-8");

			request.setEntity(entity);
			response = httpClient.execute(request);

			inreader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent(), "utf-8"));
			// inreader = new BufferedReader(new
			// InputStreamReader(response.getEntity().getContent()));
			{
				String strline = new String();

				while ((strline = inreader.readLine()) != null) {
					strbuffer.append(strline);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strbuffer.toString();
	}

	public String httpToPost(String url) {
		StringBuffer strbuffer = new StringBuffer();
		BufferedReader inreader = null;
		StatusLine status = null;
		HttpResponse response = null;

		try {
			HttpPost request = new HttpPost(url);

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					"utf-8");

			request.setEntity(entity);
			response = httpClient.execute(request);

			inreader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent(), "utf-8"));
			// inreader = new BufferedReader(new
			// InputStreamReader(response.getEntity().getContent()));
			{
				String strline = new String();

				while ((strline = inreader.readLine()) != null) {
					strbuffer.append(strline);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strbuffer.toString();
	}

	public void setParam(ArrayList<Params> params) {
		for (Params p : params) {
			this.params.add(new BasicNameValuePair(p.getId(), p.getValue()));
		}
	}

	/**
	 * 실제 클래스들에서 사용하게 되는 메소드 호출하면 통신이 일어나고 리턴을 OnResponseListener 가 받음
	 * 
	 * @param callbackListener
	 */

	public void doAsyncExecute(OnResponseListener callbackListener) {
		AsyncHttpDoPost httpDoPost = new AsyncHttpDoPost(new Handler(),
				new CallbackWrapper(callbackListener));

		httpDoPost.start();
	}

	protected class AsyncHttpDoPost extends Thread {
		private Handler handler = null;
		private CallbackWrapper wrapper = null;
		private String reqUrl = null;
		private String resContent;

		public AsyncHttpDoPost(Handler handler, CallbackWrapper wrapper) {
			this.handler = handler;
			this.wrapper = wrapper;
		}

		public AsyncHttpDoPost(Handler handler, String reqUrl,
				CallbackWrapper wrapper) {
			this.handler = handler;
			this.wrapper = wrapper;
			this.reqUrl = reqUrl;
		}

		public AsyncHttpDoPost(Handler handler, int serviceType,
				CallbackWrapper wrapper) {
			this.handler = handler;
			this.wrapper = wrapper;
			this.reqUrl = ServiceType.getInstance().getUrl(serviceType);
		}

		/**
		 * 런 하게 되면 통신이 일어나고 그 리턴값을 저장하고 쓰레드 CallBackWapper 를 돌려 시점을 맞
		 */
		@Override
		public void run() {
			try {

				// resContent = doHttpPost(httpClient, reqUrl);
				resContent = httpToPost();
			}
			// catch( ConnectTimeoutException e )
			// {
			// e.printStackTrace();
			// }
			// catch( SocketTimeoutException e )
			// {
			// e.printStackTrace();
			// }
			catch (Exception e) {
				e.printStackTrace();
			}
			wrapper.setResContent(resContent);
			handler.post(wrapper);
		}
	}

	public interface OnResponseListener {
		public void onResponseReceived(String resContent);
	}

	/**
	 * 이 콜랙 웹퍼가 런 되면 인터페이스 OnResposeReceived call!
	 * 
	 * @author takeoff004
	 * 
	 */
	protected class CallbackWrapper implements Runnable {
		private OnResponseListener callbackActivity;
		private String resContent;

		public CallbackWrapper(OnResponseListener callbackActivity) {
			this.callbackActivity = callbackActivity;
		}

		@Override
		public void run() {
			callbackActivity.onResponseReceived(resContent);
		}

		public void setResContent(String resContent) {
			this.resContent = resContent;
		}
	}

}
