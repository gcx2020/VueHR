<template>
	<div>
		<div style="display: flex;justify-content: space-between">
			<el-button icon="el-icon-plus" type="primary" @click="showAddSalaryView">添加工资账套</el-button>
			<el-button icon="el-icon-refresh" type="success" @click="refresh"></el-button>
		</div>
		<div style="margin-top: 10px">
			<el-table :data="salaries" border stripe>
				<el-table-column type="selection" width="55"></el-table-column>
				<el-table-column width="150" prop="name" label="账套名称"></el-table-column>
				<el-table-column width="80" prop="basicSalary" label="基本工资"></el-table-column>
				<el-table-column width="80" prop="trafficSalary" label="交通补助"></el-table-column>
				<el-table-column width="80" prop="lunchSalary" label="午餐补助"></el-table-column>
				<el-table-column width="80" prop="bonus" label="奖金"></el-table-column>
				<el-table-column width="150" prop="createDate" label="启用时间"></el-table-column>
				<el-table-column label="养老金" align="center">
					<el-table-column width="80" prop="pensionPer" label="比率"></el-table-column>
					<el-table-column width="80" prop="pensionBase" label="基数"></el-table-column>
				</el-table-column>
				<el-table-column label="医疗保险" align="center">
					<el-table-column width="80" prop="medicalPer" label="比率"></el-table-column>
					<el-table-column width="80" prop="medicalBase" label="基数"></el-table-column>
				</el-table-column>
				<el-table-column label="公积金" align="center">
					<el-table-column width="80" prop="accumulationFundPer" label="比率"></el-table-column>
					<el-table-column width="80" prop="accumulationFundBase" label="基数"></el-table-column>
				</el-table-column>
				<el-table-column label="操作">
					<template slot-scope="scope">
						<el-button size="mini" @click="showEditSalaryView(scope.row)">编辑</el-button>
						<el-button size="mini" type="danger" @click="deleteSalary(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<el-dialog
			:title="dialogTitle"
			:visible.sync="dialogVisible"
			width="50%"
			:before-close="handleClose">
			<div style="display: flex;justify-content: space-around;align-items: center">
				<el-steps direction="vertical" :active="activeItemIndex">
					<el-step :title="itemName" v-for="(itemName,index) in salaryItemName" :key="index"></el-step>
				</el-steps>
				<el-input v-model="salary[title]" :placeholder="'请输入'+salaryItemName[index]+'...'"
						  v-for="(value,title,index) in salary" :key="index"
						  v-show="index==activeItemIndex" style="width: 200px">

				</el-input>
			</div>
			<span slot="footer" class="dialog-footer">
    <el-button @click="preStep">{{ activeItemIndex == 10 ? '取消' : '上一步' }}</el-button>
    <el-button type="primary" @click="nextStep">{{ activeItemIndex == 10 ? '完成' : '下一步' }}</el-button>
  </span>
		</el-dialog>
	</div>
</template>

<script>
export default {
	name: "SalSob",
	data() {
		return {
			dialogTitle: '添加工资账套',
			salaries: [],
			dialogVisible: false,
			activeItemIndex: 0,
			salaryItemName: [
				'账套名称',
				'基本工资',
				'交通补助',
				'午餐补助',
				'奖金',
				'养老金比率',
				'养老金基数',
				'医疗保险比率',
				'医疗保险基数',
				'公积金比率',
				'公积金基数',
			],
			salary: {
				name: null,
				basicSalary: null,
				trafficSalary: null,
				lunchSalary: null,
				bonus: null,
				pensionPer: null,
				pensionBase: null,
				medicalPer: null,
				medicalBase: null,
				accumulationFundPer: null,
				accumulationFundBase: null,
			},
		}
	},
	mounted() {
		this.initSalaries();
	},
	methods: {
		refresh(){
			this.initSalaries();
			this.$message({
				message: '刷新成功！',
				type: 'success'
			});
		},
		deleteSalary(data) {
			this.$confirm('此操作将永久删除【' + data.name + '】账套，是否继续？', '提示', {
				cancelButtonText: '取消',
				confirmButtonText: '确定',
			}).then(() => {
				this.deleteRequest("/salary/sob/" + data.id).then(resp => {
					if (resp) {
						this.initSalaries();
					}
				})
			}).catch(() => {
				this.$message.info("取消删除！")
			})
		},
		preStep() {
			if (this.activeItemIndex == 0) {
				this.dialogVisible = false;
				return;
			}
			if (this.activeItemIndex == 10) {
				//2.关闭对话框
				this.dialogVisible = false;
				return;
			}
			this.activeItemIndex--;
		},
		nextStep() {
			if (this.activeItemIndex == 10) {
				if (this.salary.id) {
					this.putRequest("/salary/sob/", this.salary).then(resp => {
						if (resp) {
							this.initSalaries();
							this.dialogVisible = false;
						}
					})
				} else {
					this.postRequest("/salary/sob/", this.salary).then(resp => {
						if (resp) {
							this.initSalaries();
							this.dialogVisible = false;
						}
					})
				}
				return;
			}
			this.activeItemIndex++;
		},
		showAddSalaryView() {
			//1.数据初始化
			this.salary = {
				name: null,
				basicSalary: null,
				trafficSalary: null,
				lunchSalary: null,
				bonus: null,
				pensionPer: null,
				pensionBase: null,
				medicalPer: null,
				medicalBase: null,
				accumulationFundPer: null,
				accumulationFundBase: null,
			};
			this.activeItemIndex = 0;
			this.dialogTitle = '添加工资账套';
			this.dialogVisible = true;
		},
		showEditSalaryView(data) {
			this.dialogTitle = '修改工资账套';
			this.dialogVisible = true;
			this.activeItemIndex = 0;
			this.salary.name = data.name;
			this.salary.basicSalary = data.basicSalary;
			this.salary.trafficSalary = data.trafficSalary;
			this.salary.lunchSalary = data.lunchSalary;
			this.salary.bonus = data.bonus;
			this.salary.creatDate = data.creatDate;
			this.salary.pensionPer = data.pensionPer;
			this.salary.pensionBase = data.pensionBase;
			this.salary.medicalPer = data.medicalPer;
			this.salary.medicalBase = data.medicalBase;
			this.salary.accumulationFundPer = data.accumulationFundPer;
			this.salary.accumulationFundBase = data.accumulationFundBase;
			this.salary.id = data.id;
		},
		initSalaries() {
			this.getRequest("/salary/sob/").then(resp => {
				if (resp) {
					this.salaries = resp;
				}
			})
		},
	}
}
</script>

<style scoped>

</style>
