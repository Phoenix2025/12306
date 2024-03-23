<template>
    <div>
      <el-container style="height: 500px; border: 1px solid #eee">
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
          <el-menu :default-openeds="['1', '2']">
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-message"></i>待出行订单</template>
              <el-menu-item-group>
                <el-menu-item index="1-1" @click="travelOrder">直达</el-menu-item>
                <el-menu-item index="1-2" @click="travelTrasferOrder">换乘</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="2">
              <template slot="title"><i class="el-icon-message"></i>历史订单</template>
              <el-menu-item-group>
                <el-menu-item index="2-1" @click="historyOrder">直达</el-menu-item>
                <el-menu-item index="2-2" @click="historyTrasferOrder">换乘</el-menu-item>
                <el-menu-item index="2-3" @click="handlequit(1)">被改签</el-menu-item>
                <el-menu-item index="2-4" @click="handlequit(2)">被退票</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </el-aside>

        <el-container>
<!--          <el-header style="text-align: right; font-size: 12px">-->
<!--            <span></span>-->
<!--          </el-header>-->
          <el-main>
            <el-table class="el-table" :data="tableData" border style="width: 100%;" v-show="!tfShowTransfer" :row-class-name="tableRowClassName2">
              <el-table-column prop="Id" label="订单号"></el-table-column>
              <el-table-column prop="TrainNo" label="车次"></el-table-column>
              <el-table-column prop="Start" label="起点站"></el-table-column>
              <el-table-column prop="End" label="终点站"></el-table-column>
              <el-table-column prop="Date" label="出行日期" sortable></el-table-column>
              <el-table-column prop="StartTime" label="发车时间"></el-table-column>
              <el-table-column prop="EndTime" label="到达时间"></el-table-column>
              <el-table-column prop="CarriageNo" label="车厢号"></el-table-column>
              <el-table-column prop="SeatNo" label="座位号"></el-table-column>
              <el-table-column label="票价"><template slot-scope="scope">{{'￥'+scope.row.TicketPrice}}</template></el-table-column>
              <el-table-column
                fixed="right"
                label="操作"
                width="100">
                <template slot-scope="scope">
                  <el-row><el-button @click="handleChange(scope.$index,scope.row)" type="text" size="small" v-show="tfShow">改签</el-button></el-row>
                  <el-row><el-button @click="handleDelete(scope.$index,scope.row)" type="text" size="small" v-show="tfShow">退票</el-button></el-row>
                </template>
              </el-table-column>
            </el-table>

            <el-table class="el-table" :data="tableData" border style="width: 100%" v-show="tfShowTransfer" :row-class-name="tableRowClassName" >
              <el-table-column prop="Id" label="订单号"></el-table-column>
              <el-table-column prop="TrainNo" label="车次"></el-table-column>
              <el-table-column prop="Start" label="起点站"></el-table-column>
              <el-table-column prop="End" label="终点站"></el-table-column>
              <el-table-column prop="Date" label="出行日期" sortable></el-table-column>
              <el-table-column prop="StartTime" label="发车时间"></el-table-column>
              <el-table-column prop="EndTime" label="到达时间"></el-table-column>
              <el-table-column prop="CarriageNo" label="车厢号"></el-table-column>
              <el-table-column prop="SeatNo" label="座位号"></el-table-column>
              <el-table-column label="票价"><template slot-scope="scope">{{'￥'+scope.row.TicketPrice}}</template></el-table-column>
              <el-table-column
                fixed="right"
                label="操作"
                width="100">
                <template slot-scope="scope">
                  <el-row><el-button @click="handleChange(scope.$index,scope.row)" type="text" size="small" v-show="tfShow">改签</el-button></el-row>
                  <el-row><el-button @click="handleDelete(scope.$index,scope.row)" type="text" size="small" v-show="tfShow">退票</el-button></el-row>
                </template>
              </el-table-column>
            </el-table>

          </el-main>
        </el-container>
      </el-container>

    </div>
</template>

<script>
    export default {
        name: "order",
        data() {
            return{
                tableData:[
                    // {Id:'1',TrainNo:'1133',CarriageNo:8,SeatNo:'3',Start:'下花园',End:'天镇',Date:'2020-10-22',StartTime:'15:28',EndTime:'18:23',TicketPrice: 35.5}
                ],
                tfShow:true,
                tfShowTransfer:true,
            }
        },
        methods:{
            historyOrder(){
                this.tfShow=false
                this.tfShowTransfer=false
                let account=sessionStorage.getItem('account')
                var d=new Date()
                let date=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate()
                let time=d.getHours()+':'+d.getMinutes()
                let url="http://localhost:8181/historyOrder/"+account+"/"+date+"/"+time
                url=url.replace(/"/g,"")
                console.log(url)
                const that=this
                this.$axios.get(url).then(function (response) {
                    console.log(response);
                    that.tableData=response.data
                }),function (err) {
                    console.log(err);
                }
            },
            historyTrasferOrder(){
                this.tfShow=false
                this.tfShowTransfer=true
                let account=sessionStorage.getItem('account')
                var d=new Date()
                let date=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate()
                let time=d.getHours()+':'+d.getMinutes()
                let url="http://localhost:8181/historyTransferOrder/"+account+"/"+date+"/"+time
                url=url.replace(/"/g,"")
                console.log(url)
                const that=this
                this.$axios.get(url).then(function (response) {
                    console.log(response);
                    that.tableData=response.data
                }),function (err) {
                    console.log(err);
                }
            },
            travelTrasferOrder(){
                this.tfShow=true
                this.tfShowTransfer=true
                let account=sessionStorage.getItem('account')
                var d=new Date()
                let date=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate()
                let time=d.getHours()+':'+d.getMinutes()
                let url="http://localhost:8181/travelTransferOrder/"+account+"/"+date+"/"+time
                url=url.replace(/"/g,"")
                console.log(url)
                const that=this
                this.$axios.get(url).then(function (response) {
                    console.log(response);
                    that.tableData=response.data
                }),function (err) {
                    console.log(err);
                }
            },
            travelOrder(){
                this.tfShow=true
                this.tfShowTransfer=false
                let account=sessionStorage.getItem('account')
                var d=new Date()
                let date=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate()
                let time=d.getHours()+':'+d.getMinutes()
                let url="http://localhost:8181/travelOrder/"+account+"/"+date+"/"+time
                url=url.replace(/"/g,"")
                console.log(url)
                const that=this
                this.$axios.get(url).then(function (response) {
                    console.log(response);
                    that.tableData=response.data
                }),function (err) {
                    console.log(err);
                }
            },
            arraySpanMethod({ row, column, rowIndex, columnIndex }) {
                if(rowIndex%2==0&&columnIndex==10){
                    return [2,1];
                }

            },
            handleChange(index,row) {
                sessionStorage.setItem('ChangeStart', JSON.stringify(row.Start+"站"));
                sessionStorage.setItem('ChangeEnd', JSON.stringify(row.End+"站"));
                this.$router.push({ path: '/ticketChange' ,query:{row:row}});

            },
            handleDelete(index,row) {
                let that=this
                this.$confirm('此操作将永久删除该订单, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let order=""
                    if(this.tfShowTransfer){
                        if(index%2==0) order=this.tableData[index].Id+"|"+this.tableData[index+1].Id
                        else order=this.tableData[index].Id+"|"+this.tableData[index-1].Id
                    }
                    else order=row.Id
                    let url="http://localhost:8181/deleteOrders/"+order
                    url=url.replace(/"/g,"")
                    const that=this
                    this.$axios.get(url).then(function (response) {
                        console.log(response);

                        if(that.tfShowTransfer) that.travelTrasferOrder();
                        else that.travelOrder();

                        that.$message({
                            type: 'success',
                            message: '删除成功!'
                        });

                    }),function (err) {
                        console.log(err);
                    }
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            handlequit(i){
                this.tfShowTransfer=false
                this.tfShow=false
                let account=sessionStorage.getItem('account')
                let url="http://localhost:8181/quit/"+account+"/"+i
                url=url.replace(/"/g,"")
                const that=this
                this.$axios.get(url).then(function (response) {
                    console.log(response);
                    that.tableData=response.data
                }),function (err) {
                    console.log(err);
                }
            },
            tableRowClassName({row, rowIndex}) {
                if(rowIndex%2==1) rowIndex=rowIndex-1
                if ((rowIndex/2)%2==1) {
                    return 'warning-row';
                }
                else {
                    return 'success-row';
                }
            },
            tableRowClassName2({row, rowIndex}) {
                if (rowIndex%2==1) {
                    return 'warning-rowwarning-row';
                }
                else {
                    return 'success-row';
                }
            }
        },
        created() {
            let account=sessionStorage.getItem('account')
            if(account==null||account=='null'){
                alert("请先登录，再查看订单")
                this.$router.push({ path: '/'})
            }
            else {
                // var d=new Date()
                // let date=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate()
                // let time=d.getHours()+':'+d.getMinutes()
                // let url="http://localhost:8181/travelOrder/"+account+"/"+date+"/"+time
                // url=url.replace(/"/g,"")
                // console.log(url)
                // const that=this
                // this.$axios.get(url).then(function (response) {
                //     console.log(response);
                //     that.tableData=response.data
                // }),function (err) {
                //     console.log(err);
                // }
            }
        }
    }
</script>

<style scoped>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
  }


</style>
