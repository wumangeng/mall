<!-- 采购需求 -->
<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item label="仓库">
        <el-select style="width:200px;" v-model="dataForm.wareId" placeholder="请选择仓库" clearable>
          <el-option :label="w.name" :value="w.id" v-for="w in wareList" :key="w.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-select style="width:200px;" v-model="dataForm.status" placeholder="请选择状态" clearable>
          <el-option label="新建" :value="0"></el-option>
          <el-option label="已分配" :value="1"></el-option>
          <el-option label="正在采购" :value="2"></el-option>
          <el-option label="已完成" :value="3"></el-option>
          <el-option label="采购失败" :value="4"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="条件检索">
        <el-input style="width:200px;" v-model="dataForm.searchParam" placeholder="检索参数" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getDataList()" style="width:100px" icon="el-icon-search">查询</el-button>
        <el-button type="primary" @click="addOrUpdateHandle()" style="width:100px" icon="el-icon-plus">新增</el-button>
        <el-dropdown @command="handleBatchCommand" :disabled="dataListSelections.length <= 0" style="padding-left:10px">
          <el-button type="warning" icon="el-icon-coin"> 批量操作 <i class="el-icon-arrow-down el-icon--right"></i> </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="delete">批量删除</el-dropdown-item>
            <el-dropdown-item command="merge">合并整单</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <el-button icon="el-icon-refresh" style="padding-left:10px"  @click="refresh()"  round />
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;"
    >
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="id" header-align="center" align="center" label="采购需求编号"></el-table-column>
      <el-table-column prop="purchaseId" header-align="center" align="center" label="采购单id"></el-table-column>
      <el-table-column prop="skuId" header-align="center" align="center" label="采购商品id"></el-table-column>
      <el-table-column prop="skuNum" header-align="center" align="center" label="采购数量"></el-table-column>
      <el-table-column prop="skuPrice" header-align="center" align="center" label="采购金额"></el-table-column>
      <el-table-column prop="wareName" header-align="center" align="center" label="仓库名"></el-table-column>
      <el-table-column prop="status" header-align="center" align="center" label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status==0">新建</el-tag>
          <el-tag type="info" v-if="scope.row.status==1">已分配</el-tag>
          <el-tag type="wanring" v-if="scope.row.status==2">正在采购</el-tag>
          <el-tag type="success" v-if="scope.row.status==3">已完成</el-tag>
          <el-tag type="danger" v-if="scope.row.status==4">采购失败</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)" icon="el-icon-edit">编辑</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)" icon="el-icon-delete">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      style="padding: 30px 0; text-align: center"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <el-dialog title="合并到整单" :visible.sync="mergedialogVisible"  >
      <!-- id  assignee_id  assignee_name  phone   priority status -->
      <el-select v-model="purchaseId" placeholder="请选择" clearable filterable>
        <el-option
          v-for="item in purchasetableData"
          :key="item.id"
          :label="item.id"
          :value="item.id"
        >
          <span style="float: left">{{ item.id }}</span>
          <span  style="float: right; color: #8492a6; font-size: 13px"
          >{{ item.assigneeName }} : {{item.phone}}</span>
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="mergedialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="mergeItem">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import AddOrUpdate from "./detail-add-or-update";

import { fetchDetailList,delDetail,delDetailBatch,searchDetailList } from '@/api/ware/detail'
import {getAll} from '@/api/ware/info'
import { donePurchase,receive,unreceive,merge } from '@/api/ware/purchase'


export default {
  data() {
    return {
      dataForm: {
        searchParam: "",
        status: "",
        wareId: ""
      },
      wareList: [],
      dataList: [],
      pageIndex: 1,
      pageSize: 20,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      mergedialogVisible: false,
      purchaseId: "",
      purchasetableData: []
    };
  },
  components: {
    AddOrUpdate
  },
  created() {
    this.getDataList();
    this.getWares();
  },
  methods: {
    mergeItem() {
      let items = this.dataListSelections.map(item => {
        return item.id;
      });
      if (!this.purchaseId) {
        this.$confirm(
          "没有选择任何【采购单】，将自动创建新单进行合并。确认吗？",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(() => {
            merge({ items: items }).then(({ data }) => {
              this.getDataList();
            });

          })
          .catch(() => {});
      } else {
        merge( { purchaseId: this.purchaseId, items: items }).then(({ data }) => {
              this.getDataList();
            });
      }
      this.mergedialogVisible = false;
    },
    getUnreceivedPurchase() {
        unreceive({
          currentPage: this.pageIndex,
          pageSize: this.pageSize
        }).then(({ data }) => {
            this.purchasetableData = data.data.list;
        });
    },
    
    handleBatchCommand(cmd) {
      if (cmd == "delete") {
        this.deleteHandle();
      }
      if (cmd == "merge") {
        if (this.dataListSelections.length != 0) {
          this.getUnreceivedPurchase();
          this.mergedialogVisible = true;
        } else {
          this.$alert("请先选择需要合并的需求", "提示", {
            confirmButtonText: "确定",
            callback: action => {}
          });
        }
      }
    },
    getWares() {
      getAll().then(({ data }) => {
        this.wareList = data.data;
      });
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;

      let params={
          currentPage: this.pageIndex,
          pageSize: this.pageSize,
          searchParam: this.dataForm.searchParam,
          status: this.dataForm.status,
          wareId: this.dataForm.wareId
      }
     searchDetailList(params).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list;
          this.totalPage = data.data.totalCount;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }     
      });

      this.dataListLoading = false;
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
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },
     //刷新
    refresh(){
        this.getDataList;
        this.dataForm.searchParam= '';
        this.dataForm.status= '';
        this.dataForm.wareId= '';
    },
    // 删除
    deleteHandle(id) {
      var ids = id
        ? [id]
        : this.dataListSelections.map(item => {
            return item.id;
          });
      this.$confirm(
        `确定对[id=${ids.join(",")}]进行[${id ? "删除" : "批量删除"}]操作?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      ).then(() => {

        delDetailBatch(ids).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: "删除成功",
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
   /deep/.el-dialog__footer {
       text-align: center;
   }
</style>
