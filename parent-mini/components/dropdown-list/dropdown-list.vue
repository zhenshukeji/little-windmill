<template>
	<view class="dropdown-wrap">
		<view class="select-box" @click="handleShow">
			{{ current >= 0 ? value[current]?.name : '全部' }}
			<view class="arrow"></view>
		</view>
		<view class="dropdown-view" :class="[show ? 'dropdownlist-show' : '']">
			<view class="list-item-wrap" v-for="(item,index) in value" :key="index" @click="clickItem(index)">
				<view class="list-item" :class="current==index?'active':''">
					{{item.name}}
					<image v-if="current==index" src="/static/images/health/select.png" mode=""></image>
				</view>
				<view class="line" v-if="index!=value.length-1"></view>
			</view>
		</view>
		<view class="mask" :class="[show ? 'mask-show' : '']"></view>
	</view>
</template>

<script setup>
	import api from '@/api/index.js'
	import {
		ref,
		reactive,
	} from 'vue'

	const props = defineProps({
		//控制显示
		show: {
			type: Boolean,
			default: false
		},
		//当前选中的值
		current: {
			type: Number,
		default: null
		},
		//子选项列表
		value: {
			type: Array,
			default: ()=>{
				return []
			}
		}

	})
	
	const emits = defineEmits(['change','toggle'])
	//点击子项
	function clickItem(index){
		emits('change',index)
	}
	//切换子菜单的显示与隐藏
	function handleShow(){
		emits('toggle')
	}
</script>

<style scoped lang="scss">
	.dropdown-wrap {
		position: relative;

		.select-box {
			position: relative;
			height: 102rpx;
			display: flex;
			align-items: center;
			font-size: 30rpx;
			color: $theme-color;
			font-weight: bold;
			padding: 0 32rpx;
			z-index: 996;
			background-color: $card-bg;

			.arrow {
				border-top: 14rpx solid transparent;
				border-bottom: 14rpx solid $theme-color;
				border-left: 10rpx solid transparent;
				border-right: 10rpx solid transparent;
				margin-top: -14rpx;
				margin-left: 16rpx;
			}
		}

		.dropdown-view {
			width: 100%;
			overflow: hidden;
			position: absolute;
			z-index: -99;
			left: 0;
			opacity: 0;
			transition: all 0.2s ease-in-out;
			padding: 0 32rpx 30rpx;
			background-color: $card-bg;

			.list-item-wrap {

				.line {
					width: 100%;
					height: 1px;
					background: $line-color;
					margin: 20rpx 0;
				}

				.list-item {
					display: flex;
					align-items: center;
					justify-content: space-between;
					font-size: 26rpx;
					color: $color1;

					&.active {
						color: $theme-color;
					}

					image {
						width: 30rpx;
						height: 30rpx;
					}
				}
			}


		}

		.dropdownlist-show {
			opacity: 1;
			z-index: 996;
		}

		.mask {
			position: fixed;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			background-color: rgba(0, 0, 0, 0.6);
			transition: all 0.3s ease-in-out;
			opacity: 0;
			visibility: hidden;
		}

		.mask-show {
			visibility: visible;
			opacity: 1;
			z-index: 9;
		}
	}
</style>
