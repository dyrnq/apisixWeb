$(function() {

    var editor = ace.edit("editor");
    editor.setTheme("ace/theme/twilight");
    editor.session.setMode("ace/mode/json");
    editor.setFontSize(16);
    editor.setOptions({
        minLines: 10,
        maxLines: Infinity
    });
    editor.resize();

    console.log(encodeURI(id));
    $.ajax({
        url: ctx + '/api/raw',
        type:'post',
        contentType: 'application/json',
        data: JSON.stringify({id: id,cls:cls}),
        success:function (data,statusText) {
            console.log(data);
            //var element =
            if(data.code=='200'){
                editor.setValue(data.data.rawData,-1);
            }else{
                editor.setValue("",-1);
                layer.msg(data.description);
            }
        },
        'error':function () {
            layer.msg('系统错误');
        }
    });


    $("#save").click(function(){
        //保存当前JSON文本的方法体。
        var editor = ace.edit("editor");
        var text = editor.getValue();
        console.log(text);
        //确认表单弹窗
        var layer = layui.layer
        layer.confirm('确认提交更新吗？', function(index) {
            $.ajax({
                url: ctx + '/api/'+cls+'/put',
                type:'post',
                contentType: 'application/json',
                data: JSON.stringify({id: id,rawData: text}),
                success:function (data,statusText) {
                    //alert(data.code)
                    if(data.code=='200'){
                        layer.msg('修改成功');
                    }else{
                        layer.msg(data.description);
                    }
                },
                'error':function () {
                    layer.msg('系统错误');
                }
            });

        });
    });

});
