var element
var netList = []

$(function () {
  //netDiv = echarts.init(document.getElementById('netDiv'));

  layui.use('element', function () {
    element = layui.element // Tab的切换功能，切换事件监听等，需要依赖element模块
  })

  setInterval(() => {
    load()
  }, 2000)

  //initEchart();
  //network();
  sys()
})

function sys() {
  var editor = ace.edit('editor')
  editor.setTheme('ace/theme/merbivore')
  editor.session.setMode('ace/mode/json')
  editor.setFontSize(13)
  editor.setOptions({
    minLines: 10,
    maxLines: Infinity,
  })
  editor.resize()

  $.ajax({
    url: ctx + '/admin/monitor/sys',
    type: 'post',
    contentType: 'application/json',
    success: function (data, statusText) {
      if (data.code == '200') {
        editor.setValue(data.data, -1)
      } else {
        editor.setValue('', -1)
      }
    },
    error: function () {
      editor.setValue('', -1)
    },
  })
}

function load() {
  $.ajax({
    type: 'POST',
    url: ctx + '/admin/monitor/check',
    dataType: 'json',
    success: function (data) {
      if (data.code == 200) {
        var monitorInfo = data.data
        element.progress('cpu', monitorInfo.cpuRatio)
        element.progress('mem', monitorInfo.memRatio)

        $('#memContent').html(
          '( ' +
            monitorStr.used +
            ':' +
            monitorInfo.usedMemory +
            ' / ' +
            monitorStr.total +
            ':' +
            monitorInfo.totalMemorySize +
            ' )'
        )
        $('#cpuCount').html(
          '( ' +
            monitorStr.coreCount +
            ':' +
            monitorInfo.cpuCount +
            ' / ' +
            monitorStr.threadCount +
            ':' +
            monitorInfo.threadCount +
            ' )'
        )
      }
    },
    error: function () {
      //layer.alert(commonStr.errorInfo);
    },
  })
}

function network() {
  $.ajax({
    type: 'POST',
    url: ctx + '/admin/monitor/network',
    dataType: 'json',
    success: function (data) {
      if (data.success) {
        var networkInfo = data.obj
        netList.push(networkInfo)

        if (netList.length > 10) {
          netList.splice(0, 1)
        }

        initEchart()
        network()
      }
    },
    error: function () {
      //layer.alert(commonStr.errorInfo);
    },
  })
}

function initEchart() {
  var time = []
  var send = []
  var receive = []
  for (let i = 0; i < netList.length; i++) {
    time.push(netList[i].time)
    send.push(netList[i].send)
    receive.push(netList[i].receive)
  }

  var option = {
    title: {
      text: monitorStr.netStatistics,
      left: 'left',
    },
    tooltip: {
      trigger: 'axis',
      formatter(params) {
        return `
                    ${monitorStr.send}: ${params[0].value} kB/s<br>
                    ${monitorStr.receive}: ${params[1].value}  kB/s
                `
      },
    },
    legend: {
      data: [monitorStr.send, monitorStr.receive],
    },
    xAxis: {
      name: monitorStr.time,
      type: 'category',
      data: time,
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        formatter: '{value} kB/s',
      },
    },
    series: [
      {
        name: monitorStr.send,
        data: send,
        type: 'line',
        showBackground: true,
        backgroundStyle: {
          color: 'rgba(108,80,243,0.3)',
        },
      },
      {
        name: monitorStr.receive,
        data: receive,
        type: 'line',
        showBackground: true,
        backgroundStyle: {
          color: 'rgba(0,202,149,0.3)',
        },
      },
    ],
  }

  //netDiv.setOption(option);
}
