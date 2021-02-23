import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import {postRequest} from "@/utils/api";
import {postKeyValueRequest} from "@/utils/api";
import {putRequest} from "@/utils/api";
import {deleteRequest} from "@/utils/api";
import {getRequest} from "@/utils/api";
import {initMenu} from "@/utils/menus";
import 'font-awesome/css/font-awesome.min.css'

Vue.prototype.postRequest = postRequest;
Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.getRequest = getRequest;


Vue.config.productionTip = false
Vue.use(ElementUI,{size:'small'});
// 全局配置

router.beforeEach((to, from, next) => {
	// ...全局导航守护
	// console.log(to)
	// console.log(from)
	// next();
	if(to.path=='/'){
		next();
	}else{
		if(window.sessionStorage.getItem("user")){
			initMenu(router,store);//初始化
			next();
		}
		else{
			// next('/');//未登录的话需要跳转到登录页面
			next('/?redirect='+to.path);//to是之前的未登录时候想去的地方，这样直接可以重定向
		}
	}
})

new Vue({
	router,
	store,
	render: h => h(App)
}).$mount('#app')
