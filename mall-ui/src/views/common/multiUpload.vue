<template>
  <div>
    <el-upload
      action="/resources/upload/uploadProduct"
      :data="dataObj"
      list-type="picture-card"
      :file-list="fileList"
      :before-upload="beforeUpload"
      :auto-upload="false"
      :on-remove="handleRemove"
      :on-success="handleUploadSuccess"
      :on-preview="handlePreview"
      :limit="maxCount"
      :on-exceed="handleExceed"
    >
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt/>
    </el-dialog>
  </div>
</template>
<script>

  export default {
    name: "multiUpload",
    props: {
      //图片属性数组
      value: Array,
      //最大上传图片数量
      maxCount: {
        type: Number,
        default: 30
      }
    },
    data() {
      return {
        dataObj: {
          policy: "",
          signature: "",
          key: "",
          ossaccessKeyId: "",
          dir: "",
          host: "",
          uuid: ""
        },
        dialogVisible: false,
        dialogImageUrl: null
      };
    },
    computed: {
      fileList() {
        let fileList = [];
        for (let i = 0; i < this.value.length; i++) {
          fileList.push({url: this.value[i]});
        }

        return fileList;
      }
    },
    mounted() {
    },
    methods: {
    /**获取uuid*/
      getUUID() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
            return (c === 'x' ? (Math.random() * 16 | 0) : ('r&0x3' | '0x8')).toString(16)
        })
      },
      emitInput(fileList) {
        let value = [];
        for (let i = 0; i < fileList.length; i++) {
          value.push(fileList[i].url);
        }
        this.$emit("input", value);
      },

    //删除文件钩子
      handleRemove(file, fileList) {
        this.emitInput(fileList);
      },
    //点击文件列表中已上传的文件时的钩子
      handlePreview(file) {
        this.dialogVisible = true;
        this.dialogImageUrl = file.url;
      },
    //上传文件之前的钩子，参数为上传的文件，若返回 false 或者返回 Promise 且被 reject，则停止上传。
      beforeUpload(file) {
        let _self = this;
       
      },
    //文件上传成功时的钩子
      handleUploadSuccess(res, file) {
        this.fileList.push({
          name: file.name,
          // url: this.dataObj.host + "/" + this.dataObj.dir + "/" + file.name； 替换${filename}为真正的文件名
          url: this.dataObj.host + "/" + this.dataObj.key.replace("${filename}", file.name)
        });
        this.emitInput(this.fileList);
      },
    //文件超出个数限制时的钩子
      handleExceed(files, fileList) {
        this.$message({
          message: "最多只能上传" + this.maxCount + "张图片",
          type: "warning",
          duration: 1000
        });
      }
    }
  };
</script>
<style>
</style>


