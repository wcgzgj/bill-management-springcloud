<!--更新账目页面-->
<template>
    <a-layout-content style="padding: 0 50px">
        <a-breadcrumb style="margin: 16px 0"></a-breadcrumb>
        <a-layout style="padding: 24px 0; background: #fff">

            <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">

                <h1 style="font-size: 30px">账单修改</h1>

                类型:
                <a-select
                        v-model:value="bill.typeId"
                        style="width: 140px"
                        @focus="focus"
                        ref="select"
                >
                    <a-select-option v-for="type in types" :value="type.id">{{type.name}}</a-select-option>
                </a-select>

                <br/>
                <br/>
                标题:
                <a-input v-model:value="bill.title" placeholder="标题"  />

                <br/>
                <br/>
                日期:
                <a-input v-model:value="bill.billTimeStr" placeholder="日期"  />

                <br/>
                <br/>
                金额:
                <a-input v-model:value="bill.price" placeholder="金额"  />

                <br/>
                <br/>
                说明:
                <a-input v-model:value="bill.explain" placeholder="说明"  />

                <br/>
                <br/>

                <a-button type="primary" @click="save">保存</a-button>
                &nbsp;
                &nbsp;
                <a-button >
                    <router-link to="/">
                        返回
                    </router-link>
                </a-button>

            </a-layout-content>
        </a-layout>
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
    import router from '../../router';
    import {onMounted} from "@vue/runtime-core";
    export default {
        name: "Update",
        setup() {
            const route = useRoute();

            /**
             * 获取 url 中包含的 billId
             * @type {string | LocationQueryValue[]}
             */
            const billId = route.query.billId;

            const bill = ref({
                typeId:0,
                title:"",
                billTimeStr:"",
                price:"",
                explain:"",
                id:billId
            });

            /**
             * 搜索账单信息
             * 将信息回显在前端
             */
            const searchBillInfo = () => {
                axios.get("/bill/selectById/"+billId).then(resp=> {
                    const data = resp.data;
                    if (data.success) {
                        /**
                         * 查询成功，数据回填
                         */
                        bill.value=data.content;

                    } else {
                        message.error(data.message);
                    }
                })
            }


            /**
             * 获取所有账单类型
             */
            const types = ref([]);
            const getTypes = () => {
                axios.get("/billType/list").then(resp=>{
                    const data = resp.data;
                    if (data.success) {
                        console.log("账单类型查询成功");
                        types.value=data.content;
                    } else {
                        message.error(data.message);
                    }
                })
            }


            /**
             * 保存修改信息
             */
            const save = () => {
                axios.post("/bill/save",bill.value).then(resp=> {
                    const data = resp.data;
                    if (data.success) {
                        message.success("修改成功");
                        // 修改成功，返回首页
                        router.push("/");
                    } else {
                        message.error(data.message);
                    }
                })
            }


            onMounted(()=>{
                searchBillInfo();
                getTypes();
            });



            return {
                bill,
                searchBillInfo,
                types,
                getTypes,
                save
            };
        },
    }
</script>

<style scoped>

</style>