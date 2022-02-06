<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form :inline="true" :model="dataForm">
        <el-form-item label="分类">
          <el-cascader v-model="catelogPath"
                             ref="cascader"
                             filterable
                             :options="categoryList"
                             placeholder="试试搜索"
                             :props="props"
                             :clearable="true"
                             @visible-change="hideChange($event)"
                             >
          </el-cascader>
        </el-form-item>
        <el-form-item label="品牌">
           <el-select placeholder="请选择" v-model="dataForm.brandId" filterable clearable style="width:150">
                <el-option v-for="(item,index) in brands" :key="index+'1'" 
                    :label="item.brandName" :value="item.brandId">
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="价格">
          <el-input-number style="width:150px" v-model="dataForm.price.min" :min="0"></el-input-number>-
          <el-input-number style="width:150px" v-model="dataForm.price.max" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="检索">
          <el-input style="width:150px" v-model="dataForm.searchParam" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchSkuInfo">查询</el-button>
        </el-form-item>
        <el-form-item>
            <el-button icon="el-icon-refresh"   @click="refreshChange()"  round />
        </el-form-item>
      </el-form>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;"
      @expand-change="getSkuDetails"
    >
      <el-table-column type="expand">
        <template slot-scope="scope">
          商品标题：{{scope.row.skuTitle}}
          <br />
          商品副标题：{{scope.row.skuSubtitle}}
          <br />
          商品描述：{{scope.row.skuDesc}}
          <br />
          分类ID：{{scope.row.catelogId}}
          <br />
          SpuID：{{scope.row.spuId}}
          <br />
          品牌ID：{{scope.row.brandId}}
          <br />
        </template>
      </el-table-column>
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="skuId" header-align="center" align="center" label="skuId"></el-table-column>
      <el-table-column prop="skuName" header-align="center" align="center" label="名称"></el-table-column>
      <el-table-column prop="skuDefaultImg" header-align="center" align="center" label="默认图片">
        <template slot-scope="scope">
          <img v-if="scope.row.skuDefaultImg!=null" :src="scope.row.skuDefaultImg" style="width:50px;height:50px;" />
        </template>
      </el-table-column>
      <el-table-column prop="price" header-align="center" align="center" label="价格"></el-table-column>
      <el-table-column prop="saleCount" header-align="center" align="center" label="销量"></el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="200px" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="previewHandle(scope.row.skuId)" icon="el-icon-view">预览</el-button>
          <el-button type="text" size="small" @click="commentHandle(scope.row.skuId)" icon="el-icon-edit-outline">评论</el-button>
          <el-dropdown
            @command="handleCommand(scope.row,$event)"
            size="small"
            split-button
            type="text"
            style="padding-left:10px"
          >
            <el-button type="text" size="small" icon="el-icon-more">更多 </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="uploadImages" >上传图片</el-dropdown-item>
              <el-dropdown-item command="seckillSettings">参与秒杀</el-dropdown-item>
              <el-dropdown-item command="reductionSettings">满减设置</el-dropdown-item>
              <el-dropdown-item command="discountSettings">折扣设置</el-dropdown-item>
              <el-dropdown-item command="memberPriceSettings">会员价格</el-dropdown-item>
              <el-dropdown-item command="stockSettings">库存管理</el-dropdown-item>
              <el-dropdown-item command="couponSettings">优惠劵</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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
  </div>
</template>

<script>
import { listWithTree } from '@/api/product/category'
import { getRelationBrandsList} from '@/api/product/brand'
import {fetchSkuInfoList} from '@/api/product/spuman'


export default {
  data() {
    return {
      catPathSub: null,
      brandIdSub: null,
      dataForm: {
        searchParam: '',
        brandId: undefined,
        catelogId: 0,
        price: {
          min: 0,
          max: 0
        }
      },
      catId: 0,
      catelogPath: [],
      categoryList: [],
      props: {
        value: 'catId',
        label: 'name',
        children: 'children',
      },
      brands: [],
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
    };
  },

  created() {
    this.getDataList();
    listWithTree().then((response) => {
      this.categoryList = response.data.data
    })
  },
  methods: {
    getSkuDetails(row, expand) {
      //sku详情查询
      //  console.log("展开某行...", row, expand);
    },
    //处理更多指令
    handleCommand(row, command) {
      if ("stockSettings" == command) {
        this.$router.push({ path: "/product/ware/sku", query: { skuId: row.skuId } });
      }
    },
    //查询
    searchSkuInfo() {
       this.dataListLoading = true;
      fetchSkuInfoList({
          currentPage: this.pageIndex,
          pageSize: this.pageSize,
          searchParam: this.dataForm.searchParam,
          catelogId: this.dataForm.catelogId,
          brandId: this.dataForm.brandId,
          min: this.dataForm.price.min,
          max: this.dataForm.price.max
        }).then(({ data }) => {
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
    //刷新
    refreshChange(){
      this.dataForm.searchParam = '';
      this.dataForm.brandId = undefined;
      this.dataForm.catelogId = 0;
      this.dataForm.price.min = 0;
      this.dataForm.price.max = 0;
      this.catelogPath = '';
      this.getDataList();
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      fetchSkuInfoList({
          currentPage: this.pageIndex,
          pageSize: this.pageSize,
        }).then(({ data }) => {
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
 //级联选择器隐藏时触发
    hideChange : function(callback){  
      //只有回调参数为false时才触发 
        if(!callback){
            //被选中的节点
            this.dataForm.catelogId= this.$refs.cascader.getCheckedNodes()[0].value;

            getRelationBrandsList(this.dataForm.catelogId).then(response => {
                this.brands = response.data.data;
            }
            )
        }
    },

  },
  mounted() {
    this.catPathSub = PubSub.subscribe("catPath", (msg, val) => {
      this.dataForm.catelogId = val[val.length - 1];
    });
    this.brandIdSub = PubSub.subscribe("brandId", (msg, val) => {
      this.dataForm.brandId = val;
    });
  },
  beforeDestroy() {
    PubSub.unsubscribe(this.catPathSub);
    PubSub.unsubscribe(this.brandIdSub);
  } //生命周期 - 销毁之前
};
</script>
