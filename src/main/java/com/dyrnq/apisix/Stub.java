package com.dyrnq.apisix;

import com.dyrnq.apisix.response.Multi;

import java.util.List;
import java.util.Map;

public interface Stub<T> {

    T get(String id) throws ApisixSDKException;

    Multi<T> query(String page, String page_size) throws ApisixSDKException;

    Multi<T> query(String page, String page_size, Map<String, String> qp) throws ApisixSDKException;

    void del(String id) throws ApisixSDKException;

    List<T> list() throws ApisixSDKException;

    T put(String id, T obj) throws ApisixSDKException;

    T putRaw(String id, String rawData) throws ApisixSDKException;

    T patchRaw(String id, String rawData) throws ApisixSDKException;

    //T post(T obj) throws ApisixSDKException;
}
