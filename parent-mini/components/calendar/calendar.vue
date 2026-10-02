<template>
	<view class="calendar">
		<view class="calendar_wrap">
			<!-- 顶部操作栏 -->
			<view class="head_ope" v-if="type==1">
				<view class="head-btn-box left">
					<image @click="changeYear(-1)" class="left" style="transform: rotate(180deg);"
						src="/static/images/common/year.png" mode=""></image>
					<image @click="changeMonth(-1)" class="left" style="transform: rotate(180deg);"
						src="/static/images/common/month.png" mode=""></image>
				</view>
				<view class="head_content">
					{{ year }}年{{ month + 1 }}月
				</view>
				<view class="head-btn-box right">
					<image @click="changeMonth(1)" class="right" src="/static/images/common/month.png" mode=""></image>
					<image @click="changeYear(1)" class="right" src="/static/images/common/year.png" mode=""></image>
				</view>
			</view>
			<view class="head_ope" v-if="type==2">
				<view class="head-btn-box left">
					<image @click="changeMonth(-1)" class="left" style="transform: rotate(180deg);"
						src="/static/images/common/year.png" mode=""></image>
					<image @click="changeDay(-1)" class="left" style="transform: rotate(180deg);"
						src="/static/images/common/month.png" mode=""></image>
				</view>
				<view class="head_content">
					{{ year }}年{{ month + 1 }}月{{chooseDay}}日
				</view>
				<view class="head-btn-box right">
					<image @click="changeDay(1)" class="right" src="/static/images/common/month.png" mode=""></image>
					<image @click="changeMonth(1)" class="right" src="/static/images/common/year.png" mode=""></image>
				</view>
			</view>
			<!-- 日历主体 -->
			<template v-for="(item,index) in daysArr" :key="index" v-if="type==1">
				<view class="calendar_box">
					<!-- 周 -->
					<view class="weeks_container">
						<view v-for="(unit, index) in weekDay" :key="index" class="calendar_item">
							<text class="calendar_item_unit">{{ unit }}</text>
						</view>
					</view>
					<view v-for="(day, k1) in item" :key="k1" class="calendar_container">
						<view v-for="(item2, index2) in day" :key="index2" class="calendar_item" :class="{
							selected:item2.type === 0 && item2.day === chooseDay,
							gray:item2.type === -1 || item2.type === 1
						}" @click="chooseItem(item2)">
							{{ item2.day }}

							<template v-if="item2.extraInfo">
								<view class="dot"
									:style="{background:item2.extraInfo.status==-1?'#CFCFCF':item2.extraInfo.status==2?'#27846F':'#B78A32'}">
								</view>
							</template>


						</view>
					</view>
				</view>
			</template>

		</view>
	</view>
</template>

<script setup>
	import {
		ref,
		reactive,
		onMounted,
		watch
	} from 'vue'

	// 接收的值
	const props = defineProps({
		//默认日期
		time: {
			type: [String, Date, Number],
			default: () => new Date()
		},
		//打点数据
		selected: {
			type: Array,
			default: () => {
				return []
			}
		},
		//1-普通 2-只显示头部，按钮切换月日
		type: {
			type: Number,
			default: 1
		},
	})


	let daysArr = reactive([]) // 存放日历数组
	let year = ref('')
	let month = ref('')
	let date = ref('')
	let chooseDay = ref(-1) // 选择的天
	const weekDay = ['日', '一', '二', '三', '四', '五', '六']

	onMounted(() => {
		init()
	})

	// 日历初始化
	function init() {
		let now = new Date(props.time)
		console.log('now', now);
		year.value = now.getFullYear()
		month.value = now.getMonth()
		chooseDay.value = now.getDate()
		// 如果存在默认日期
		render()
	}

	// 渲染日期
	function render() {
		// 清空数组
		daysArr.length = 0
		let firstDayOfMonth = new Date(year.value, month.value, 1).getDay() //当月第一天是星期几
		let lastDateOfMonth = new Date(year.value, month.value + 1, 0).getDate() //当月最后一天
		let lastDayOfLastMonth = new Date(year.value, month.value, 0).getDate() //上个月的最后一天
		let nextDayOfLastMonth = new Date(year.value, month.value + 1, 1).getDay() //下个月的第一天
		// 显示今天日期
		let date = new Date()
		let nowYear = date.getFullYear()
		let nowMonth = date.getMonth()
		let nowDate = date.getDate()
		let i,
			line = 0,
			temp = []
		// 通过遍历获取本月日历
		for (i = 1; i <= lastDateOfMonth; i++) {
			// 获取每一天是星期几
			let day = new Date(year.value, month.value, i).getDay() //返回星期几（0～6）
			// 第一行
			// 当月第一天是星期天
			if (day == 0) {
				temp[line] = []
			} else if (i == 1) {
				// 渲染第一行数据
				temp[line] = []
				// 渲染上一个月的数据 第一行的数据
				for (let j = firstDayOfMonth; j > 0; j--) {
					temp[line].push(
						Object.assign({
							day: lastDayOfLastMonth - j + 1,
							type: -1
						})
					)
				}
			}
			let isToday = false
			// 当天
			if (nowYear === year.value && nowMonth === month.value && i === nowDate) {
				isToday = true
			} else {
				isToday = false
			}




			// 推送数据
			// type -1 上个月 0 这个月 1下个月
			temp[line].push(
				Object.assign({
					day: i,
					today: isToday,
					type: 0,
				})
			)

			// 到周六换行
			if (day == 6 && i < lastDateOfMonth) {
				line++
			} else if (i == lastDateOfMonth) {
				// 渲染下一个月的数据
				for (let d = 6; d > day; d--) {
					temp[line].push(Object.assign({
						day: 6 - d + 1,
						type: 1
					}))
				}
			}
		}
		daysArr.push(temp)
		handleSelected(daysArr)
	}
	watch(props.selected, (newVal) => {
		handleSelected()
	})

	function handleSelected() {
		if (!daysArr.length) return
		daysArr[0].forEach(val => {
			val.forEach((val2, key2) => {
				if (val2.type == 0 && key2 !== 0 && key2 !== 6) {
					let fullTime = year.value + '-' + ((month.value + 1) < 10 ?
						'0' + (month.value + 1) : (month.value + 1)) + '-' + (val2.day < 10 ?
						'0' + val2.day : val2.day)
					// 获取打点信息
					let info = props.selected && props.selected.find((item) => {
						return item.checkingDate == fullTime
					})
					if (info) {
						val2.extraInfo = info
					} else {
						val2.extraInfo = {
							status: -1
						}
					}
				}

			})
		})
	}

	const emits = defineEmits(['chooseTime', 'changeTime'])
	// 切换年份
	function changeYear(type,emit = true) {
		year.value += type
		if (year.value < 0) {
			year.value = 0
		}
		if (year.value > 3000) {
			year.value = 3000
		}
		render()
		if (!emit) return
		triggerEmit('changeTime')
	}
	// 切换月份
	function changeMonth(type, emit = true) {
		month.value += type
		// 边界
		if (month.value === 12) {
			month.value = 0
			changeYear(1, emit)
		}
		if (month.value === -1) {
			month.value = 11
			changeYear(-1, emit)
		}
		render()
		if (!emit) return
		triggerEmit('changeTime')
	}
	// 切换日期
	function changeDay(type) {
		chooseDay.value += type
		let lastDateOfMonth = new Date(year.value, month.value + 1, 0).getDate() //当月最后一天
		let lastDayOfLastMonth = new Date(year.value, month.value, 0).getDate() //上个月的最后一天
		// 边界
		if (chooseDay.value > lastDateOfMonth) {
			chooseDay.value = 1
			changeMonth(1, false)
		}
		if (chooseDay.value < 1) {
			chooseDay.value = lastDayOfLastMonth
			changeMonth(-1, false)
		}
		render()
		triggerEmit('changeTime')
		// emits('changeTime', `${year.value}-${
		//      month.value + 1 < 10 ? '0' + (month.value + 1) : month.value + 1
		//    }-${chooseDay.value < 10 ? '0' + chooseDay.value : chooseDay.value}`)
	}

	// 选择了某一天
	function chooseItem(item) {
		chooseDay.value = item.day
		// 上个月
		if (item.type === -1) {
			changeMonth(-1)
		} else if (item.type === 1) {
			changeMonth(1)
		}

		triggerEmit('chooseTime')

		// emits('chooseTime', `${year.value}-${
		//      month.value + 1 < 10 ? '0' + (month.value + 1) : month.value + 1
		//    }-${chooseDay.value < 10 ? '0' + chooseDay.value : chooseDay.value}`)
	}

	function triggerEmit(funName) {
		let y = year.value
		let m = month.value < 9 ? '0' + (month.value + 1) : month.value + 1
		let d = chooseDay.value < 10 ? '0' + chooseDay.value : chooseDay.value
		let result = {
			year: y,
			month: m,
			date: d
		}
		emits(funName, result)
	}
</script>

<style scoped lang="scss">
	.calendar_wrap {

		// 头部
		.head_ope {
			height: 80rpx;
			display: flex;
			align-items: center;
			justify-content: space-between;

			.head_content {
				text-align: center;
				font-size: 30rpx;
				color: $color1;
				font-weight: bold;
			}

			.head-btn-box {
				image {
					width: 40rpx;
					height: 40rpx;

				}

				&.left image {
					margin-left: 28rpx;
				}

				&.right image {
					margin-right: 28rpx;
				}
			}
		}

		// 主体
		.calendar_box {
			.weeks_container {
				display: flex;
				align-items: center;
				justify-content: space-around;
				font-size: 27rpx;
				font-weight: 500;
				color: $color1;
				height: 83rpx;
				margin-top: 18rpx;
			}
		}

		.calendar_container {
			display: flex;
			align-items: center;
			justify-content: space-around;
			font-size: 32rpx;
			color: $color1;
			height: 95rpx;
			margin-top: 18rpx;

			.calendar_item {
				position: relative;
				width: 54rpx;
				height: 54rpx;
				display: flex;
				align-items: center;
				justify-content: center;

				&.selected {
					background: $theme-color;
					border-radius: 50%;
					color: #fff;
				}

				&.gray {
					color: #CFCFCF;
				}

				.dot {
					position: absolute;
					bottom: -14rpx;
					left: 50%;
					transform: translateX(-50%);
					width: 8rpx;
					height: 8rpx;
					background: $theme-color;
					border-radius: 50%;

				}
			}
		}
	}
</style>
