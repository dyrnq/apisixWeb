package com.dyrnq.apisix.domain;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class NodeAdapter extends TypeAdapter<List<Node>> {

    private final Gson gson;

    public NodeAdapter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public List<Node> read(JsonReader jsonReader) throws IOException {
        switch (jsonReader.peek()) {
            case NULL:
                return Collections.<Node>emptyList();
            case BEGIN_OBJECT:
                Type type = new TypeToken<Map<String, Integer>>() {}.getType();
                Map<String, Integer> map = gson.fromJson(jsonReader, type);

                List<Node> list = new ArrayList<Node>();

                if (map != null) {
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        String key = entry.getKey();
                        Integer val = entry.getValue();

                        String[] hostandport = org.apache.commons.lang3.StringUtils.splitByWholeSeparator(key, ":");
                        Node node = new Node();
                        if (hostandport != null) {
                            if (hostandport.length >= 1) {
                                node.setHost(hostandport[0]);
                            }
                            if (hostandport.length == 2) {
                                node.setPort(new Integer(hostandport[1]));
                            } else {
                                node.setPort(80);
                            }
                        }

                        node.setWeight(val);
                        list.add(node);
                    }
                }
                return list;

            case BEGIN_ARRAY:
                return gson.fromJson(jsonReader, new TypeToken<List<Node>>() {}.getType());
            default:
                throw new RuntimeException("Expected object or string, not " + jsonReader.peek());
        }
    }

    @Override
    public void write(JsonWriter out, List<Node> value) throws IOException {
        Type type = new TypeToken<List<Node>>() {}.getType();
        gson.toJson(value, type, out);
    }
}
