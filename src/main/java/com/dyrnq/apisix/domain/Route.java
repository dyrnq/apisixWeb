package com.dyrnq.apisix.domain;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Map;
import java.util.List;
public class Route{
    public Route(){
        super();
    }
    @SerializedName("create_time")
    @Expose
    private Long createTime;

    public void setCreateTime(Long createTime){
        this.createTime=createTime;
    }

    public Long getCreateTime(){
        return this.createTime;
    }

    @SerializedName("desc")
    @Expose
    private String desc;

    public void setDesc(String desc){
        this.desc=desc;
    }

    public String getDesc(){
        return this.desc;
    }

    @SerializedName("enable_websocket")
    @Expose
    private Boolean enableWebsocket;

    public void setEnableWebsocket(Boolean enableWebsocket){
        this.enableWebsocket=enableWebsocket;
    }

    public Boolean isEnableWebsocket(){
        return this.enableWebsocket;
    }

    @SerializedName("filter_func")
    @Expose
    private String filterFunc;

    public void setFilterFunc(String filterFunc){
        this.filterFunc=filterFunc;
    }

    public String getFilterFunc(){
        return this.filterFunc;
    }

    @SerializedName("host")
    @Expose
    private String host;

    public void setHost(String host){
        this.host=host;
    }

    public String getHost(){
        return this.host;
    }

    @SerializedName("hosts")
    @Expose
    private List<String> hosts;

    public void setHosts(List<String> hosts){
        this.hosts=hosts;
    }

    public List<String> getHosts(){
        return this.hosts;
    }

    @SerializedName("labels")
    @Expose
    private Map<String,String> labels;

    public void setLabels(Map<String,String> labels){
        this.labels=labels;
    }

    public Map<String,String> getLabels(){
        return this.labels;
    }

    @SerializedName("methods")
    @Expose
    private List<String> methods;

    public void setMethods(List<String> methods){
        this.methods=methods;
    }

    public List<String> getMethods(){
        return this.methods;
    }

    @SerializedName("name")
    @Expose
    private String name;

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    @SerializedName("plugin_config_id")
    @Expose
    private String pluginConfigId;

    public void setPluginConfigId(String pluginConfigId){
        this.pluginConfigId=pluginConfigId;
    }

    public String getPluginConfigId(){
        return this.pluginConfigId;
    }

    @SerializedName("plugins")
    @Expose
    private Map<String,Plugin> plugins;

    public void setPlugins(Map<String,Plugin> plugins){
        this.plugins=plugins;
    }

    public Map<String,Plugin> getPlugins(){
        return this.plugins;
    }

    @SerializedName("priority")
    @Expose
    private Integer priority;

    public void setPriority(Integer priority){
        this.priority=priority;
    }

    public Integer getPriority(){
        return this.priority;
    }

    @SerializedName("remote_addr")
    @Expose
    private String remoteAddr;

    public void setRemoteAddr(String remoteAddr){
        this.remoteAddr=remoteAddr;
    }

    public String getRemoteAddr(){
        return this.remoteAddr;
    }

    @SerializedName("remote_addrs")
    @Expose
    private List<String> remoteAddrs;

    public void setRemoteAddrs(List<String> remoteAddrs){
        this.remoteAddrs=remoteAddrs;
    }

    public List<String> getRemoteAddrs(){
        return this.remoteAddrs;
    }

    @SerializedName("script")
    @Expose
    private String script;

    public void setScript(String script){
        this.script=script;
    }

    public String getScript(){
        return this.script;
    }

    @SerializedName("service_id")
    @Expose
    private String serviceId;

    public void setServiceId(String serviceId){
        this.serviceId=serviceId;
    }

    public String getServiceId(){
        return this.serviceId;
    }

    @SerializedName("status")
    @Expose
    private Integer status;

    public void setStatus(Integer status){
        this.status=status;
    }

    public Integer getStatus(){
        return this.status;
    }

    @SerializedName("timeout")
    @Expose
    private Timeout timeout;

    public void setTimeout(Timeout timeout){
        this.timeout=timeout;
    }

    public Timeout getTimeout(){
        return this.timeout;
    }

    @SerializedName("update_time")
    @Expose
    private Long updateTime;

    public void setUpdateTime(Long updateTime){
        this.updateTime=updateTime;
    }

    public Long getUpdateTime(){
        return this.updateTime;
    }

    @SerializedName("upstream")
    @Expose
    private Upstream upstream;

    public void setUpstream(Upstream upstream){
        this.upstream=upstream;
    }

    public Upstream getUpstream(){
        return this.upstream;
    }

    @SerializedName("upstream_id")
    @Expose
    private String upstreamId;

    public void setUpstreamId(String upstreamId){
        this.upstreamId=upstreamId;
    }

    public String getUpstreamId(){
        return this.upstreamId;
    }

    @SerializedName("uri")
    @Expose
    private String uri;

    public void setUri(String uri){
        this.uri=uri;
    }

    public String getUri(){
        return this.uri;
    }

    @SerializedName("uris")
    @Expose
    private List<String> uris;

    public void setUris(List<String> uris){
        this.uris=uris;
    }

    public List<String> getUris(){
        return this.uris;
    }

    @SerializedName("vars")
    @Expose
    private List<List<String>> vars;

    public void setVars(List<List<String>> vars){
        this.vars=vars;
    }

    public List<List<String>> getVars(){
        return this.vars;
    }

    @SerializedName("id")
    @Expose
    private String id;

    public void setId(String id){
        this.id=id;
    }

    public String getId(){
        return this.id;
    }

}
