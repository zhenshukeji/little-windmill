/**
 * 格式化日期
 * @param date 2020-01-01 00:00:00
 * @return { String } 03-01
 */
export function formatDate(date) {
  if (date) {
    date = date.replace(/-/g, '/')
  }
  let d = new Date(date)
  let year = d.getFullYear()
  let month = d.getMonth() + 1
  if (month < 10) {
    month = `0${month}`
  }
  let day = d.getDate()
  if (day < 10) {
    day = `0${day}`
  }
  return `${month}-${day}`
}

export function timeAgo(dateTimeStamp = '') {
  if (dateTimeStamp === '') {
    return ''
  }
  let date = dateTimeStamp.replace(/-/g, '/')

  date = Date.parse(new Date(date))
  var minute = 1000 * 60      //把分，时，天，周，半个月，一个月用毫秒表示
  var hour = minute * 60
  var day = hour * 24
  var week = day * 7
  var halfamonth = day * 15
  var month = day * 30
  let result
  var now = new Date().getTime()   //获取当前时间毫秒
  var diffValue = now - date//时间差
  if (diffValue < 0) {
    return
  }
  var minC = Math.ceil(diffValue / minute)  //计算时间差的分，时，天，周，月
  var hourC = Math.ceil(diffValue / hour)
  var dayC = Math.ceil(diffValue / day)
  var weekC = Math.ceil(diffValue / week)
  var monthC = Math.ceil(diffValue / month)

  if (diffValue >= 0 && diffValue <= minute) {
    result = "刚刚"
  } else if (minC >= 1 && minC <= 59) {
    result = " " + parseInt(minC) + "分钟前"
  } else if (hourC >= 1 && hourC <= 23) {
    result = " " + parseInt(hourC) + "小时前"
  } else {
    return dateTimeStamp
  }


  //  else if (dayC >= 1 && dayC <= 6) {
  //   result = " " + parseInt(dayC) + "天前"
  // } else if (weekC >= 1 && weekC <= 3) {
  //   result = " " + parseInt(weekC) + "周前"
  // }else if (monthC >= 1 && monthC <= 3) {
  //   result = " " + parseInt(monthC) + "月前"
  // }
  // else {
  //   var datetime = new Date()
  //   datetime.setTime(dateTimeStamp)
  //   var Nyear = datetime.getFullYear()
  //   var Nmonth = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1
  //   var Ndate = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate()
  //   var Nhour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours()
  //   var Nminute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes()
  //   var Nsecond = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds()
  //   result = Nyear + "-" + Nmonth + "-" + Ndate
  // }



  // if (monthC >= 1 && monthC <= 3) {
  //   result = " " + parseInt(monthC) + "月前"
  // } else if (weekC >= 1 && weekC <= 3) {
  //   result = " " + parseInt(weekC) + "周前"
  // } else if (dayC >= 1 && dayC <= 6) {
  //   result = " " + parseInt(dayC) + "天前"
  // } else if (hourC >= 1 && hourC <= 23) {
  //   result = " " + parseInt(hourC) + "小时前"
  // } else if (minC >= 1 && minC <= 59) {
  //   result = " " + parseInt(minC) + "分钟前"
  // } else if (diffValue >= 0 && diffValue <= minute) {
  //   result = "刚刚"
  // } else {
  //   var datetime = new Date()
  //   datetime.setTime(dateTimeStamp)
  //   var Nyear = datetime.getFullYear()
  //   var Nmonth = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1
  //   var Ndate = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate()
  //   var Nhour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours()
  //   var Nminute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes()
  //   var Nsecond = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds()
  //   result = Nyear + "-" + Nmonth + "-" + Ndate
  // }
  return result
}
