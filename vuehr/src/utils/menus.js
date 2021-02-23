import {getRequest} from "@/utils/api";
import router from "@/router";

export const initMenu = (router, store) => {
	if (store.state.routes.length > 0) {
		return;
	}
	getRequest("/system/config/menu").then(data => {
		if (data) {
			let fmtRoutes = formatRoutes(data);
			router.addRoutes(fmtRoutes);
			store.commit('initRoutes',fmtRoutes);//格式化之后的参数
			store.dispatch('connect');
		}
	})
	
}

export const formatRoutes = (router) => {
	let fmRoutes = [];
	router.forEach(router=>{
		let{
			path,
			component,
			name,
			meta,
			iconCls,
			children
			// 批量的定义 相当于 let path = router.path;
		}=router;
		if(children&& children instanceof Array){
			children=formatRoutes(children);//递归调用//套娃处理
		}
		let fmRouter={
			path:path,
			name:name,
			iconCls:iconCls,
			meta:meta,
			children:children,//已经格式化完成了
			component(resolve){
				if(component.startsWith("Home")) {
					require(['../views/' + component + '.vue'], resolve);
				}
				else if(component.startsWith("Emp")){
					require(['../views/emp/'+component+'.vue'],resolve);
				}
				else if(component.startsWith("Per")){
					require(['../views/per/'+component+'.vue'],resolve);
				}
				else if(component.startsWith("Sal")){
					require(['../views/sal/'+component+'.vue'],resolve);
				}
				else if(component.startsWith("Sta")){
					require(['../views/sta/'+component+'.vue'],resolve);
				}
				else if(component.startsWith("Sys")){
					require(['../views/sys/'+component+'.vue'],resolve);
				}
			}
		}
		fmRoutes.push(fmRouter);
	})
	return fmRoutes;
}
