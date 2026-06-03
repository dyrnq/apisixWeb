function cleanData(d) {
  if (d === true) {
    $('#div_id').hide()
  } else {
    $('#div_id').show()
  }

  $('#addForm1 input[name="id"]').val('')
  editor.setValue('', -1)
}

function addLink(d) {
  var addLink = d.id
  if ('' == addLink || null == addLink || undefined == addLink) {
    return ''
  }
  if (addLink.length > 0) {
    let editBtn =
      '<button type="button" class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">' +
      commonStr.edit +
      '</button>'
    let delBtn =
      '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">' +
      commonStr.del +
      '</button>'
    return editBtn + '&nbsp;' + delBtn
  }
}

layui.use(function () {
  var layer = layui.layer
  var laypage = layui.laypage
  var table = layui.table
  var form = layui.form
  var dropdown = layui.dropdown
  var viewEditor = ace.edit('viewEditor')
  viewEditor.setTheme('ace/theme/twilight')
  viewEditor.session.setMode('ace/mode/yaml')
  viewEditor.session.setUseWorker(false)
  viewEditor.setReadOnly(true)
  viewEditor.setFontSize(16)
  viewEditor.setOptions({
    minLines: 10,
    maxLines: Infinity,
  })
  viewEditor.resize()

  var default_limit = localStorage.getItem('pageLimit')

  if ('' == default_limit || null == default_limit || undefined == default_limit) {
    default_limit = cfg.pageLimit
  }

  $('#add').click(function () {
    cleanData(false)
    layer.open({
      type: 1,
      area: ['800px', '600px'],
      title: 'Add',
      content: $('#windowDiv'),
      anim: 'slideRight',
      shade: 0.6,
      shadeClose: true,
      maxmin: true,
      skin: 'layui-layer-win10',
    })
  })

  $('#addOver').click(function () {
    var id = $('#addForm1 input[name="id"]').val()
    var text = editor.getValue()
    var modeName = editor.session.getMode().$id
    if (/yaml/.test(modeName)) {
      const jsonData = jsyaml.load(text)
      text = JSON.stringify(jsonData, null, 2)
    }

    $.ajax({
      type: 'POST',
      url: ctx + '/api/credential/put',
      contentType: 'application/json',
      data: JSON.stringify({ id: id, rawData: text }),
      dataType: 'json',
      success: function (data) {
        if (data.code == '200') {
          layer.closeAll()
          layer.msg(commonStr.success)
        } else {
          layer.msg(data.description)
        }
      },
      error: function () {
        layer.alert(commonStr.errorInfo)
      },
    })
  })

  $('#dropAll').click(function () {
    layer.confirm(commonStr.confirmClear, function (index) {
      $.ajax({
        type: 'POST',
        url: ctx + '/api/credential/drop',
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
      })
    })
  })

  table.render({
    elem: '#demo',
    height: 620,
    url: ctx + '/api/credential',
    title: 'Credential Table',
    page: true,
    limit: default_limit,
    limits: cfg.pageLimits,
    toolbar: '#toolbarDemo',
    defaultToolbar: ['filter', 'exports', 'print', { title: '提示', layEvent: 'LAYTABLE_TIPS', icon: 'layui-icon-tips' }],
    totalRow: false,
    cols: [
      [
        { type: 'checkbox', fixed: 'left' },
        { field: 'id', title: 'id', width: 120, sort: true, fixed: 'left' },
        {
          field: 'createTime',
          title: 'create_time',
          sort: false,
          width: 180,
          templet: "<div>{{ layui.util.toDateString(d.createTime*1000, 'yyyy-MM-dd HH:mm:ss') }}</div>",
        },
        {
          field: 'updateTime',
          title: 'update_time',
          sort: true,
          width: 180,
          templet: "<div>{{ layui.util.toDateString(d.updateTime*1000, 'yyyy-MM-dd HH:mm:ss') }}</div>",
        },
        { field: 'name', title: 'name', width: 150 },
        { field: 'desc', title: 'desc', width: 200 },
        { field: 'uri', title: 'operation', fixed: 'right', templet: addLink },
      ],
    ],
    done: function (res, curr, count) {
      var thisOptions = table.getOptions('demo')
      localStorage.setItem('pageLimit', thisOptions.limit)
    },
  })

  table.on('toolbar(test)', function (obj) {
    var checkStatus = table.checkStatus(obj.config.id)
    var that = this
    switch (obj.event) {
      case 'clear':
        layer.confirm(commonStr.confirmClear, function (index) {
          $.ajax({
            type: 'POST',
            url: ctx + '/api/credential/drop',
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
          })
        })
        break
      case 'more':
        dropdown.render({
          elem: that,
          data: [
            { title: commonStr.batchDelete, id: 'deleteSelected' },
            { title: commonStr.batchEnable, id: 'statusAll1' },
            { title: commonStr.batchDisable, id: 'statusAll0' },
          ],
          click: function (data) {
            var dataX = checkStatus.data
            if (data.id == 'deleteSelected') {
              if (dataX.length === 0) {
                layer.msg(commonStr.pleaseSelect)
              } else {
                layer.confirm(commonStr.confirmBatchDelete, function (index) {
                  var allId = []
                  for (let i = 0; i < dataX.length; i++) {
                    allId.push(dataX[i].id)
                  }
                  $.ajax({
                    url: ctx + '/api/credential/del',
                    type: 'post',
                    contentType: 'application/json',
                    data: JSON.stringify({ id: allId }),
                    success: function (data, statusText) {
                      if (data.code == '200') {
                        table.reload('demo', {})
                        layer.msg(commonStr.delSuccess)
                      } else {
                        layer.msg(data.description)
                      }
                    },
                    error: function () {
                      layer.msg(commonStr.errorInfo)
                    },
                  })
                  layer.close(index)
                })
              }
            }
          },
          align: 'right',
          style: 'box-shadow: 1px 1px 10px rgb(0 0 0 / 12%);',
        })
        dropdown.reload(that, {})
        break
      case 'add':
        cleanData(false)
        layer.open({
          type: 1,
          area: ['800px', '600px'],
          title: 'Add',
          content: $('#windowDiv'),
          anim: 'slideRight',
          shade: 0.6,
          shadeClose: true,
          maxmin: true,
          skin: 'layui-layer-win10',
        })
        break
    }
  })

  table.on('tool(test)', function (obj) {
    var data = obj.data
    var layEvent = obj.event
    if (layEvent === 'del') {
      layer.confirm(commonStr.confirmDel, function (index) {
        $.ajax({
          url: ctx + '/api/credential/del',
          type: 'post',
          contentType: 'application/json',
          data: JSON.stringify({ id: [obj.data.id] }),
          success: function (data, statusText) {
            if (data.code == '200') {
              obj.del()
              layer.msg(commonStr.delSuccess)
            } else {
              layer.msg(data.description)
            }
          },
          error: function () {
            layer.msg(commonStr.errorInfo)
          },
        })
        layer.close(index)
      })
    } else if (layEvent === 'edit') {
      cleanData(true)
      $.ajax({
        url: ctx + '/api/raw',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify({ id: obj.data.id, cls: 'credential' }),
        success: function (data, statusText) {
          if (data.code == '200') {
            $('#addForm1 input[name="id"]').val(obj.data.id)
            var modeName = editor.session.getMode().$id
            if (/yaml/.test(modeName)) {
              const jsonObject = JSON.parse(data.data.rawData)
              const yamlText = jsyaml.dump(jsonObject)
              editor.setValue(yamlText, -1)
            } else {
              editor.setValue(data.data.rawData, -1)
            }
            layer.open({
              type: 1,
              area: ['800px', '600px'],
              title: 'Edit',
              content: $('#windowDiv'),
              anim: 'slideRight',
              shade: 0.6,
              shadeClose: true,
              maxmin: true,
              skin: 'layui-layer-win10',
            })
          } else {
            layer.msg(data.description)
          }
        },
        error: function () {
          layer.msg(commonStr.errorInfo)
        },
      })
    }
  })

  element.on('tab(demo)', function (data) {
    layer.tips('切换了 ' + data.index + '：' + this.innerHTML, this, { tips: 1 })
  })
})
