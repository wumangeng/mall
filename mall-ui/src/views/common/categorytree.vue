<template>
    <div>
        <el-input placeholder="输入关键字进行过滤" v-model="filterText"></el-input>
        <el-tree
        :data="menus"
        :props="defaultProps"
        node-key="catId"
        ref="menuTree"
        @node-click="handleTreeNodeClick"
        :filter-node-method="filterNode"
        :highlight-current="true"
        >
        </el-tree>
    </div>
</template>

<script>
import { listWithTree } from "@/api/product/category";

    export default {
        data() {
           return {
                filterText: "",
                menus: [],
                expandedKey: [],
                defaultProps: {
                    children: "children",
                    label: "name",
                },
            };
        },
        created() {
            this.getMenu();
        },
        //监控data中的数据变化
        watch: {
            filterText(val) {
                this.$refs.menuTree.filter(val);
            }
        },
        methods: {
             //树节点过滤
            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            },
           // 查询树形分类列表
            getMenu() {
                listWithTree().then((response) => {
                    this.menus = response.data.data;
                });
            },
               //节点被点击时触发(事件派发到子组件)
            handleTreeNodeClick(data,node,component){
                 //向父组件发送事件；
                this.$emit("handleTreeNodeClick",data,node,component);
            },
        }
    }
</script>

<style lang="scss" scoped>
</style>
