<template>

    <a-layout-content style="padding: 0 50px">
      <a-breadcrumb style="margin: 16px 0"></a-breadcrumb>
      <a-layout style="padding: 24px 0; background: #fff">

        <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">

          <h1 style="font-size: 30px">账单列表</h1>

          类型:
          <a-select
                  v-model:value="bill.typeId"
                  style="width: 140px"
                  @focus="focus"
                  ref="select"
          >
            <!--这个信息，到时候要从数据库中动态获取-->
            <!--<a-select-option value="0">全部</a-select-option>-->
            <!--<a-select-option value="1">收入</a-select-option>-->
            <!--<a-select-option value="2" >支出</a-select-option>-->
            <!--<a-select-option value="3">借入</a-select-option>-->
            <!--<a-select-option value="4">借出</a-select-option>-->
            <!--<a-select-option value="0">&#45;&#45;选择类型&#45;&#45;</a-select-option>-->
            <a-select-option v-for="type in types" :value="type.id">{{type.name}}</a-select-option>
          </a-select>

          &nbsp;
          &nbsp;

          开始时间:
          <a-date-picker v-model:value="bill.startDate" placeholder="开始时间"/>

          &nbsp;
          &nbsp;
          结束时间:
          <a-date-picker v-model:value="bill.endDate" placeholder="结束时间"/>

          &nbsp;
          &nbsp;

          <a-button type="primary" :onclick="onSearch">查询</a-button>
          &nbsp;
          &nbsp;
          <a-button type="primary" :onclick="reset">重置</a-button>
          &nbsp;
          &nbsp;
          <a-button type="primary">
            <router-link to="/add">
              添加
            </router-link>
          </a-button>

          <br/>
          <br/>


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
import router from '../router';
import {onMounted} from "@vue/runtime-core";

const columns = [
  {
    title: '标题',
    dataIndex: 'title',
    key: 'title',
  },
  {
    title: '时间',
    dataIndex: 'billTimeStr',
    key: 'billTimeStr',
  },
  {
    title: '金额',
    dataIndex: 'price',
    key: 'price',
  },
  {
    title: '类别',
    key: 'billTypeName',
    dataIndex: 'billTypeName',
  },
  {
    title: '说明',
    key: 'explain',
    dataIndex: 'explain',
  },
  {
    title: '操作',
    key: 'action',
    slots: {
      customRender: 'action',
    },
  },
];

export default defineComponent({

  name: 'Home',


  setup() {
    const pagination = ref( {
      onChange: page => {
        console.log("点击的页码为:"+page);
        bill.value.pageNum=page;
        onSearch(page);
      },
      pageSize: 5,
      total: 0
    });

    /**
     * bill 是待查询的数据
     * 包括模糊查询的信息
     * 分页信息
     */
    const bill = ref({
      id:"",
      title:"",
      startDate:"",
      endDate:"",
      typeId:'0',
      pageNum:1,
      pageSize:5
    });

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
     * 获取所有账单信息
     * 包括分页信息、模糊查询
     * @param id
     */
    const bills = ref([]);


    /**
     * 根据传入的 账单 id 进行删除
     * @param id
     */
    const deleteById = (id) => {
      console.log("传入的账单id为:"+id);
      axios.delete("/bill/delete/"+id).then(resp=> {
        const data = resp.data;
        if (data.success) {
          message.success("删除成功");
          getTypes();
          onSearch();
        } else {
          message.error(data.message);
        }
      })
    }


    /**
     * 点击，就会去查找的函数
     * 传入查找信息
     */
    const onSearch = () => {
      console.log("准备去查找账信息！");
      axios.get("/bill/list",{
        params:{
          pageNum:bill.value.pageNum,
          pageSize:bill.value.pageSize,
          startDate:bill.value.startDate,
          endDate:bill.value.endDate,
          title:bill.value.title,
          typeId:bill.value.typeId
        }
      }).then(resp=> {
        const data = resp.data;
        if (data.success) {
          console.log("查询数据成功");
          console.log("查询出的数据条数为:"+data.content.list.length);
          bills.value=data.content.list;
          console.log("传来的total值为:"+data.content.total);
          pagination.value.total=data.content.total;
        } else {
          message.error(data.message);
        }
      })
    }

    /**
     * 重置按钮
     * 清空之前选择的所有查询条件
     */
    const reset = () => {
      /**
       * id:"",
       title:"",
       startDate:"",
       endDate:"",
       typeId:0,
       pageNum:1,
       pageSize:5
       */
      bill.value.startDate="";
      bill.value.endDate="";
      bill.value.typeId=0;
    }


    /**
     * 初始状态下，查询出所有账单信息（在选择框中显示）
     * 和所有的账单信息（在 table 中显示）
     */
    onMounted(()=>{
      getTypes();
      onSearch();
    });

    return {
      columns,
      bill,
      bills,
      types,
      deleteById,
      pagination,
      onSearch,
      reset
    }
  },


});
</script>
