<template>
  <div class="mod-config">
    

    <el-table
      :data="tableData"
      border
      :stripe="true"
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;"
    >
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="id" header-align="center" align="center" label="商品编号"></el-table-column>
      <el-table-column prop="spuName" header-align="center" align="center" label="名称"></el-table-column>
      <el-table-column prop="spuDescription" header-align="center" align="center" label="描述"></el-table-column>
      <el-table-column prop="catelogId" header-align="center" align="center" label="分类"></el-table-column>
      <el-table-column prop="brandId" header-align="center" align="center" label="品牌"></el-table-column>
      <el-table-column prop="weight" header-align="center" align="center" label="重量"></el-table-column>
      <el-table-column prop="publishStatus" header-align="center" align="center" label="上架状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.publishStatus == 0">新建</el-tag>
          <el-tag v-if="scope.row.publishStatus == 1">已上架</el-tag>
          <el-tag v-if="scope.row.publishStatus == 2">已下架</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" header-align="center" align="center" label="创建时间" show-overflow-tooltip></el-table-column>
      <el-table-column prop="updateTime" header-align="center" align="center" label="修改时间" show-overflow-tooltip></el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button v-if="scope.row.publishStatus == 0" type="text" size="small" @click="productUp(scope.row.id)" icon="el-icon-sell">上架</el-button>
          <el-button type="text" size="small" @click="attrUpdateShow(scope.row)" icon="el-icon-edit-outline">规格</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="block">
    <el-pagination 
                    @size-change="sizeChangeHandle"
                    @current-change="currentChangeHandle"
                    :current-page="page.currentPage"
                    :page-size="page.pageSize"
                    style="padding: 30px 0; text-align: center"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="page.total">
    </el-pagination>
    </div>
  </div>
</template>

<script>
import PubSub from 'pubsub-js' 
import {fetchSpuInfoList,searchList,upSpu} from '@/api/product/spuman'
  export default {
    data() {
      return {
        dataSub: null,
        refresh: null,
        searchForm: {},
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
    props: {
      catId: {
        type: Number,
        default: 0
      }
    },

    components: {},

     created() {
      this.getDataList();
    },
    activated() {
      this.getDataList();
    },
   
    methods: {

    // 商品上架
      productUp(id) {
       upSpu(id).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: "上架成功",
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
      },
    //规格 路由跳转
      attrUpdateShow(row) {
        this.$router.push( {
                path: "/product/maintain/attrupdate",
                query: {spuId: row.id, catelogId: row.catalogId}
            });
      },

      // 获取所有数据列表
      getDataList() {
        this.dataListLoading = true;

        fetchSpuInfoList(this.page).then((response) => {
            this.tableData = response.data.data.records
            this.page.total = response.data.data.total
        });
        this.dataListLoading = false;
      },
        // 每页数
      sizeChangeHandle(val) {
        this.page.pageSize = val;
        this.page.currentPage = 1;
        this.getDataList();
      },
      // 当前页
      currentChangeHandle(val) {
        this.page.currentPage = val;
        this.getDataList();
      },
      
      // 多选
      selectionChangeHandle(val) {
        this.dataListSelections = val;
      },

      // 新增 / 修改
      addOrUpdateHandle(id) {
      }
    },
    mounted() {
       //条件查询
      this.dataSub = PubSub.subscribe("searchForm", (msg, val) => {
       let params={
                   publishStatus: val.status,
                   brandId: val.brandId,
                   catalogId: val.catalogId,
                   searchParam: val.searchParam,
                   currentPage: this.page.currentPage,
                   pageSize: this.page.pageSize}
        
        searchList(params).then(({data})=>{
            this.tableData = data.data.list
            this.page.total = data.data.totalCount
        })
      });
        //刷新
      this.refresh = PubSub.subscribe("refreshChange", (msg, val) => {
         this.getDataList();
      });

    },
    beforeDestroy() {
      PubSub.unsubscribe(this.dataSub);
      PubSub.unsubscribe(this.refresh);
    }
  };
</script>
