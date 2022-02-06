<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
    <el-form-item label="仓库名" prop="name">
      <el-input v-model="dataForm.name" placeholder="仓库名"></el-input>
    </el-form-item>
    <el-form-item label="仓库地址" prop="address">
      <el-input v-model="dataForm.address" placeholder="仓库地址"></el-input>
    </el-form-item>
    <el-form-item label="区域编码" prop="areacode">
      <el-input v-model="dataForm.areacode" placeholder="区域编码"></el-input>
    </el-form-item>
    <el-form-item label="仓库负责人" prop="areacode">
      <el-input v-model="dataForm.warePrincipal" placeholder="仓库负责人"></el-input>
    </el-form-item>
    <el-form-item label="负责人号码" prop="areacode">
      <el-input v-model="dataForm.principalPhone" placeholder="负责人号码"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { searchList,addObj,getObj,putObj } from '@/api/ware/info'


  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          name: '',
          address: '',
          areacode: '',
          warePrincipal: '',
          principalPhone: ''
        },
        dataRule: {
          name: [
            { required: true, message: '仓库名不能为空', trigger: 'blur' }
          ],
          address: [
            { required: true, message: '仓库地址不能为空', trigger: 'blur' }
          ],
          areacode: [
            { required: true, message: '区域编码不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {

            getObj(this.dataForm.id).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.data.name
                this.dataForm.address = data.data.address
                this.dataForm.areacode = data.data.areacode
                this.dataForm.warePrincipal = data.data.warePrincipal
                this.dataForm.principalPhone = data.data.principalPhone
              }
            })


          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            let ware = {
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'address': this.dataForm.address,
                'areacode': this.dataForm.areacode,
                'warePrincipal': this.dataForm.warePrincipal,
                'principalPhone': this.dataForm.principalPhone,
            }
            if(!this.dataForm.id){
                addObj(ware).then(({data}) => {
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
                putObj(ware).then(({data}) => {
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
        })
      }
    }
  }
</script>
<style lang="scss" scoped>
   /deep/.el-dialog__footer {
       text-align: center;
   }
</style>