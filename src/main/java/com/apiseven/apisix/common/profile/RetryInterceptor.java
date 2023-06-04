package com.apiseven.apisix.common.profile;

import java.io.IOException;
import java.util.List;

//import com.squareup.okhttp.Interceptor;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetryInterceptor implements Interceptor{
    int retryCount;
    Profile profile;

    public RetryInterceptor(int retryCount, Profile profile) {
        this.retryCount = retryCount;
        this.profile = profile;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        int retryCount = 0;
        // try the request
        List<Endpoint> endpoints = this.profile.getEndpoints();
        Endpoint endpoint = this.profile.getCurrentEndpoint();
        try {
            Response response = chain.proceed(request);
            //有结果就认为是成功了，只有连接不上或者超时才重试其他IP
            if(response != null && response.code() > 0){
                return response;
            }
            while (!response.isSuccessful() && retryCount <= this.retryCount) {
                String url = request.url().toString();
                for (Endpoint ep : endpoints) {
                    if(!url.contains(ep.getDomain()) && ep.isAlive()){
                        //替换
                        url = url.replace(endpoint.getDomain(), ep.getDomain());
                        //设置为当前
                        this.profile.setCurrentEndpoint(ep);
                    }
                }
                Request newRequest = response.request().newBuilder().url(url).build();
                retryCount++;
                // retry the request
                response = chain.proceed(newRequest);
            }

            return response;
        }catch (Exception e){
            String url = request.url().toString();
            for (Endpoint ep : endpoints) {
                if(!url.contains(ep.getDomain()) && ep.isAlive()){
                    //替换
                    url = url.replace(endpoint.getDomain(), ep.getDomain());
                    //设置为当前
                    this.profile.setCurrentEndpoint(ep);
                }
            }
            Request newRequest = request.newBuilder().url(url).build();
            retryCount++;
            // retry the request
            Response response = chain.proceed(newRequest);

            return response;
        }
    }
}
