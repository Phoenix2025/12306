<template>
    <div>
      <div style="margin-left: 15%;margin-right: 15%;margin-top: 1%;border: #131576 1px solid">
        <div>
          <el-row style="height: 50px;background: #5889ff;color: white;padding-left: 15px;padding-top:13px">车次信息</el-row>
          <div style="border: 1px solid black;margin: 10px;">
            <el-row style="margin-left: 20px;margin-top: 10px">
              <el-col :span="1"><el-row>&nbsp;</el-row><el-row>{{data1.StartStation}}</el-row></el-col>
              <el-col :span="2"><el-row>&nbsp;</el-row><el-row>{{data1.Depart1}}开</el-row></el-col>
              <el-col :span="2">
                <el-row style="text-align: center">{{data1.TrainNo1}}</el-row>
                <el-row>—————</el-row>
                <el-row style="text-align: center">{{data1.duration1}}</el-row>
              </el-col>
              <el-col :span="2"><el-row>&nbsp;</el-row><el-row style="text-align: center">{{data1.Arrival1}}到</el-row></el-col>

              <el-col :span="4">
                <el-row style="text-align: center">{{data1.Name}}</el-row>
                <el-row style="text-align: center">{{data1.duration}}</el-row>
              </el-col>

              <el-col :span="2"><el-row>&nbsp;</el-row><el-row>{{data1.Depart2}}开</el-row></el-col>
              <el-col :span="2">
                <el-row style="text-align: center">{{data1.TrainNo2}}</el-row>
                <el-row>—————</el-row>
                <el-row style="text-align: center">{{data1.duration2}}</el-row>
              </el-col>
              <el-col :span="2"><el-row>&nbsp;</el-row><el-row style="text-align: center">{{data1.Arrival2}}到</el-row></el-col>

              <el-col :span="2"><el-row>&nbsp;</el-row><el-row style="text-align: center">{{data1.EndStation}}</el-row></el-col>
              <el-col :span="4"><el-row>&nbsp;</el-row><el-row style="text-align: center">总历时：{{data1.sumduration}}</el-row></el-col>

            </el-row>

            <el-row style="margin-top: 10px;margin-left: 15px;">
              <el-tag>{{data2.date}}</el-tag>
            </el-row>

            <el-row style="margin-left: 20px;margin-top: 20px">
              <el-col :span="2">{{data2.TrainNo}}</el-col>
              <el-col :span="3">
                <el-row>起：{{data2.StartStation}}</el-row>
                <el-row>终：{{data2.EndStation}}</el-row>
              </el-col>
              <el-col :span="3" style="margin-right: 10px">
                <el-row>{{data2.Depart}}开</el-row>
                <el-row>{{data2.Arrival}}到</el-row>
              </el-col>

              <el-col :span="27">
                <el-checkbox-group v-model="checkboxGroup1" max="1" size="mini" >
                  <el-checkbox-button v-show="data2.BusinessPrice!=0" label="Business" key="1"><el-col>商务座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data2.BusinessPrice}}</el-col><el-col>{{data2.BusinessNum}}张</el-col></el-checkbox-button>
                  <el-checkbox-button v-show="data2.FirstPrice!=0" label="First" key="2"><el-col>一等座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data2.FirstPrice}}</el-col><el-col>{{data2.FirstNum}}张</el-col></el-checkbox-button>
                  <el-checkbox-button v-show="data2.SecondPrice!=0" label="Second" key="3"><el-col>二等座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data2.SecondPrice}}</el-col><el-col>{{data2.SecondNum}}张</el-col></el-checkbox-button>
                  <el-checkbox-button v-show="data2.SoftSleeperUpPrice!=0" label="SoftSleeper" key="4"><el-col>软卧</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data2.SoftSleeperUpPrice}}</el-col><el-col>{{data2.SoftSleeperUpNum+data2.SoftSleeperDownNum}}张</el-col></el-checkbox-button>
                  <el-checkbox-button v-show="data2.HardSleeperUpPrice!=0" label="HardSleeper" key="6"><el-col>硬卧</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data2.HardSleeperUpPrice}}</el-col><el-col>{{data2.HardSleeperUpNum+data2.HardSleeperMidNum+data2.HardSleeperDownNum}}张</el-col></el-checkbox-button>
                  <el-checkbox-button v-show="data2.SoftSeatPrice!=0" label="SoftSeat" key="9"><el-col>软座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data2.SoftSeatPrice}}</el-col><el-col>{{data2.SoftSeatNum}}张</el-col></el-checkbox-button>
                  <el-checkbox-button v-show="data2.HardSeatPrice!=0" label="HardSeat" key="10"><el-col>硬座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data2.HardSeatPrice}}</el-col><el-col>{{data2.HardSeatNum}}张</el-col></el-checkbox-button>
                  <el-checkbox-button v-show="data2.NoSeatPrice!=0" label="NoSeat" key="11"><el-col>无座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data2.NoSeatPrice}}</el-col><el-col>{{data2.NoSeatNum}}张</el-col></el-checkbox-button>
                </el-checkbox-group>
              </el-col>

            </el-row>

            <el-row style="margin-top: 10px;margin-left: 15px">
              <el-tag :span="3">{{data3.date}}</el-tag>
            </el-row>

            <el-row style="margin-left: 20px;margin-top: 20px;margin-bottom: 10px">
              <el-col :span="2">{{data3.TrainNo}}</el-col>
              <el-col :span="3">
                <el-row>起：{{data3.StartStation}}</el-row>
                <el-row>终：{{data3.EndStation}}</el-row>
              </el-col>
              <el-col :span="3" style="margin-right: 10px">
                <el-row>{{data3.Depart}}开</el-row>
                <el-row>{{data3.Arrival}}到</el-row>
              </el-col>

              <el-col :span="27">
                <el-checkbox-group v-model="checkboxGroup2" max="1" size="mini" >
                  <el-checkbox-button v-show="data3.BusinessPrice!=0" label="Business" key="1"><el-col>商务座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data3.BusinessPrice}}</el-col><el-col>{{data3.BusinessNum}}张</el-col></el-checkbox-button>
                  <el-checkbox-button v-show="data3.FirstPrice!=0" label="First" key="2"><el-col>一等座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data3.FirstPrice}}</el-col><el-col>{{data3.FirstNum}}张</el-col></el-checkbox-button>
                  <el-checkbox-button v-show="data3.SecondPrice!=0" label="Second" key="3"><el-col>二等座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data3.SecondPrice}}</el-col><el-col>{{data3.SecondNum}}张</el-col></el-checkbox-button>
                  <el-checkbox-button v-show="data3.SoftSleeperUpPrice!=0" label="SoftSleeper" key="4"><el-col>软卧</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data3.SoftSleeperUpPrice}}</el-col><el-col>{{data3.SoftSleeperUpNum+data3.SoftSleeperDownNum}}张</el-col></el-checkbox-button>
                  <el-checkbox-button v-show="data3.HardSleeperUpPrice!=0" label="HardSleeper" key="6"><el-col>硬卧</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data3.HardSleeperUpPrice}}</el-col><el-col>{{data3.HardSleeperUpNum+data3.HardSleeperMidNum+data3.HardSleeperDownNum}}张</el-col></el-checkbox-button>
                  <el-checkbox-button v-show="data3.SoftSeatPrice!=0" label="SoftSeat" key="9"><el-col>软座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data3.SoftSeatPrice}}</el-col><el-col>{{data3.SoftSeatNum}}张</el-col></el-checkbox-button>
                  <el-checkbox-button v-show="data3.HardSeatPrice!=0" label="HardSeat" key="10"><el-col>硬座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data3.HardSeatPrice}}</el-col><el-col>{{data3.HardSeatNum}}张</el-col></el-checkbox-button>
                  <el-checkbox-button v-show="data3.NoSeatPrice!=0" label="NoSeat" key="11"><el-col>无座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data3.NoSeatPrice}}</el-col><el-col>{{data3.NoSeatNum}}张</el-col></el-checkbox-button>
                </el-checkbox-group>
              </el-col>

            </el-row>

          </div>
        </div>

        <div>
          <div style="height: 35px;background: #5889ff;color: white;padding-left: 15px;padding-top:13px">乘客信息</div>
          <div style="border: 1px solid black;margin: 10px;">
            <el-row style="margin-left: 15px;margin-top:10px">
              <el-col :span="1"><el-avatar icon="el-icon-user-solid" size="small"></el-avatar></el-col>
              <el-col :span="2">乘车人</el-col>
            </el-row>
            <el-row>
              <el-form ref="form" label-width="100px">
                <el-col :span="10">
                  <el-form-item label="乘车人添加">
                    <el-input size="medium" v-model="adduser"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item>
                    <el-button type="primary" @click="onAdd">添加</el-button>
                  </el-form-item>
                </el-col>
              </el-form>
            </el-row>
            <el-row style="margin-left: 15px;margin-bottom: 15px">
              <el-col :span="3">已添加乘车人：</el-col>
<!--              <el-col :span="2" v-for="item in userlist">{{item}}</el-col>-->
              <el-col :span="3" v-for="(item,index) in userlist">{{item}} <el-button size="mini" icon="el-icon-delete" @click="userdelete(index)"></el-button> </el-col>
            </el-row>
          </div>
        </div>
      </div>

      <div align="middle" style="margin-top: 15px;">
        <el-button type="primary" style="width: 200px" @click="onSubmit">提交订单</el-button>
      </div>

    </div>
</template>

<script>
    export default {
        name: "buyTransferTicket",
        data(){
            return{
                data1: {StartStation:'应县', EndStation:'济南东', duration1:'1小时44分', duration2:'2小时33分', sumduration:'8小时', index:1, Depart1:'11:13', Arrival1:'14:50', Depart2:'11:31', Arrival2:'17:52', Name:"临汾西", TrainNo1:"D2517", TrainNo2:"G2621",
                    // Arrival:"14:50",
                    // Departure:"11:31",
                    // duration:1241
                    duration:'1小时'
                },
                data2:{StartStationNo:2,EndStationNo:10,Depart:"11:13",Arrival:"14:50",BusinessPrice:0.0,FirstPrice:222.0,SecondPrice:139.0,SoftSleeperUpPrice:0.0,SoftSleeperDownPrice:0.0,HardSleeperUpPrice:0.0,HardSleeperMidPrice:0.0,HardSleeperDownPrice:0.0,SoftSeatPrice:0.0,HardSeatPrice:0.0,NoSeatPrice:0.0,detail:[{TrainNo:"D2517",Name:"应县",StationNo:2,Arrival:"11:11",Departure:"11:13"},{TrainNo:"D2517",Name:"山阴",StationNo:3,Arrival:"11:25",Departure:"11:27"},{TrainNo:"D2517",Name:"原平西",StationNo:4,Arrival:"12:09",Departure:"12:11"},{TrainNo:"D2517",Name:"忻州西",StationNo:5,Arrival:"12:25",Departure:"12:27"}],StartStation:"应县",EndStation:"临汾西",TrainNo:"D2517",isPriceShow:false,SoftSeatNum:0,SecondNum:1400,FirstNum:104,HardSeatNum:0,BusinessNum:0,NoSeatNum:0,HardSleeperMidNum:0,HardSleeperDownNum:0,SoftSleeperUpNum:0,SoftSleeperDownNum:0,HardSleeperUpNum:0},
                data3:{StartStationNo:4,EndStationNo:17,Depart:"11:31",Arrival:"17:12",BusinessPrice:0.0,FirstPrice:406.0,SecondPrice:254.0,SoftSleeperUpPrice:0.0,SoftSleeperDownPrice:0.0,HardSleeperUpPrice:0.0,HardSleeperMidPrice:0.0,HardSleeperDownPrice:0.0,SoftSeatPrice:0.0,HardSeatPrice:0.0,NoSeatPrice:0.0,detail:[{TrainNo:"G2621",Name:"临汾西",StationNo:4,Arrival:"11:29",Departure:"11:31"},{TrainNo:"G2621",Name:"霍州东",StationNo:5,Arrival:"11:55",Departure:"11:57"},{TrainNo:"G2621",Name:"介休东",StationNo:6,Arrival:"12:19",Departure:"12:21"},{TrainNo:"G2621",Name:"太原南",StationNo:7,Arrival:"12:57",Departure:"13:08"}],StartStation:"临汾西",EndStation:"济南东",TrainNo:"G2621",isPriceShow:false,SoftSeatNum:0,SecondNum:700,FirstNum:104,HardSeatNum:0,BusinessNum:0,NoSeatNum:0,HardSleeperMidNum:0,HardSleeperDownNum:0,SoftSleeperUpNum:0,SoftSleeperDownNum:0,HardSleeperUpNum:0},
                checkboxGroup1: [],
                checkboxGroup2: [],
                userlist:[],
                adduser:''

            }
        },
        methods:{
            userdelete(index){
                this.userlist.splice(index,1)
            },
            onAdd(){
                if(this.userlist.includes(this.adduser)){
                    alert("该用户已添加")
                    return
                }
                let url="http://localhost:8181/tfUserExist/"+this.adduser
                url=url.replace(/"/g,"")
                let that=this
                this.$axios.get(url).then(function (response) {
                    console.log(response);
                    if(response.data){
                        that.userlist.push(that.adduser)
                    }else{
                        that.adduser=""
                        alert("您添加的用户不存在")
                    }

                }),function (err) {
                    console.log(err);
                }
            },
            onSubmit(){
                if(this.checkboxGroup1.length==0||this.checkboxGroup2.length==0){
                    alert("请选择座位类型")
                    return
                }
                let type1=this.checkboxGroup1[0]
                let type2=this.checkboxGroup2[0]
                let TicketPrice1='',TicketPrice2=''
                if(type1=='SoftSleeper') TicketPrice1=this.data2["SoftSleeperUpPrice"]+'|'+this.data2["SoftSleeperDownPrice"]
                else if(type1=='HardSleeper') TicketPrice1=this.data2["HardSleeperUpPrice"]+'|'+this.data2["HardSleeperMidPrice"]+'|'+this.data2["HardSleeperDownPrice"]
                else TicketPrice1=this.data2[type1+"Price"]
                if(type2=='SoftSleeper') TicketPrice2=this.data3["SoftSleeperUpPrice"]+'|'+this.data3["SoftSleeperDownPrice"]
                else if(type2=='HardSleeper') TicketPrice2=this.data3["HardSleeperUpPrice"]+'|'+this.data3["HardSleeperMidPrice"]+'|'+this.data3["HardSleeperDownPrice"]
                else TicketPrice2=this.data3[type2+"Price"]
                let that=this
                this.$axios.get(
                    'http://localhost:8181/transferBuy',
                    {
                        params: {
                            userlist:that.userlist.join("|"),
                            TrainNo1:that.data2.TrainNo,
                            Start1:that.data2.StartStation,
                            End1:that.data2.EndStation,
                            Date1:that.data2.date,
                            Account:sessionStorage.getItem('account'),
                            StartTime1:that.data2.Depart,
                            EndTime1:that.data2.Arrival,
                            TicketPrice1:TicketPrice1,
                            SeatType1:type1,
                            StartStationNo1:that.data2.StartStationNo,
                            EndStationNo1:that.data2.EndStationNo,

                            TrainNo2:that.data3.TrainNo,
                            Start2:that.data3.StartStation,
                            End2:that.data3.EndStation,
                            Date2:that.data3.date,
                            StartTime2:that.data3.Depart,
                            EndTime2:that.data3.Arrival,
                            TicketPrice2:TicketPrice2,
                            SeatType2:type2,
                            StartStationNo2:that.data3.StartStationNo,
                            EndStationNo2:that.data3.EndStationNo
                        }
                    }
                ).then(function (response) {
                    console.log(response);
                    // let info=response.data
                    // that.$alert(info, '提醒', {
                    //     confirmButtonText: '确定'
                    // });

                    let info=response.data
                    var ht=info.toString().split("|")
                    ht[0]='<Strong>'+ht[0]+'</Strong>'
                    var html=ht.join("<br>")
                    that.$alert(html, '提醒', {
                        confirmButtonText: '确定',
                        dangerouslyUseHTMLString: true
                    });

                }),function (err) {
                    console.log(err);
                }
            }

        },
        created() {
            // alert(this.$route.query.tf)

            // alert(this.$route.query.row.TrainNo)
            this.userlist.push(sessionStorage.getItem('account').replace(/"/g,""))
            this.data1=this.$route.query.row1
            this.data2=this.$route.query.row2
            this.data3=this.$route.query.row3
        }
    }
</script>

<style scoped>

</style>
