<template>
	<div style="width: 500px">
		<el-input
			placeholder="请输入部门名称进行搜索..."
			prefix-icon="el-icon-search"
			v-model="filterText">
		</el-input>

		<el-tree
			:data="deps"
			:props="defaultProps"
			:expand-on-click-node="false"
			:filter-node-method="filterNode"
			ref="tree">
			<span class="custom-tree-node" style="display:flex;justify-content: space-between;width:100% "
				  slot-scope="{node,data}">
        <span>{{ data.name }}</span>
        <span>
          <el-button
			  type="primary"
			  class="deBtn"
			  size="mini"
			  @click="() => showAddDepView(data)">
            添加部门
          </el-button>
          <el-button
			  type="danger"
			  class="deBtn"
			  size="mini"
			  @click="() => deleteDep(data)">
            删除部门
          </el-button>
        </span>
      </span>
		</el-tree>
		<el-dialog
			title="添加部门"
			:visible.sync="dialogVisible"
			width="30%">
			<div>
				<table>
					<tr>
						<td>
							<el-tag>上级部门</el-tag>
						</td>
						<td>
							{{ pname }}
						</td>
					</tr>
					<tr>
						<td>
							<el-tag>
								部门名称
							</el-tag>
						</td>
						<td>
							<el-input v-model="dep.name" placeholder="请输入部门名称"></el-input>
						</td>
					</tr>
				</table>
			</div>
			<span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doAddDep">确 定</el-button>
  </span>
		</el-dialog>
	</div>
</template>

<script>
export default {
	name: "DepMana",
	data() {
		return {
			dialogVisible: false,
			dep: {
				name: '',
				parentId: -1,
			},
			pname: '',
			filterText: '',
			deps: [],
			defaultProps: {
				children: 'children',
				label: 'name'
			}
		}
	},
	watch: {//监控器当值发生变化时，方法就会调用，val为最新的值
		filterText(val) {
			this.$refs.tree.filter(val);
		}
	},
	mounted() {
		this.initDeps();
	},
	methods: {//methods是属性用“：”
		removeDepFromDeps(p, deps, id) {
			for (let i = 0; i < deps.length; i++) {
				let d = deps[i];
				if (d.id == id) {
					deps.splice(i, 1);//移除数组的一个的元素的操作
					if (deps.length == 0) {
						p.parent = false;
					}
					return;
				} else {
					this.removeDepFromDeps(d, d.children, id);
				}
			}
		},
		addDep2Deps(deps, dep) {
			for (let i = 0; i < deps.length; i++) {
				let d = deps[i];
				if (d.id == dep.parentId) {
					d.children = d.children.concat(dep);
					d.parent = true;
					return;
				} else {
					this.addDep2Deps(d.children, dep);//递归操作
				}
			}
		},
		initDep() {
			this.dep = {
				name: '',
				parentId: -1,
			}
			this.pname = ''
		},
		doAddDep() {
			this.postRequest("/system/basic/department/", this.dep).then(resp => {
				if (resp) {
					this.addDep2Deps(this.deps, resp.obj);
					this.dialogVisible = false;
					//初始化页面
					this.initDep();
				}
			})
		},
		showAddDepView(data) {
			this.pname = data.name;
			this.dep.parentId = data.id;
			this.dialogVisible = true;
			// console.log(data);
		},
		deleteDep(data) {
			if (data.parent) {
				this.$message.error("父部门不可以直接删除！");
			} else {
				this.$confirm('此操作将永久删除【' + data.name + '】部门, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.deleteRequest("/system/basic/department/" + data.id).then(resp => {
						if (resp) {
							this.removeDepFromDeps(null, this.deps, data.id);//动态的移除被删除的一项。
						}
					})
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});
			}
		},
		initDeps() {
			this.getRequest("/system/basic/department/").then(resp => {
				if (resp) {
					this.deps = resp;//初始化后端数据
				}
			})
		},
		filterNode(value, data) {
			if (!value) return true;
			return data.name.indexOf(value) !== -1;
		}
	}
}
</script>

<style>
.deBtn {
	padding: 2px;
}

</style>
