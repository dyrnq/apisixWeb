function cleanData(d){
    if( d === true ){
        $("#div_id").hide();
        $('#addForm1 input[name="u"]').val("update");
    }else{
        $('#addForm1 input[name="u"]').val("add");
        $("#div_id").show();
    }

    $('#addForm1 input[name="id"]').val("");
    $('#addForm1 input[name="name"]').val("");
    $('#addForm1 input[name="url"]').val("");
    $('#addForm1 input[name="apiKey"]').val("");
}

function addLink(d) {
    var addLink = d.id;
    if ('' == addLink || null == addLink || undefined == addLink) {
        return '';
    }
    if (addLink.length > 0) {

       let editBtn = '<button type="button" class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">' + commonStr.edit + '</button>'
       let delBtn  = '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">' + commonStr.del + '</button>'
       let dropBtn = '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="drop">' + commonStr.clear + '</button>'
       let exptBtn = '<button type="button" class="layui-btn layui-btn-normal layui-btn-xs" lay-event="export">' + commonStr.export + '</button>'
       let imptBtn = '<button type="button" class="layui-btn layui-btn-normal layui-btn-xs" lay-event="importData">' + commonStr.import + '</button>'
       return editBtn+'&nbsp;'+delBtn+'&nbsp;'+dropBtn+'&nbsp;'+exptBtn+'&nbsp;'+imptBtn ;
    }
}



layui.use(function(){

var layer = layui.layer;
var laypage = layui.laypage;
var table = layui.table;
var form = layui.form;
var upload = layui.upload;

    var instUpload = upload.render({
                    elem: '#upload-cert'
                    ,auto: false
                    ,accept: "file"
                    ,multiple: false
                    ,url: ctx + '/api/tar/import'
                    ,exts: 'gz|tar|zip|gzip|tar.gz|'
                    ,size: 5210
                    ,bindAction: '#ID-upload-demo-action'
                    ,before: function(res){
                        //console.log(res);
                        $('#loading').show();
                    }
                    ,done: function(res,index,upload){
                          //layer.msg('上传成功');
                          //console.log(res);
                          if(res.code=='200'){
                              layer.closeAll();
                              layer.msg(commonStr.success);
                          }
                    }
                    ,progress: function(n, elem, res, index){
                      var percent = n + '%' // 获取进度百分比
                    }
                    ,error: function(index, upload){
                        console.log(index);
                    }
                });


    var default_limt = localStorage.getItem('pageLimit');

    if ('' == default_limt || null == default_limt || undefined == default_limt) {
        default_limt = cfg.pageLimit;
    }

$('#add').click(function(){
    cleanData(false);
    layer.open({
        type: 1,
        area: ['800px', '600px'],
        title: 'Add',
        content : $('#windowDiv'),
        anim: 'slideRight',
        shade: 0.6, // 遮罩透明度
        shadeClose: true, // 点击遮罩区域，关闭弹层
        maxmin: true, // 允许全屏最小化
        skin: 'layui-layer-win10'
    });

});

$('#addOver').click(function(){
    let u = $('#addForm1 input[name="u"]').val();
    $.ajax({
        type : 'POST',
        url: ctx + '/api/inst/'+u,
        data : $('#addForm1').serialize(),
        dataType : 'json',
        success : function(data) {
            if(data.code=='200'){
                layer.closeAll();
                layer.msg(commonStr.success);
                table.reload('demo',{});
            } else {
                layer.msg(data.description);
            }
        },
        error : function() {
            layer.alert(commonStr.errorInfo);
        }
    });
});

$('#choose').click(function (){
    let format = $('#exportForm input[name="format"]').val();
    let id = $('#exportForm input[name="id"]').val();
    //console.log(format)
    window.open(ctx + '/api/tar/export?id='+id+"&format="+format);
    layer.closeAll();
})

    form.on('radio(demo-radio-filter)', function(data){
        var elem = data.elem; // 获得 radio 原始 DOM 对象
        var checked = elem.checked; // 获得 radio 选中状态
        var value = elem.value; // 获得 radio 值
        var othis = data.othis; // 获得 radio 元素被替换后的 jQuery 对象
        if(checked == true){
            $('#exportForm input[name="format"]').val(value);
        }

        //layer.msg(['value: '+ value, 'checked: '+ checked].join('<br>'));
    });


    //执行一个 table 实例
    table.render({
        elem: '#demo'
        , height: 620
        , url: ctx + '/api/inst' //数据接口
        , title: '用户表'
        , page: true //开启分页
        , limit: default_limt
        , limits: cfg.pageLimits
        , toolbar: '#toolbarDemo' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示'
            , layEvent: 'LAYTABLE_TIPS'
            , icon: 'layui-icon-tips'
        }]
        , totalRow: false //开启合计行
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', title: 'id', width: 200, sort: true, fixed: 'left', totalRowText: '合计：'}
            , {field: 'name', title: 'name', width: 200}
            , {field: 'url', title: 'url', width: 300, sort: true}
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
            case 'getCheckData':
                var dataX = checkStatus.data;
                layer.alert(JSON.stringify(dataX));
                break;
            case 'deleteSelected':
                var dataX = checkStatus.data;
                var allId = [];
                if (dataX.length === 0) {
                    layer.msg(commonStr.pleaseSelect);
                } else {
                    layer.confirm(commonStr.confirmBatchDelete, function(index) {
                        for (let i = 0; i < dataX.length; i++) {
                            const val = dataX[i];
                            if(val.id == "1"){
                                continue;//在id=1的情况下跳过剩余循环，直接进入id=2的循环。
                            }else{
                                allId.push(val.id)
                            }
                        }

                        $.ajax({
                            url: ctx + '/api/inst/del',
                            type:'post',
                            contentType: 'application/json',
                            data: JSON.stringify({id: allId}),
                            success:function (data,statusText) {
                                if(data.code=='200'){
                                    table.reload('demo',{});
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
                    });

                }
                break;
            case 'add':
                cleanData(false);
                layer.open({
                    type: 1,
                    area: ['800px', '600px'],
                    title: 'Add',
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
            if(obj.data.id == "1") {
                layer.msg(commonStr.cantDel)
            }else{
                layer.confirm(commonStr.confirmDel, function(index){
                    obj.del();
                    $.ajax({
                    url: ctx + '/api/inst/del',
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
                });
            }
        } else if (layEvent === 'edit'){

                //console.log(obj.data.id);
                cleanData(true);
                $.ajax({
                    url: ctx + '/api/inst/get',
                    type:'post',
                    contentType: 'application/json',
                    data:JSON.stringify({id:obj.data.id}),
                    success:function (data,statusText) {


                        if(data.code=='200'){
                            $('#addForm1 input[name="id"]').val(data.data.id);
                            $('#addForm1 input[name="name"]').val(data.data.name);
                            $('#addForm1 input[name="url"]').val(data.data.url);
                            $('#addForm1 input[name="apiKey"]').val(data.data.apiKey);


                            layer.open({
                                type: 1,
                                area: ['800px', '600px'],
                                title: 'Edit',
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
            } else if(layEvent === 'importData'){//导入事件
                $('#addForm3 input[name="id"]').val(obj.data.id);
                $('#addForm3').find(".layui-upload-choose").each(function(index, element) {
                      //console.log(element);
                      $(element).html("");
                });
                $("#loading").hide();

                instUpload.reload({data: { id: obj.data.id } });
                layer.open({
                    type: 1,
                    area: ['500px', '300px'],
                    title: 'import data',
                    content : $('#guideDiv'),
                    anim: 'slideRight',
                    shade: 0.6, // 遮罩透明度
                    shadeClose: true, // 点击遮罩区域，关闭弹层
                    maxmin: true, // 允许全屏最小化
                    skin: 'layui-layer-win10'
                });
        } else if(layEvent === 'export'){//导出事件
                //console.log(obj.data.id);
            $('#exportForm input[name="id"]').val(obj.data.id);
            layer.open({
                type: 1,
                area: ['500px', '300px'],
                title: '输出格式',
                content : $('#exportDiv'),
                anim: 'slideRight',
                shade: 0.6, // 遮罩透明度
                shadeClose: true, // 点击遮罩区域，关闭弹层
                maxmin: true, // 允许全屏最小化
                skin: 'layui-layer-win10'
            })
        } else if(layEvent === 'drop'){
                layer.confirm(commonStr.confirmClear, function(index) {
                    $.ajax({
                        url: ctx + '/api/inst/drop',
                        type:'post',
                        contentType: 'application/json',
                        data:JSON.stringify({id:obj.data.id}),
                        success:function (data,statusText) {
                            if(data.code=='200'){
                                layer.msg("OK");
                            }else{
                                 layer.msg(data.description);
                            }
                        },
                        'error':function () {
                            layer.msg(commonStr.errorInfo);
                        }
                    });
                });
        }

    });

  //监听Tab切换
  element.on('tab(demo)', function(data){
    layer.tips('切换了 '+ data.index +'：'+ this.innerHTML, this, {
      tips: 1
    });
  });})