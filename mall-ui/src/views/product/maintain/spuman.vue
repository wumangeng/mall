<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-form :inline="true" :model="searchForm" label-width="70px">
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
             <el-select placeholder="请选择" v-model="searchForm.brandId" filterable clearable>
                <el-option v-for="(item,index) in brands" :key="index+'1'" 
                    :label="item.brandName" :value="item.brandId">
                </el-option>
             </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select style="width:160px" v-model="searchForm.status" clearable>
              <el-option label="新建" :value="0"></el-option>
              <el-option label="上架" :value="1"></el-option>
              <el-option label="下架" :value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="检索">
            <el-input style="width:160px" v-model="searchForm.searchParam" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchSpuInfo">查询</el-button>
          </el-form-item>
          <el-form-item>
              <el-button icon="el-icon-refresh"   @click="refreshChange()"  round />
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="24">
        <spuinfo :catId="catId"></spuinfo>
      </el-col>
    </el-row>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
import Spuinfo from "./sputable";

import {fetchSpuInfoList,searchList} from '@/api/product/spuman'
import { listWithTree } from '@/api/product/category'
import { getRelationBrandsList} from '@/api/product/brand'

export default {
  //import引入的组件需要注入到对象中才能使用
  components: {Spuinfo },
  props: {},
  data() {
    //这里存放数据
    return {
      catId: 0,
      catelogPath: [],
      categoryList: [],
      props: {
        value: 'catId',
        label: 'name',
        children: 'children',
      },
      brands: [],
      searchForm: {
        status: "",
        searchParam: "",
        brandId: undefined,
        catelogId: 0
      },
      catPathSub: null,
      brandIdSub: null

    };
  },
  //计算属性 类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    searchSpuInfo() {
      this.PubSub.publish("searchForm",this.searchForm);
    },
    refreshChange(){
      this.PubSub.publish("refreshChange", null);
      this.searchForm.searchParam = '';
      this.catelogPath = '';
      this.searchForm.brandId= '';
    },
        //级联选择器隐藏时触发
    hideChange : function(callback){  
      //只有回调参数为false时才触发 
        if(!callback){
            //被选中的节点
            this.searchForm.catelogId= this.$refs.cascader.getCheckedNodes()[0].value;

            getRelationBrandsList(this.searchForm.catelogId).then(response => {
                this.brands = response.data.data;
            }
            )
        }
    },

  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
       listWithTree().then((response) => {
        this.categoryList = response.data.data
      })
  },
  //生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {
    this.catPathSub = PubSub.subscribe("catPath", (msg, val) => {
      this.searchForm.catelogId = val[val.length-1];
    });
    this.brandIdSub = PubSub.subscribe("brandId", (msg, val) => {
      this.searchForm.brandId = val;
    });
  },
  beforeCreate() {}, //生命周期 - 创建之前
  beforeMount() {}, //生命周期 - 挂载之前
  beforeUpdate() {}, //生命周期 - 更新之前
  updated() {}, //生命周期 - 更新之后
  beforeDestroy() {
     PubSub.unsubscribe(this.catPathSub); 
     PubSub.unsubscribe(this.brandIdSub); 
  }, //生命周期 - 销毁之前
  destroyed() {}, //生命周期 - 销毁完成
  activated() {} //如果页面有keep-alive缓存功能，这个函数会触发
};
</script>
<style scoped>
</style>