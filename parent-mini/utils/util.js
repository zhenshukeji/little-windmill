// 清提示

function Toast(title, icon = 'none', duration = 2000) {
	uni.showToast({
		title,
		icon,
		duration
	})
}


export default {
	Toast
}
