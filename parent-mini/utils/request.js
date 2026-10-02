import cache from './cache.js'
export const baseData = {
	appid: "${WX_APP_ID:your-app-id}",
	// baseUrl: 'https://${API_BASE_URL:http://localhost:8184}/api/dev/KgParent/wx',
	// 社区版：API 地址由部署配置决定，默认指向本机后端
	baseUrl: 'http://127.0.0.1:8184',
}

// 封装的请求
export const request = (url, data, method = 'get') => {
	return new Promise(function(resolve, reject) {
		// 防止数据重复提交 针对修改和新增
		if (method === 'post' || method === 'put') {
			try{
				const requestObj = {
					url: url,
					data: typeof data === 'object' ? JSON.stringify(data) : data,
					time: new Date().getTime()
				}
				const localCacheObj = cache.localCache.getJSON('localCacheObj')
				if (localCacheObj === undefined || localCacheObj === null || localCacheObj === '') {
					cache.localCache.setJSON('localCacheObj', requestObj)
				} else {
					const s_url = localCacheObj.url; // 请求地址
					const s_data = localCacheObj.data; // 请求数据
					const s_time = localCacheObj.time; // 请求时间
					const interval = 300; // 间隔时间(ms)，小于此时间视为重复提交
					if (s_data === requestObj.data && requestObj.time - s_time < interval && s_url ===
						requestObj.url) {
						console.log('数据正在处理，请勿重复提交');
						return reject('重复提交，请稍后再试')
					} else {
						cache.localCache.setJSON('localCacheObj', requestObj)
					}
				}
			}catch(e){
				console.log('报错',e);
				//TODO handle the exception
			}
		}
		uni.request({
			url: baseData.baseUrl + url,
			data: data,
			method: method,
			header: {
				Authorization: uni.getStorageSync('token')
			},
			success: (res) => {
				let code = res.data.code
				if (code === 200) { // 成功的请求状态
					resolve(res.data);
				} else if (code === 401) {
          // 移除所有本地数据
          uni.clearStorageSync();
          if (url !== "/wx/user/getInfo") {
            // 非获取用户信息接口去往登录页
            uni.redirectTo({
              url: "/pages/login/login",
            });
          } else {
            resolve(res.data);
          }
        } else {
					let msg = res.data.msg || '网络繁忙,请稍后再试~'
					uni.showToast({
						title: msg,
						icon: 'none',
						duration: 1500,
					});
					reject(res.data.msg)
				}
			},
			fail: (err) => {
				reject(err)
			}
		});
	})
};
