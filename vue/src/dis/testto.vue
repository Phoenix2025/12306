<template>
<div>
  <h1>testto</h1>
  {{user}}
</div>
</template>

<script>
    export default {
        name: "testto",
        data () {
          return{
              user:'user',
              password:'password'
          }
        },
        mounted() {
            this.user = sessionStorage.getItem('user');
        }
    }
</script>

<style scoped>

</style>
