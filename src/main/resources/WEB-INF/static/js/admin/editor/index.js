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


    var buildDom = ace.require("ace/lib/dom").buildDom;
//    var editor = ace.edit();
//    editor.setOptions({
//        theme: "ace/theme/tomorrow_night_eighties",
//        mode: "ace/mode/markdown",
//        maxLines: 30,
//        minLines: 30,
//        autoScrollEditorIntoView: true,
//    });
    var refs = {};
//    function updateToolbar() {
//        refs.saveButton.disabled = editor.session.getUndoManager().isClean();
//        refs.undoButton.disabled = !editor.session.getUndoManager().hasUndo();
//        refs.redoButton.disabled = !editor.session.getUndoManager().hasRedo();
//    }
//    editor.on("input", updateToolbar);
//    editor.session.setValue(localStorage.savedValue || "Welcome to ace Toolbar demo!")
//    function save() {
//        localStorage.savedValue = editor.getValue();
//        editor.session.getUndoManager().markClean();
//        updateToolbar();
//    }
//    editor.commands.addCommand({
//        name: "save",
//        exec: save,
//        bindKey: { win: "ctrl-s", mac: "cmd-s" }
//    });

    buildDom(["div", { class: "toolbar" },
//        ["button", {
//            ref: "undoButton",
//            onclick: function() {
//                editor.undo();
//            }
//        }, "undo"],
//        ["button", {
//            ref: "redoButton",
//            onclick: function() {
//                editor.redo();
//            }
//        }, "redo"],
        ["button", {
            ref: "yaml",
            style: "font-style: italic;font-size:25px;padding:5px;",
            onclick: function() {
                //console.log('yaml');
                //console.log(lang);
                const jsonInput = editor.getValue();
                const jsonObject = JSON.parse(jsonInput);
                const yamlText = jsyaml.dump(jsonObject);
                //console.log(yamlText);
                editor.session.setMode("ace/mode/yaml");
                editor.setValue(yamlText,-1);
            }
        }, "yaml"],
        ["button",{
            style: "font-weight: bold;font-size:25px;padding:5px;margin-left:20px;",
            onclick: function() {
                const yamlInput = editor.getValue();
                const jsonObject = jsyaml.load(yamlInput);
                //console.log(JSON.stringify(jsonObject));

                // 格式化并输出为易读的 JSON 字符串
                const prettyJson = JSON.stringify(jsonObject, null, 2);

                editor.session.setMode("ace/mode/json");
                editor.setValue(prettyJson,-1);
//                editor.insertSnippet("**${1:$SELECTION}**");
//                editor.renderer.scrollCursorIntoView()
            }
        }, "json"],
        ["button", {
            ref: "saveButton",
            style: "font-style: italic;font-size:25px;padding:5px;margin-left:20px;",
            onclick: function(){


                    var text = editor.getValue();
                    //console.log(text);
                    //确认表单弹窗
                    var layer = layui.layer;

                    $.ajax({
                        url: ctx + '/api/'+cls+'/put',
                        type:'post',
                        contentType: 'application/json',
                        data: JSON.stringify({id: id,rawData: text}),
                        success:function (data,statusText) {

                            if(data.code=='200'){
                                layer.msg(commonStr.success);
                            }else{
                                layer.msg(data.description);
                            }
                        },
                        'error':function () {
                            layer.msg(commonStr.errorInfo);
                        }
                    });



            }
        }, "save"],

//        ["button", {
//            style: "font-style: italic",
//            onclick: function() {
//                editor.insertSnippet("*${1:$SELECTION}*");
//                editor.renderer.scrollCursorIntoView()
//            }
//        }, "Italic"],
    ], document.getElementById('toolbar'), refs);
    //document.body.appendChild(editor.container)

    //window.editor = editor;




    //console.log(encodeURI(id));
    $.ajax({
        url: ctx + '/api/raw',
        type:'post',
        contentType: 'application/json',
        data: JSON.stringify({id: id,cls:cls}),
        success:function (data,statusText) {


            if(data.code=='200'){
                editor.setValue(data.data.rawData,-1);
            }else{
                editor.setValue("",-1);
                layer.msg(data.description);
            }
        },
        'error':function () {
            layer.msg(commonStr.errorInfo);
        }
    });


    $("#save").click(function(){
        //保存当前JSON文本的方法体。
        var editor = ace.edit("editor");
        var text = editor.getValue();
        //console.log(text);
        //确认表单弹窗
        var layer = layui.layer;

        $.ajax({
            url: ctx + '/api/'+cls+'/put',
            type:'post',
            contentType: 'application/json',
            data: JSON.stringify({id: id,rawData: text}),
            success:function (data,statusText) {

                if(data.code=='200'){
                    layer.msg(commonStr.success);
                }else{
                    layer.msg(data.description);
                }
            },
            'error':function () {
                layer.msg(commonStr.errorInfo);
            }
        });

    });

});
