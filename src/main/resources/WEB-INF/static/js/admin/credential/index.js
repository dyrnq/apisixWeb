function cleanData(d) {
  if (d === true) {
    $('#div_username').hide()
    $('#div_id').hide()
  } else {
    $('#div_username').show()
    $('#div_id').show()
  }
  $('#addForm_username').val('')
  $('#addForm_id').val('')
  editor.setValue('', -1)
}

function addLink(d) {
  if (!d.id) return ''
  var username = d.username || ''
  var editBtn = '<button type="button" class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">' + commonStr.edit + '</button>'
  var delBtn = '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">' + commonStr.del + '</button>'
  return editBtn + '&nbsp;' + delBtn
}

layui.use(function () {
  var layer = layui.layer
  var table = layui.table
  var form = layui.form
  var dropdown = layui.dropdown
  var viewEditor = ace.edit('viewEditor')
  viewEditor.setTheme('ace/theme/twilight')
  viewEditor.session.setMode('ace/mode/yaml')
  viewEditor.session.setUseWorker(false)
  viewEditor.setReadOnly(true)
  viewEditor.setFontSize(16)
  viewEditor.setOptions({ minLines: 10, maxLines: Infinity })
  viewEditor.resize()

  var default_limit = localStorage.getItem('pageLimit') || cfg.pageLimit

  $('#add').click(function () {
    cleanData(false)
    layer.open({
      type: 1, area: ['800px', '600px'], title: 'Add',
      content: $('#windowDiv'), anim: 'slideRight',
      shade: 0.6, shadeClose: true, maxmin: true, skin: 'layui-layer-win10',
    })
  })

  $('#addOver').click(function () {
    var username = $('#addForm_username').val()
    var id = $('#addForm_id').val()
    var text = editor.getValue()
    var modeName = editor.session.getMode().$id
    if (/yaml/.test(modeName)) {
      const jsonData = jsyaml.load(text)
      text = JSON.stringify(jsonData, null, 2)
    }
    $.ajax({
      type: 'POST', url: ctx + '/api/credential/put',
      contentType: 'application/json',
      data: JSON.stringify({ username: username, id: id, rawData: text }),
      dataType: 'json',
      success: function (data) {
        if (data.code == '200') {
          layer.closeAll()
          layer.msg(commonStr.success)
          table.reload('demo', {})
        } else {
          layer.msg(data.description)
        }
      },
      error: function () { layer.alert(commonStr.errorInfo) },
    })
  })

  $('#dropAll').click(function () {
    layer.confirm(commonStr.confirmClear, function (index) {
      $.ajax({
        type: 'POST', url: ctx + '/api/credential/drop',
        dataType: 'json',
        success: function (data) {
          if (data.code == '200') {
            layer.closeAll()
            layer.msg(commonStr.success)
            table.reload('demo', {})
          } else { layer.msg(data.description) }
        },
      })
    })
  })

  table.render({
    elem: '#demo', height: 620,
    url: ctx + '/api/credential',
    title: 'Credential Table', page: true,
    limit: default_limit, limits: cfg.pageLimits,
    toolbar: '#toolbarDemo',
    defaultToolbar: ['filter', 'exports', 'print', { title: '提示', layEvent: 'LAYTABLE_TIPS', icon: 'layui-icon-tips' }],
    totalRow: false,
    response: {
      statusCode: 200,
    },
    parseData: function (res) {
      return {
        code: res.code,
        msg: res.description,
        count: res.total || (res.data ? res.data.length : 0),
        data: res.data,
      }
    },
    cols: [[
      { type: 'checkbox', fixed: 'left' },
      { field: 'id', title: 'id', width: 120, sort: true, fixed: 'left' },
      { field: 'username', title: 'consumer', width: 120 },
      { field: 'name', title: 'name', width: 120 },
      { field: 'desc', title: 'desc', width: 200 },
      {
        field: 'createTime', title: 'create_time', sort: false, width: 180,
        templet: "<div>{{ layui.util.toDateString(d.createTime*1000, 'yyyy-MM-dd HH:mm:ss') }}</div>",
      },
      {
        field: 'updateTime', title: 'update_time', sort: true, width: 180,
        templet: "<div>{{ layui.util.toDateString(d.updateTime*1000, 'yyyy-MM-dd HH:mm:ss') }}</div>",
      },
      { field: 'uri', title: 'operation', fixed: 'right', templet: addLink },
    ]],
    done: function (res) {
      localStorage.setItem('pageLimit', table.getOptions('demo').limit)
    },
  })

  table.on('toolbar(test)', function (obj) {
    var checkStatus = table.checkStatus(obj.config.id)
    switch (obj.event) {
      case 'clear':
        layer.confirm(commonStr.confirmClear, function (index) {
          $.ajax({
            type: 'POST', url: ctx + '/api/credential/drop',
            dataType: 'json',
            success: function (data) {
              if (data.code == '200') {
                layer.closeAll()
                layer.msg(commonStr.success)
                table.reload('demo', {})
              } else { layer.msg(data.description) }
            },
          })
        })
        break
      case 'more':
        dropdown.render({
          elem: this, align: 'right', style: 'box-shadow: 1px 1px 10px rgb(0 0 0 / 12%);',
          data: [
            { title: commonStr.batchDelete, id: 'deleteSelected' },
          ],
          click: function (data) {
            if (data.id == 'deleteSelected') {
              var dataX = checkStatus.data
              if (dataX.length === 0) { layer.msg(commonStr.pleaseSelect); return }
              layer.confirm(commonStr.confirmBatchDelete, function (index) {
                var ids = dataX.map(function (v) { return v.username + '/' + v.id })
                $.ajax({
                  url: ctx + '/api/credential/del', type: 'post',
                  contentType: 'application/json',
                  data: JSON.stringify({ id: ids }),
                  success: function (d) {
                    if (d.code == '200') {
                      table.reload('demo', {})
                      layer.msg(commonStr.delSuccess)
                    } else { layer.msg(d.description) }
                  },
                  error: function () { layer.msg(commonStr.errorInfo) },
                })
                layer.close(index)
              })
            }
          },
        })
        break
      case 'add':
        cleanData(false)
        layer.open({
          type: 1, area: ['800px', '600px'], title: 'Add',
          content: $('#windowDiv'), anim: 'slideRight',
          shade: 0.6, shadeClose: true, maxmin: true, skin: 'layui-layer-win10',
        })
        break
    }
  })

  table.on('tool(test)', function (obj) {
    var data = obj.data
    switch (obj.event) {
      case 'del':
        layer.confirm(commonStr.confirmDel, function (index) {
          $.ajax({
            url: ctx + '/api/credential/del', type: 'post',
            contentType: 'application/json',
            data: JSON.stringify({ id: [data.username + '/' + data.id] }),
            success: function (d) {
              if (d.code == '200') {
                obj.del()
                layer.msg(commonStr.delSuccess)
              } else { layer.msg(d.description) }
            },
            error: function () { layer.msg(commonStr.errorInfo) },
          })
          layer.close(index)
        })
        break
      case 'edit':
        cleanData(true)
        $.ajax({
          url: ctx + '/api/raw', type: 'post',
          contentType: 'application/json',
          data: JSON.stringify({ id: data.username + '/' + data.id, cls: 'credential' }),
          success: function (d) {
            if (d.code == '200') {
              $('#addForm_username').val(data.username)
              $('#addForm_id').val(data.id)
              var modeName = editor.session.getMode().$id
              if (/yaml/.test(modeName)) {
                const jsonObject = JSON.parse(d.data.rawData)
                editor.setValue(jsyaml.dump(jsonObject), -1)
              } else {
                editor.setValue(d.data.rawData, -1)
              }
              layer.open({
                type: 1, area: ['800px', '600px'], title: 'Edit',
                content: $('#windowDiv'), anim: 'slideRight',
                shade: 0.6, shadeClose: true, maxmin: true, skin: 'layui-layer-win10',
              })
            } else { layer.msg(d.description) }
          },
          error: function () { layer.msg(commonStr.errorInfo) },
        })
        break
    }
  })
})
