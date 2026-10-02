<template>
	<view class='nav'
		:style="{height:status + navHeight + 'px',background:'url(' + headBg + ')',backgroundColor:background}">
		<view class='status' :style="{height: status + 'px'}"></view>
		<view class='navbar' :style="{height:navHeight +'px'}">
			<view class="icon"  v-if="showBack" @click='back'>
				<image v-if="backType==1" class="nav_icon" src="/static/images/back.png"></image>
				<image v-if="backType==2" class="nav_icon" src="/static/images/common/back_white.png"></image>
			</view>
			<view class='nav-title' :class="leftAlign?'left-align':''">
				<text :style="{textStyle,color:titleColor}">{{titleText}}</text>
			</view>
			<view class="nav-desc">
				<slot />
			</view>
		</view>
	</view>
	<view class="nav-seat" :style="{height:status + navHeight + 'px'}"></view>
</template>

<script setup>
	import {
		ref,
		onMounted
	} from 'vue'
	const props = defineProps({
		headBg: {
			type: String,
			default: ''
		},
		background: {
			type: String,
			default: '#F6F7F2'
		},
		color: {
			type: String,
			default: 'rgba(0, 0, 0, 1)'
		},
		titleText: {
			type: String,
			default: ''
		},
		titleImg: {
			type: String,
			default: ''
		},
		titleColor: {
			type: String,
			default: '#293D36'
		},
		backIcon: {
			type: String,
			default: ''
		},
		homeIcon: {
			type: String,
			default: ''
		},
		fontSize: {
			type: Number,
			default: 36
		},
		iconHeight: {
			type: Number,
			default: 19
		},
		iconWidth: {
			type: Number,
			default: 58
		},
		showBack: {
			type: Boolean,
			default: true
		},
		leftAlign: {
			type: Boolean,
			default: false
		},
		backType: {
			type: Number,
			default: 1
		},
		
	})
	onMounted(() => {
		setNavSize()
		setStyle()
	})
	let containerStyle = ref('')
	let textStyle = ref(0)
	let iconStyle = ref('')
	let status = ref(0)
	let navHeight = ref(0)
	// 通过获取系统信息计算导航栏高度     

	const setNavSize = function() {
		let sysinfo = uni.getSystemInfoSync(),
			statusHeight = sysinfo.statusBarHeight,
			isiOS = sysinfo.system.indexOf('iOS') > -1,
			navHeights;
		if (!isiOS) {
			navHeights = 48;
		} else {
			navHeights = 44;
		}

		status.value = statusHeight
		navHeight.value = navHeights
	}
	const setStyle = function() {
		let containerStyles
		let textStyles
		let iconStyles
		textStyles = [
			'color:' + props.color,
			'font-size:' + props.fontSize + 'rpx'
		].join(';');
		iconStyles = [
			'width: ' + props.iconWidth + 'px',
			'height: ' + props.iconHeight + 'px'
		].join(';');


		containerStyle.value = containerStyles
		textStyle.value = textStyles
		iconStyle.value = iconStyles
	}
	// 返回事件        
	const back = function() {
		console.log('当前页面栈', getCurrentPages().length)
		let len = getCurrentPages().length
		// this.triggerEvent('back', {
		// 	back: 1
		// })
		if (len === 1) {
			uni.switchTab({
				url: '/pages/tabbar/home/home'
			})
		} else {
			uni.navigateBack({
				delta: 1
			})
		}
	}
	// const home = function() {
	// 	this.triggerEvent('home', {});
	// }
</script>

<style lang="scss" scoped>
	.nav {
		width: 100%;
		/* position: absolute; */
		top: 0;
		left: 0;
		position: fixed;
		z-index: 10;
		background-repeat: no-repeat;
		background-position: 50% 50%;
		background-size: cover;

		.navbar {
			position: relative;
			display: flex;
			align-items: center;

			.icon {
				height: 100%;
				display: flex;
				align-items: center;
				.nav_icon {
					margin-left: 30rpx;
					margin-right: 10rpx;
					width: 36rpx;
					height: 36rpx;
					/* flex: 0 0 auto; */
				}
			}

			.nav-title {
				/* flex: 1 1 auto; */
				font-weight: bold;
				position: absolute;
				/* left:80rpx; */
				top: 0;
				/* width: 100%; */
				height: 100%;
				left: 50%;
				transform: translate(-50%, 0);
				display: flex;
				justify-content: flex-start;
				align-items: center;
				&.left-align{
					left: 0;
					transform: translate(0, 0);
					padding-left: 16px;
				}
			}
		}
	}



	// .back-icon,
	// .home-icon {
	// 	width: 28px;
	// 	height: 100%;
	// 	position: absolute;
	// 	transform: translateY(-50%);
	// 	top: 50%;
	// 	display: flex;
	// }

	// .back-icon {
	// 	left: 16px;
	// }

	// .home-icon {
	// 	left: 44px
	// }
</style>
