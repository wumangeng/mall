<template>
  <div class="mod-config">
    <div class="top-button">
        <el-button  type="primary" @click="addOrUpdateHandle()"   icon="el-icon-plus"  style="width: 100px" >新增</el-button>
         <el-button type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0"  icon="el-icon-delete" style="width: 100px" >批量删除</el-button>
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
      style="width: 100%;"
    >
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="id"   label="等级编号"  header-align="center"  align="center"  :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="name" header-align="center" align="center" label="等级名称"></el-table-column>
      <el-table-column prop="growthPoint" header-align="center" align="center" label="所需成长值"></el-table-column>
      <el-table-column prop="defaultStatus" header-align="center" align="center" label="默认等级">
        <template slot-scope="scope">
          <i  class="el-icon-success" style="color: green;" v-if="scope.row.defaultStatus==1"></i>
          <i class="el-icon-error" v-else></i>
        </template>
      </el-table-column>
      <el-table-column prop="freeFreightPoint" header-align="center" align="center" label="免运费标准"></el-table-column>
      <el-table-column
        prop="commentGrowthPoint"
        header-align="center"
        align="center"
        label="每次评价获取的成长值"
      ></el-table-column>
      <el-table-column label="特权" header-align="center" align="center">
        <el-table-column prop="priviledgeFreeFreight" header-align="center" align="center" label="免邮特权" >
          <template slot-scope="scope">
            <i  class="el-icon-success" style="color: green;" v-if="scope.row.priviledgeFreeFreight==1"></i>
            <i class="el-icon-error" v-else></i>
          </template>
        </el-table-column>
        <el-table-column prop="priviledgeMemberPrice" header-align="center" align="center" label="会员价格特权" >
          <template slot-scope="scope">
            <i  class="el-icon-success" style="color: green;" v-if="scope.row.priviledgeMemberPrice==1"></i>
            <i class="el-icon-error" v-else></i>
          </template>
        </el-table-column>
        <el-table-column prop="priviledgeBirthday" header-align="center" align="center" label="生日特权"  >
          <template slot-scope="scope">
            <i  class="el-icon-success" style="color: green;" v-if="scope.row.priviledgeBirthday==1"></i>
            <i class="el-icon-error" v-else></i>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column prop="note" header-align="center" align="center" label="备注"></el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)" icon="el-icon-edit" >编辑</el-button>
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
import {fetchMemberLevelList, getMemberLevelSearch, addMemberLevel,getMemberLevel,
        delMemberLevel,putMemberLevel,delBatchLevel
        } from '@/api/member/memberLevel'

import AddOrUpdate from "./memberlevel-add-or-update";

export default {
  data() {
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
    };
  },
  components: {
    AddOrUpdate
  },
  created() {
    this.getDataList();
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      fetchMemberLevelList(this.page).then((response) => {
        this.tableData = response.data.data.records
        this.page.total = response.data.data.total
      });
      this.dataListLoading = false;
    },
     //刷新
    refreshChange(){
        this.getDataList();
    },
     //条件查询
    searchChange(){
        if(this.searchForm!= null){
            this.dataListLoading = true;
            getMemberLevelSearch(Object.assign({
                    current: this.page.currentPage,
                    size: this.page.pageSize,
                    searchParam: this.searchForm
                })).then(response=>{
                     this.tableData = response.data.data.list
                     this.page.total = response.data.data.totalCount
                })
            this.dataListLoading = false;
        }else{
            this.getDataList();
        }
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },
    // 删除
    deleteHandle(id) {
      let ids = id  ? [id]  : this.dataListSelections.map(item => {  return item.id;  });
      this.$confirm(
        `确定对[id=${ids.join(",")}]进行[${id ? "删除" : "批量删除"}]操作?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      ).then(() => {
        delBatchLevel( ids).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: "操作成功",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
              }
            });
          } else {
            this.$message.error(data.msg);
          }
        });


      });
    }
  }
};
</script>

<style lang="scss" scoped>
.top-button {
  display: flex;
  height: 30;
  justify-content: left;
  align-items: center;
}
</style>
