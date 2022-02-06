<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px" size="small">
    <el-form-item label="会员等级" prop="levelId">
        <el-select v-model="dataForm.levelId" placeholder="会员等级"  style="width: 300px">
        <el-option v-for="item in levelList" :key="item.id" 
            :label="item.name" :value="item.id"></el-option>
        </el-select>
    </el-form-item>
    <el-form-item label="用户名" prop="username">
      <el-input v-model="dataForm.username" placeholder="用户名"  style="width:300px"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="dataForm.password" placeholder="密码" style="width:300px"></el-input>
    </el-form-item>
    <el-form-item label="昵称" prop="nickname">
      <el-input v-model="dataForm.nickname" placeholder="昵称" style="width:300px"></el-input>
    </el-form-item>
    <el-form-item label="手机号码" prop="mobile">
      <el-input v-model="dataForm.mobile" placeholder="手机号码" style="width:300px"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="dataForm.email" placeholder="邮箱" style="width:300px"></el-input>
    </el-form-item>
    <el-form-item label="头像" prop="header">
       <el-upload
            class="upload-demo"
            action="/resources/upload/uploadMemberHeader"
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
            <el-button size="small"  slot="trigger" type="primary">上传头像</el-button>
        </el-upload>
    </el-form-item>
    <el-form-item label="性别" prop="gender">
    <el-radio-group  prop="gender" v-model="dataForm.gender" size="medium">
      <el-radio border :label="0">女</el-radio>
      <el-radio border :label="1">男</el-radio>
    </el-radio-group>
    </el-form-item>
    <el-form-item label="生日" prop="birth">
      <el-date-picker v-model="dataForm.birth"  type="date" placeholder="选择日期" value-format="yyyy-MM-dd">  </el-date-picker>
    </el-form-item>
   
    <el-form-item label="职业" prop="job">
      <el-input v-model="dataForm.job" placeholder="职业" style="width:300px"></el-input>
    </el-form-item>
    <el-form-item label="所在城市" prop="city">
      <el-input v-model="dataForm.city" placeholder="所在城市" style="width:350px"></el-input>
    </el-form-item>
    <el-form-item label="个性签名" prop="sign">
      <el-input v-model="dataForm.sign" placeholder="个性签名" style="width:350px"></el-input>
    </el-form-item>
    <el-form-item label="用户来源" prop="sourceType">
      <el-input v-model="dataForm.sourceType" placeholder="用户来源" style="width:300px"></el-input>
    </el-form-item>
    <el-form-item label="积分" prop="integration">
      <el-input-number v-model="dataForm.integration" :min="0"  placeholder="积分"></el-input-number>
    </el-form-item>
    <el-form-item label="成长值" prop="growth">
      <el-input-number v-model="dataForm.growth" :min="0"  placeholder="成长值"></el-input-number>
    </el-form-item>
    <el-form-item label="启用状态">
        <el-switch v-model="dataForm.status" active-color="#13ce66" inactive-color="#ff4949" :active-value="1"  :inactive-value="0"> </el-switch>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import {addMember,getMember, putMember ,upload} from '@/api/member/member'
import {fetchMemberLevelList} from '@/api/member/memberLevel'
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          levelId: undefined,
          username: '',
          password: '',
          nickname: '',
          mobile: '',
          email: '',
          header: '',
          gender: undefined,
          birth: '',
          city: '',
          job: '',
          sign: '',
          sourceType: '',
          integration: '',
          growth: '',
          status: 1,
        },
        levelList: [],
        file: "",
        fileList: [],
        dataRule: {
          levelId: [
            { required: true, message: '会员等级不能为空', trigger: 'blur' }
          ],
          username: [
            { required: true, message: '用户名不能为空', trigger: 'blur' }
          ],
          nickname: [
            { required: true, message: '昵称不能为空', trigger: 'blur' }
          ],
          mobile: [
            { required: true, message: '手机号码不能为空', trigger: 'blur' }
          ]
         
        }
      }
    },
    created() {
      //会员等级列表数据获取
         fetchMemberLevelList().then((response) => {
            this.levelList = response.data.data.records
         })
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            getMember(this.dataForm.id).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.levelId = data.data.levelId
                this.dataForm.username = data.data.username
                this.dataForm.password = data.data.password
                this.dataForm.nickname = data.data.nickname
                this.dataForm.mobile = data.data.mobile
                this.dataForm.email = data.data.email
                this.dataForm.header = data.data.header
                this.dataForm.gender = data.data.gender
                this.dataForm.birth = data.data.birth
                this.dataForm.city = data.data.city
                this.dataForm.job = data.data.job
                this.dataForm.sign = data.data.sign
                this.dataForm.sourceType = data.data.sourceType
                this.dataForm.integration = data.data.integration
                this.dataForm.growth = data.data.growth
                this.dataForm.status = data.data.status
                let obj = new Object();
                obj.url = data.data.header;
                this.fileList.push(obj);
              }
            })
          }
        })
      },

      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
              let member = {
                                'id': this.dataForm.id || undefined,
                                'levelId': this.dataForm.levelId,
                                'username': this.dataForm.username,
                                'password': this.dataForm.password,
                                'nickname': this.dataForm.nickname,
                                'mobile': this.dataForm.mobile,
                                'email': this.dataForm.email,
                                'header': this.dataForm.header,
                                'gender': this.dataForm.gender,
                                'birth': this.dataForm.birth,
                                'city': this.dataForm.city,
                                'job': this.dataForm.job,
                                'sign': this.dataForm.sign,
                                'sourceType': this.dataForm.sourceType,
                                'integration': this.dataForm.integration,
                                'growth': this.dataForm.growth,
                                'status': this.dataForm.status,
                                'createTime': this.dataForm.createTime
                            }
            if(!this.dataForm.id){
                addMember(member).then(({data}) => {
                if (data && data.code === 0) {
                    this.fileList= [];
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
                putMember(member).then(({data}) => {
                if (data && data.code === 0) {
                    this.fileList= [];
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
      },


    //上传请求
    submitUpload(){
        upload(this.file.raw).then(response=>{
           this.dataForm.header = response.data;
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
        this.dataForm.header = '';
    },
    
    //文件超出个数限制时的钩子
    limitExceeded(file, fileList){
            this.$message.error('最多只能上传一张图片！')
    },

    }
  }
</script>

<style lang="scss" scoped>
   /deep/.el-dialog__footer {
       text-align: center;
   }
</style>