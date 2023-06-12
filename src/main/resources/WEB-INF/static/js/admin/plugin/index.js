var editor1 = ace.edit("editor1");
editor1.setTheme("ace/theme/twilight");
editor1.session.setMode("ace/mode/json");
editor1.setFontSize(16);
editor1.setOptions({
    minLines: 20,
    maxLines: Infinity
});
editor1.resize();

function addOver() {
    layui.use(['layer', 'form'], function(){

        var id = $('#addForm1 input[name="id"]').val();
        console.log(id);
        var text = editor1.getValue();
        console.log(text);

        $.ajax({
            type : 'POST',
            url: ctx + '/api/pluginMetadata/put',
            contentType: 'application/json',
            data: JSON.stringify({id: id,rawData:text}),
            dataType : 'json',
            success : function(data) {
                if(data.code=='200'){
                    //location.reload();
                    layer.closeAll();
                    layer.msg("新增成功");
                } else {
                    layer.msg(data.description);
                }
            },
            error : function() {
                layer.alert("系统错误");
            }
        });
    });
}
var pluginMetadata={};
try {
    $.ajax({
        type : 'POST',
        url: ctx + '/api/pluginMetadata',
        contentType: 'application/json',
        dataType : 'json',
        async: false,
        success : function(data) {
            if(data.code=='200'){
                console.log(data);
                pluginMetadata=data.data;
            } else {
            }
        },
        error : function() {
        }
    });
} catch(error) {
   // 处理错误的代码
}


function plugin_metadata(d){
　　var addLink = d.id;
   if ('' == addLink || null == addLink || undefined == addLink) {
        return '';
   }
   if (addLink.length > 0) {
        let propertyValue = false;
        let badge='';
        try {
            propertyValue = pluginMetadata[d.id];
            if(propertyValue == true){
                badge = '<span class="layui-badge-dot layui-bg-blue"></span>&nbsp;&nbsp;'
            }else{
                badge = '&nbsp;&nbsp;&nbsp;&nbsp;'
            }
        } catch(error) {
            // 处理错误的代码
        }
        let bt1 = '<button class="layui-btn layui-btn-danger layui-btn-xs" lay-event="setting" >'+commonStr.setting+'</button>';
        let bt2 = '<a class="layui-btn layui-btn-normal layui-btn-xs" href="https://apisix.apache.org/zh/docs/apisix/plugins/'+addLink+'/" target="_blank">'+commonStr.help+'</a>';
        return badge + bt1 + '&nbsp;' + bt2;
   }
}

function schema(d) {
    var addLink = d.id;
    if ('' == addLink || null == addLink || undefined == addLink) {
        return '';
    }
    return '<button class="layui-btn layui-btn-normal layui-btn-xs" lay-event="detail">'+commonStr.view+'</button>';
}
function consumer_schema(d) {
    var addLink = d.consumer_schema;
    if ('' == addLink || null == addLink || undefined == addLink) {
        return '';
    }
    return '<button class="layui-btn layui-btn-normal layui-btn-xs" lay-event="detail_consumer_schema">'+commonStr.view+'</button>';
}

function metadata_schema(d) {
   var addLink = d.metadata_schema;
   if ('' == addLink || null == addLink || undefined == addLink) {
        return '';
   }
   var p = d.metadata_schema.properties;

   if ('' == p || null == p || undefined == p || JSON.stringify(p) === '{}') {
        return '';
   }
   return '<button class="layui-btn layui-btn-normal layui-btn-xs" lay-event="detail_metadata_schema">'+commonStr.view+'</button>';
}



layui.use(function(){
  //得到各种内置组件
  var layer = layui.layer //弹层
  ,laypage = layui.laypage //分页
  ,table = layui.table //表格




  //执行一个 table 实例
  table.render({
    elem: '#demo'
    ,height: 'auto'
    ,url: ctx + '/api/plugin' //数据接口
    ,title: '用户表'
    ,page: false //开启分页
    ,toolbar: false
    ,totalRow: false //开启合计行
    ,cols: [[ //表头
       {field: 'id', title: 'id', width:200, sort: true, fixed: 'left', totalRowText: '合计：'}
      ,{field: 'type', title: 'type', width: 90, sort: true, totalRow: true}
      ,{field: 'version', title: 'version', width: 150}
      ,{field: 'priority', title: 'priority', width: 200 ,sort: true}
      ,{field: 'consumer_schema', title: 'consumer_schema', width: 150,templet: consumer_schema}
      ,{field: 'metadata_schema', title: 'metadata_schema', width: 150,templet: metadata_schema}
      ,{field: 'schema', title: 'schema', templet: schema, width: 150}
      ,{field: 'plugin_metadata', title: 'plugin_metadata', width: 150 ,templet: plugin_metadata}
    ]]
    ,response: {
        statusCode: 200
    }
    ,parseData: function(res){ //将原始数据解析成 table 组件所规定的数据
      return {
        "code": res.code, //解析接口状态
        "msg": res.description, //解析提示文本
        "count": res.total, //解析数据长度
        "data": res.data //解析数据列表
      };
    }
  });



//工具条事件
    table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

        var layer = layui.layer //弹层
        var laypage = layui.laypage //分页

        if (layEvent === 'detail'){ //查看
            //do something
            //layer.alert(JSON.stringify(data.schema),{ area: ['600px']});
            editor.setValue("",-1);
            editor.setValue(JSON.stringify(data.schema,null, '\t'),-1);
            layer.open({
                type: 1,
                area: ['800px', '600px'],
                title: 'schema',
                content : $('#guideDiv'),
                anim: 'slideRight',
                shade: 0.6, // 遮罩透明度
                shadeClose: true, // 点击遮罩区域，关闭弹层
                maxmin: true, // 允许全屏最小化
                skin: 'layui-layer-win10'
            });

        } else if(layEvent === 'del'){ //删除

        } else if(layEvent === 'LAYTABLE_TIPS'){
            layer.alert('Hi，头部工具栏扩展的右侧图标。');
        } else if (layEvent === 'edit'){//编辑,暂无方法体

        } else if (layEvent === 'detail_consumer_schema'){
            //layer.alert(JSON.stringify(data.consumer_schema));
            editor.setValue("",-1);
            editor.setValue(JSON.stringify(data.consumer_schema,null, '\t'),-1);
            layer.open({
                type: 1,
                area: ['800px', '600px'],
                title: 'consumer_schema',
                content : $('#guideDiv'),
                anim: 'slideRight',
                shade: 0.6, // 遮罩透明度
                shadeClose: true, // 点击遮罩区域，关闭弹层
                maxmin: true, // 允许全屏最小化
                skin: 'layui-layer-win10'
            });

        } else if (layEvent === 'detail_metadata_schema'){
            //layer.alert(JSON.stringify(data.metadata_schema));
            editor.setValue("",-1);
            editor.setValue(JSON.stringify(data.metadata_schema,null, '\t'),-1);
            layer.open({
                type: 1,
                area: ['800px', '600px'],
                title: 'metadata_schema',
                content : $('#guideDiv'),
                anim: 'slideRight',
                shade: 0.6, // 遮罩透明度
                shadeClose: true, // 点击遮罩区域，关闭弹层
                maxmin: true, // 允许全屏最小化
                skin: 'layui-layer-win10'
            });

        } else if (layEvent === 'setting'){
            $('#addForm1 input[name="id"]').val("");
            //$('#addForm1 textarea[name="rawData"]').val("");
            $.ajax({
                    url: ctx + '/api/raw',
                    type: 'post',
                    contentType: 'application/json',
                    data:JSON.stringify({id:obj.data.id,cls:'pluginMetadata'}),
                    success:function (data,statusText) {
                        console.log(data);
                        //var element =
                        if(data.code=='200'){
                            $('#addForm1 input[name="id"]').val(obj.data.id);
                            //$('#addForm1 textarea[name="rawData"]').val(data.data.rawData);
                            //layer.msg(data.description);
                            //layer.open({
                            //    type: 1,
                            //    area: ['800px', '600px'],
                            //    title: '修改插件Metadata',
                            //    content : $('#windowDiv')
                            //});

                            editor1.setValue("",-1);
                            editor1.setValue(data.data.rawData,-1);
                            layer.open({
                                type: 1,
                                area: ['800px', '600px'],
                                title: 'setting plugin_metadata',
                                content : $('#windowDiv'),
                                anim: 'slideRight',
                                shade: 0.6, // 遮罩透明度
                                shadeClose: true, // 点击遮罩区域，关闭弹层
                                maxmin: true, // 允许全屏最小化
                                skin: 'layui-layer-win10'
                            });


                        }else{
                             layer.msg(data.description);
                        }
                    },
                    'error':function () {
                        layer.msg('系统错误');
                    }
                });


        }
    });



  //监听Tab切换
  element.on('tab(demo)', function(data){
    layer.tips('切换了 '+ data.index +'：'+ this.innerHTML, this, {
      tips: 1
    });
  });
});