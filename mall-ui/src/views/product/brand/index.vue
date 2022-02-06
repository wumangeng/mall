<template>
    <div class="execution">
        <basic-container>

            <div class="top-button" >
                <el-button   type="primary" icon="el-icon-plus" style="width:100px;"  @click="() => append()"  round>新增</el-button>
                <el-form :inline="true"  class="search-form-inline" style="padding-top: 15px;margin-left: auto;margin-right: auto;" >
                    <el-form-item label="品牌名">
                        <el-input v-model="searchForm.name" placeholder="品牌名"></el-input>
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

                <el-table-column    prop="brandId" label="品牌编号"   header-align="center"   align="center" show-overflow-tooltip>    </el-table-column>
                <el-table-column    prop="name" label="品牌名"  header-align="center"   align="center" show-overflow-tooltip>            </el-table-column>
                <el-table-column    prop="logo" label="品牌logo" width="200" header-align="center"   align="center" show-overflow-tooltip  > 
                    <template slot-scope="scope">
                        <el-image style="width:60px; height:30px;" :src="scope.row.logo" fit="contain"/>
                    </template>
                </el-table-column>
                <el-table-column    prop="descript" label="介绍"  header-align="center"   align="center" show-overflow-tooltip >           </el-table-column>
                <el-table-column    prop="showStatus" label="显示状态"  header-align="center"   align="center" >
                    <template slot-scope="scope">
                        <el-switch 
                          v-model="scope.row.showStatus"
                          active-color="#13ce66"
                          inactive-color="#ff4949" 
                          :active-value="1"  
                          :inactive-value="0"  
                          @change="updateBrandStatus(scope.row)">
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column    prop="firstLetter" label="检索首字母"  header-align="center"   align="center"  >    </el-table-column>
                <el-table-column    prop="sort" label="排序"  header-align="center"   align="center"  >       </el-table-column>
        
                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope" >
                        <el-button type="text" size="small" icon="el-icon-data-line"  @click="handleRelation(scope.row.brandId)">关联分类</el-button>
                        <el-button type="text" size="small" icon="el-icon-edit"  @click="handleUpdate(scope.row.brandId)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" size="small" @click="rowDel(scope.row)">删除</el-button>
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
            style="padding: 30px 0; text-align: center;"
            layout="total, sizes, prev, pager, next, jumper"
            :total= "page.total">
            </el-pagination>
        </div>

        <!-- 添加、编辑 弹框 -->
         <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="50%">
            <el-form ref="form" :model="brand" label-width="100px" >
                <el-form-item label="品牌名">
                    <el-input v-model="brand.name" style="width:250px"  auto-complete="off"></el-input>
                </el-form-item>

                <el-form-item label="logo" >
                    <el-upload
                        class="upload-demo"
                        action="/resources/upload/uploadBrandLogo"
                        :auto-upload="false"
                        :on-preview="handleChange"
                        :on-change="handleChange"
                        :file-list="fileList"
                        :on-remove="handleRemove"
                        accept= "*.jpeg,*.jpg,*.jpe,*.png,*.webp,*.jfif,*.pdf,*.gif"
                        :limit="1"
                        :on-exceed ="limitExceeded"
                        style="width:250px"
                        list-type="picture">
                        <el-button size="small"  slot="trigger" type="primary">上传logo</el-button>
                    </el-upload>
                </el-form-item>

                <el-form-item label="介绍">
                    <el-input v-model="brand.descript"  auto-complete="off"></el-input>
                </el-form-item>

                <el-form-item label="显示状态">
                    <el-switch
                    v-model="brand.showStatus"  
                    active-color="#13ce66"  
                    inactive-color="#ff4949" 
                    :active-value="1"  
                    :inactive-value="0">  
                    </el-switch>
                </el-form-item>

                <el-form-item label="检索首字母">
                     <el-input v-model="brand.firstLetter" style="width:250px"  auto-complete="off"></el-input>
                </el-form-item>

                <el-form-item label="排序">
                     <el-input v-model="brand.sort" style="width:250px"  auto-complete="off"></el-input>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer" style="">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitData">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 关联分类 弹框 -->
        <el-dialog    title="关联分类"    :visible.sync="dialogVisible2"    width="50%">

            <el-form :inline="true"  class="search-form-inline"  >
                <el-form-item label="选择分类">
                <el-cascader 
                    v-model="AttrGroup.catelogIdPath"
                    filterable :options="categoryList"
                    placeholder="试试搜索"
                    clearable
                    :props="props" style="width:300px"> </el-cascader>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" icon="el-icon-search" @click="saveChange()">添加分类</el-button>
                </el-form-item>
            </el-form>
           
             <el-table    :data="tableData2" stripe   style="width: 100%">
                <el-table-column   prop="brandName"   label="品牌名" header-align="center"   align="center"  >  </el-table-column>
                <el-table-column   prop="catelogName"    label="分类名称" header-align="center"   align="center"   >    </el-table-column>
                  <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope" >
                        <el-button type="text" icon="el-icon-delete" size="small" @click="relationRowDel(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <span slot="footer" class="dialog-footer" >
                <el-button style="width: 100px" @click="dialogVisible2 = false">关闭</el-button>
            </span>
        </el-dialog>
        
        </basic-container>

       
    </div>
</template>

<script>
    import {fetchList, searchList, getObj, addObj, putObj, delObj,upload,
            getCategoryBrandRelationList,addRelation,delRelation} from '@/api/product/brand'
    import { listWithTree,getCategoryById } from "@/api/product/category";

    export default {
        name: 'brand',
        data() {
            return {
                searchForm: {name:"",current: 1,size:10},
                tableData: [],
                page: {
                    total: 0, // 总页数
                    currentPage: 1, // 当前页数
                    pageSize: 10 // 每页显示多少条
                },
                dialogVisible: false, //对话框默认关闭
                dialogType: "", //对话框按钮区分（添加和编辑）
                dialogTitle: "",
                brand: {
                    name: "",
                    logo: "",
                    descript: "",
                    showStatus: 1,
                    firstLetter: '',
                    sort: 0
                },
                file: "",
                fileList: [],
                dialogVisible2: false,
                tableData2: [],
                relation: {
                    brandId: 0,
                    catelogId: 0,
                },
                AttrGroup: {
                    catelogIdPath: [],
                    catelogId: 0,
                },
                categoryList: [],
                props: {
                    value: 'catId',
                    label: "name",
                    children: "children"
                },
            }
        },
        created() {
            this.getList();
        },
        methods: {
            //查询所有记录
            getList(params) {
                fetchList(Object.assign({
                    current: this.page.currentPage,
                    size: this.page.pageSize
                },params)).then(response => {
                    this.tableData = response.data.data.records;
                    this.page.total = response.data.data.total
                })
            },
             //添加
            append() {
            this.dialogTitle = "添加品牌";
            this.dialogType = "add";
            this.dialogVisible = true;
            this.file='';
            this.fileList= [];
            this.brand.id = '';
            this.brand.name = "";
            this.brand.logo = "";
            this.brand.descript = "";
            this.brand.showStatus = 1;
            this.brand.firstLetter = "";
            this.brand.sort = 0;
            },

            submitData() {
            //添加分类
                if (this.dialogType == "add") {
                    addObj(this.brand).then(data => {
                        this.$message.success('添加成功')
                        this.getList(this.page)
                    }).catch(() => {
                        this.$message.error(data.data.msg)
                    });
                }
                 if (this.dialogType == "edit") {
                    putObj(this.brand).then(data => {
                        this.$message.success('修改成功')
                        this.getList(this.page)
                    }).catch(() => {
                        
                    });
                }
                this.dialogVisible = false;
        
            },
            //修改状态
            updateBrandStatus(data){
                let {brandId,showStatus} = data;
                 putObj({brandId,showStatus}).then(response => {
                    this.$message.success('显示状态更新成功')
                }).catch(() => {
                    this.$message.error('显示状态更新失败')
                });
            },
            //删除
            rowDel(row, index) {
                this.$confirm('是否确认删除:' + row.name, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    return delObj(row.brandId)
                }).then(data => {
                    this.$message.success('删除成功')
                    this.getList(this.page)
                })
            },
            //编辑
            handleUpdate (row) {
                this.dialogTitle = "修改分类";
                this.fileList= [];
                this.dialogType = "edit";
                this.dialogVisible = true;
                getObj(row).then(response=>{
                    this.brand={ ...response.data.data };
                    let obj = new Object();
                    obj.url = this.brand.logo;
                    this.fileList.push(obj);
                })

            },
            //查询
            searchChange() {
                this.page.currentPage = 1
               searchList(this.searchForm).then(response=>{
                    this.tableData = response.data.data.records;
                    this.page.total = response.data.data.total
               })
                
            },
            //刷新
            refreshChange() {
                this.searchForm.params = ''
                this.getList(this.page)
            },

            // 每页数
            sizeChangeHandle(val) {
                this.page.pageSize = val;
                this.page.currentPage = 1;
                this.getList();
            },
            // 当前页
            currentChangeHandle(val) {
                this.page.currentPage = val;
                this.getList();
            },

            //上传请求
            submitUpload(){
                upload(this.file.raw).then(response=>{
                    this.brand.logo = response.data;
                })
            },
            //文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用
            handleChange(file, fileList){
                this.file = file;
                this.submitUpload();
            },

            //文件列表移除文件时的钩子
            handleRemove(file, fileList) {
               this.fileList = fileList;
               this.brand.logo = '';
            },
            
            //文件超出个数限制时的钩子
            limitExceeded(file, fileList){
                 this.$message.error('最多只能上传一张图片！')
            },

         
            //打开关联分类弹框
            handleRelation(brandId){
                this.dialogVisible2=true;
                this.relation.brandId =brandId;
                this.getTableData2(brandId);
                //查询分类树
                listWithTree().then((response) => {
                    this.categoryList = response.data.data;
                });
            },
            //表格数据
            getTableData2(brandId){
                getCategoryBrandRelationList(brandId).then(response=>{
                    this.tableData2 = response.data.data;
                });
            },
            //添加关联
            saveChange(){
                 this.AttrGroup.catelogId =this.AttrGroup.catelogIdPath[this.AttrGroup.catelogIdPath.length-1]
                 getCategoryById(this.AttrGroup.catelogId).then(response=>{
                    if(2!=response.data.data.catLevel){
                        this.$message.error('只能关联三级分类！');
                    }else{
                        this.relation.catelogId = this.AttrGroup.catelogId;
                        addRelation(this.relation).then(response=>{
                            this.getTableData2(this.relation.brandId);
                            if(response.data.msg!=null){
                                this.$message.warning(response.data.msg);
                            }else{
                                this.$message.success('添加关联分类成功！');
                            }
                        });
                    }
                 })
            },
            //删除关联
            relationRowDel(row){
                this.$confirm('是否确认删除: <'+row.brandName+ '> 与 <' + row.catelogName+'> 关联', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    return delRelation(row.id)
                }).then(data => {
                    this.$message.success('删除成功!')
                    this.getTableData2(row.brandId)
                })
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
   .search-form-inline{
      
   }
   /deep/.el-dialog__footer {
       text-align: center;
   }
</style>
