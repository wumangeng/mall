
<template>
  <div >
    <!-- <el-button  class="app_button_add"  type="success"  icon="el-icon-plus"  size="mini"  @click="appendGen" plain>添加</el-button> -->
    <div class="down-tree">
      <el-input placeholder="输入关键字进行过滤" v-model="filterText"></el-input>

      <el-tree
      :data="menus"
      :props="defaultProps"
      show-checkbox
      node-key="catId"
      ref="menuTree"
      :default-expanded-keys="expandedKeys"
      :filter-node-method="filterNode"
      :highlight-current="true"
      style="position: relative"
    >
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span class="app_button_area">
          <el-button
            class="app_button_add"
            v-if="node.level <= 2"
            type="success"
            icon="el-icon-plus"
            size="mini"
            @click="() => append(data)"
            plain
            >添加</el-button
          >
          <el-button
            class="app_button_edit"
            type="primary"
            icon="el-icon-edit"
            size="mini"
            @click="() => edit(data)"
            plain
            >编辑</el-button
          >
          <el-button
            class="app_button_delete"
            v-if="node.childNodes.length == 0"
            type="danger"
            icon="el-icon-delete"
            size="mini"
            @click="() => remove(node, data)"
            plain
          >
            删除</el-button
          >
        </span>
      </span>
    </el-tree>
    </div>
    

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="30%">
      <el-form ref="form" :model="category" label-width="80px">
        <el-form-item label="分类名称">
          <el-input v-model="category.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="category.icon" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="计量单位">
          <el-input
            v-model="category.productUnit"
            auto-complete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitData">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  listWithTree,
  deleteBatch,
  saveCategory,
  editCategory,
  getCategoryById,
} from "@/api/product/category";

export default {
  data() {
    return {
      filterText: "",
      menus: [],
      expandedKeys: [], //删除分页后默认展开菜单
      dialogVisible: false, //对话框默认关闭
      dialogType: "", //对话框按钮区分（添加和编辑）
      dialogTitle: "",

      category: {
        //表单对象
        catId: 0,
        name: "",
        parentCid: 0,
        catLevel: 0,
        showStatus: 1,
        sort: 0,
        icon: "",
        productUnit: "",
        productCount: 0,
      },

      defaultProps: {
        children: "children",
        label: "name",
      },
    };
  },
    //监控data中的数据变化
    watch: {
        filterText(val) {
            this.$refs.menuTree.filter(val);
        }
    },

  methods: {
     //树节点过滤
    filterNode(value, data) {
        if (!value) return true;
        return data.name.indexOf(value) !== -1;
    },
    // 查询树形分类列表
    getMenu() {
      listWithTree().then((response) => {
        this.menus = response.data.data;
      });
    },
    appendGen(){
      this.dialogTitle = "添加分类";
      this.dialogType = "add";
      this.dialogVisible = true;
      this.category.parentCid = 0;
      this.category.catLevel = 0;
      this.category.catId = null;
      this.category.name = "";
      this.category.icon = "";
      this.category.productUnit = "";
      this.category.sort = 0;
      this.category.showStatus = 1;
    },
    //添加分类
    append(data) {
      this.dialogTitle = "添加分类";
      this.dialogType = "add";
      this.dialogVisible = true;
      this.category.parentCid = data.catId;
      this.category.catLevel = data.catLevel * 1 + 1;
      this.category.catId = null;
      this.category.name = "";
      this.category.icon = "";
      this.category.productUnit = "";
      this.category.sort = 0;
      this.category.showStatus = 1;
    },

    edit(data) {
      this.dialogTitle = "修改分类";
      this.dialogType = "edit";
      this.dialogVisible = true;
      getCategoryById(data.catId).then((response) => {
        this.category = { ...response.data.data };
      });
      this.category.name = data.name;
    },

    submitData() {
      //添加分类
      if (this.dialogType == "add") {
        saveCategory(this.category).then((response) => {
          this.$message({
            message: `分类【${this.category.name}】添加成功！`,
            type: "success",
          });
        });
      }
      //编辑分类
      if (this.dialogType == "edit") {
        var { catId, name, icon, productUnit } = this.category;
        editCategory({ catId, name, icon, productUnit }).then(() => {
          this.$message({
            message: `分类【${name}】修改成功！`,
            type: "success",
          });
        });
      }
      this.dialogVisible = false;
      this.getMenu();
      //默认展开父级分类
      this.expandedKeys = [this.category.parentCid];
    },

    //删除分类
    remove(node, data) {
      //弹出消息提示框
      this.$confirm(
        `此操作将永久删除【${data.name}】分类, 是否删除?`,
        "删除确认",
        {
          confirmButtonText: "确定删除",
          cancelButtonText: "取消删除",
          type: "warning",
        }
      )
        .then(() => {
          //删除
          var catIdList = [data.catId];
          deleteBatch(catIdList).then((response) => {
            this.$message({
              message: `【${data.name}】分类删除成功！`,
              type: "success",
            });
            //刷新
            this.getMenu();
            //默认展开父级分类
            this.expandedKeys = [node.parent.data.catId];
          });
        })
        .catch(() => {});
    },
  },

  created() {
    this.getMenu();
  },
};
</script>

<style lang="scss" scoped>

.down-tree{
    flex: 1;//父元素采用flex布局
    max-width:80%;//设置最小宽度
    height:678px;
    background:rgba(245,248,250,1);
    border-radius:3px;
    border:1px solid rgba(211,219,222,1);
    margin-left: 12px;
    padding: 14px;
    //设置滚动条高度，隐藏横向滚动条
    /deep/.el-scrollbar{
      height: 578px;
      .el-scrollbar__wrap{
        overflow-x: hidden;
      }
    }
}
.app_button_area {
  text-align: center;
  position: absolute;
  right: 50%;
}
.app_button_add {
  position: absolute;
  left: -70px;
}
.app_button_edit {
  position: absolute;
  left: 0px;
}
.app_button_delete {
  position: absolute;
  left: 80px;
}
</style>
