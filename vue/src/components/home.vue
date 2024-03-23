<template>
    <div>
      <img src="../assets/signbg.jpg">
    </div>
</template>

<script>
    export default {
        name: "home",
        data(){
            return{
                account:''
            }
        },
        created() {
            let account=sessionStorage.getItem('account')
            this.account=account
        }
    }
</script>

<style scoped>

</style>
