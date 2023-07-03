layui.use(function(){

var layer = layui.layer;
var laypage = layui.laypage;
var table = layui.table;
var form = layui.form;


function addLink(d) {
    var addLink = d.id;
    if ('' == addLink || null == addLink || undefined == addLink) {
        return '';
    }
    if (addLink.length > 0) {
       let editBtn = '<button type="button" class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">' + commonStr.edit + '</button>'
       let delBtn  = '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">' + commonStr.del + '</button>'
       return delBtn;
    }
}


  //执行一个 table 实例
  table.render({
    elem: '#demo'
    ,height: 'auto'
    ,url: ctx + '/api/ca' //数据接口
    ,title: '用户表'
    ,page: true //开启分页
    ,toolbar: false
    ,totalRow: false //开启合计行
    ,cols: [[ //表头
       {field: 'id', title: 'id', width: 100, sort: true, fixed: 'left', totalRowText: '合计：'}
      ,{field: 'title', title: 'title', sort: true}
      ,{field: 'subject', title: 'subject'}
      ,{field: 'notBefore', title: 'notBefore', width: 150, templet: "<div>{{!d.notBefore?'':layui.util.toDateString(d.notBefore, 'yyyy-MM-dd HH:mm:ss') }}</div>" }
      ,{field: 'notAfter', title: 'notAfter', width: 150, templet: "<div>{{!d.notAfter?'':layui.util.toDateString(d.notAfter, 'yyyy-MM-dd HH:mm:ss') }}</div>" }
      ,{field: 'upstream', title: 'operation', fixed: 'right', templet: addLink}
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

//头部工具条监听事件
table.on('toolbar(test)', function (obj) {
    var checkStatus = table.checkStatus(obj.config.id);
    switch (obj.event) {
        case 'LAYTABLE_TIPS':
            //console.log(obj.event);
            break;
    }
});


//工具条事件
    table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

        var layer = layui.layer;
        var laypage = layui.laypage //分页

        if (layEvent === 'detail'){ //查看
        } else if (layEvent === 'del'){ //删除

            layer.confirm(commonStr.confirmDel, function(index){

                $.ajax({
                    url: ctx + '/api/ca/del',
                    type: 'post',
                    contentType: 'application/json',
                    data: JSON.stringify({id: [obj.data.id] }),
                    success:function (data,statusText) {
                         if(data.code=='200'){
                            obj.del();
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

        } else if (layEvent === 'edit'){
        } else if (layEvent === 'detail_consumer_schema'){
        } else if (layEvent === 'detail_metadata_schema'){
        } else if (layEvent === 'setting'){
        }
    });



  //监听Tab切换
  element.on('tab(demo)', function(data){
    layer.tips('切换了 '+ data.index +'：'+ this.innerHTML, this, {
      tips: 1
    });
  });
});