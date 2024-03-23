<template>
  <div>
    <div>
      <el-row :gutter="20">
        <el-col :span="20"><div class="grid-content bg-purple">
          <img src="../assets/12306.png"height="80" width="218">
        </div></el-col>
        <el-col :span="4"><div class="grid-content bg-purple" style="margin-top: 30px">
         <span v-if="account==null||account=='null'" id="Siginin" @click="toSign">登录 | 注册</span>
         <div v-else>
           <el-dropdown trigger="click" @command="handleCommand">
             <span class="el-dropdown-link"><el-avatar :size="50"> {{account.replace(/"/g,"")}} </el-avatar></span>
             <el-dropdown-menu slot="dropdown">
               <el-dropdown-item command="exit">
                 退出登录
               </el-dropdown-item>
             </el-dropdown-menu>
           </el-dropdown>
         </div>
        </div></el-col>
      </el-row>
    </div>
    <div>
      <el-menu router
        default-active="1"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b">
        <el-menu-item index="/home">首页</el-menu-item>
        <el-menu-item index="/ticket">车票</el-menu-item>
        <el-menu-item index="/ticket">信息查询</el-menu-item>
        <el-menu-item index="/order">我的订单</el-menu-item>
      </el-menu>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
    export default {
        name: "index",
        data(){
            return{
                account:null
            }
        },
        methods:{
            toSign(){
                this.$router.push({ path: '/sign' })
            },
            handleCommand(command) {
                sessionStorage.setItem('account', JSON.stringify(null));
                this.account=null
            },
        },
        created() {
            let account=sessionStorage.getItem('account')
            this.account=account
        }
    }
</script>

<style scoped>

  .bg-purple {
    /*background: #d3dce6;*/
  }
  .grid-content {
    border-radius: 4px;
    min-height: 30px;
  }
  #Siginin:hover{
    cursor: pointer;
    color: red;
  }
</style>
