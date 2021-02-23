<template>
	<div>
		<div>
			<el-input
				size="small"
				class="addPosInput"
				style="width: 300px;margin-right: 8px"
				placeholder="添加职位"
				prefix-icon="el-icon-plus"
				@keydown.enter.native="addPosition"
				v-model="pos.name">
			</el-input>
			<el-button icon="el-icon-plus" size="small" type="primary" @click="addPosition">添加</el-button>
		</div>
		<div class="posManaTable">
			<el-table
				:data="positions"
				border
				@selection-change="handleSelectionChange"
				size="small"
				stripe
				style="width: 70%">
				<el-table-column
					type="selection"
					width="55">
				</el-table-column>
				<el-table-column
					prop="id"
					label="编号"
					width="55">
				</el-table-column>
				<el-table-column
					prop="name"
					label="职位名称"
					width="180">
				</el-table-column>
				<el-table-column
					prop="createDate"
					label="创建时间"
					width="150">
				</el-table-column>
				<el-table-column
					label="是否启用">
					<template slot-scope="scope">
						<el-tag size="small" type="success" v-if="scope.row.enabled">已启用</el-tag>
						<el-tag size="small" type="danger" v-else>未启用</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="操作" >
					<template slot-scope="scope">
						<el-button
							size="mini"
							@click="showEditView(scope.$index, scope.row)">编辑</el-button>
						<el-button
							size="mini"
							type="danger"
							@click="handleDelete(scope.$index, scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-button @click="deleteMany" type="danger" size="small" style="margin-top: 8px" :disabled="multipleSelection.length==0">
				批量删除</el-button>
		</div>
		<el-dialog
			title="修改职位"
			:visible.sync="dialogVisible"
			width="30%">
			<div>
				<div>
					<el-tag>职位名称</el-tag>
					<el-input class="updatePosInout" size="small" v-model="updatePos.name"></el-input>
				</div>
				<div>
					<el-switch style="margin-top: 8px"
						v-model="updatePos.enabled"
						active-text="启用"
						inactive-text="禁用">
					</el-switch>
				</div>

			</div>
			<span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible = false">取 消</el-button>
    <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
  </span>
		</el-dialog>
	</div>
</template>

<script>
export default {
	name: "PosMana",
	data(){
		return{
			pos:{
				name:''
			},
			multipleSelection:[],
			dialogVisible:false,
			updatePos:{
				name:'',
				enabled:false
			},
			positions: []
		}
	},
	mounted() {
		this.initPositions();//组件初始化的时候就可以调用initPositions
	},
	methods:{
		deleteMany(){
			this.$confirm('此操作将永久删除【'+this.multipleSelection.length+'】条记录, 是否继续?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				let ids ='?';
				this.multipleSelection.forEach(item=>{
					ids+='ids='+item.id+'&';
				})
				this.deleteRequest("/system/basic/pos/"+ids).then(resp=>{
					if(resp){
						this.initPositions();
					}
				});
			}).catch(() => {
				this.$message({
					type: 'info',
					message: '已取消删除'
				});
			});
		},
		handleSelectionChange(val){
			this.multipleSelection=val;
			console.log(val);
		},
		addPosition(){
			if(this.pos.name){
				this.postRequest("/system/basic/pos/",this.pos).then(resp=>{
					if(resp){
						this.initPositions();
						this.pos.name='';
					}
				})
			}else{
				this.$message.error('错了哦，职位名称为空');
			}
		},
		showEditView(index,data){
			// this.updatePos=data;
			Object.assign(this.updatePos,data);//拷贝属性
			this.dialogVisible=true;
		},
		doUpdate(){
			this.putRequest("/system/basic/pos/",this.updatePos).then(resp=>{
				if(resp){
					this.initPositions();
					this.updatePos.name='';
					this.dialogVisible=false;
				}
			})
		},
		handleDelete(index,data){
			this.$confirm('此操作将永久删除['+data.name+']职位, 是否继续?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				this.deleteRequest("/system/basic/pos/"+data.id).then(resp=>{
					if(resp){
						this.initPositions();
					}
				})
			}).catch(() => {
				this.$message({
					type: 'info',
					message: '已取消删除'
				});
			});

		},
		initPositions(){//方法要自动调用，自动挂载
			this.getRequest("/system/basic/pos/").then(resp=>{
				if(resp){
					this.positions = resp;
				}
			})
		}
	}
}
</script>

<style >
	.updatePosInout{
		width: 200px;
		margin-left: 8px;
	}
	.addPosInput{
		width: 300px;
		margin-right: 8px;
	}
	.posManaTable{
		margin-top: 10px;
	}
</style>
