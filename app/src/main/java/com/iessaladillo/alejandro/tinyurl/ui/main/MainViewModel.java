package com.iessaladillo.alejandro.tinyurl.ui.main;

import android.os.AsyncTask;

import com.iessaladillo.alejandro.tinyurl.utils.ValidationUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<String> tinyUrl = new MutableLiveData<>();
    private Task task;

    public LiveData<String> getTinyUrl() {
        return tinyUrl;
    }

    public void makeSmall(String url) {
        if (ValidationUrl.isValidUrl(url)) {
            task = new Task();
            task.execute(url);
        }
    }

    public class Task extends AsyncTask<String, Void , String> {

        @Override
        protected String doInBackground(String... strings) {
            String linea;
            StringBuilder content = new StringBuilder();
            HttpURLConnection httpUrlConnection = null;

            try {
                URL url = new URI("https://tinyurl.com/api-create.php?url="+strings[0]).toURL();
                httpUrlConnection = (HttpURLConnection) url.openConnection();

                httpUrlConnection.setRequestMethod("POST");
                httpUrlConnection.setConnectTimeout(5000);
                httpUrlConnection.setReadTimeout(5000);
                httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpUrlConnection.setDoOutput(true);

//                if (strings[0] != null) {
//                    try(PrintWriter writer = new PrintWriter(httpUrlConnection.getOutputStream())){
//                        writer.write("url=" + strings[0]);
//                        writer.flush();
//                    }
//
//                }
                if (httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    try(BufferedReader reader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()))) {
                        while((linea = reader.readLine()) != null) {
                            content.append(linea);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } finally {
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            }

            tinyUrl.postValue(content.toString());
            return content.toString();
        }
    }

}
