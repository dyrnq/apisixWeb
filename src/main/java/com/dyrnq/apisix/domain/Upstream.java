package com.dyrnq.apisix.domain;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Map;
import java.util.List;
public class Upstream{
    public Upstream(){
        super();
    }
    @SerializedName("checks")
    @Expose
    private String checks;

    public void setChecks(String checks){
        this.checks=checks;
    }

    public String getChecks(){
        return this.checks;
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

    @SerializedName("discovery_type")
    @Expose
    private String discoveryType;

    public void setDiscoveryType(String discoveryType){
        this.discoveryType=discoveryType;
    }

    public String getDiscoveryType(){
        return this.discoveryType;
    }

    @SerializedName("hash_on")
    @Expose
    private String hashOn;

    public void setHashOn(String hashOn){
        this.hashOn=hashOn;
    }

    public String getHashOn(){
        return this.hashOn;
    }

    //@SerializedName("keepalive_pool.idle_timeout")
    //@Expose
    //private String keepalivePool.idleTimeout;

    //@SerializedName("keepalive_pool.requests")
    //@Expose
    //private String keepalivePool.requests;

    //@SerializedName("keepalive_pool.size")
    //@Expose
    //private String keepalivePool.size;

    @SerializedName("key")
    @Expose
    private String key;

    public void setKey(String key){
        this.key=key;
    }

    public String getKey(){
        return this.key;
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

    @SerializedName("name")
    @Expose
    private String name;

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    @SerializedName("nodes")
    @Expose
    @JsonAdapter(NodeAdapterFactory.class)
    private List<Node> nodes;

    public void setNodes(List<Node> nodes){
        this.nodes=nodes;
    }

    public List<Node> getNodes(){
        return this.nodes;
    }

    @SerializedName("pass_host")
    @Expose
    private String passHost;

    public void setPassHost(String passHost){
        this.passHost=passHost;
    }

    public String getPassHost(){
        return this.passHost;
    }

    @SerializedName("retries")
    @Expose
    private Integer retries;

    public void setRetries(Integer retries){
        this.retries=retries;
    }

    public Integer getRetries(){
        return this.retries;
    }

    @SerializedName("retry_timeout")
    @Expose
    private String retryTimeout;

    public void setRetryTimeout(String retryTimeout){
        this.retryTimeout=retryTimeout;
    }

    public String getRetryTimeout(){
        return this.retryTimeout;
    }

    @SerializedName("scheme")
    @Expose
    private String scheme;

    public void setScheme(String scheme){
        this.scheme=scheme;
    }

    public String getScheme(){
        return this.scheme;
    }

    @SerializedName("service_name")
    @Expose
    private String serviceName;

    public void setServiceName(String serviceName){
        this.serviceName=serviceName;
    }

    public String getServiceName(){
        return this.serviceName;
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

    //@SerializedName("tls.client_cert")
    //@Expose
    //private String tls.clientCert;

    //@SerializedName("tls.client_cert_id")
    //@Expose
    //private String tls.clientCertId;

    //@SerializedName("tls.client_key")
    //@Expose
    //private String tls.clientKey;

    @SerializedName("type")
    @Expose
    private String type;

    public void setType(String type){
        this.type=type;
    }

    public String getType(){
        return this.type;
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

    @SerializedName("upstream_host")
    @Expose
    private String upstreamHost;

    public void setUpstreamHost(String upstreamHost){
        this.upstreamHost=upstreamHost;
    }

    public String getUpstreamHost(){
        return this.upstreamHost;
    }

    @SerializedName("keepalivePool")
    @Expose
    private KeepalivePool keepalivePool;

    public void setKeepalivePool(KeepalivePool keepalivePool){
        this.keepalivePool=keepalivePool;
    }

    public KeepalivePool getKeepalivePool(){
        return this.keepalivePool;
    }

    @SerializedName("tls")
    @Expose
    private Tls tls;

    public void setTls(Tls tls){
        this.tls=tls;
    }

    public Tls getTls(){
        return this.tls;
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
