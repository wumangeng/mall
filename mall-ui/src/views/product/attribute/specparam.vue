<!--   规格属性页面  -->
<template>
  <div class="execution">
    <basic-container>
      <el-row :gutter="20">
        <el-col :span="6">
          <CategoryTree @handleTreeNodeClick="handleTreeNodeClick"> </CategoryTree>
        </el-col>
        <el-col :span="18">
          <div class="top-button">
            <el-button type="primary"  icon="el-icon-plus"  style="width: 100px"  @click="() => append()"   round>新增</el-button>
            <el-form :inline="true"    class="search-form-inline"   style="padding-top: 15px; margin-left: auto; margin-right: auto">
              <el-form-item label="">
                <el-input v-model="searchParam"   placeholder=""></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary"  icon="el-icon-search"   @click="searchChange()">查询</el-button>
              </el-form-item>
            </el-form>
            <el-button icon="el-icon-refresh"   @click="refreshChange()"  round />
          </div>

          <el-table :data="tableData" border style="width: 100%" fit stripe highlight-current-row>
            <el-table-column prop="attrId" label="属性编号" header-align="center" align="center" show-overflow-tooltip>  </el-table-column>

            <el-table-column prop="attrName" label="属性名" header-align="center" align="center" show-overflow-tooltip>   </el-table-column>

            <el-table-column prop="icon" label="图标" align="center" width="70">
              <template slot-scope="scope">
                <i :class="scope.row.icon" />
              </template>
            </el-table-column>

            <el-table-column prop="valueSelect" label="可选值" header-align="center" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                    <el-tooltip placement="top">
                        <div slot="content">
                        <span v-for="(i,index) in scope.row.valueSelect.split(';')" :key="index">{{i}}<br/></span>
                        </div>
                        <el-tag>{{scope.row.valueSelect.split(";")[0]+" ..."}}</el-tag>
                    </el-tooltip>
                </template>
            </el-table-column>

            <el-table-column prop="attrType" label="属性类型" header-align="center" align="center">
              <template slot-scope="scope">
　　　　　　　　　　<span v-if="scope.row.attrType == 0" >销售属性</span>
　　　　　　　　　　<span v-if="scope.row.attrType == 1" >规格参数</span>                　　　　　　　　
              </template>
            </el-table-column>

            <el-table-column prop="categoryName"  label="所属分类"  header-align="center" align="center" show-overflow-tooltip> </el-table-column>

            <el-table-column prop="attrGroupName" label="所属分组" header-align="center" align="center" show-overflow-tooltip>  </el-table-column>
           
            <el-table-column prop="searchType" label="是否检索" header-align="center" align="center">
              <template slot-scope="scope">
                <i  class="el-icon-success" style="color: green;" v-if="scope.row.searchType==1"></i>
                <i class="el-icon-error" v-else></i>
              </template>
            </el-table-column>

            <el-table-column prop="valueSelect" label="值类型" header-align="center" align="center" >
                <template slot-scope="scope">
                    <el-tag :type="(scope.row.valueType == '0' ? 'success' : '' )" size="mini">
                        {{ scope.row.valueType == '0' ? '单选' : '多选' }}
                    </el-tag>
                </template>
            </el-table-column>

            <el-table-column prop="showDesc" label="快速展示" header-align="center" align="center">
             <template slot-scope="scope">
                <i  class="el-icon-success" style="color: green;" v-if="scope.row.showDesc==1"></i>
                <i class="el-icon-error" v-else></i>
             </template>
            </el-table-column>

            <el-table-column prop="enable" label="启用状态" header-align="center" align="center">
              <template slot-scope="scope">
                <i  class="el-icon-success" style="color: green;" v-if="scope.row.enable==1"></i>
                <i class="el-icon-error" v-else></i>
             </template>
            </el-table-column>

            <el-table-column label="操作" width="200"  align="center">
              <template slot-scope="scope">
                <el-button type="text"   size="small" icon="el-icon-edit"   @click="handleUpdate(scope.row.attrId)">编辑</el-button>
                <el-button type="text"  icon="el-icon-delete"  size="small"  @click="rowDel(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页 -->
        <el-pagination
            @size-change="sizeChangeHandle"
            @current-change="currentChangeHandle"
            :current-page="pageIndex"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="pageSize"
            :total="totalPage"
            style="padding: 30px 0; text-align: center;"
            layout="total, sizes, prev, pager, next, jumper"
        ></el-pagination>

          <!-- 弹框 -->
          <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="40%">
            <el-form ref="form" :model="Attr" label-width="100px">
              <el-form-item label="属性名">
                <el-input v-model="Attr.attrName"  auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="图标"    prop="icon">
                <avue-input-icon v-model="Attr.icon"  :icon-list="iconList"></avue-input-icon>
              </el-form-item>
              <el-form-item label="可选值">
                <!-- <el-input v-model="Attr.valueSelect"  auto-complete="off"></el-input> -->
                <el-select
                    v-model="Attr.valueSelect"
                    multiple
                    filterable
                    allow-create
                    placeholder="请输入内容"
                ></el-select>
              </el-form-item>
              <el-form-item label="属性类型">
                <el-select v-model="Attr.attrType"   placeholder="请选择属性类型" style="width:300px">
                  <el-option label="规格参数" :value="1"></el-option>
                  <el-option label="销售属性" :value="0"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="所属分类">
                <el-cascader v-model="Attr.catelogIdPath"
                             ref="cascader"
                             filterable
                             :options="categoryList"
                             placeholder="试试搜索"
                             :props="props"
                             @visible-change="hideChange($event)"
                             style="width: 300px">
                </el-cascader>
              </el-form-item>
            <el-form-item label="所属分组">
                <el-select v-model="Attr.attrGroupId" placeholder="请先选择分类后选择分组"  style="width: 300px">
                    <el-option v-for="item in attrGroupList" :key="item.attrGroupId" 
                    :label="item.attrGroupName" :value="item.attrGroupId">
                    </el-option>
                </el-select>
            </el-form-item>
              <el-form-item label="值类型">
                <el-switch v-model="Attr.valueType"
                           active-color="#13ce66"
                           active-text="多选"
                           inactive-text="单选"
                           :active-value="1"
                           :inactive-value="0">
                </el-switch>
              </el-form-item>
              <el-form-item label="是否检索">
                <el-switch v-model="Attr.searchType"
                           active-color="#13ce66"
                           inactive-color="#ff4949"
                           :active-value="1"
                           :inactive-value="0">
                </el-switch>
              </el-form-item>
          
              <el-form-item label="快速展示">
                <el-switch v-model="Attr.showDesc"
                           active-color="#13ce66"
                           inactive-color="#ff4949"
                           :active-value="1"
                           :inactive-value="0">
                </el-switch>
              </el-form-item>
              <el-form-item label="启用状态">
                <el-switch v-model="Attr.enable"
                           active-color="#13ce66"
                           inactive-color="#ff4949"
                           :active-value="1"
                           :inactive-value="0">
                </el-switch>
              </el-form-item>
            </el-form>
            <span slot="footer"
                  class="dialog-footer"
                  style="">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary"
                         @click="submitData">确 定</el-button>
            </span>
          </el-dialog>
        </el-col>
      </el-row>
    </basic-container>
  </div>
</template>

<script>
import CategoryTree from '../../common/categorytree'
import iconList from '@/const/iconList'
import { getAttrGroupList} from '@/api/product/attrgroup'
import { listWithTree } from '@/api/product/category'
import {
  getBSAttrList,getListByCatelogId,searchList,
  getAttrById, addObj, putObj, delObj,
} from '@/api/product/attr'

export default {
  components: { CategoryTree: CategoryTree },
  data() {
    return {
    props: {
      attrtype: {
        type: Number,
        default: 0
      }
    },
      catId: 0,
      searchParam: '',
      tableData: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dialogVisible: false, //对话框默认关闭
      dialogType: '', //对话框按钮区分（添加和编辑）
      dialogTitle: '',
      // 图标
      iconList: iconList,
      Attr: {
        attrId: 0,
        attrName: '',
        searchType: 1,
        icon: '',
        valueSelect: '',
        attrType: 0,
        enable: 1,
        catelogId: 0,
        categoryName: '',
        attrGroupId: undefined,
        attrGroupName: '',
        showDesc: 1,
        valuetype: 0,

        catelogIdPath: [],
      },
      attrGroupList: [],
      categoryList: [],
      props: {
        value: 'catId',
        label: 'name',
        children: 'children',
      },
    }
  },
  created() {
    this.getDataList()
    this.getMenu()
  },
  methods: {

    // 查询树形分类列表
    getMenu() {
      listWithTree().then((response) => {
        this.categoryList = response.data.data
      })
    },
    //查询所有记录
    getDataList() {
    let type = this.attrtype == 1 ? "sale" : "base";
      getBSAttrList(type,this.catId,{
            current: this.pageIndex,
            size: this.pageSize,
            searchParam: this.searchParam
          }).then((response) => {
              console.log(response.data)
        this.tableData = response.data.data.list
        this.totalPage = response.data.data.totalCount
      })
    },
    //节点被点击时触发
    handleTreeNodeClick(data, node, component) {
      if (node.level == 3) {
        this.catId = data.catId
        let current={current: this.pageIndex}
        let size={size: this.pageSize}
        let searchParam= {searchParam: this.catId}
       getListByCatelogId(Object.assign(current,size,searchParam)).then(response=>{
            this.tableData = response.data.data.records
           this.totalPage = response.data.data.total
       })
      }
    },
    attrType(type) {
      if (type == 0) {
        return '销售属性'
      } else {
        return '规格参数'
      }
    },
    append() {
      this.dialogTitle = '添加规格参数'
      this.dialogType = 'add'
      this.dialogVisible = true
      this.Attr.attrId = ''
      this.Attr.attrName = ''
      this.Attr.searchType = 1
      this.Attr.icon = ''
      this.Attr.valueSelect = ''
      this.Attr.attrType = 1
      this.Attr.enable = 1
      this.Attr.catelogId = 0
      this.Attr.showDesc = 1
      this.Attr.attrGroupId= ''
      this.Attr.catelogIdPath = []
    },
    searchChange() {
        let current={current: this.pageIndex}
        let size={size: this.pageSize}
        let searchParam= {searchParam: this.searchParam}
      searchList(Object.assign(current,size,searchParam)).then((response) => {
        this.tableData = response.data.data.records
        this.totalPage = response.data.data.total
      })
    },
    refreshChange() {
      this.getDataList()
    },
    // 每页数
    sizeChangeHandle(val) {
        this.pageSize = val;
        this.pageIndex = 1;
        this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
        this.pageIndex = val;
        this.getDataList();
    },
    // 多选
    selectionChangeHandle(val) {
        this.dataListSelections = val;
    },
    handleUpdate(attrId) {
      this.dialogTitle = '修改规格参数'
      this.dialogType = 'edit'
      this.dialogVisible = true
      getAttrById(attrId).then((response) => {
        this.Attr = response.data.data
        this.Attr.valueSelect= response.data.data.valueSelect.split(";");
        let catelogId = this.Attr.catelogIdPath[this.Attr.catelogIdPath.length-1]
        getAttrGroupList(catelogId).then(response=>{
                this.attrGroupList=response.data.data;
        })
      })
    },
    rowDel(row) {
      this.$confirm('是否确认删除:' + row.name, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(function () {
          return delObj(row.attrId)
        })
        .then((data) => {
          this.$message.success('删除成功')
          this.getDataList()
        })
    },
    submitData() {
      //添加
      if (this.dialogType == 'add') {
        this.Attr.catelogId =  this.Attr.catelogIdPath[this.Attr.catelogIdPath.length - 1]
        this.Attr.valueSelect = this.Attr.valueSelect.join(";"),
        addObj(this.Attr)
          .then((data) => {
            this.$message.success('添加成功')
            this.getDataList()
          })
          .catch(() => {})
      }
      //修改
      if (this.dialogType == 'edit') {
        putObj(this.Attr)
          .then((data) => {
            this.$message.success('修改成功')
            this.getDataList()
          })
          .catch(() => {})
      }
      this.dialogVisible = false
    },

    //级联选择器隐藏时触发
    hideChange : function(callback){  
      //只有回调参数为false时才触发 
        if(!callback){
            //被选中的节点
            let catelogId = this.$refs['cascader'].getCheckedNodes()[0].value
            getAttrGroupList(catelogId).then(response=>{
                this.attrGroupList=response.data.data;
            })
        }
    },
  


  },
}
</script>

<style lang="scss" scoped>
.top-button {
  display: flex;
  height: 30;
  justify-content: left;
  align-items: center;
}
</style>
