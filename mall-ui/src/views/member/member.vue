<template>
  <div class="mod-config">
      <div class="top-button">
            <el-button  type="primary" @click="addOrUpdateHandle()"   icon="el-icon-plus"  style="width: 100px" >新增</el-button>
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
      <el-table-column prop="id"   label="会员编号"  header-align="center"  align="center"  :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="levelId" header-align="center" align="center" label="会员等级"  :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="username" header-align="center" align="center" label="用户名" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="nickname" header-align="center" align="center" label="昵称" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="mobile" header-align="center" align="center" label="手机号码" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="email" header-align="center" align="center" label="邮箱" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="header" header-align="center" align="center" label="头像"  :show-overflow-tooltip="true">
        <template slot-scope="scope">
            <el-image style="width:40px; height:30px;" :src="scope.row.header" fit="contain"/>
        </template>
      </el-table-column>
      <el-table-column prop="gender" header-align="center" align="center" label="性别">
        <template slot-scope="scope">
　　　　　　　<span v-if="scope.row.gender == 0" >女</span>
　　　　　　　<span v-if="scope.row.gender == 1" >男</span>                　　　　　　　　
        </template>
      </el-table-column>
      <el-table-column prop="birth" header-align="center" align="center" label="生日" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="city" header-align="center" align="center" label="所在城市" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="job" header-align="center" align="center" label="职业" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="sign" header-align="center" align="center" label="个性签名" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="sourceType" header-align="center" align="center" label="用户来源" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="integration" header-align="center" align="center" label="积分"></el-table-column>
      <el-table-column prop="growth" header-align="center" align="center" label="成长值"></el-table-column>
      <el-table-column prop="status" header-align="center" align="center" label="启用状态">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status" 
            active-color="#13ce66"
            inactive-color="#ff4949"
            :active-value="1"
            :inactive-value="0"
            @change="updateSwitch(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" header-align="center" align="center" label="注册时间" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="200" label="操作">
        <template slot-scope="scope">
          <el-button  type="text" @click="addOrUpdateHandle(scope.row.id)"   icon="el-icon-edit" >编辑</el-button>
          <el-button type="text" size="small"  icon="el-icon-s-ticket">送券</el-button>
          <el-button type="text" size="small"  icon="el-icon-s-order">查订单</el-button>
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
import {addMember, delMember, fetchMemberList,getMember, putMember,getMemberSearch} from '@/api/member/member'
import AddOrUpdate from "./member-add-or-update";
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
      fetchMemberList(this.page).then((response) => {
        this.tableData = response.data.data.records
        this.page.total = response.data.data.total
      })
      this.dataListLoading = false
    },

    //刷新
    refreshChange(){
        this.getDataList();
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
      console.log("多选-->"+val)
    },
    //条件查询
    searchChange(){
        if(this.searchForm!= null){
            this.dataListLoading = true;
            getMemberSearch(Object.assign({
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
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },
    //启用状态开关
    updateSwitch(row){
       putMember(row).then((response) => {
        this.$message.success('启用状态更新成功')
        this.getList()
      })
    },

    
    
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