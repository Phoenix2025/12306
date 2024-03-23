<template>
<div>
  <el-table class="el-table" :data="tableData" border :header-cell-style="{background:'#117edd',color:'white'}"
  :row-class-name="tableRowClassName" :row-style="rowStyle" v-loading="tfload"
  >
    <el-table-column type="expand" width="100%">
      <template slot-scope="props">
        <el-row style="font-size: 15px" :gutter="2">
          <el-col :span="3" v-if="props.row.BusinessPrice!=0">商务座：￥{{props.row.BusinessPrice}}</el-col>
          <el-col :span="3" v-if="props.row.FirstPrice!=0">一等座：￥{{props.row.FirstPrice}}</el-col>
          <el-col :span="3" v-if="props.row.SecondPrice!=0">二等座：￥{{props.row.SecondPrice}}</el-col>
          <el-col :span="3" v-if="props.row.SoftSleeperUpPrice!=0">软卧：￥{{props.row.SoftSleeperUpPrice}}</el-col>
          <el-col :span="3" v-if="props.row.HardSleeperUpPrice!=0">硬卧：￥{{props.row.HardSleeperUpPrice}}</el-col>
          <el-col :span="3" v-if="props.row.SoftSeatPrice!=0">软座：￥{{props.row.SoftSeatPrice}}</el-col>
          <el-col :span="3" v-if="props.row.HardSeatPrice!=0">硬座：￥{{props.row.HardSeatPrice}}</el-col>
          <el-col :span="3" v-if="props.row.NoSeatPrice!=0">无座：￥{{props.row.NoSeatPrice}}</el-col>
        </el-row>
      </template>
    </el-table-column>

    <el-table-column label="车次">
      <template slot-scope="scope">
        <el-popover trigger="click" placement="top">
          <el-table :data="scope.row.detail" height="250" border stripe>
            <el-table-column prop="StationNo" label="站序" width="60"></el-table-column>
            <el-table-column prop="Name" label="站名" width="80"></el-table-column>
            <el-table-column prop="Arrival" label="到达时间" width="120"></el-table-column>
            <el-table-column prop="Departure" label="出发时间" width="120"></el-table-column>
          </el-table>

          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">{{ scope.row.TrainNo }}</el-tag>
          </div>
        </el-popover>
      </template>
    </el-table-column>
    <el-table-column label="出发站/到达站" :render-header="renderheader">
      <template slot-scope="scope">
        <el-tag>{{scope.row.StartStation}}</el-tag>
        <el-tag>{{scope.row.EndStation}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="出发时间/到达时间" prop="Depart" sortable :render-header="renderheader">
      <template slot-scope="scope">
        <el-row>{{scope.row.Depart}}</el-row>
        <el-row>{{scope.row.Arrival}}</el-row>
      </template>
    </el-table-column>
    <el-table-column label="历时" prop="duration" sortable></el-table-column>
    <el-table-column label="商务座" prop="">
      <template slot-scope="scope">
        <div v-if="scope.row.BusinessPrice==0">--</div>
        <div v-else>{{scope.row.BussinessNum}}</div>
      </template>
    </el-table-column>
    <el-table-column label="一等座" prop="">
      <template slot-scope="scope">
        <div v-if="scope.row.FirstPrice==0">--</div>
        <div v-else>{{scope.row.FirstNum}}</div>
      </template>
    </el-table-column>
    <el-table-column label="二等座" prop="">
      <template slot-scope="scope">
        <div v-if="scope.row.SecondPrice==0">--</div>
        <div v-else>{{scope.row.SecondNum}}</div>
      </template>
    </el-table-column>
    <el-table-column label="软卧" prop="">
      <template slot-scope="scope">
        <div v-if="scope.row.SoftSleeperUpPrice==0">--</div>
        <div v-else>{{scope.row.SoftSleeperUpNum+scope.row.SoftSleeperDownNum}}</div>
      </template>
    </el-table-column>
    <el-table-column label="硬卧" prop="">
      <template slot-scope="scope">
        <div v-if="scope.row.HardSleeperUpPrice==0">--</div>
        <div v-else>{{scope.row.HardSleeperUpNum+scope.row.HardSleeperDownNum+scope.row.HardSleeperMidNum}}</div>
      </template>
    </el-table-column>
    <el-table-column label="软座" prop="">
      <template slot-scope="scope">
        <div v-if="scope.row.SoftSeatPrice==0">--</div>
        <div v-else>{{scope.row.SoftSeatNum}}</div>
      </template>
    </el-table-column>
    <el-table-column label="硬座" prop="">
      <template slot-scope="scope">
        <div v-if="scope.row.HardSeatPrice==0">--</div>
        <div v-else>{{scope.row.HardSeatNum}}</div>
      </template>
    </el-table-column>
    <el-table-column label="无座" prop="">
      <template slot-scope="scope">
        <div v-if="scope.row.NoSeatPrice==0">--</div>
        <div v-else>{{scope.row.NoSeatNum}}</div>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button type="primary" size="small" @click="book(scope.$index,scope.row)">预订</el-button>
      </template>
    </el-table-column>

  </el-table>

  <div class="block">
  <el-pagination
    layout="prev, pager, next"
    @current-change="handleCurrentChange"
    :total="total">
  </el-pagination>
</div>

  <div style="height: 200px">&nbsp;</div>

</div>



</template>

<style>
  .el-table{
    width:99.9%!important;
  }
  .el-table--enable-row-hover .el-table__body tr:hover>td{
       background: transparent;
     }

  .el-table .warning-row {
    background: #ebf5f9;
  }

  .el-table .success-row {
    background: #d6dfe3;
  }

  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>

<script>
    export default {
        data() {
            return {
                tfload:true,
                total:0,
                ta:[1],
                table:[],
                tableData:[
                // {TrainNo:'1133',StartStation:'应县站',EndStation: '济南站',Depart:'18:22',Arrival:'18:26',BusinessPrice:0,FirstPrice:0,
                //     SecondPrice:0,SoftSleeperUpPrice:99.5,SoftSleeperDownPrice:105.5,HardSleeperUpPrice:64.5,
                //     HardSleeperMidPrice:69.5,
                //     HardSleeperDownPrice:72.5,
                //     SoftSeatPrice:0,
                //     HardSeatPrice:18.5,
                //     NoSeatPrice:18.5,
                //     BussinessNum:0,
                //     FirstNum:0,
                //     SecondNum:0,
                //     SoftSleeperUpNum:2,
                //     SoftSleeperDownNum:6,
                //     HardSleeperUpNum:10,
                //     HardSleeperMidNum:11,
                //     HardSleeperDownNum:12,
                //     SoftSeatNum:0,
                //     HardSeatNum:100,
                //     NoSeatNum:2,
                //     isPriceShow:false
                // },
                // {
                //     TrainNo:'1134',
                //     StartStation:'应县站',
                //     EndStation: '济南站',
                //     Depart:'18:22',
                //     Arrival:'18:26',
                //     BusinessPrice:0,
                //     FirstPrice:0,
                //     SecondPrice:0,
                //     SoftSleeperUpPrice:99.5,
                //     SoftSleeperDownPrice:105.5,
                //     HardSleeperUpPrice:64.5,
                //     HardSleeperMidPrice:69.5,
                //     HardSleeperDownPrice:72.5,
                //     SoftSeatPrice:0,
                //     HardSeatPrice:18.5,
                //     NoSeatPrice:18.5,
                //     BussinessNum:0,
                //     FirstNum:0,
                //     SecondNum:0,
                //     SoftSleeperUpNum:2,
                //     SoftSleeperDownNum:6,
                //     HardSleeperUpNum:10,
                //     HardSleeperMidNum:11,
                //     HardSleeperDownNum:12,
                //     SoftSeatNum:0,
                //     HardSeatNum:100,
                //     NoSeatNum:2,
                //     isPriceShow:false
                // }
                ],
                tfChange:false,
                oldrow:{}
            }
        },
        methods:{
            handleCurrentChange(val) {
                this.tableData=this.table.slice((val-1)*10,val*10)
            },
            renderheader(h, { column, $index }) {
                return h('span', {}, [
                    h('span', {}, column.label.split('/')[0]),
                    h('br'),
                    h('span', {}, column.label.split('/')[1])
                ])
            },
            tableRowClassName({row, rowIndex}) {
                if (rowIndex%2== 1) {
                    return 'warning-row';
                } else {
                    return 'success-row';
                }
            },
            rowStyle({ row, rowIndex}) {
                // return 'height:200px'
            },
            book(index,row){
                var d=new Date();
                var t=row.Depart.split(":")
                var date1=row.date.toString().split('-')
                if(date1[0]==d.getFullYear()&&date1[1]==(d.getMonth()+1)&&date1[2]==d.getDate()){
                    if(d.getHours()>t[0]||(d.getHours()==t[0]&&d.getMinutes()>=t[1])){
                        alert("已超出购票时间")
                        return;
                    }
                }
                let account=sessionStorage.getItem('account')
                if(account==null||account=='null') alert("请先登录，再购票")
                else{
                    if(!this.tfChange) this.$router.push({ path: '/buyDirectTicket',query:{row:row}})
                    else this.$router.push({ path: '/buyDirectTicket',query:{tfChange:true,oldrow:this.oldrow,row:row}})
                }
            }
        },
        created() {
            let StartPoint = ""+ sessionStorage.getItem('StartPoint');
            if(StartPoint==null) return;
            let EndPoint = ""+ sessionStorage.getItem('EndPoint');
            let date = ""+ sessionStorage.getItem('date');
            console.log(StartPoint);console.log(EndPoint);console.log(date)

            this.tfChange=this.$route.query.tfChange
            if(this.tfChange==undefined) this.tfChange=false
            else {
                this.oldrow=this.$route.query.oldrow
            }

            let url="http://localhost:8181/ticketResultDirect/"+StartPoint+"/"+EndPoint+"/"+date
            url=url.replace(/"/g,"")
            console.log(url)
            const that=this
            this.$axios.get(url).then(function (response) {
                console.log(response);
                that.table=response.data
                that.tableData=that.table.slice(0,10)
                that.total=that.table.length
                that.tfload=false
            }),function (err) {
                console.log(err);
            }
        }
    }
</script>
