package com.hightechcommerce.grabbit;

import android.app.Activity;
import android.app.Notification;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.loopj.android.http.*;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;


public class GrabbitActivity extends Activity {

    private class SalesforceRestClient {

        public GrabbitActivity m_parent;
        private AsyncHttpClient m_client = new AsyncHttpClient();


        public SalesforceRestClient(GrabbitActivity parent) {
            m_parent = parent;
        }

        public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
            m_client.get(url, params, responseHandler);
        }

        public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
            m_client.post(url, params, responseHandler);
        }

        public void getAccessToken() {
            String url = "https://login.salesforce.com/services/oauth2/token";
            RequestParams params = new RequestParams();
            params.put("grant_type", "password");
            params.put("client_id", "3MVG9Rd3qC6oMalUYpGoMBRpKDnGNj5eGHM3Sr5VgzWOGNSCa0RxtepSVr8ssFjvPpyInlqEXKTlG8nfryaqC");
            params.put("client_secret", "7263624499985818506");
            params.put("username", "thomas.s.kunze@me.com");
            params.put("password", "hightechcommerce123");

            JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // If the response is JSONObject instead of expected JSONArray

                    Notification notification = new NotificationCompat.Builder(getApplication())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(new long[]{1000, 1000, 1000})
                            .setContentTitle("Have fun with Grabbit!")
                            .extend(
                                    new NotificationCompat.WearableExtender()
                                            .setHintShowBackgroundOnly(true)
                                            .setHintScreenTimeout(WearableExtender.SCREEN_TIMEOUT_LONG)
                                            .setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.hintergrundlogo)))
                            .build();

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());

                    notificationManager.notify(notificationId, notification);
                    notificationId += 1;


                    try {
                        m_parent.setToken(response.getString("access_token"));
                    }
                    catch (Exception e ) {
                        Log.d("Exception:", e.getMessage());
                    }

                    submitTake(1);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                    // Pull out the first event on the public timeline

                    Log.d("INFO:", "Connection established! 2");
                }

                @Override
                public void	onSuccess(int statusCode, org.apache.http.Header[] headers, java.lang.String responseString) {
                    Log.d("INFO:", "Connection established! 3");
                }


                @Override
                public void	onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseString, java.lang.Throwable throwable) {
                    Log.d("INFO:", "Connection failed! 1");
                }

                @Override
                public void	onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.Throwable throwable, org.json.JSONArray errorResponse) {
                    Log.d("INFO:", "Connection failed! 2");
                }

                @Override
                public void	onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.Throwable throwable, org.json.JSONObject errorResponse) {
                    Log.d("INFO:", "Statuscode: " + String.valueOf(statusCode) + " " + throwable.getMessage() + " " + errorResponse.toString());
                }



                @Override
                public void onStart() {
                    // called before request is started
                }

                /*@Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    System.out.println("Connection failed!");
                }*/

                @Override
                public void onRetry(int retryNo) {
                    // called when request is retried
                }
            };

            post(url, params, responseHandler);

        }

        public void submitTake(int product) {
            String url = "https://eu5.salesforce.com/services/data/v34.0/query"; //Link tauschen
            RequestParams params = new RequestParams();
            params.put("q", "SELECT ID FROM Product2");
            //Header[] // Authorizationen Bearer Access_Token
            m_client.addHeader("Content-type", "application/json");
            m_client.addHeader("Authorization", "Bearer " + m_access_token);

            JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // If the response is JSONObject instead of expected JSONArray
                    Log.d("INFO:", response.toString());
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                    // Pull out the first event on the public timeline

                    Log.d("INFO:", "Statuscode: " + String.valueOf(statusCode) + " " + timeline.toString());
                }

                @Override
                public void	onSuccess(int statusCode, org.apache.http.Header[] headers, java.lang.String responseString) {
                    Log.d("INFO:", "Connection established! 3");
                }


                @Override
                public void	onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseString, java.lang.Throwable throwable) {
                    Log.d("INFO:", "Statuscode: " + String.valueOf(statusCode) + " " + responseString.toString() + " " + throwable.toString());
                }

                @Override
                public void	onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.Throwable throwable, org.json.JSONArray errorResponse) {
                    Log.d("INFO:", "Statuscode: " + String.valueOf(statusCode) + " " + errorResponse.toString() + " " + throwable.toString());
                }

                @Override
                public void	onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.Throwable throwable, org.json.JSONObject errorResponse) {
                    Log.d("INFO:", "Statuscode: " + String.valueOf(statusCode) + " " + throwable.getMessage() + " " + errorResponse.toString());
                }



                @Override
                public void onStart() {
                    // called before request is started
                }

                /*@Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    System.out.println("Connection failed!");
                }*/

                @Override
                public void onRetry(int retryNo) {
                    // called when request is retried
                }
            };

            get(url, params, responseHandler);
        }


    }

    SalesforceRestClient m_SalesforceModel;
    public int notificationId;
    public String m_access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabbit);

        notificationId = 1;
        m_SalesforceModel = new SalesforceRestClient(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grabbit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void connectToSalesforce(View v) {
        m_SalesforceModel.getAccessToken();
    }

    public void setToken(String s) {
        m_access_token = s;
        Log.d("Token", m_access_token);
    }

}
