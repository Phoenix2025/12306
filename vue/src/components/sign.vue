<template>
<div>
  <div>
    <img src="../assets/signhead.jpg" width="413" height="60">
  </div>
<!--  <img src="../assets/signbg.jpg" style="z-index: 1;left:0px;top:0px">-->
  <div style="width: 100%;height:700px" :style="conTop">
      <div style="float:right;font-weight: bold;color: black;margin-top: 20px">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="账号">
            <el-input v-model="form.account"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password"></el-input>
          </el-form-item>
          <el-form-item >
            <el-button type="primary" @click="onSubmit">登录</el-button>
            <el-button type="primary" @click="">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
  </div>

</div>
</template>

<script>
    export default {
        name: "sign",
        data(){
            return {
                form: {
                    account:'',
                    password:''
                },
                conTop: {
                    backgroundImage:'url(' + require('../assets/signbg.jpg') + ')',
                    backgroundRepeat:'no-repeat',
                    // backgroundSize: cover
                 }
            }
        },
        methods: {
            onSubmit() {
                let url="http://localhost:8181/tfSign/"+this.form.account+"/"+this.form.password
                url=url.replace(/"/g,"")
                console.log(url)
                const that=this
                this.$axios.get(url).then(function (response) {
                    console.log(response);
                    let tf=response.data
                    if(tf){
                        sessionStorage.setItem('account', JSON.stringify(that.form.account));
                        that.$router.push({ path: '/home' });
                    }
                    else{
                        alert('账户或密码输入错误')
                    }
                }),function (err) {
                    console.log(err);
                }
            }
        }
    }
</script>

<style scoped>

</style>
