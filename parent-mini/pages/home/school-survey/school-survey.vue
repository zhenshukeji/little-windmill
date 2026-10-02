<template>
	<view class="survey-page">
		<view class="swiper-wrap">
			// #ifdef MP-WEIXIN
			<heads :titleText="'园区概况'" :backType="2" :background="'transparent'" :fontSize="36" :titleColor="'#FFFFFF'">
			</heads>
			// #endif
			<!-- 轮播图 -->
			<view class="uni-margin-wrap">
				<swiper class="swiper" :autoplay="autoplay" circular :interval="interval" :duration="duration"
					indicator-dots>
					<swiper-item v-for="item in imgList" :key="item">
						<image :src="item"></image>
					</swiper-item>
				</swiper>
				<!-- 无园区图片时的中性提示（底色为主色，保证透明导航的白色标题可读） -->
				<view class="img-empty" v-if="!imgList.length">暂无园区图片</view>
			</view>
		</view>
		<!-- 内容 -->
		<view class="content-wrap">
			<!-- 原为 <mp-html>：本工程并未依赖该组件（uni_modules/无 mp-html，编译产物 usingComponents 也未注册），
			     微信端与 H5 端都渲染不出正文。改用内置 rich-text，零新增依赖。
			     后端 survey_content 是纯文本（含 \n 分段），转成 <br/> 保留换行。 -->
			<rich-text :nodes="richNodes"></rich-text>
		</view>

	</view>
</template>

<script setup>
	import api from '@/api/index.js'
	import {
		onMounted
	} from 'vue'
	import {
		ref,
		reactive,
		computed
	} from 'vue'
	let show = ref(false)
	let indicatorDots = ref(true)
	let interval = ref(2000)
	let duration = ref(500)
	let autoplay = ref(true)
	let currentIndex = ref(0)
	let imgList = reactive([])
	let surveyContent = ref('')
	// rich-text 需要 HTML 字符串；survey_content 为纯文本，把换行转成 <br/> 才能分段显示
	const richNodes = computed(() => String(surveyContent.value || '').replace(/\r?\n/g, '<br/>'))

	// 园区图片字段为单字符串；后端 Banner 同源字段也是逗号分隔（BannerServiceImpl 同样 split(",")），
	// 这里按同一约定拆分为多图，单张 URL 拆分结果与原行为一致。
	function toImgList(url) {
		if (!url) return []
		return String(url).split(',').map(item => item.trim()).filter(item => item)
	}

	onMounted(() => {
		api.home.getSurvey().then(res => {
			if (!res || !res.data) return
			imgList.push(...toImgList(res.data.imgUrl))
			surveyContent.value = res.data.surveyContent
		}).catch(() => {})
	})
</script>

<style scoped lang="scss">
	.survey-page {
		position: relative;

		.swiper-wrap {
			width: 100%;
			height: 776rpx;
			box-sizing: border-box;
			overflow: hidden;
			background: $theme-color;

			//轮播图
			.uni-margin-wrap {
				position: absolute;
				top: 0;
				left: 0;
				width: 100%;
				height: 776rpx;

				.swiper {
					height: 100%;

					image {
						width: 100%;
						height: 100%;
					}
				}

				.img-empty {
					position: absolute;
					left: 0;
					right: 0;
					top: 50%;
					text-align: center;
					font-size: $f26;
					color: #FFFFFF;
				}
			}
		}

		.content-wrap {
			position: relative;
			width: 100%;
			height: calc(100vh - 776rpx);
			background: $card-bg;
			margin-top: -76rpx;
			border-radius: $radius-card $radius-card 0 0;
			overflow-y: scroll;
			padding: 32rpx $gutter 40rpx;
			font-size: $f28;
			color: $color1;
			line-height: 1.7;
			box-sizing: border-box;
		}
	}
</style>
