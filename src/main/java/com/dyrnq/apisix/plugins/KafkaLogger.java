package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// kafka-logger
public class KafkaLogger { 
// broker_list
@SerializedName("broker_list")
@Expose
public Object brokerList;
// meta_format
@SerializedName("meta_format")
@Expose
public String metaFormat;
// timeout
@SerializedName("timeout")
@Expose
public Integer timeout;
// kafka_topic
@SerializedName("kafka_topic")
@Expose
public String kafkaTopic;
// producer_type
@SerializedName("producer_type")
@Expose
public String producerType;
// include_req_body
@SerializedName("include_req_body")
@Expose
public boolean includeReqBody;
// required_acks
@SerializedName("required_acks")
@Expose
public Integer requiredAcks;
// cluster_name
@SerializedName("cluster_name")
@Expose
public Integer clusterName;
// producer_batch_num
@SerializedName("producer_batch_num")
@Expose
public Integer producerBatchNum;
// producer_batch_size
@SerializedName("producer_batch_size")
@Expose
public Integer producerBatchSize;
// producer_max_buffering
@SerializedName("producer_max_buffering")
@Expose
public Integer producerMaxBuffering;
// producer_time_linger
@SerializedName("producer_time_linger")
@Expose
public Integer producerTimeLinger;
// meta_refresh_interval
@SerializedName("meta_refresh_interval")
@Expose
public Integer metaRefreshInterval;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// batch_max_size
@SerializedName("batch_max_size")
@Expose
public Integer batchMaxSize;
// retry_delay
@SerializedName("retry_delay")
@Expose
public Integer retryDelay;
// inactive_timeout
@SerializedName("inactive_timeout")
@Expose
public Integer inactiveTimeout;
// buffer_duration
@SerializedName("buffer_duration")
@Expose
public Integer bufferDuration;
// max_retry_count
@SerializedName("max_retry_count")
@Expose
public Integer maxRetryCount;
// name
@SerializedName("name")
@Expose
public String name;
// brokers
@SerializedName("brokers")
@Expose
public String[] brokers;
// include_resp_body_expr
@SerializedName("include_resp_body_expr")
@Expose
public String[] includeRespBodyExpr;
// include_req_body_expr
@SerializedName("include_req_body_expr")
@Expose
public String[] includeReqBodyExpr;
// key
@SerializedName("key")
@Expose
public String key;
// include_resp_body
@SerializedName("include_resp_body")
@Expose
public boolean includeRespBody;
// log_format
@SerializedName("log_format")
@Expose
public Object logFormat;
// metadata_schema
// private Object logFormat;
}