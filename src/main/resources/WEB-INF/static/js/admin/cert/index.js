function cleanData(d){
    if( d === true ){
        $("#div_id").hide();
        $('#addForm1 input[name="u"]').val("update");
    }else{
        $('#addForm1 input[name="u"]').val("add");
        $("#div_id").show();
    }

    $('#addForm1 input[name="id"]').val("");
    $('#addForm1 input[name="domain"]').val("");
    $('#addForm1 input[name="subject"]').val("");
    $('#addForm1 input[name="privateKey"]').val("");
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
       return editBtn+'&nbsp;'+delBtn;
    }
}



layui.use(function(){

var layer = layui.layer;
var laypage = layui.laypage;
var table = layui.table;
var form = layui.form;
var upload = layui.upload;
form.render(); // 渲染全部表单
$("#privateKeyFile").hide();
$("#certFile").hide();

    // // checkbox 事件
    // form.on('checkbox(demo-checkbox-filter)', function(data){
    //     var elem = data.elem; // 获得 checkbox 原始 DOM 对象
    //     var checked = elem.checked; // 获得 checkbox 选中状态
    //     var value = elem.value; // 获得 checkbox 值
    //     var othis = data.othis; // 获得 checkbox 元素被替换后的 jQuery 对象
    //     layer.msg('checked 状态: '+ elem.checked);
    //     if ( elem.checked == true ){
    //         $('#addForm1 input[name="renew"]').val("1");
    //     }else{
    //         $('#addForm1 input[name="renew"]').val("0");
    //     }
    // });

function view(value){
    if(value == 1){
        $("#privateKeyFile").show();
        $("#certFile").show();
        $("#renew").hide();
        $("#supplier").hide();
        $("#encryption").hide();
        $("#challenge").hide();
        $("#domain").hide();
        $("#subject").hide();
        $("#aux").hide();
        $("#privateKey").hide();
        $("#Ca").hide();
    }else if(value == 2){
        $("#privateKeyFile").hide();
        $("#certFile").hide();
        $("#renew").hide();
        $("#supplier").hide();
        $("#encryption").hide();
        $("#challenge").hide();
        $("#domain").show();
        $("#subject").show();
        $("#aux").hide();
        $("#privateKey").hide();
        $("#Ca").show();
    }else if(value == 0){
        $("#privateKeyFile").hide();
        $("#certFile").hide();
        $("#renew").show();
        $("#supplier").show();
        $("#encryption").show();
        $("#challenge").show();
        $("#domain").show();
        $("#subject").show();
        $("#aux").show();
        $("#privateKey").show();
        $("#Ca").hide();
    }
}

form.on('select(approach)', function(data){
    var elem = data.elem; // 获得 select 原始 DOM 对象
    var value = data.value; // 获得被选中的值
    var othis = data.othis; // 获得 select 元素被替换后的 jQuery 对象
    view(value);

});


    var upload_c=upload.render({
        elem: '#upload-cert'
        ,auto: false
        ,accept: "file"
        ,multiple: false
        ,before: function(res){
            console.log(res);
        }
        ,exts: 'crt|key|pem|der|jks|txt'
        ,field: 'certFile'
        ,size: 5210
        ,before: function(res){
        }
        ,done: function(res, index, upload){
        }
        ,error: function(index, upload){
            console.log(index);
        }
    });

    var upload_k=upload.render({
        elem: '#upload-key'
        ,auto: false
        ,accept: "file"
        ,multiple: false
        ,before: function(res){
            console.log(res);
        }
        ,exts: 'crt|key|pem|der|jks|txt'
        ,field: 'keyFile'
        ,size: 5210
        ,before: function(res){
        }
        ,done: function(res, index, upload){
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
        area: ['1000px', '400px'],
        title: 'Add Cert',
        content : $('#windowDiv'),
        anim: 'slideRight',
        shade: 0.6, // 遮罩透明度
        shadeClose: true, // 点击遮罩区域，关闭弹层
        maxmin: true, // 允许全屏最小化
        skin: 'layui-layer-win10'
    });

});

    $('#reloadCa').click(function(){

        $('select[name="caId"]').empty();
        $.ajax({
            type : 'GET',
            url: ctx + '/api/ca/dropdown',
            dataType : 'json',
            data : {},
            success : function(data) {
                if(data.code=='200'){

                    for (let i = 0; i < data.data.length; i++) {
                        let item = data.data[i];
                        //console.log(item);
                        $('select[name="caId"]').append('<option value="'+item.id+'">'+item.title+'</option>');
                        // 重新渲染下拉框组件
                        layui.form.render('select');
                    }
                } else {
                    layer.msg(data.description);
                }
            },
            error : function() {
                layer.alert(commonStr.errorInfo);
            }
        });


    });


    $('#newCa').click(function(){
        layer.open({
            type: 1,
            area: ['1000px', '400px'],
            title: 'New Ca',
            content : $('#newDiv'),
            anim: 'slideRight',
            shade: 0.6, // 遮罩透明度
            shadeClose: true, // 点击遮罩区域，关闭弹层
            maxmin: true, // 允许全屏最小化
            skin: 'layui-layer-win10'
        });

    });

$('#newCaOver').click(function (){
    $.ajax({
        type:'post',
        url:ctx + '/api/ca/add',
        dataType : 'json',
        data : $('#caForm').serialize(),
        success : function(data) {
            if(data.code=='200'){
                layer.msg('新建成功');
            } else {
                layer.msg(data.description);
            }
        },
        error : function() {
            layer.alert(commonStr.errorInfo);
        }

    })
})

$('#addOver').click(function(){
    let u = $('#addForm1 input[name="u"]').val();

    let formData = new FormData();
    $("#addForm1").find("input").each(function(index, element) {
        let inputN = $(element).attr("name");
        let inputV = $(element).val();
        console.log(inputN + '====' + inputV);
        if ('' == inputN || null == inputN || undefined == inputN || 'certFile' == inputN || 'keyFile' == inputN ) {
            //
        }else{
            formData.append(inputN,inputV);
        }
    });

    $("#addForm1").find("select").each(function(index, element) {
        let inputN = $(element).attr("name");
        let inputV = $(element).val();
        console.log(inputN + '====' + inputV);

        if ('' == inputN || null == inputN || undefined == inputN ) {
            //
        }else{
            formData.append(inputN,inputV);
        }
    });

    if(formData['approach'] == 1){
        formData.append("certFile",$('#addForm1 input[name="certFile"]')[0].files[0]);
        formData.append("keyFile",$('#addForm1 input[name="keyFile"]')[0].files[0]);
    }
    $.ajax({
        type : 'POST',
        url: ctx + '/api/cert/'+u,
        data : formData,
        processData: false,
        contentType: false,
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


$('select[name="caId"]').empty();
$.ajax({
    type : 'GET',
    url: ctx + '/api/ca/dropdown',
    dataType : 'json',
    data : {},
    success : function(data) {
        if(data.code=='200'){

            for (let i = 0; i < data.data.length; i++) {
                let item = data.data[i];
                //console.log(item);
                $('select[name="caId"]').append('<option value="'+item.id+'">'+item.title+'</option>');
                // 重新渲染下拉框组件
                layui.form.render('select');
            }
        } else {
            layer.msg(data.description);
        }
    },
    error : function() {
        layer.alert(commonStr.errorInfo);
    }
});


// $('#CaSelect').click(function (){
//     $.ajax({
//         type : 'GET',
//         url: ctx + '/api/ca/dropdown',
//         dataType : 'json',
//         data : {},
//         success : function(data) {
//             if(data.code=='200'){
//             } else {
//                 layer.msg(data.description);
//             }
//         },
//         error : function() {
//             layer.alert(commonStr.errorInfo);
//         }
//         });
//     });

    // form.on('select(demo-select-filter)', function(data){
    //     var elem = data.elem; // 获得 select 原始 DOM 对象
    //     var value = data.value; // 获得被选中的值
    //     var othis = data.othis; // 获得 select 元素被替换后的 jQuery 对象
    //
    //     layer.msg(this.innerHTML + ' 的 value: '+ value); // this 为当前选中 <option> 元素对象
    // });

    //执行一个 table 实例
    table.render({
        elem: '#demo'
        , height: 620
        , url: ctx + '/api/cert' //数据接口
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
            , {field: 'domain', title: 'domain', width: 200}
            , {field: 'renew', title: 'renew', width: 100}
            , {field: 'caId', title: 'caId', width: 100}
            , {field: 'supplier', title: 'supplier', width: 100}
            , {field: 'encryption', title: 'encryption', width: 100}
            , {field: 'challenge', title: 'challenge', width: 100}
            , {field: 'subject', title: 'subject', width: 300, sort: true}
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
            case 'deleteAll':
                var dataX = checkStatus.data;
                var allId = [];
                if (dataX.length === 0) {
                    layer.msg(commonStr.pleaseSelect);
                } else {
                    layer.confirm(commonStr.confirmBatchDelete, function(index) {
                        for (let i = 0; i < dataX.length; i++) {
                            const val = dataX[i];
                                allId.push(val.id)
                        }

                        $.ajax({
                            url: ctx + '/api/cert/del',
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
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                $.ajax({
                url: ctx + '/api/cert/del',
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

        }else if (layEvent === 'edit'){//编辑,暂无方法体

                //console.log(obj.data.id);
                cleanData(true);
                $.ajax({
                    url: ctx + '/api/cert/get',
                    type:'post',
                    contentType: 'application/json',
                    data:JSON.stringify({id:obj.data.id}),
                    success:function (data,statusText) {


                        if(data.code=='200'){
                            $('#addForm1 input[name="id"]').val(data.data.id);
                            $('#addForm1 input[name="domain"]').val(data.data.domain);
                            $('#addForm1 input[name="subject"]').val(data.data.subject);
                            $('#addForm1 input[name="privateKey"]').val(data.data.privateKey);
                            $('#addForm1 select[name="approach"]').val(data.data.approach);

                            $('#addForm1 select[name="caId"]')


                                .val(data.data.caId);

                            layui.form.render('select');
                            view(data.data.approach);

                            layer.open({
                                type: 1,
                                area: ['800px', '600px'],
                                title: 'Edit Cert',
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
                window.open('/api/tar/export?id='+obj.data.id);
        } else if(layEvent === 'drop'){
                layer.confirm(commonStr.confirmClear, function(index) {
                    $.ajax({
                        url: ctx + '/api/cert/drop',
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