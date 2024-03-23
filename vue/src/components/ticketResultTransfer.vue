<template>
<div>
  <el-table :data="tableData"
            border
            :header-cell-style="{background:'#117edd',color:'white'}"
            :span-method="arraySpanMethod"
            :cell-style="cellStyle"
            v-loading="tfload"
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
        <div v-if="scope.$index%3!=0">
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
        </div>
        <div v-else>
          <el-row>
            <el-col :span="2" ><el-row>&nbsp;</el-row><el-row>{{scope.row.index}}</el-row><el-row>&nbsp;</el-row></el-col>
            <el-col :span="1"><el-row>&nbsp;</el-row><el-row>{{scope.row.StartStation}}</el-row></el-col>
            <el-col :span="2"><el-row>&nbsp;</el-row><el-row>{{scope.row.Depart1}}开</el-row></el-col>
            <el-col :span="2">
              <el-row style="text-align: center">{{scope.row.TrainNo1}}</el-row>
              <el-row>——————</el-row>
              <el-row style="text-align: center">{{scope.row.duration1}}</el-row>
            </el-col>
            <el-col :span="2"><el-row>&nbsp;</el-row><el-row style="text-align: center">{{scope.row.Arrival1}}到</el-row></el-col>

            <el-col :span="4">
              <el-row style="text-align: center">{{scope.row.Name}}</el-row>
              <el-row style="text-align: center">{{scope.row.duration}}</el-row>
            </el-col>

            <el-col :span="2"><el-row>&nbsp;</el-row><el-row>{{scope.row.Depart2}}开</el-row></el-col>
            <el-col :span="2">
              <el-row style="text-align: center">{{scope.row.TrainNo2}}</el-row>
              <el-row>——————</el-row>
              <el-row style="text-align: center">{{scope.row.duration2}}</el-row>
            </el-col>
            <el-col :span="2"><el-row>&nbsp;</el-row><el-row style="text-align: center">{{scope.row.Arrival2}}到</el-row></el-col>

            <el-col :span="2"><el-row>&nbsp;</el-row><el-row style="text-align: center">{{scope.row.EndStation}}</el-row></el-col>
            <el-col :span="2"><el-row>&nbsp;</el-row><el-row style="text-align: center">总历时：{{scope.row.sumduration}}</el-row></el-col>
            <el-col :span="2"><el-row>&nbsp;</el-row><el-row style="text-align: center"></el-row></el-col>
            <el-col :span="2"><el-row>&nbsp;</el-row><el-row style="text-align: center"></el-row></el-col>
            <el-col :span="2"><el-row>&nbsp;</el-row><el-row style="text-align: center"></el-row></el-col>

          </el-row>
        </div>
      </template>
    </el-table-column>

    <el-table-column label="出发站/到达站" :render-header="renderheader">
      <template slot-scope="scope">
        <el-tag>{{scope.row.StartStation}}</el-tag>
        <el-tag>{{scope.row.EndStation}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="出发时间/到达时间" :render-header="renderheader">
      <template slot-scope="scope">
        <el-row>{{scope.row.Depart}}</el-row>
        <el-row>{{scope.row.Arrival}}</el-row>
      </template>
    </el-table-column>
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
    <el-table-column label="操作" prop="">
     <template slot-scope="scope">
       <el-button type="primary" @click="handleBook(scope.$index,scope.row)">预订</el-button>
     </template>
    </el-table-column>

  </el-table>

  <div class="block">
    <el-pagination
      layout="prev, pager, next"
      page-size="30"
      @current-change="handleCurrentChange"
      :total="total">
    </el-pagination>
  </div>

  <div style="height: 200px">&nbsp;</div>

</div>
</template>

<script>
    export default {
        name: "ticketResultTransfer",
        data() {
            return {
                tfload:true,
                total:1000,
                table:[],
                tableData:[
                    // {
                    //     StartStation:'应县',
                    //     EndStation:'济南东',
                    //     duration1:'1小时44分',
                    //     duration2:'2小时33分',
                    //     sumduration:'8小时',
                    //     index:1,
                    //     Depart1:'11:13',
                    //     Arrival1:'14:50',
                    //     Depart2:'11:31',
                    //     Arrival2:'17:52',
                    //
                    //
                    //     Name:"临汾西",
                    //     TrainNo1:"D2517",
                    //     TrainNo2:"G2621",
                    //     // Arrival:"14:50",
                    //     // Departure:"11:31",
                    //     // duration:1241
                    //
                    //     duration:'1小时'
                    // },
                    // {StartStationNo:2,EndStationNo:10,Depart:"11:13",Arrival:"14:50",BusinessPrice:0.0,FirstPrice:222.0,SecondPrice:139.0,SoftSleeperUpPrice:0.0,SoftSleeperDownPrice:0.0,HardSleeperUpPrice:0.0,HardSleeperMidPrice:0.0,HardSleeperDownPrice:0.0,SoftSeatPrice:0.0,HardSeatPrice:0.0,NoSeatPrice:0.0,detail:[{TrainNo:"D2517",Name:"应县",StationNo:2,Arrival:"11:11",Departure:"11:13"},{TrainNo:"D2517",Name:"山阴",StationNo:3,Arrival:"11:25",Departure:"11:27"},{TrainNo:"D2517",Name:"原平西",StationNo:4,Arrival:"12:09",Departure:"12:11"},{TrainNo:"D2517",Name:"忻州西",StationNo:5,Arrival:"12:25",Departure:"12:27"}],"StartStation":"应县","EndStation":"临汾西","TrainNo":"D2517","isPriceShow":false,"SoftSeatNum":0,"SecondNum":1400,"FirstNum":104,"HardSeatNum":0,"BusinessNum":0,"NoSeatNum":0,"HardSleeperMidNum":0,"HardSleeperDownNum":0,"SoftSleeperUpNum":0,"SoftSleeperDownNum":0,"HardSleeperUpNum":0},
                    // {StartStationNo:4,EndStationNo:17,Depart:"11:31",Arrival:"17:12",BusinessPrice:0.0,FirstPrice:406.0,SecondPrice:254.0,SoftSleeperUpPrice:0.0,SoftSleeperDownPrice:0.0,HardSleeperUpPrice:0.0,HardSleeperMidPrice:0.0,HardSleeperDownPrice:0.0,SoftSeatPrice:0.0,HardSeatPrice:0.0,NoSeatPrice:0.0,detail:[{TrainNo:"G2621",Name:"临汾西",StationNo:4,Arrival:"11:29",Departure:"11:31"},{TrainNo:"G2621",Name:"霍州东",StationNo:5,Arrival:"11:55",Departure:"11:57"},{TrainNo:"G2621",Name:"介休东",StationNo:6,Arrival:"12:19",Departure:"12:21"},{TrainNo:"G2621",Name:"太原南",StationNo:7,Arrival:"12:57",Departure:"13:08"}],"StartStation":"临汾西","EndStation":"济南东","TrainNo":"G2621","isPriceShow":false,"SoftSeatNum":0,"SecondNum":700,"FirstNum":104,"HardSeatNum":0,"BusinessNum":0,"NoSeatNum":0,"HardSleeperMidNum":0,"HardSleeperDownNum":0,"SoftSleeperUpNum":0,"SoftSleeperDownNum":0,"HardSleeperUpNum":0}
                ]
            }
        },
        methods:{
            handleCurrentChange(val) {
                // this.tableData=this.table.slice((val-1)*30,val*30)
                this.tfload=true
                let StartPoint = ""+ sessionStorage.getItem('StartPoint');
                let EndPoint = ""+ sessionStorage.getItem('EndPoint');
                let date = ""+ sessionStorage.getItem('date');
                let url="http://localhost:8181/ticketResultTransfer/"+StartPoint+"/"+EndPoint+"/"+date+"/"+val
                url=url.replace(/"/g,"")
                console.log(url)
                const that=this
                this.$axios.get(url).then(function (response) {
                    console.log(response);
                    that.table=response.data.ans
                    that.tableData=that.table
                    that.total=response.data.total
                    that.tfload=false
                }),function (err) {
                    console.log(err);
                }
            },
            handleBook(index,row){
              index=index-1
                var d=new Date();
                var t=this.tableData[index+1].Depart.split(":")
                var date1=this.tableData[index+1].date.toString().split('-')
                if(date1[0]==d.getFullYear()&&date1[1]==(d.getMonth()+1)&&date1[2]==d.getDate()){
                    if(d.getHours()>t[0]||(d.getHours()==t[0]&&d.getMinutes()>=(t[1]-20))){
                        alert("已超出购票时间")
                        return;
                    }
                }
                let account=sessionStorage.getItem('account')
                if(account==null||account=='null') alert("请先登录，再购票")
                else{
                    this.$router.push({ path: '/buyTransferTicket',query:{tf:true,row1:this.tableData[index],row2:this.tableData[index+1],row3:this.tableData[index+2]}})
                }
            },
            renderheader(h, { column, $index }) {
                return h('span', {}, [
                    h('span', {}, column.label.split('/')[0]),
                    h('br'),
                    h('span', {}, column.label.split('/')[1])
                ])
            },
            arraySpanMethod({ row, column, rowIndex, columnIndex }) {
                if (rowIndex % 3 === 0) {
                    return[1,13];
                }
                if(rowIndex%3==1&&columnIndex==12)
                    return [2,1];
            },
            cellStyle({row, column, rowIndex, columnIndex}) {
                if (rowIndex%3== 0 && columnIndex==0) {
                    return "display:none";
                }
            }
        },
        created() {
            let StartPoint = ""+ sessionStorage.getItem('StartPoint');
            let EndPoint = ""+ sessionStorage.getItem('EndPoint');
            let date = ""+ sessionStorage.getItem('date');
            console.log(StartPoint);console.log(EndPoint);console.log(date)
            let url="http://localhost:8181/ticketResultTransfer/"+StartPoint+"/"+EndPoint+"/"+date+"/1"
            url=url.replace(/"/g,"")
            console.log(url)
            const that=this
            this.$axios.get(url).then(function (response) {
                console.log(response);
                that.table=response.data.ans
                that.tableData=that.table
                that.total=response.data.total
                that.tfload=false
            }),function (err) {
                console.log(err);
            }
        }
    }
</script>

<style scoped>

</style>
