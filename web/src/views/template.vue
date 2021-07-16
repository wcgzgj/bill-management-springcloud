<!--模板页面，不会在主页上显示-->
<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-breadcrumb :style="{ margin: '16px 0' }"><!--面包屑导航目前做不了，就把空间先留下了--></a-breadcrumb>
        <div :style="{ background: '#fff', padding: '24px', minHeight: '380px' }">

            <!--内容加载，由loading(true/false)决定-->
            <a-spin :spinning="loading">


                <!--1、下拉选择框的模板-->
                <!--v-model:value="bill.typeId" 和后台的数据绑定，其 value 值，也会影响选择框的默认选中内容-->
                类型:
                <a-select
                        v-model:value="bill.typeId"
                        style="width: 140px"
                        @focus="focus"
                        ref="select"
                >
                    <!--使用v-for，遍历从后端获取的数据列表-->
                    <a-select-option v-for="type in types" :value="type.id">{{type.name}}</a-select-option>
                </a-select>


                <!--2、table 的模板-->
                <!--table的列信息，由 :columns="columns" 决定，:columns="columns"是 js 中定义的列数组-->
                <!--table的分页信息，由 pagination 决定，pagination 在 js 中已经定义-->
                <a-table
                        :columns="columns"
                        :data-source="bills"
                        :pagination="pagination">
                    <template #name="{ text }">
                        <a>{{ text }}</a>
                    </template>

                    <template #action="{ record }">
                      <span>
                        <!--使用 router-link 跳转，带上参数-->
                        <a-button type="primary">
                          <!--跳转到修改页面-->
                            <!--传入账单信息-->
                          <router-link :to="'/update?billId='+record.id">
                            修改
                          </router-link>
                        </a-button>
                      </span>
                        &nbsp;
                        <span>
                            <!--使用 restful 风格的删除-->
                            <a-button type="danger" @click="deleteById(record.id)">删除</a-button>
                        </span>
                    </template>
                </a-table>

            </a-spin>

        </div>
    </a-layout-content>
</template>

<script>
    import { defineComponent,ref } from 'vue';
    import {Tool} from "@/util/Tool";
    import { message } from 'ant-design-vue';
    import {computed} from "@vue/reactivity";
    import store from "@/store";
    import axios from 'axios';
    import {useRoute} from "vue-router";
    import router from '../router';
    import {onMounted} from "@vue/runtime-core";

    export default defineComponent({
        name: 'template',
        setup() {
            /**
             * 页面加载的参数
             */
            const loading = ref(false);

            /**
             * 获取 url 中信息的工具
             */
            const route = useRoute();

            /**
             * 获取 sessionStorage 中信息的模板
             */
            const user = computed(() => store.state.user);


            /**
             * 展示图片的基础路径
             */
            const basePicPath=ref("");
            basePicPath.value+=process.env.VUE_APP_SERVER+"/disPic/";

            /**
             * 分页的基本格式
             */
            const pagination = ref( {
                onChange: page => {
                    console.log("点击的页码为:"+page);
                    // bill.value.pageNum=page;
                    // onSearch(page);
                },
                pageSize: 5,
                total: 0
            });


            /**
             * axios get 的基本格式
             * 传输参数，需要使用 params 形式
             */
            axios.get("/uri",{
                params:{
                    // pageNum:bill.value.pageNum,
                    // pageSize:bill.value.pageSize,
                    // startDate:bill.value.startDate,
                    // endDate:bill.value.endDate,
                    // title:bill.value.title,
                    // typeId:bill.value.typeId
                }
            }).then(resp=> {
                const data = resp.data;
                if (data.success) {
                    console.log("查询数据成功");
                    console.log("查询出的数据条数为:"+data.content.list.length);
                    // bills.value=data.content.list; 为表格/列表中的数据赋值
                    console.log("传来的total值为:"+data.content.total);
                    // 修改分页信息中的 total 信息
                    pagination.value.total=data.content.total;
                } else {
                    message.error(data.message);
                }
            })


            /**
             * axios post 基本格式
             * 传输参数，必须使用封装好了的数据类型（如下面的 bill.value）
             * 后端接口，要加上 @RequestBody 注解（将 json 形式的数据自动转换）
             */
            axios.post("/uri",
                // bill.value
            ).then(resp=> {
                const data = resp.data;
                if (data.success) {
                    message.success("修改成功");
                    // 修改成功，返回首页
                    router.push("/");
                } else {
                    message.error(data.message);
                }
            })


            /**
             * onMounted 基本格式
             */
            onMounted(()=>{

            });

            return {
                loading
            };
        },

    });
</script>

<style scoped>

    .ant-layout-header {
        background-color: transparent !important;
        padding: 20px !important;
    }

    .ant-layout-footer {
        background-color: transparent !important;
        padding: 20px !important;
    }

    .ant-layout-content {
        background-color: transparent !important;
        padding: 20px !important;
    }

</style>