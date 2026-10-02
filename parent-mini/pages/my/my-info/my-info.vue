<template>
	<view class="container">
		// #ifdef MP-WEIXIN
		<heads titleText="我的资料"></heads>
		// #endif

		<form @submit="formSubmit">
			<!-- 幼儿资料 -->
			<view class="card shadow-box">
				<view class="title">
					<view class="line"></view>
					幼儿资料
				</view>
				<view class="content">
					<view class="form-item" @click="setAvatar">
						<view class="name">设置头像</view>
						<view class="value avatar-wrap">
							<image v-if="headImg" class="avatar" :src="headImg" mode=""></image>
							<image v-else class="avatar" src="/static/images/common/avatar.png" mode=""></image>
							<image class="arrow" src="/static/images/common/arrow.png" mode=""></image>
						</view>
					</view>
					<view class="form-item">
						<view class="name">设置性别</view>
						<view class="value">
							<radio-group name="gender">
								<label class="radio">
									<radio :value="0" :checked="gender==0" style="transform:scale(0.7)"
										color="#27846F" />男
								</label>
								<label class="radio">
									<radio :value="1" :checked="gender==1" style="transform:scale(0.7)"
										color="#27846F" />女
								</label>
							</radio-group>
						</view>
					</view>
					<view class="form-item">
						<view class="name">学生姓名</view>
						<view class="value">
							<input v-model="studentName" name="studentName" placeholder="请输入学生姓名"
								:placeholder-style="inpHolderStyle" />
						</view>
					</view>
					<view class="form-item">
						<view class="name">出生日期</view>
						<view class="value">
							<input v-model="birthdate" name="birthdate" placeholder="请输入出生日期"
								:placeholder-style="inpHolderStyle" />
						</view>
					</view>
					<view class="form-item column">
						<view class="name">家庭地址</view>
						<view class="value">
							<input v-model="address" name="address" placeholder="详细地址（例如：**街区**号）"
								placeholder-style="font-size:28rpx;color:#65766C;" />
						</view>
					</view>
				</view>
			</view>

			<!-- 家长联系人1 -->
			<view class="card shadow-box">
				<view class="title">
					<view class="line"></view>
					家长联系人 1
				</view>
				<view class="content">
					<view class="form-item">
						<view class="name">关系</view>
						<view class="value">
							<radio-group name='m1_relation'>
								<label class="radio">
									<radio value="父亲" :checked="menber1.relation=='父亲'" style="transform:scale(0.7)"
										color="#27846F" />父亲
								</label>
								<label class="radio">
									<radio value="母亲" :checked="menber1.relation=='母亲'" style="transform:scale(0.7)"
										color="#27846F" />母亲
								</label>
							</radio-group>
						</view>
					</view>
					<view class="form-item">
						<view class="name">姓名</view>
						<view class="value">
							<input v-model="menber1.name" name='m1_name' placeholder="请输入家长姓名"
								:placeholder-style="inpHolderStyle" />
						</view>
					</view>
					<view class="form-item">
						<view class="name">手机号</view>
						<view class="value">
							<input v-model="menber1.phone" name='m1_phone' placeholder="请输入家长手机号"
								:placeholder-style="inpHolderStyle" />
						</view>
					</view>
				</view>
			</view>
			<!-- 家长联系人2 -->
			<view class="card shadow-box">
				<view class="title">
					<view class="line"></view>
					家长联系人 2
				</view>
				<view class="content">
					<view class="form-item">
						<view class="name">关系</view>
						<view class="value">
							<radio-group name='m2_relation'>
								<label class="radio">
									<radio value="父亲" :checked="menber2.relation=='父亲'" style="transform:scale(0.7)"
										color="#27846F" />父亲
								</label>
								<label class="radio">
									<radio value="母亲" :checked="menber2.relation=='母亲'" style="transform:scale(0.7)"
										color="#27846F" />母亲
								</label>
							</radio-group>
						</view>
					</view>
					<view class="form-item">
						<view class="name">姓名</view>
						<view class="value">
							<input v-model="menber2.name" name='m2_name' placeholder="请输入家长姓名"
								:placeholder-style="inpHolderStyle" />
						</view>
					</view>
					<view class="form-item">
						<view class="name">手机号</view>
						<view class="value">
							<input v-model="menber2.phone" name='m2_phone' placeholder="请输入家长手机号"
								:placeholder-style="inpHolderStyle" />
						</view>
					</view>
				</view>
			</view>

			<button class="submit-btn" form-type="submit">保存</button>
		</form>

	</view>
</template>

<script setup>
	import api from '@/api/index.js'
	import {
		ref,
		reactive,
		computed
	} from 'vue'
	import {
		onLoad
	} from '@dcloudio/uni-app'
	import {
		baseData
	} from "@/utils/request.js"
	import {
		toast
	} from "@/utils/common.js"

	const inpHolderStyle = ref('font-size:28rpx;color:#65766C;text-align:right')


	let headImg = ref('') //头像
	let gender = ref() //性别 0男 1女 2未知
	let studentName = ref('') //学生姓名
	let birthdate = ref('') //出生日期
	let address = ref('') //家庭地址
	let menber1 = reactive({}) //家庭成员1
	let menber2 = reactive({}) //家庭成员2

	/* 页面加载 */
	onLoad(() => {
		getMyInfo()
	})
	
	function getMyInfo(){
		api.my._getMyInfo().then(res=>{
			console.log(333,res);
			if(res.code==200){
				const data = res.data
				headImg.value = data.headImg
				gender.value = data.gander
				console.log('gender.value ',gender.value );
				studentName.value = data.studentName
				birthdate.value = data.birthdate
				address.value = data.address
				if(data.guardians&&data.guardians[0]){
					Object.assign(menber1,res.data.guardians[0])
				}
				if(data.guardians&&data.guardians[1]){
					Object.assign(menber2,data.guardians[1])
				}

			}
		}).catch(() => {})
	}
	/* 设置头像 */
	function setAvatar() {
		uni.chooseImage({
			count: 1, //默认9
			success: function(res) {
				const tempFilePaths = res.tempFilePaths;
				uni.uploadFile({
					url: baseData.baseUrl + "/cdn/upload",
					filePath: tempFilePaths[0],
					name: 'file',
					formData: {

					},
					success: (uploadFileRes) => {
						const resObj = JSON.parse(uploadFileRes.data)
						if (resObj.code == 200) {
							headImg.value = resObj.data.url
						}
					}
				});
			}
		});
	}


	function formSubmit(e) {
		
		console.log('提交表单',e);
		let data = e.detail.value
		
		if(!headImg.value){
			toast('请上传头像！')
			return
		}
		if(!data.gender){
			toast('请选择性别！')
			return
		}
		if(!data.studentName){
			toast('请填写学生姓名！')
			return
		}
		if(!data.birthdate){
			toast('请填写出生日期！')
			return
		}
		
		let params = {
			headImg:headImg.value,
			gander:data.gender,
			studentName:data.studentName,
			birthdate:data.birthdate,
			address:data.address,
		}
		let m1 = {
			id: menber1.id||'',
			name: data.m1_name,
			phone: data.m1_phone,
			relation: data.m1_relation,
		}
		let m2 = {
			id: menber2.id||'',
			name: data.m2_name,
			phone: data.m2_phone,
			relation: data.m2_relation,
		}
		params.guardians = [m1,m2]
		// console.log('api',api.my);
		api.my._putMyInfo(params).then(res=>{
			console.log('pput',res);
			if (res.code == 200) {
				toast('修改成功！')
				// setTimeout(()=>{
				// 	uni.navigateBack({
				// 		delta: 1,
				// 		animationType: 'pop-out',
				// 		animationDuration: 200,
				// 		success(){
				// 		},
				// 		fail(){
				// 			uni.switchTab({
				// 				url: '/pages/tabbar/home/home'
				// 			});
				// 		}
				// 	});
				// },2000)

			}
		}).catch(() => {})
		// console.log('params',params);
		// console.log('menber1',m1);
		// console.log('menber2',m2);
		
	}
	
	function setMyInfo(){
		let params = {
			headImg:headImg.value,
			gander:gender.value,
			studentName:studentName.value,
			birthdate:birthdate.value,
			address:address.value,
		}
		console.log('params',params);
		console.log('menber1',menber1);
		console.log('menber2',menber2);
	}





	// api.home.getBacklog().then(res => {
	// 	if (res.code == 200) {

	// 	}

	// })
</script>

<style scoped lang="scss">
	.container {
		min-height: 100vh;
		background: $page-bg;
		padding: 24rpx $gutter 40rpx;

		.card {
			background: $card-bg;
			border-radius: $radius-card;
			padding: $card-padding;
		}

		.shadow-box {
			margin-bottom: 24rpx;

			&:last-child {
				margin-bottom: 0;
			}

			.title {
				font-size: $f30;
				color: $color1;
				font-weight: bold;
				line-height: 42rpx;
				display: flex;
				align-items: center;

				.line {
					height: 28rpx;
					width: 8rpx;
					border-radius: 4rpx;
					background: $theme-color;
					margin-right: 12rpx;
				}
			}

			.content {
				margin-top: 20rpx;
				padding-top: 4rpx;
				border-top: 1rpx solid $line-color;
			}

			.form-item {
				display: flex;
				align-items: center;
				justify-content: space-between;
				padding: 16rpx 0;

				&.column {
					flex-direction: column;
					align-items: stretch;

					.value {
						margin-top: 12rpx;
					}
				}

				.name {
					width: 200rpx;
					flex-shrink: 0;
					font-size: $f26;
					line-height: 40rpx;
					color: $color6;
				}

				.value {
					flex: 1;
					min-width: 0;
					text-align: right;
					font-size: $f28;
					line-height: 40rpx;
					color: $color1;

					input {
						font-size: $f28;
						color: $color1;
						text-align: right;
					}

					.arrow {
						width: 40rpx;
						height: 40rpx;
					}

					&.avatar-wrap {
						display: flex;
						align-items: center;
						justify-content: flex-end;

						.avatar {
							width: 84rpx;
							height: 84rpx;
							border-radius: 50%;
							margin-right: 10rpx;
							background: $page-bg;
						}
					}
				}
			}
		}

		.submit-btn {
			display: flex;
			align-items: center;
			justify-content: center;
			width: 100%;
			height: $btn-height;
			margin: 48rpx 0 0;
			padding: 0;
			font-size: 32rpx;
			color: #ffffff;
			background: $theme-color;
			border-radius: $radius-btn;

			&::after {
				border: 0;
			}
		}
	}
</style>
