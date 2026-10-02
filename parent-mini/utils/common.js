/**
 * 格式化时间
 * @param {*} time 字符串或者时间对象 
 * @param {*} pattern  需要转化的格式
 * @returns 
 */
const parseTime = (time, pattern) => {
	if (arguments.length === 0 || !time) {
		return null
	}
	const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}'
	let date
	if (typeof time === 'object') {
		date = time
	} else {
		if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
			time = parseInt(time)
		} else if (typeof time === 'string') {
			time = time.replace(new RegExp(/-/gm), '/');
		}
		if ((typeof time === 'number') && (time.toString().length === 10)) {
			time = time * 1000
		}
		date = new Date(time)
	}
	const formatObj = {
		y: date.getFullYear(),
		m: date.getMonth() + 1,
		d: date.getDate(),
		h: date.getHours(),
		i: date.getMinutes(),
		s: date.getSeconds(),
		a: date.getDay()
	}
	const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
		let value = formatObj[key]
		if (key === 'a') {
			return ['(周日)', '(周一)', '(周二)', '(周三)', '(周四)', '(周五)', '(周六)'][value]
		}
		if (result.length > 0 && value < 10) {
			value = '0' + value
		}
		return value || 0
	})
	return time_str
}

// 删除对应参数 及值为空、null、undefined的参数
const formatObject = (paramObj) => {
  for (let key in paramObj) {
    if ((paramObj[key] === undefined || paramObj[key] === null || paramObj[key] === '')) {
      delete paramObj[key]
    }
  }
}

/* 轻提示 */
const toast = (title, icon = 'none', duration = 2000) => {
	uni.showToast({
		title,
		icon,
		duration
	})
}


export {
	parseTime,
	formatObject,
	toast
}
