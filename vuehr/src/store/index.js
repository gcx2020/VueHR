import Vue from 'vue'
import Vuex from 'vuex'
import {getRequest} from "@/utils/api";
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { Notification } from 'element-ui';


Vue.use(Vuex)

const now = new Date();
const store = new Vuex.Store({
	state: {
		routes: [],
		sessions: {},
		hrs: [],
		currentSession: null,
		currentHr: JSON.parse(window.sessionStorage.getItem("user")),
		filterKey: '',
		stomp: null,
		isDot:{},
	},
	mutations: {
		INIT_CURRENTHR(state, hr) {
			state.currentHr = hr;
		},
		initRoutes(state, data) {
			state.routes = data;
		},
		changeCurrentSession(state, currentSession) {
			state.currentHr=JSON.parse(window.sessionStorage.getItem("user"));
			state.currentSession = currentSession;
			Vue.set(state.isDot,state.currentHr.username + '#' + currentSession.username,false);
		},
		addMessage(state, msg) {
			console.log(msg);
			let mss = state.sessions[state.currentHr.username + '#' + msg.to];
			if (!mss) {
				// state.sessions[state.currentHr.username+'#'+msg.to]=[];
				Vue.set(state.sessions, state.currentHr.username + '#' + msg.to, [])
			}
			state.sessions[state.currentHr.username + '#' + msg.to].push({
				content: msg.content,
				date: new Date(),
				self: !msg.notSelf
			});
		},
		INIT_DATA(state) {
			//浏览器本地的历史聊天记录可以在这里完成
			let data = localStorage.getItem('vue-chat-session');
			if (data) {
				state.sessions = JSON.parse(data);
			}
		},
		INIT_HR(state, data) {
			state.hrs = data;
		}
	},
	//做异步操作，同时提交mutatons。
	actions: {
		//建立Socket连接，服务端消息订阅。
		connect: function (context) {
			// console.log("开始建立Socket连接");
			context.state.stomp = Stomp.over(new SockJS('/ws/ep'));
			// console.log("建立stomp对象")
			context.state.stomp.connect({}, success => {
				context.state.stomp.subscribe('/user/queue/chat', msg => {
					let receiveMsg = JSON.parse(msg.body);
					if (!context.state.currentSession||receiveMsg.from != context.state.currentSession.username) {
						Notification.info({
							title: '【' + receiveMsg.fromNickName + '】发来一条消息',
							message: receiveMsg.content.length > 10 ? receiveMsg.content.substr(0, 10) : receiveMsg.current,
							position: 'bottom-right'
						})
						Vue.set(context.state.isDot,context.state.currentHr.username +'#'+ receiveMsg.from,true);
						
					}
					receiveMsg.notSelf = true;
					receiveMsg.to = receiveMsg.from;
					context.commit('addMessage', receiveMsg);//消息接收
				})
			}, error => {
			
			})
		},
		initData(context) {
			context.commit('INIT_DATA')
			getRequest("/chat/hrs").then(resp => {
				if (resp) {
					context.commit('INIT_HR', resp)
				}
			})
		}
	}
})


store.watch(function (state) {//当session发生变化时会自动存到vue-chat-session中
	return state.sessions
}, function (val) {
	console.log('CHANGE: ', val);
	localStorage.setItem('vue-chat-session', JSON.stringify(val));
}, {
	deep: true/*这个貌似是开启watch监测的判断,官方说明也比较模糊*/
})


export default store;
