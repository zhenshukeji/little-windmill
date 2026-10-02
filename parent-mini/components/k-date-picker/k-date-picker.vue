<template>
	<view>
		<!-- 遮罩：@touchmove.stop.prevent 在 mp-weixin 编译为 catchtouchmove（阻止滚动穿透），H5 编译为 withModifiers 包装；
		     原 Vue2 时代手写的 catchtouchmove="stop" 会导致 WXML 出现重复 catchtouchmove 属性，已删 -->
		<view class="mask" :class="{ 'mask-show': isShow }" @touchmove.stop.prevent="stop" @click="hide"></view>
		<!-- 主体 -->
		<view class="pick-body" :class="{ 'pick-show': isShow }">
			<view class="picker-header">
				<view class="btn-picker" @click="hide">取消</view>
				<view class="title-picker">日期
				</view>
				<view class="btn-picker" @click="btnFix">确定</view>
			</view>
			<view class="picker-wrap">
				<picker-view :value="value" indicator-class="indicator" @change="bindChange" class="picker-view">
					<picker-view-column>
						<view class="item" v-for="(item,index) in years" :key="index">{{item}}年</view>
					</picker-view-column>
					<picker-view-column>
						<view class="item" v-for="(item,index) in months" :key="index">{{item}}月</view>
					</picker-view-column>
					<picker-view-column>
						<view class="item" v-for="(item,index) in days" :key="index">{{item}}日</view>
					</picker-view-column>
					<picker-view-column v-if="type==1">
						<view class="item" v-for="(item,index) in hours" :key="index">{{item}}时</view>
					</picker-view-column>
					<picker-view-column v-if="type==1">
						<view class="item" v-for="(item,index) in minutes" :key="index">{{item}}分</view>
					</picker-view-column>
				</picker-view>
			</view>
		</view>
	</view>
</template>

<script setup>
	import {
		ref,
		reactive,
		onMounted,
		watch,
		nextTick
	} from 'vue'
	const props = defineProps({
		//通过 type 属性设置选择类型，1（年月日 时分） 2（年月日）
		type: {
			type: Number,
			default: 1
		},
		//控制组件的显示与隐藏
		isShow: {
			type: Boolean,
			default: false
		},
		//设置默认显示日期 2019-08-01 || 2019-08-01 17:01 || 2019/08/01
		setDateTime: {
			type: String,
			default: ''
		},
	})
	const emit = defineEmits(['cancel', 'confirm'])

	// 遮罩 touchmove 处理：模板 @touchmove.stop.prevent 与 catchtouchmove 均引用此函数。
	// 此前未定义，H5 渲染时对 undefined 调 withModifiers 抛 _withMods 异常（三个表单页受累），
	// mp-weixin 触摸遮罩也会报 handler 缺失；空实现即可配合 .stop/.prevent 与 catch 前缀完成滚动拦截。
	function stop() {}

	// const isShow = ref(true)
	const years = reactive([])
	const months = reactive([])
	const days = reactive([])
	const hours = reactive([])
	const minutes = reactive([])

	// 默认选中日期
	const year = ref('')
	const month = ref('')
	const day = ref('')
	const hour = ref('')
	const minute = ref('')
	let value = reactive([0, 0, 0, 0, 0])

	const startYear = ref('1990')
	const endYear = ref('2990')

	function generateArray(start, end) {
		return Array.from(new Array(end + 1).keys()).slice(start);
	}

	function getIndex(arr, val) {
		let index = arr.indexOf(val);
		return ~index ? index : 0;
	}

	//设置年份
	function setYears() {
		years.length = 0
		years.push(...generateArray(Number(startYear.value), Number(endYear.value)))
		let index = getIndex(years, year.value)
		nextTick(function() {
			value[0] = index
		})
	}
	//设置月份
	function setMonths() {
		months.length = 0
		months.push(...generateArray(1, 12))
		let index = getIndex(months, month.value)
		nextTick(function() {
			value[1] = index
		})
	}

	// 设置日数
	function setDays() {
		days.length = 0
		let totalDays = new Date(year.value, month.value, 0).getDate();
		days.push(...generateArray(1, totalDays))
		let index = getIndex(days, day.value)
		nextTick(function() {
			value[2] = index
		})
	}

	// 设置小时
	function setHours() {
		let data = generateArray(1, 24).map(item => {
			return formatNum(item)
		})
		hours.length = 0
		hours.push(...data)
		let index = getIndex(hours, hour.value)
		nextTick(function() {
			value[3] = index
		})
	}
	// 设置分钟
	function setMinutes() {
		let data = generateArray(0, 59).map(item => {
			return formatNum(item)
		})
		minutes.length = 0
		minutes.push(...data)
		let index = getIndex(minutes, minute.value)
		nextTick(function() {
			value[4] = index
		})
	}
	//不足两位数补0处理
	function formatNum(num) {
		return num < 10 ? '0' + num : num + '';
	}

	onMounted(() => {
		nextTick(function() {
			initData()
		})

	})

	//日期时间处理
	function initSelectValue() {
		const time = props.setDateTime ? new Date(props.setDateTime) : new Date()
		year.value = time.getFullYear();
		month.value = time.getMonth() + 1;
		day.value = time.getDate();
		hour.value = formatNum(time.getHours());
		minute.value = formatNum(time.getMinutes());
	}

	//初始化
	function initData() {
		initSelectValue();
		switch (props.type) {
			case 1:
				value = [0, 0, 0, 0, 0];
				setYears();
				setMonths();
				setDays();
				setHours();
				setMinutes()
				break;
			case 2:
				value = [0, 0, 0];
				setYears();
				setMonths();
				setDays();
				break;
			default:
				break;
		}
	}

	function hide() {
		emit('cancel', {});
	}

	function btnFix() {
		let {y,M,d,H,m} = {y: year.value, M:month.value,d: day.value, H:hour.value,m:minute.value}
		M = formatNum(M)
		d = formatNum(d)
		let result = {}
		switch(props.type){
			case 1:
			result = {
				year: y,
				month: M,
				day: d,
				hour: H,
				minute: m,
				result: `${y}-${M}-${d} ${H}:${m}:00`
			};
			break;
			case 2:
			result = {
				year: y,
				month: M,
				day: d,
				result: `${y}-${M}-${d}`
			};
			break;
			default:
			break;
		}
		emit('confirm', result);
	}


	function bindChange(e) {
		value.length = 0
		value.push(...e.detail.value)
		year.value = years[value[0]];
		month.value = months[value[1]];
		day.value = days[value[2]];
		hour.value = hours[value[3]];
		minute.value = minutes[value[4]];
	}
</script>

<style>
	/* 遮罩 */
	.mask {
		position: fixed;
		z-index: 997;
		top: 0;
		right: 0;
		bottom: 0;
		left: 0;
		background-color: rgba(0, 0, 0, 0.6);
		visibility: hidden;
		opacity: 0;
		transition: all 0.3s ease-in-out;
	}

	.mask-show {
		visibility: visible !important;
		opacity: 1 !important;
	}

	/* 主体 */
	.pick-body {
		z-index: 998;
		position: fixed;
		bottom: 0;
		left: 0;
		width: 100%;
		transition: all 0.3s ease-in-out;
		transform: translateY(100%);
		background-color: #fff;
	}

	.pick-show {
		transform: translateY(0);
	}

	/* 头部 */
	.picker-header {
		width: 100%;
		height: 84rpx;
		display: flex;
		justify-content: space-between;
		align-items: center;
		box-sizing: border-box;
		padding: 0 30rpx;
		font-size: 34rpx;
	}

	/* 取消 确定的按钮样式 */
	.btn-picker {
		color: $theme-color;
	}

	.title-picker {
		color: #000;
	}

	/* picker-c */
	.picker-wrap {
		width: 100%;
		overflow: hidden;
	}

	.picker-view {
		width: 750rpx;
		height: 426rpx;
	}

	/*picker-view-column的子节点高度会自动设置成与 picker-view 选中框的高度一致 */
	.indicator {
		height: 84rpx;
		line-height: 84rpx;
	}

	.item {
		display: flex;
		align-items: center;
		justify-content: center;
		text-align: center;
	}
</style>
