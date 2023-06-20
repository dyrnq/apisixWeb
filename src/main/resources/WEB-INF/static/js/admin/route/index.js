function cleanData(d){
    if( d === true ){
        $("#div_id").hide();
    }else{
        $("#div_id").show();
    }

    $('#addForm1 input[name="id"]').val("");
    editor.setValue("",-1);
}

function addLink(d) {

    var addLink = d.id;
    //判断是否存在合法link,空格和null和未定义都会返回''。
    if ('' == addLink || null == addLink || undefined == addLink) {
        return '';
    }
    //如果是合法的路径，那么就正常添加，并配备编辑和删除的按键。
   if (addLink.length > 0) {
       let editBtn = '<button type="button" class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">' + commonStr.edit + '</button>'
       let delBtn  = '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">'+commonStr.del+'</button>'
       return editBtn+'&nbsp;'+delBtn;
   }
}



layui.use(function(){

var layer = layui.layer;
var laypage = layui.laypage;
var table = layui.table;
var form = layui.form;
var dropdown = layui.dropdown;



    var default_limt = localStorage.getItem('pageLimit');

    if ('' == default_limt || null == default_limt || undefined == default_limt) {
        default_limt = cfg.pageLimit;
    }

$('#add').click(function(){
    cleanData(false);
    layer.open({
        type: 1,
        area: ['800px', '600px'],
        title: 'Add Route',
        content : $('#windowDiv'),
        anim: 'slideRight',
        shade: 0.6, // 遮罩透明度
        shadeClose: true, // 点击遮罩区域，关闭弹层
        maxmin: true, // 允许全屏最小化
        skin: 'layui-layer-win10'
    });

});

$('#addOver').click(function(){
    var id = $('#addForm1 input[name="id"]').val();
    var text = editor.getValue();

    $.ajax({
        type : 'POST',
        url : ctx + '/api/route/put',
        contentType: 'application/json',
        data: JSON.stringify({id: id,rawData:text}),
        dataType : 'json',
        success : function(data) {
            if(data.code=='200'){
                layer.closeAll();
                layer.msg(commonStr.success);
            } else {
                layer.msg(data.description);
            }
        },
        error : function() {
            layer.alert(commonStr.errorInfo);
        }
    });
});

$('#dropAll').click(function(){
    layer.confirm(commonStr.confirmClear, function(index) {
         $.ajax({
                type : 'POST',
                url : ctx + '/api/route/drop',
                dataType : 'json',
                success : function(data) {
                    if(data.code=='200'){
                        layer.closeAll();
                        layer.msg(commonStr.success);
                        table.reload('demo',{});
                    } else {
                        layer.msg(data.description);
                    }
                }
        });
    });
});


    //执行一个 table 实例
    table.render({
        elem: '#demo'
        , height: 620
        , url: ctx + '/api/route' //数据接口
        , title: '用户表'
        , page: true //开启分页
        , limit: default_limt
        , limits: cfg.pageLimits
        , toolbar: '#toolbarDemo' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , defaultToolbar: [
            'filter', 'exports', 'print'
            , {title: '提示', layEvent: 'LAYTABLE_TIPS', icon: 'layui-icon-tips'}
        ]
        , totalRow: false //开启合计行
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', title: 'id', width: 100, sort: true, fixed: 'left', totalRowText: '合计：'}
            , {field: 'priority',title:'priority', width: 80, sort: true}
            , {field: 'name', title: 'name', width: 200}
            , {field: 'desc', title: 'desc', width: 90}
            , {field: 'createTime', title: 'create_time', sort: false , width: 300 , templet: "<div>{{layui.util.toDateString(d.createTime*1000, 'yyyy-MM-dd HH:mm:ss')}}</div>" }
            , {field: 'updateTime', title: 'update_time', sort: true , width: 300 , templet: "<div>{{layui.util.toDateString(d.updateTime*1000, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
            , {field: 'uri', title: 'uri', width: 100}
            , {field: 'status', title: 'status', width: 80}
            , {field: 'upstream', title: 'operation', fixed: 'right', templet: addLink}
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
        , response: {
            statusCode: 200
        }
        , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.description, //解析提示文本
                "count": res.total, //解析数据长度
                "data": res.data //解析数据列表
            };
        }
    });
// //监听表格复选框
// table.on('checkbox(test)', function (obj) {
//     console.log(obj)
// });
//头部工具条监听事件
    table.on('toolbar(test)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'LAYTABLE_TIPS':
                layer.alert(desc.route, { area: ['500px', '300px'] });
                break;
            case 'clear':
                layer.confirm(commonStr.confirmClear, function(index) {
                    $.ajax({
                        type : 'POST',
                        url : ctx + '/api/route/drop',
                        dataType : 'json',
                        success : function(data) {
                            if(data.code=='200'){
                                layer.closeAll();
                                layer.msg(commonStr.success);
                                table.reload('demo',{});
                            } else {
                                layer.msg(data.description);
                            }
                        }
                    });
                });
                break;
            case 'more':

                var that = this;


                dropdown.render({
                    elem: that,
                    show: true,
                    data: [
                        {title: commonStr.del, id: 'del'}
                        ,{title: commonStr.enable, id: 'enable'}
                        ,{title: commonStr.disable, id: 'disable'}
                    ],
                    click: function(data, othis){
                    var dataX = table.checkStatus(obj.config.id).data;
                    //layer.alert(JSON.stringify(dataX));

                    var url = ctx + '/api/route/'+data.id;
                    var confirmMsg = commonStr.confirm + ' ' + data.title +'?';
                    var allId = [];
                    if (dataX.length === 0) {
                        layer.msg(commonStr.pleaseSelect);
                    } else {
                        layer.confirm(confirmMsg , function(index) {
                            for (let i = 0; i < dataX.length; i++) {
                                const val = dataX[i];
                                allId.push(val.id);
                            }
                            $.ajax({
                                url: url,
                                type: 'post',
                                contentType: 'application/json',
                                data: JSON.stringify({id: allId}),
                                success:function (data,statusText) {
                                if(data.code=='200'){
                                    table.reload('demo',{});
                                    layer.msg(commonStr.success);
                                }else{
                                    layer.msg(data.description);
                                }
                                },
                                'error':function () {
                                    layer.msg(commonStr.errorInfo);
                                }
                            });
                            layer.close(index);
                        });
                    }
                    },
                    align: 'right', // 右对齐弹出
                    style: 'box-shadow: 1px 1px 10px rgb(0 0 0 / 12%);' //设置额外样式
                })
                dropdown.reload(that,{});
              break;


            case 'help':
                window.open("https://apisix.apache.org/zh/docs/apisix/admin-api/#route");
                break;
            case 'getCheckData':
                var dataX = checkStatus.data;
                layer.alert(JSON.stringify(dataX));
                break;
            case 'add':
                cleanData(false);
                layer.open({
                    type: 1,
                    area: ['800px', '600px'],
                    title: 'Add Route',
                    content : $('#windowDiv'),
                    anim: 'slideRight',
                    shade: 0.6, // 遮罩透明度
                    shadeClose: true, // 点击遮罩区域，关闭弹层
                    maxmin: true, // 允许全屏最小化
                    skin: 'layui-layer-win10'
                });
                break;
            }

})




//工具条事件
    table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

        if(layEvent === 'detail'){ //查看

        } else if(layEvent === 'del'){ //删除
            layer.confirm(commonStr.confirmDel, function(index){


                obj.del();
                $.ajax({
                    url: ctx + '/api/route/del',
                    type: 'post',
                    contentType: 'application/json',
                    data: JSON.stringify({id: [obj.data.id] }),
                    success:function (data,statusText) {
                         if(data.code=='200'){
                             layer.msg(commonStr.delSuccess);

                         }else{
                             layer.msg(data.description);
                         }
                    },
                    'error':function () {
                     layer.msg(commonStr.errorInfo);
                    }
                });

                layer.close(index);
                table.reload('demo',{})
            });

        } else if (layEvent === 'edit'){
            cleanData(true);
            $.ajax({
                    url: ctx + '/api/raw',
                    type:'post',
                    contentType: 'application/json',
                    data: JSON.stringify({id: obj.data.id,cls: 'route'}),
                    success:function (data,statusText) {

                        if(data.code=='200'){
                            $('#addForm1 input[name="id"]').val(obj.data.id);
                            editor.setValue(data.data.rawData,-1);
                            layer.open({
                                type: 1,
                                area: ['800px', '600px'],
                                title: 'Edit Route',
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
                        layer.msg(commonStr.errorInfo);
                    }
                });

        }
    });

  //监听Tab切换
  element.on('tab(demo)', function(data){
    layer.tips('切换了 '+ data.index +'：'+ this.innerHTML, this, {
      tips: 1
    });
  });})
