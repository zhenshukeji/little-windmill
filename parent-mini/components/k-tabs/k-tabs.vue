<template>
	<view class="tabs-root">
		<!--uni-app-->
		<view class="tabs-box">
			<view class="tab-item" v-for="(item, index) in tabs" :key="index" @tap.stop="swichTabs(index)">
				<view class="tabs-title" :class="{ 'tabs-active': currentTab == index}">
					{{ item}}
				</view>
			</view>
		</view>
	</view>
</template>

<script setup>
	import {
		ref,
		reactive,
		onMounted,
		onUnmounted
	} from 'vue'
	const props = defineProps({
		currentTab: {
			type: Number,
			default: () => 0
		},
		tabs: {
			type: Array,
			default: () => []
		}
	})
	const emit = defineEmits(['change'])
	// 点击标题切换当前页时改变样式
	function swichTabs(index) {
		if (props.currentTab == index) {
			return false;
		} else {
			emit('change', {
				index: Number(index)
			})
		}
	}
</script>

<style scoped lang="scss">
	/* 分段控件：浅灰底 + 选中白底青绿字（实施方案 §5 申请） */
	.tabs-box {
		box-sizing: border-box;
		display: flex;
		align-items: center;
		margin: 24rpx 32rpx 8rpx;
		padding: 8rpx;
		background: #EDF0E9;
		border-radius: 24rpx;

		.tab-item {
			flex: 1;
			text-align: center;

			.tabs-title {
				padding: 20rpx 8rpx;
				font-size: 28rpx;
				color: $color6;
				border-radius: 20rpx;

				&.tabs-active {
					color: $theme-color;
					background: #FFFFFF;
					font-weight: bold;
					box-shadow: 0 4rpx 12rpx rgba(38, 61, 48, 0.06);
				}
			}
		}
	}
</style>
