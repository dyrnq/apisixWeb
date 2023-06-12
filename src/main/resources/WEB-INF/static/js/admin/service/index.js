function cleanData(d){
    if( d === true ){
        $("#div_id").hide();
    }else{
        $("#div_id").show();
    }

    $('#addForm1 input[name="id"]').val("");
    editor.setValue("",-1);
}

function add() {
    layui.use(['layer', 'form'], function(){
        var layer = layui.layer;
        var form = layui.form;
        cleanData(false);
        layer.open({
            type: 1,
            area: ['800px', '600px'],
            title: 'Add Service',
            content : $('#windowDiv'),
            anim: 'slideRight',
            shade: 0.6, // 遮罩透明度
            shadeClose: true, // 点击遮罩区域，关闭弹层
            maxmin: true, // 允许全屏最小化
            skin: 'layui-layer-win10'
        });

    });
}

function addOver() {
    layui.use(['layer', 'form'], function(){
        var id = $('#addForm1 input[name="id"]').val();
        console.log(id);
        var text = editor.getValue();
        console.log(text);

        $.ajax({
            type : 'POST',
            url: ctx + '/api/service/put',
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

function addLink(d) {
　　var addLink = d.id;
   if ('' == addLink || null == addLink || undefined == addLink) {
        return '';
   }
   if (addLink.length > 0) {
       let editBtn = '<button type="button" class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">' + commonStr.edit + '</button>'
       let delBtn  = '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">'+commonStr.del+'</button>'
       return editBtn+'&nbsp;'+delBtn;
   }
}


layui.use(function(){
  //得到各种内置组件
  var layer = layui.layer //弹层
  ,laypage = layui.laypage //分页
  ,table = layui.table //表格




    var default_limt = localStorage.getItem('pageLimit');

    if ('' == default_limt || null == default_limt || undefined == default_limt) {
        default_limt = cfg.pageLimit;
    }



  //执行一个 table 实例
  table.render({
    elem: '#demo'
    ,height: 620
    ,url: ctx + '/api/service' //数据接口
    ,title: '用户表'
    ,page: true //开启分页
    , limit: default_limt
    , limits: cfg.pageLimits
    ,toolbar: '#toolbarDemo' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
    , defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
        title: '提示'
        , layEvent: 'LAYTABLE_TIPS'
        , icon: 'layui-icon-tips'
    }]
    ,totalRow: false //开启合计行
    ,cols: [[ //表头
      {type: 'checkbox', fixed: 'left'}
      ,{field: 'id', title: 'id', width:300, sort: true, fixed: 'left', totalRowText: '合计：'}
      ,{field: 'name', title: 'name', width:80}
      ,{field: 'desc', title: 'desc', width: 90, sort: true, totalRow: true}
      ,{field: 'createTime', title: 'create_time', width: 200, sort: false , templet: "<div>{{layui.util.toDateString(d.createTime*1000, 'yyyy-MM-dd HH:mm:ss')}}</div>" }
      ,{field: 'updateTime', title: 'update_time', width: 200, sort: false , templet: "<div>{{layui.util.toDateString(d.updateTime*1000, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
      ,{field: 'upstream', title: 'upstream', width: 200 ,templet: addLink}

    ]]
      , done: function (res, curr, count){
          //如果是异步请求数据方式，res即为你接口返回的信息。
          //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
          console.log(res);
          //得到当前页码
          console.log(curr);
          //得到数据总量
          console.log(count);


        // 获取配置项
        var thisOptions = table.getOptions('demo');
        console.log(thisOptions);
        localStorage.setItem('pageLimit', thisOptions.limit);


          if(res.data && res.data.length == 0){
              if(curr>1){
                  toPage=curr-1;
                  console.log(toPage);
                  table.reload('demo',{page: {curr:toPage}});
              }
          }
      }
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

    //头部工具条事件
    table.on('toolbar(test)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'deleteAll':
                var dataX = checkStatus.data;
                var allId = [];
                if (dataX.length === 0) {
                    layer.msg('请选择要删除的数据');
                } else {
                    layer.confirm('确定删除选中的数据吗？', function(index) {
                        // 发送删除请求，并重新加载表格
                        //console.log(JSON.stringify(dataX));
                        console.log(dataX)
                        for (let i = 0; i < dataX.length; i++) {
                            const val = dataX[i];
                            allId.push(val.id)




                        }
                        $.ajax({
                            url: ctx + '/api/service/del',
                            type:'post',
                            contentType: 'application/json',
                            data: JSON.stringify({id: allId}),
                            success:function (data,statusText) {
                                //alert(data.code)
                                if(data.code=='200'){
                                    table.reload('demo',{});
                                    layer.msg('删除成功');
                                    //table.reload('idTest',{});
                                }else{
                                    layer.msg(data.description);
                                }
                            },
                            'error':function () {
                                //layer.msg('系统错误');
                            }
                        });


                        layer.close(index);
                    });


                }
                break;

        }
    })

//工具条事件
    table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

        if(layEvent === 'detail'){ //查看
            //do something
        } else if(layEvent === 'del'){ //删除
            layer.confirm('真的删除行么', function(index){
                //alert(obj.data.id);
                //向服务端发送删除指令
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                $.ajax({
                    url: ctx + '/api/service/del',
                    type:'post',
                    data:"id="+obj.data.id,
                    success:function (data,statusText) {
                        //alert(data.code)
                        if(data.code=='200'){
                            layer.msg('删除成功');
                            //table.reload('idTest',{});
                        }else{
                            layer.msg(data.description);
                        }
                    },
                    'error':function () {
                        layer.msg('系统错误');
                    }
                });

                layer.close(index);
            });
            // } else if(layEvent === 'edit'){ //编辑
            //     //do something
            //
            //     //同步更新缓存对应的值
            //     obj.update({
            //         username: '123'
            //         ,title: 'xxx'
            //     });
        } else if(layEvent === 'LAYTABLE_TIPS'){
            layer.alert('Hi，头部工具栏扩展的右侧图标。');
        }else if (layEvent === 'edit'){//编辑,暂无方法体

            cleanData(true);
            $.ajax({
                url: ctx + '/api/raw',
                type:'post',
                contentType: 'application/json',
                data: JSON.stringify({id: obj.data.id,cls: 'service'}),
                success:function (data,statusText) {
                    console.log(data);
                    if(data.code=='200'){
                        $('#addForm1 input[name="id"]').val(obj.data.id);
                        editor.setValue(data.data.rawData,-1);
                        layer.open({
                            type: 1,
                            area: ['800px', '600px'],
                            title: 'Edit Service',
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
