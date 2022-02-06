<template>
  <el-dialog
    :title="!dataForm.id ? '新增Sku库存' : '修改Sku库存信息'"
    :close-on-click-modal="false"
    :visible.sync="visible"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="120px"
    >
      <el-form-item label="sku_id" prop="skuId">
        <el-input v-model="dataForm.skuId" placeholder="sku_id"></el-input>
      </el-form-item>
      <el-form-item label="仓库" prop="wareId">
        <el-select v-model="dataForm.wareId" placeholder="请选择仓库" clearable>
          <el-option :label="w.name" :value="w.id" v-for="w in wareList" :key="w.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="库存数" prop="stock">
        <el-input v-model="dataForm.stock" placeholder="库存数"></el-input>
      </el-form-item>
      <el-form-item label="sku_name" prop="skuName">
        <el-input v-model="dataForm.skuName" placeholder="sku_name"></el-input>
      </el-form-item>
      <el-form-item label="锁定库存" prop="stockLocked">
        <el-input v-model="dataForm.stockLocked" placeholder="锁定库存"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import {getWareSku,addWareSku,putWareSku} from '@/api/ware/sku'
import {getAll} from '@/api/ware/info'

export default {
  data() {
    return {
      visible: false,
      wareList: [],
      dataForm: {
        id: 0,
        skuId: "",
        wareId: "",
        stock: 0,
        skuName: "",
        stockLocked: 0
      },
      dataRule: {
        skuId: [{ required: true, message: "sku_id不能为空", trigger: "blur" }],
        wareId: [
          { required: true, message: "仓库id不能为空", trigger: "blur" }
        ],
        stock: [{ required: true, message: "库存数不能为空", trigger: "blur" }],
        skuName: [
          { required: true, message: "sku_name不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created(){
    this.getWares();
  },
  methods: {
    getWares() {
      getAll().then(({ data }) => {
        this.wareList = data.data;
      });

    },
    init(id) {
      this.dataForm.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.id) {

          getWareSku(this.dataForm.id).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.skuId = data.data.skuId;
              this.dataForm.wareId = data.data.wareId;
              this.dataForm.stock = data.data.stock;
              this.dataForm.skuName = data.data.skuName;
              this.dataForm.stockLocked = data.data.stockLocked;
            }
          });

        }
      });
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {

         let wareSku={
              id: this.dataForm.id || undefined,
              skuId: this.dataForm.skuId,
              wareId: this.dataForm.wareId,
              stock: this.dataForm.stock,
              skuName: this.dataForm.skuName,
              stockLocked: this.dataForm.stockLocked
         }
         
          if(!this.dataForm.id){
              addWareSku(wareSku).then(({data}) => {
                if (data && data.code === 0) {
                    this.$message({
                        message: '添加成功',
                        type: 'success',
                        duration: 1500,
                        onClose: () => {
                            this.visible = false
                            this.$emit('refreshDataList')
                        }
                    })
                } else {
                    this.$message.error(data.msg)
                }
                })
            }else{
              putWareSku(wareSku).then(({data}) => {
                if (data && data.code === 0) {
                    this.$message({
                    message: '编辑成功',
                    type: 'success',
                    duration: 1500,
                    onClose: () => {
                        this.visible = false
                        this.$emit('refreshDataList')
                    }
                    })
                } else {
                    this.$message.error(data.msg)
                }
                })
            }
        }
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

