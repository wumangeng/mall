<template>
    <div class="execution">
        <basic-container>
            <el-row :gutter="20">
                <el-col :span="6">
                    <CategoryTree @handleTreeNodeClick ="handleTreeNodeClick"></CategoryTree>
                </el-col>
                <el-col :span="18">
                     <div class="top-button" >
                        <el-button   type="primary" icon="el-icon-plus" style="width:100px;"  @click="() => append()"  round>新增</el-button>
                        <el-form :inline="true"  class="search-form-inline" style="padding-top: 15px;margin-left: auto;margin-right: auto;" >
                            <el-form-item label="">
                                <el-input v-model="searchForm.param" placeholder=""></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" icon="el-icon-search" @click="searchChange()">查询</el-button>
                            </el-form-item>
                        </el-form>
                        <el-button icon="el-icon-refresh" @click="refreshChange()" round/>
                    </div>

                    <el-table  :data="tableData"
                        border
                        style="width: 100%"
                        fit stripe
                        highlight-current-row >
                        <el-table-column    prop="attrGroupId" label="分组编号"   header-align="center"   align="center" show-overflow-tooltip>     </el-table-column>
                        <el-table-column    prop="attrGroupName" label="组名"  header-align="center"   align="center"> </el-table-column>
                        <el-table-column    prop="descript" label="描述"  header-align="center"   align="center" show-overflow-tooltip> </el-table-column>               
                    
                        <el-table-column prop="icon"  label="组图标"  align="center" width="100">
                            <template slot-scope="scope">
                                <i :class="scope.row.icon" />
                            </template>
                        </el-table-column>
                        <el-table-column    prop="catelogName" label="所属分类"  header-align="center"   align="center"> </el-table-column>               
                        <el-table-column    prop="sort" label="排序"  header-align="center"   align="center"></el-table-column>
                        <el-table-column label="操作" width="200" align="center">
                            <template slot-scope="scope" >
                                <el-button type="text" size="small" icon="el-icon-connection"  @click="handleRelation(scope.row.attrGroupId)">关联</el-button>
                                <el-button type="text" size="small" icon="el-icon-edit"  @click="handleUpdate(scope.row.attrGroupId)">编辑</el-button>
                                <el-button type="text" icon="el-icon-delete" size="small" @click="rowDel(scope.row)">删除</el-button>
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

                <!-- 添加-编辑弹框 -->
                <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="50%">
                    <el-form ref="form" :model="AttrGroup" label-width="100px" >
                        <el-form-item label="组名">
                            <el-input v-model="AttrGroup.attrGroupName" auto-complete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="描述">
                            <el-input v-model="AttrGroup.descript"  auto-complete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="组图标" >
                            <avue-input-icon v-model="AttrGroup.icon"  :icon-list="iconList"></avue-input-icon>
                        </el-form-item>
                       
                        <el-form-item label="所属分类">
                            <el-cascader 
                                v-model="AttrGroup.catelogIdPath"
                                filterable :options="categoryList"
                                placeholder="试试搜索"
                                :props="props" style="width:300px"> </el-cascader>
                        </el-form-item>

                         <el-form-item label="排序">
                            <el-input v-model="AttrGroup.sort"   auto-complete="off"></el-input>
                        </el-form-item>
                       
                    </el-form>
                    <span slot="footer" class="dialog-footer" style="">
                        <el-button @click="dialogVisible = false">取 消</el-button>
                        <el-button type="primary" @click="submitData">确 定</el-button>
                    </span>
                </el-dialog>

                <!-- 关联弹框 -->
                <el-dialog title="关联参数" :visible.sync="dialogVisible2" width="50%">
                    
                    <el-table :data="relationTableData"   border    style="width: 100%"    fit  stripe    highlight-current-row>
                        <el-table-column prop="attrId" header-align="center" align="center" label="属性编号" show-overflow-tooltip></el-table-column>

                        <el-table-column prop="attrName"  label="属性名"   header-align="center"   align="center">   </el-table-column>

                        <el-table-column prop="valueSelect"    label="可选值"     header-align="center"    align="center">
                            <template slot-scope="scope">
                            <el-tag
                            :type="'primary'"
                            disable-transitions>{{scope.row.valueSelect}}</el-tag>
                            </template>
                        </el-table-column>

                        <el-table-column prop="attrType"   label="属性类型" header-align="center" align="center">
                            <template slot-scope="scope">
                　　　　　　　　　　<span v-if="scope.row.attrType == 0" >销售属性</span>
                　　　　　　　　　　<span v-if="scope.row.attrType == 1">基本属性</span>                　　　　　　　　
                            </template>
                        </el-table-column>

                        <el-table-column prop="valueSelect" label="值类型" header-align="center" align="center" >
                            <template slot-scope="scope">
                                <el-tag :type="(scope.row.valueType == '0' ? 'success' : '' )" size="mini">
                                    {{ scope.row.valueType == '0' ? '单选' : '多选' }}
                                </el-tag>
                            </template>
                        </el-table-column>

                        <el-table-column prop="enable" label="启用状态" header-align="center" align="center">
                        <template slot-scope="scope">
                            <el-switch v-model="scope.row.enable"
                                    active-color="#13ce66"
                                    inactive-color="#ff4949"
                                    :active-value="1"
                                    :inactive-value="0">
                            </el-switch>
                        </template>
                        </el-table-column>

                        <el-table-column label="操作" width="200"  align="center">
                        <template slot-scope="scope">
                            <el-button type="text"  icon="el-icon-delete"  size="small"  @click="rowDelRelation(scope.row)">删除</el-button>
                        </template>
                        </el-table-column>
                    </el-table>
                   
                </el-dialog>
                </el-col>
            </el-row>
          
        </basic-container>

    </div>
</template>

<script>
    import CategoryTree from '../../common/categorytree'
    import iconList from '@/const/iconList'
    import {queryList, getAttrGroupById, addObj, putObj, delObj} from '@/api/product/attrgroup'
    import { listWithTree } from "@/api/product/category";
    import { getAttrByAttrGroupId,removeRelation } from "@/api/product/attr";

    export default {
        components: {CategoryTree:CategoryTree},
        data() {
            return {
                catId: 0,
                iconList: iconList,
                relationTableData: [],
                searchForm: {
                    catelogId: 0,
                    param: "",
                    current: 1,
                    size: 10
                },
                tableData: [],
                pageIndex: 1,
                pageSize: 10,
                totalPage: 0,
                dialogVisible: false, //对话框默认关闭
                dialogType: "", //对话框按钮区分（添加和编辑）
                dialogTitle: "",
                dialogVisible2: false, 
                dialogAttrGroupId: 0, //用于保存关联弹出层的id
                AttrGroup: {
                    attrGroupId: '',
                    attrGroupName: "",
                    icon: "",
                    descript: "",
                    sort: 0,
                    catelogIdPath: [],
                    catelogId: 0,
                    catelogName: ''
                },
                file: "",
                categoryList: [],
                props: {
                    value: 'catId',
                    label: "name",
                    children: "children"
                },
                Attr: {
                    attrId: 0,
                    attrName: '',
                    valueSelect: '',
                    attrType: 0,
                    valuetype: 0,
                }
            }
        },
        created() {
            this.getDataList();
        },
        methods: {
        //查询所有记录
        getDataList() {
          
            queryList({current: this.pageIndex,size: this.pageSize}).then(response => {
                this.tableData = response.data.data.records;
                this.totalPage = response.data.data.total;
            })
            listWithTree().then(response=>{
                this.categoryList= response.data.data;
            })
        },
         searchChange(){
            this.searchForm.catelogId =this.catId;
            this.searchForm.current = this.pageIndex
            this.searchForm.size = this.pageSize

            queryList(this.searchForm).then(response => {
                this.tableData = response.data.data.records;
                this.totalPage = response.data.data.total;
            });
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
        //节点被点击时触发
        handleTreeNodeClick(data, node, component){
            if (node.level==3){
                this.catId = data.catId;
                this.searchChange();
            }
        },
        append(){
            this.dialogTitle = "添加分组";
            this.dialogType = "add";
            this.dialogVisible = true;
            this.AttrGroup.attrGroupId = '';
            this.AttrGroup.attrGroupName = "";
            this.AttrGroup.icon = "";
            this.AttrGroup.descript = "";
            this.AttrGroup.catelogId = 0;
            this.AttrGroup.catelogIdPath = [];
            this.AttrGroup.sort = 0; 
        },
        
         refreshChange(){
            queryList({ current: 1,size: 20}).then(response => {
                this.tableData = response.data.data.records;
                this.totalPage = response.data.data.total;
                this.searchForm.param = ""
            })
         },
         //添加或编辑
         handleUpdate(attrGroupId){
            this.dialogTitle = "修改分组";
            this.dialogType = "edit";
            this.dialogVisible = true;
            getAttrGroupById(attrGroupId).then(response=>{
                this.AttrGroup={ ...response.data.data };
            })

         },
         //关联弹出
         handleRelation(attrGroupId){
              this.dialogVisible2 = true;
              getAttrByAttrGroupId(attrGroupId).then(response=>{
                   this.relationTableData  = response.data.data
              })
              this.dialogAttrGroupId = attrGroupId
         },
         rowDelRelation(row){
              let that =this
              this.$confirm('是否确认删除: ' + row.attrName, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    return removeRelation(row.attrId,that.dialogAttrGroupId)
                }).then(data => {
                    this.$message.success('删除成功');
                    getAttrByAttrGroupId(that.dialogAttrGroupId).then(response=>{
                        this.relationTableData  = response.data.data
                    })
                })
         },
         rowDel(row){
              this.$confirm('是否确认删除:' + row.name, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    return delObj(row.attrGroupId)
                }).then(data => {
                    this.$message.success('删除成功')
                    this.getDataList()
                })
         },
            submitData(){
                this.AttrGroup.catelogId =this.AttrGroup.catelogIdPath[this.AttrGroup.catelogIdPath.length-1]
               //添加
                if (this.dialogType == "add") {
                   
                    addObj(this.AttrGroup).then(data => {
                        this.$message.success('添加成功')
                        this.getDataList()
                    }).catch(() => {
                        
                    });
                }
                //修改
                 if (this.dialogType == "edit") {
                    putObj(this.AttrGroup).then(data => {
                        this.$message.success('修改成功')
                        this.getDataList()
                    }).catch(() => {
                        
                    });
                }
                this.dialogVisible = false;
         }

        }
    }
</script>

<style lang="scss" scoped>
.top-button{
       display: flex;
       height: 30;
       justify-content: left;
       align-items: center;
   }
</style>
