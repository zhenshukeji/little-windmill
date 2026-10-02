<template>
	<view class="container">
		// #ifdef MP-WEIXIN
		<heads :showBack="false" titleText="申请"></heads>
		// #endif

		<!-- 请假（社区版仅保留请假分类） -->
		<view class="content-box">
			<view class="list">
				<view class="card" v-for="item in vacateList" :key="item.id" @click="toDetail(item.id)">
					<view class="head">
						<image class="type-icon" src="/static/images/apply/qingjia.png"></image>
						<view class="type">请假申请</view>
						<view class="statu status1 bg1" v-if="item.status==1">已同意</view>
						<view class="statu status2 bg2" v-if="item.status==2">拒绝</view>
						<view class="statu status3 bg3" v-if="item.status==3">撤回</view>
						<view class="statu status0 bg0" v-if="item.status==0">待处理</view>
					</view>
					<view class="msg">
						{{item.vacateReason}}
					</view>
					<view class="foot">
						<view class="date">
							{{item.beginTime}}-{{item.endTime}}
						</view>
						<view class="more">查看详情 ›</view>
					</view>
				</view>
			</view>
		</view>

		<uni-load-more :status="status"></uni-load-more>

		<!-- 新建请假申请 -->
		<view class="apply-action">
			<view class="primary-btn" @click="toAddApply">＋ 新建请假申请</view>
		</view>
	</view>
</template>

<script setup>
	import api from '@/api/index.js'
	import {
		ref,
		reactive,
	} from 'vue'
	import {
		onReachBottom,
		onShow
	} from '@dcloudio/uni-app'

	let status = ref('loading') //控制loading状态 loading - no-more

	/* 获取请假申请列表 */
	let pageTotal0 = 0 //总页数
	let pageSize0 = 10 //每页数量
	let currentPage0 = 1 //当前页
	const vacateList = reactive([])

	function getVacateList() {
		const params = {
			pageNum: currentPage0,
			pageSize: pageSize0
		}
		api.apply._getVacateList(params).then(res => {
			if (res.code == 200) {
				if (currentPage0 === 1) vacateList.length = 0
				vacateList.push(...res.data.records)
				pageTotal0 = res.data.pages
				currentPage0++
			}
			status.value = 'no-more'
		}).catch(() => {
			status.value = 'no-more'
			uni.showToast({ title: '获取数据失败', icon: 'none' })
		})
	}

	// 页面触底加载更多
	onReachBottom(() => {
		if (pageTotal0 < currentPage0) {
			status.value = 'no-more'
		} else {
			status.value = 'loading'
			getVacateList()
		}
	})

	onShow(() => {
		currentPage0 = 1
		getVacateList()
	})

	/* 跳转到新建请假页 */
	function toAddApply() {
		uni.navigateTo({
			url: '/pages/apply/add-vacate/add-vacate'
		})
	}
	/* 跳转到详情页 */
	function toDetail(id) {
		uni.navigateTo({
			url: '/pages/apply/apply-detail/vacate?id=' + id
		})
	}
</script>

<style scoped lang="scss">
	.content-box {
		/* 底部占位 = 按钮区高度；H5 端 tabBar 为覆盖层，--window-bottom（tabBar 高+安全区）由 uni-h5 注入，
		   mp-weixin 端无该变量回退 0（原生 tabBar 不占页面视口），与下方 .apply-action 同步 */
		padding: 24rpx 32rpx calc(160rpx + var(--window-bottom, 0rpx));

		.card {
			background: $card-bg;
			border-radius: $radius-card;
			padding: $card-padding;
			margin-bottom: 24rpx;

			&:last-child {
				margin-bottom: 0;
			}
		}

		.head {
			display: flex;
			align-items: center;

			.type-icon {
				width: 44rpx;
				height: 44rpx;
				margin-right: 14rpx;
				flex-shrink: 0;
			}

			.type {
				flex: 1;
				font-size: 28rpx;
				font-weight: bold;
				color: $color1;
			}

			.statu {
				flex-shrink: 0;
				padding: 6rpx 14rpx;
				font-size: 24rpx;
				border-radius: $radius-tag;
			}
		}

		.msg {
			margin-top: 18rpx;
			font-size: 28rpx;
			line-height: 1.6;
			color: $color1;
			word-break: break-all;
		}

		.foot {
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin-top: 20rpx;
			padding-top: 18rpx;
			border-top: 1rpx solid $line-color;

			.date {
				flex: 1;
				font-size: 24rpx;
				color: $color6;
			}

			.more {
				flex-shrink: 0;
				margin-left: 16rpx;
				font-size: 24rpx;
				color: $theme-color;
			}
		}
	}

	// 新建申请主按钮
	.apply-action {
		position: fixed;
		left: 32rpx;
		right: 32rpx;
		/* 小程序端原生 tabBar 在页面视口之外，40rpx 即为与 tabBar 的间距；
		   H5 端 tabBar 是 DOM 覆盖层，需叠加 --window-bottom 让开，否则按钮被盖住 */
		bottom: calc(40rpx + var(--window-bottom, 0rpx));
		z-index: 5;

		.primary-btn {
			height: $btn-height;
			line-height: $btn-height;
			text-align: center;
			font-size: 30rpx;
			color: #FFFFFF;
			background: $theme-color;
			border-radius: $radius-btn;
			box-shadow: 0 8rpx 20rpx rgba(39, 132, 111, 0.18);
		}
	}
</style>
