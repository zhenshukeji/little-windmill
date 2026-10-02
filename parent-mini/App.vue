<script>
	export default {
		onLaunch: function() {
			console.warn('当前组件仅支持 uni_modules 目录结构 ，请升级 HBuilderX 到 3.1.0 版本以上！')
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	@import '@/uni_modules/uni-scss/index.scss';
	@import '@/icons/iconfront.css';
	/* #ifndef APP-NVUE */
	@import '@/static/customicons.css';
	@import '@/static/css/common.css';
	// 设置整个项目的背景色
	/* #endif */
	.example-info {
		font-size: 14px;
		color: #333;
		padding: 10px;
	}

	#app {
		width: 100%;
		min-height: 100vh;
	}

	.uni_head {
		/*  #ifdef MP-WEIXIN */
		// padding-top: 88px;
		/* #endif */
	}
	// 园区概况-改变指示点样式
	.survey-page{
		.wx-swiper-dots.wx-swiper-dots-horizontal {
		  margin-bottom: 96rpx;
		}
		wx-swiper .wx-swiper-dot {
			width: 10rpx;
			height: 10rpx;
			background-color: $color2;
			border-radius: 50%;
		}
		
		wx-swiper .wx-swiper-dot-active {
			background-color: $theme-color;
			width: 20rpx;
			height: 10rpx;
			border-radius: 5rpx;
		}
	}
</style>
