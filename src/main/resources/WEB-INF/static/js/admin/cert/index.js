var editor1 = ace.edit("editor1");
editor1.setTheme("ace/theme/twilight");
editor1.session.setMode("ace/mode/yaml");
editor1.setFontSize(16);
editor1.setOptions({
    minLines: 20,
    maxLines: Infinity
});
editor1.resize();
layui.use(function(){

var layer = layui.layer;
var laypage = layui.laypage;
var table = layui.table;
var form = layui.form;
var upload = layui.upload;
var xmSelect = layui.xmSelect;
form.render(); // 渲染全部表单
$("#div_keyFile").hide();
$("#div_certFile").hide();
$("#div_CA").hide();


var demo3 = xmSelect.render({
	el: '#demo3',
	radio: true,
	name: 'dnsapi',
	filterable: true,
	clickClose: true,
	data: dnsapiData
})


function view(value, isUpdate){
    $("[id^='div_']").each(function() {
      $(this).hide();
      //console.log(this);
    });
    if(isUpdate === false){
        $("#div_id").show();
        $("#div_issue").show();
    }

    $("#div_approach").show();

    if(value == enum_Approach.trustCA){
        $("#div_renew").show();

        $("#div_supplier").show();
        $("#div_encryption").show();
        $("#div_challenge").show();
        $("#div_domain").show();
        $("#div_subject").show();
        $("#div_aux").show();
        $("#div_dnsapi").show();

        if(isUpdate == false){
            $('#addForm1 select[name="supplier"]').val(enum_Supplier.acme);
        }
    }else if(value == enum_Approach.manual){
        $("#div_keyFile").show();
        $("#div_certFile").show();
        $("#div_issue").hide();
        $('#addForm1 select[name="supplier"]').val("");
        $('#addForm1 select[name="encryption"]').val("");
        $('#addForm1 select[name="challenge"]').val("");
        $('#addForm1 select[name="dnsapi"]').val("");
    }else if(value == enum_Approach.privateCA){
        $("#div_CA").show();

        $("#div_encryption").show();
        $("#div_domain").show();
        $("#div_subject").show();

        $('#addForm1 select[name="supplier"]').val("");
        $('#addForm1 select[name="dnsapi"]').val("");

    }
    layui.form.render('select');
    layui.form.render('checkbox');
}


function cleanData(d){
    $('#addForm1 input, #addForm1 select, #addForm1 textarea, #addForm1 checkbox').val('');
    if( d === true ){
        $('#addForm1 input[name="u"]').val("update");
        $('#addForm1 select[name="approach"]').prop('disabled', true);
    }else{
        $('#addForm1 input[name="u"]').val("add");
        $('#addForm1 select[name="approach"]').prop('disabled', false);
        $('#addForm1 select[name="approach"]').val(enum_Approach.trustCA);
        $('#addForm1 select[name="supplier"]').val(enum_Supplier.acme);
        $('#addForm1 select[name="encryption"]').val(enum_Encryption.RSA);
    }

    layui.form.render('select');
    layui.form.render('checkbox');
    demo3.setValue([]);
    view(enum_Approach.trustCA,d);
}

function addLink(d) {
    var addLink = d.id;
    if ('' == addLink || null == addLink || undefined == addLink) {
        return '';
    }
    if (addLink.length > 0) {
       var renewBtn = '<button type="button" class="layui-btn layui-btn-normal layui-btn-xs" lay-event="renew">' + commonStr.renew + '</button>'
       var issueBtn = '<button type="button" class="layui-btn layui-btn-normal layui-btn-xs" lay-event="issue">' + commonStr.issue + '</button>'




       //if (arr.indexOf(d.approach) !== -1) {
       if ( d.approach == enum_Approach.manual ) {
            renewBtn = '<button type="button" class="layui-btn layui-btn-disabled layui-btn-xs" lay-event="renew" disabled>' + commonStr.renew + '</button>'
            issueBtn = '<button type="button" class="layui-btn layui-btn-disabled layui-btn-xs" lay-event="issue" disabled>' + commonStr.issue + '</button>'
       }
       let editBtn = '<button type="button" class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">' + commonStr.edit + '</button>'
       let delBtn  = '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">' + commonStr.del + '</button>'
       let dropBtn = '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="drop">' + commonStr.clear + '</button>'
       let dumpBtn = '<button type="button" class="layui-btn layui-btn-normal layui-btn-xs" lay-event="dump">' + commonStr.dump + '</button>'
       let imptBtn = '<button type="button" class="layui-btn layui-btn-normal layui-btn-xs" lay-event="importData">' + commonStr.import + '</button>'
       let viewBtn = '<button type="button" class="layui-btn layui-btn-normal layui-btn-xs" lay-event="view">' + commonStr.view + '</button>'

       return editBtn + '&nbsp;'+ issueBtn + '&nbsp;' + renewBtn + '&nbsp;' + delBtn + '&nbsp;' + dumpBtn + '&nbsp;' + viewBtn;
    }
}


form.on('select(approach)', function(data){
    var elem = data.elem; // 获得 select 原始 DOM 对象
    var value = data.value; // 获得被选中的值
    var othis = data.othis; // 获得 select 元素被替换后的 jQuery 对象
    view(value,false);

});


    var upload_c=upload.render({
        elem: '#upload-cert'
        ,auto: false
        ,accept: "file"
        ,multiple: false
        ,before: function(res){
            //console.log(res);
        }
        ,exts: 'crt|key|pem|der|jks|txt'
        ,field: 'certFile'
        ,size: 5210
        ,before: function(res){
        }
        ,done: function(res, index, upload){
        }
        ,error: function(index, upload){
            //console.log(index);
        }
    });

    var upload_k=upload.render({
        elem: '#upload-key'
        ,auto: false
        ,accept: "file"
        ,multiple: false
        ,before: function(res){
            //console.log(res);
        }
        ,exts: 'crt|key|pem|der|jks|txt'
        ,field: 'keyFile'
        ,size: 5210
        ,before: function(res){
        }
        ,done: function(res, index, upload){
        }
        ,error: function(index, upload){
            //console.log(index);
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
        area: ['1000px', '600px'],
        title: 'Add',
        content : $('#windowDiv'),
        anim: 'slideRight',
        shade: 0.6, // 遮罩透明度
        shadeClose: true, // 点击遮罩区域，关闭弹层
        maxmin: true, // 允许全屏最小化
        skin: 'layui-layer-win10'
    });

});

$('#reloadCA').click(function(){

    $('select[name="caId"]').empty();
    $.ajax({
        type : 'GET',
        url: ctx + '/api/ca/dropdown',
        dataType : 'json',
        data : {},
        success : function(data) {
            if(data.code=='200'){
                $('select[name="caId"]').append('<option value=""></option>');
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

var global_index=0;
$('#newCA').click(function(){
$('#caForm input, #caForm select, #caForm textarea, #caForm checkbox').val('');
layer.open({
    type: 1,
    area: ['500px', '300px'],
    title: 'New CA',
    content : $('#newDiv'),
    anim: 'slideRight',
    shade: 0.6, // 遮罩透明度
    shadeClose: true, // 点击遮罩区域，关闭弹层
    maxmin: true, // 允许全屏最小化
    skin: 'layui-layer-win10',
      success: function(layero, index, that){
            // 弹层的最外层元素的 jQuery 对象
            //console.log(layero);
            // 弹层的索引值
            //console.log(index);
            // 弹层内部原型链中的 this --- 2.8+
            //console.log(that);
        global_index=index;
      }
});

});

$('#listCA').click(function(){
            layer.open({
                type: 2,
                area: ['1000px', '600px'],
                title: 'List CA',
                content: ctx + '/admin/ca',
                anim: 'slideRight',
                shade: 0.6, // 遮罩透明度
                shadeClose: true, // 点击遮罩区域，关闭弹层
                maxmin: true, // 允许全屏最小化
                skin: 'layui-layer-win10'
            });

});

$('#newCAOver').click(function (){
    $.ajax({
        type:'post',
        url:ctx + '/api/ca/add',
        dataType : 'json',
        data : $('#caForm').serialize(),
        success : function(data) {
            if(data.code=='200'){
                layer.msg('新建成功');
                layer.close(global_index);
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
    //收集表单信息
    let formData = new FormData();
    $('#addForm1 input, #addForm1 select, #addForm1 textarea, #addForm1 checkbox').each(function(){
          var inputN = $(this).attr('name');
          let inputV = $(this).val();
          if ('' == inputN || null == inputN || undefined == inputN || 'certFile' == inputN || 'keyFile' == inputN ) {
              //
          }else{
                if ( ($(this).is('input')) || ($(this).is('textarea'))) {
                       if ( $(this).is(':checkbox') ){
                            //console.log(inputN+' is a checkbox');
                            if ($(this).prop('checked')){
                                formData.append(inputN,'1');
                            }else{
                                formData.append(inputN,'0');
                            }
                        }else{
                            formData.append(inputN,inputV);
                        }
                }
                if ( $(this).is('select')  && inputV !=null ) {
                    formData.append(inputN,inputV);
                }
          }
          //console.log(inputN+"======="+inputV);

    });


    if($('#addForm1 select[name="approach"]').val() == enum_Approach.manual){
        //console.log("formData.append certFile");
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
            $('select[name="caId"]').append('<option value=""></option>');
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


// $('#CASelect').click(function (){
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
            , {field: 'approachDisplay', title: 'approach', width: 200}
            , {field: 'encryptionDisplay', title: 'encry', width: 100}
            , {field: 'renew', title: 'renew', width: 100}
            , {field: 'notBefore', title: 'notBefore', width: 150, templet: "<div>{{!d.notBefore?'':layui.util.toDateString(d.notBefore, 'yyyy-MM-dd HH:mm:ss') }}</div>" }
            , {field: 'notAfter', title: 'notAfter', width: 150, templet: "<div>{{!d.notAfter?'':layui.util.toDateString(d.notAfter, 'yyyy-MM-dd HH:mm:ss') }}</div>" }
            , {field: 'upstream', title: 'operation', fixed: 'right', templet: addLink}


        ]]
        , done: function (res, curr, count){
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            //console.log(res);
            //得到当前页码
            //console.log(curr);
            //得到数据总量
            //console.log(count);


            // 获取配置项
            var thisOptions = table.getOptions('demo');
            //console.log(thisOptions);
            localStorage.setItem('pageLimit', thisOptions.limit);


            if(res.data && res.data.length == 0){
                if(curr>1){
                    toPage=curr-1;
                    //console.log(toPage);
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
//     //console.log(obj)
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

            layer.confirm(commonStr.confirmDel, function(index){
                obj.del();
                $.ajax({
                url: ctx + '/api/cert/del',
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify({id: [obj.data.id] }),
                success:function (data,statusText) {
                     if(data.code=='200'){
                        layer.msg(commonStr.delSuccess);
                        table.reload('demo',{});
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
        } else if (layEvent === 'renew'){
            $.ajax({
                url: ctx + '/api/cert/renew',
                type:'post',
                contentType: 'application/json',
                data:JSON.stringify({id:obj.data.id}),
                success:function (data,statusText) {
                    if(data.code=='200'){
                        layer.msg(commonStr.success);
                        table.reload('demo',{});
                    }else{
                        layer.alert(data.description.replace(/\n/g, "<br/>"));
                    }
                },
                'error':function () {
                    layer.alert(commonStr.errorInfo);
                }
            });

        } else if (layEvent === 'issue'){
            $.ajax({
                url: ctx + '/api/cert/issue',
                type:'post',
                contentType: 'application/json',
                data:JSON.stringify({id:obj.data.id}),
                success:function (data,statusText) {
                    if(data.code=='200'){
                        layer.msg(commonStr.success);
                        table.reload('demo',{});
                    }else{
                        layer.alert(data.description.replace(/\n/g, "<br/>"));
                    }
                },
                'error':function () {
                    layer.alert(commonStr.errorInfo);
                }
            });
        } else if (layEvent === 'edit'){

                //console.log(obj.data.id);
                cleanData(true);
                $.ajax({
                    url: ctx + '/api/cert/get',
                    type:'post',
                    contentType: 'application/json',
                    data:JSON.stringify({id:obj.data.id}),
                    success:function (data,statusText) {


                        if(data.code=='200'){
                            //批量回添数据
                            $('#addForm1 input, #addForm1 select, #addForm1 textarea, #addForm1 checkbox').each(function() {
                              var elementName = $(this).attr('name');
                              if (elementName in data.data) { // 判断对象中是否有该属性
                                $(this).val(data.data[elementName]); // 将属性对应的值填充到表单元素中
                              }
                            });
                            if (data.data.dnsapi !=null){
                                  //特殊处理xmSelect
                                    demo3.setValue([data.data.dnsapi]);
                            }


                            layui.form.render('select');
                            view(data.data.approach,true);

                            layer.open({
                                type: 1,
                                area: ['1000px', '600px'],
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
        } else if(layEvent === 'dump'){

            layer.open({
                type: 2,
                area: ['800px', '600px'],
                title: 'Dump Cert',
                content: ctx + '/api/cert/dump/'+obj.data.id,
                anim: 'slideRight',
                shade: 0.6, // 遮罩透明度
                shadeClose: true, // 点击遮罩区域，关闭弹层
                maxmin: true, // 允许全屏最小化
                skin: 'layui-layer-win10'
            });


        } else if(layEvent === 'drop'){
                layer.confirm(commonStr.confirmClear, function(index) {
                    $.ajax({
                        url: ctx + '/api/cert/drop',
                        type:'post',
                        contentType: 'application/json',
                        data:JSON.stringify({id:obj.data.id}),
                        success:function (data,statusText) {
                            if(data.code=='200'){
                                layer.msg(commonStr.success);
                                table.reload('demo',{});
                            }else{
                                 layer.msg(data.description);
                            }
                        },
                        'error':function () {
                            layer.msg(commonStr.errorInfo);
                        }
                    });
                });
        } else if(layEvent === 'view'){

                 $.ajax({
                     url: ctx + '/api/cert/view',
                     type:'post',
                     contentType: 'application/json',
                     data:JSON.stringify({id:obj.data.id}),
                     success:function (data,statusText) {
                         if(data.code=='200'){
                             //layer.msg(JSON.stringify(data.data));
                             //table.reload('demo',{});

                                editor1.setValue("",-1);
                                const yamlText = jsyaml.dump(data.data);
                                editor1.setValue(yamlText,-1);
                                 layer.open({
                                     type: 1,
                                     area: ['800px', '600px'],
                                     title: 'view',
                                     content : $('#viewDiv'),
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