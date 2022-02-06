<template>
  <div class="mod-config">
    <div class="top-button">
        <el-button  type="primary" @click="addOrUpdateHandle()"   icon="el-icon-plus"  style="width: 100px" >新增</el-button>
        <el-button  type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0"  style="width: 100px" icon="el-icon-delete-solid">批量删除</el-button>
        <el-form :inline="true" class="search-form-inline" style="padding-top: 15px; margin-left: auto; margin-right: auto">
            <el-form-item label="条件检索：">
            <el-input v-model="searchForm"   placeholder="" style="width:300px"></el-input>
            </el-form-item>
            <el-form-item>
            <el-button type="primary"  icon="el-icon-search"   @click="searchChange">查询</el-button>
            </el-form-item>
        </el-form>
        <el-button icon="el-icon-refresh"   @click="refreshChange()"  round />
      </div>

    <el-table
      :data="tableData"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column  type="selection" header-align="center"  align="center" > </el-table-column>
      <el-table-column  prop="id" header-align="center" align="center"   label="仓库编号"> </el-table-column>
      <el-table-column  prop="name" header-align="center" align="center" label="仓库名" :show-overflow-tooltip="true"> </el-table-column>
      <el-table-column  prop="address" header-align="center" align="center" label="仓库地址" :show-overflow-tooltip="true"> </el-table-column>
      <el-table-column  prop="areacode"  header-align="center"  align="center"  label="区域编码"> </el-table-column>
      <el-table-column  prop="warePrincipal"  header-align="center"  align="center"  label="仓库负责人"> </el-table-column>
      <el-table-column  prop="principalPhone"  header-align="center"  align="center"  label="负责人号码" :show-overflow-tooltip="true"> </el-table-column>

      <el-table-column fixed="right"   header-align="center" align="center"   label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)" icon="el-icon-edit">编辑</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)" icon="el-icon-delete">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="block">
    <el-pagination @current-change="getDataList"
                    :current-page="page.currentPage"
                    :page-size="page.pageSize"
                    style="padding: 30px 0; text-align: center"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="page.total">
    </el-pagination>
    </div>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './info-add-or-update'
  import { searchList,delObjBatch,delObj } from '@/api/ware/info'

  export default {
    data () {
      return {
        searchForm: '',
        tableData: [],
        page: {
            total: 0, // 总页数
            currentPage: 1, // 当前页数
            pageSize: 20, // 每页显示多少条
        },
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    created () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
    
        searchList(Object.assign({
                        currentPage: this.page.currentPage,
                        pageSize: this.page.pageSize
            })).then((response) => {
                this.tableData = response.data.data.list
                this.page.total = response.data.data.totalCount
            })
        this.dataListLoading = false
      },
        //条件查询
        searchChange(){
            if(this.searchForm != null){
                this.dataListLoading = true;
                searchList(Object.assign({
                        currentPage: this.page.currentPage,
                        pageSize: this.page.pageSize,
                        searchParam: this.searchForm
                    })).then(response=>{
                        this.tableData = response.data.data.list
                        this.page.total = response.data.data.totalCount
                        this.searchParam= ''
                    })
                this.dataListLoading = false;
            }else{
                this.getDataList();
            }
        },
    
      //刷新
      refreshChange(){
        this.getDataList();
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })

        this.$confirm(`确定进行删除操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {

            delObjBatch(ids).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })

        })
      }
    }
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
