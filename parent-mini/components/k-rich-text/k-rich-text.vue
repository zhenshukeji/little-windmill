<template>
	<view>
		<view v-for="(content, index) in contentArr" :key="index">
			<rich-text :nodes="content"></rich-text>
			<video v-if="videoArr[index] !== null" :src="videoArr[index]" controls :style="{ width }"></video>
		</view>
	</view>
</template>


<script setup>
	/*from: https://ext.dcloud.net.cn/search?q=bctos-rich-text */
	import {
		ref,
		reactive,
		onMounted,
		watch
	} from 'vue'

	let props = defineProps({
		nodes: {
			type: String,
			default: ''
		},
		width: {
			type: String,
			default: '100%'
		}
	})

	let contentArr = reactive([])
	let videoArr = reactive([])

	onMounted(() => {
		parseVideo();
	})
	
	watch(props, (v) => {
		parseVideo();
	})
	
	function parseVideo() {
		//同步解决如果图片太大超出手机显示界面的问题
		let nodes = props.nodes.replace(/\<img/g, '<img style="max-width:98%!important;height:auto;"');
		let arr = nodes.split('</video>');
		let reg = /<video([\s\S]*)/g;
		for (let i in arr) {
			var item = arr[i];
			var urlMatch = item.match(/<video[\s\S]*src=\"(.*?)\"/);
			if (urlMatch && urlMatch.length > 1) {
				videoArr[i] = urlMatch[1];
			} else {
				videoArr[i] = null;
			}
			contentArr[i] = item.replace(reg, '');
		}
	}
</script>

<style>

</style>
