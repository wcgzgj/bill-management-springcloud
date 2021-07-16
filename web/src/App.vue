<template>
  <a-layout>
    <Header/>
    <router-view/>
    <Footer/>
  </a-layout>
</template>



<script lang="ts">
  import { defineComponent} from 'vue';
  import Footer from './components/Footer.vue'
  import Header from "./components/Header.vue";

  export default defineComponent({
    name: 'Home',
    components: {
      Footer,
      Header
    },
  });
</script>

<style lang="scss">
/*#app {*/
/*  font-family: Avenir, Helvetica, Arial, sans-serif;*/
/*  -webkit-font-smoothing: antialiased;*/
/*  -moz-osx-font-smoothing: grayscale;*/
/*  text-align: center;*/
/*  color: #2c3e50;*/
/*}*/

/*#nav {*/
/*  padding: 30px;*/

/*  a {*/
/*    font-weight: bold;*/
/*    color: #2c3e50;*/

/*    &.router-link-exact-active {*/
/*      color: #42b983;*/
/*    }*/
/*  }*/
/*}*/
</style>
