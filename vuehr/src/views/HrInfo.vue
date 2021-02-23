<template>
	<div>
		<el-card class="box-card" style="width: 400px">
			<div slot="header" class="clearfix">
				<span>{{ hr.name }}</span>
			</div>
			<div>
				<div style="display: flex;justify-content: center">
					<img :src="hr.userface" alt="" style="width: 100px;height: 100px;border-radius: 50px"
						 title="点击修改用户图像">
				</div>
				<div>电话号码：
					<el-tag>{{ hr.telephone }}</el-tag>
				</div>
				<div>手机号码：
					<el-tag>{{ hr.phone }}</el-tag>
				</div>
				<div>居住地址：
					<el-tag>{{ hr.address }}</el-tag>
				</div>
				<div>用户标签：
					<el-tag v-for="(r,index) in hr.roles" :key="index" style="margin-right: 5px" type="success">
						{{ r.nameZh }}
					</el-tag>
				</div>
				<div style="display: flex;justify-content: space-around;margin-top: 10px">
					<el-button type="primary" @click="showUpdateHrInfoView">修改信息</el-button>
					<el-button type="danger" @click="showUpdatePasswordView">修改密码</el-button>
				</div>
			</div>
		</el-card>
		<el-dialog
			:visible.sync="dialogVisible"
			title="修改用户信息"
			width="30%">
			<div>
				<table>
					<tr>
						<td>
							<el-tag>用户昵称：</el-tag>
						</td>
						<td>
							<el-input v-model="hr2.name"></el-input>
						</td>
					</tr>
					<tr>
						<td>
							<el-tag>电话号码：</el-tag>
						</td>
						<td>
							<el-input v-model="hr2.telephone"></el-input>
						</td>
					</tr>
					<tr>
						<td>
							<el-tag>手机号码：</el-tag>
						</td>
						<td>
							<el-input v-model="hr2.phone"></el-input>
						</td>
					</tr>
					<tr>
						<td>
							<el-tag>用户地址：</el-tag>
						</td>
						<td>
							<el-input v-model="hr2.address"></el-input>
						</td>
					</tr>
				</table>
			</div>
			<span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="updateHrInfo">确 定</el-button>
  </span>
		</el-dialog>
		<el-dialog
			:visible.sync="passwordDialogVisible"
			title="修改用户信息"
			width="30%">
			<div>
				<el-form ref="ruleForm" :model="ruleForm" :rules="rules" class="demo-ruleForm" label-width="100px"
						 status-icon>
					<el-form-item label="请输入旧密码" prop="pass">
						<el-input v-model="ruleForm.oldPass" autocomplete="off" type="password"></el-input>
					</el-form-item>
					<el-form-item label="请输入新密码" prop="pass">
						<el-input v-model="ruleForm.pass" autocomplete="off" type="password"></el-input>
					</el-form-item>
					<el-form-item label="确认新密码" prop="checkPass">
						<el-input v-model="ruleForm.checkPass" autocomplete="off" type="password"></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
						<el-button @click="resetForm('ruleForm')">重置</el-button>
					</el-form-item>
				</el-form>
			</div>
		</el-dialog>
	</div>
</template>

<script>
export default {
	name: "HrInfo",
	data() {
		var validatePass = (rule, value, callback) => {
			if (value === '') {
				callback(new Error('请输入密码'));
			} else {
				if (this.ruleForm.checkPass !== '') {
					this.$refs.ruleForm.validateField('checkPass');
				}
				callback();
			}
		};
		var validatePass2 = (rule, value, callback) => {
			if (value === '') {
				callback(new Error('请再次输入密码'));
			} else if (value !== this.ruleForm.pass) {
				callback(new Error('两次输入密码不一致!'));
			} else {
				callback();
			}
		};
		return {
			hr: null,
			dialogVisible: false,
			passwordDialogVisible: false,
			hr2: null,
			ruleForm: {
				oldPass: '',
				pass: '',
				checkPass: '',
			},
			rules: {
				oldPass: [
					{validator: validatePass, trigger: 'blur'}
				],
				pass: [
					{validator: validatePass, trigger: 'blur'}
				],
				checkPass: [
					{validator: validatePass2, trigger: 'blur'}
				],
			}
		}
	},
	mounted() {
		this.initHr();
	},
	methods: {
		submitForm(formName) {
			this.$refs[formName].validate((valid) => {
				if (valid) {
					this.ruleForm.hrid = this.hr.id;
					this.putRequest("/hr/pass",this.ruleForm).then(resp=>{
						if(resp){
							this.getRequest("/logout");
							window.sessionStorage.removeItem("user");
							this.$store.commit('initRoutes',[]);
							this.$router.replace("/");
						}
					})
				} else {
					console.log('error submit!!');
					return false;
				}
			});
		},
		resetForm(formName) {
			this.$refs[formName].resetFields();
		},
		showUpdatePasswordView() {
			this.passwordDialogVisible = true;
		},
		updateHrInfo() {
			this.putRequest("/hr/info", this.hr2).then(resp => {
				if (resp) {
					this.dialogVisible = false;
					this.initHr();
				}
			})
		},
		showUpdateHrInfoView() {
			this.dialogVisible = true;
		},
		initHr() {
			this.getRequest("/hr/info").then(resp => {
				if (resp) {
					this.hr = resp;
					this.hr2 = Object.assign({}, this.hr);//深copy
					window.sessionStorage.setItem("user", JSON.stringify(resp));
					this.$store.commit("INIT_CURRENTHR", resp);
				}
			})
		}
	}
}
</script>


<style scoped>

</style>
