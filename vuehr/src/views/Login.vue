<template>
	<div>
		<el-form
			:rules="rules"
			ref="loginForm"
			v-loading="loading"
			element-loading-text="正在登录中..."
			element-loading-spinner="el-icon-loading"
			element-loading-background="rgba(0, 0, 0, 0.8)"
			:model="loginForm"
			class="loginContainer">
			<h3 class="loginTitle">系统登录</h3>
			<el-form-item prop="username">
				<el-input size="normal" type="text" v-model="loginForm.username" auto-complete="off"
						  placeholder="请输入用户名"></el-input>
			</el-form-item>
			<el-form-item prop="password">
				<el-input size="normal" type="text" v-model="loginForm.password" auto-complete="off"
						  placeholder="请输入用户密码"></el-input>
			</el-form-item>
			<el-form-item prop="code">
				<el-input size="normal" type="text" v-model="loginForm.code" auto-complete="off"
						  placeholder="点击图片更换验证码" style="width: 250px" @keydown.enter.native="submitLogin"></el-input>
				<img :src="vcUrl" @click="updateVerifyCode" alt="">
			</el-form-item>
			<el-checkbox size="normal" v-model="checked" class="loginRember"></el-checkbox>
			<el-button size="normal" type="primary" style="width:100%;" v-on:click="submitLogin">登录</el-button>
		</el-form>
	</div>
</template>

<script>

export default {
	name: "Login",
	data() {
		return {
			vcUrl: '/verifyCode?time=' + new Date(),//加上时间戳，时间不一样就会更新
			loading: false,
			loginForm: {
				username: 'admin',
				password: '123',
				code: ''
			},
			checked: true,
			rules: {
				username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
				password: [{required: true, message: '请输入密码', trigger: 'blur'}],
				code: [{required: true, message: '请输入验证码', trigger: 'blur'}]


			}
		}
	},
	methods: {
		updateVerifyCode() {
			this.vcUrl = '/verifyCode?time=' + new Date();
		},
		submitLogin() {
			this.$refs.loginForm.validate((valid) => {
				if (valid) {
					// alert('submit');
					this.loading = true;
					this.postKeyValueRequest('/doLogin', this.loginForm).then(resp => {
						this.loading = false;
						if (resp) {
							this.$store.commit("INIT_CURRENTHR", resp.obj);//登录成功就初始化聊天页面
							// alert(JSON.stringify(resp))
							window.sessionStorage.setItem("user", JSON.stringify(resp.obj));
							let path = this.$route.query.redirect;
							this.$router.replace((path == '/' || path == undefined) ? '/home' : path);
							//如果没有定义或者为‘/’的话 就去home页 否则的话就去path，用户想去的页面
						}else{
							this.vcUrl = '/verifyCode?time=' + new Date();
						}
					})
				} else {
					this.$message.error('请重新输入');
					// console.log('error submit!!!');
					return false;
				}
			});
		}
	}
}
</script>

<style>
	.loginContainer {
		border-radius: 15px;
		background-clip: padding-box;
		margin: 180px auto;
		width: 350px;
		padding: 30px 30px 15px 15px;
		background: #fff;
		border: 1px solid #eaeaea;
		box-shadow: 0 0 25px #cacaca;
	}

	.loginTitle {
		margin: 15px auto 20px auto;
		text-align: center;
		color: #000000;
	}

	.loginRember {
		text-align: left;
		margin: 0px auto 15px auto;

	}

	.el-form-item__content{
		display: flex;
		align-items: center;
	}
</style>

