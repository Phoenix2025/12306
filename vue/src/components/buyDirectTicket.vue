<template>
  <div>
    <div style="margin-left: 15%;margin-right: 15%;margin-top: 1%;border: #131576 1px solid">
      <div>
        <el-row style="height: 50px;background: #5889ff;color: white;padding-left: 15px;padding-top:13px">车次信息</el-row>
        <div style="border: 1px solid black;margin: 10px;">
          <el-row style="background: #e1ebf0;padding-left: 15px">
            <el-col :span="2"><el-row>&nbsp;</el-row><el-row>{{data.StartStation}}</el-row></el-col>
            <el-col :span="2"><el-row>&nbsp;</el-row><el-row>{{data.Depart}}开</el-row></el-col>
            <el-col :span="3">
              <el-row style="text-align: center">{{data.TrainNo}}</el-row>
              <el-row>———————</el-row>
              <el-row style="text-align: center">{{data.duration}}</el-row>
            </el-col>
            <el-col :span="2"><el-row>&nbsp;</el-row><el-row style="text-align: center">{{data.Arrival}}到</el-row></el-col>

            <el-col :span="2">
              <el-row style="text-align: center"><el-row>&nbsp;</el-row><el-row style="text-align: center">{{data.EndStation}}</el-row></el-row>
            </el-col>
          </el-row>
          <el-row style="margin-top: 10px;margin-left: 15px">
            <el-col :span="3">{{data.date}}</el-col>
          </el-row>
  <!--          <el-col :span="2">{{data.TrainNo}}</el-col>-->
  <!--          <el-col :span="2">-->
  <!--            <el-row><el-tag>{{data.StartStation}}</el-tag></el-row>-->
  <!--            <el-row><el-tag>{{data.EndStation}}</el-tag></el-row>-->
  <!--          </el-col>-->
  <!--          <el-col :span="2" style="margin-right: 10px">-->
  <!--            <el-row>{{data.Depart}}开</el-row>-->
  <!--            <el-row>{{data.Arrival}}到</el-row>-->
  <!--          </el-col>-->
          <el-row>
            <el-col :span="27">
              <el-checkbox-group v-model="checkboxGroup1" max="1" size="mini" >
                <el-checkbox-button v-show="data.BusinessPrice!=0" label="Business" key="1"><el-col>商务座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data.BusinessPrice}}</el-col><el-col>{{data.BusinessNum}}张</el-col></el-checkbox-button>
                <el-checkbox-button v-show="data.FirstPrice!=0" label="First" key="2"><el-col>一等座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data.FirstPrice}}</el-col><el-col>{{data.FirstNum}}张</el-col></el-checkbox-button>
                <el-checkbox-button v-show="data.SecondPrice!=0" label="Second" key="3"><el-col>二等座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data.SecondPrice}}</el-col><el-col>{{data.SecondNum}}张</el-col></el-checkbox-button>
                <el-checkbox-button v-show="data.SoftSleeperUpPrice!=0" label="SoftSleeper" key="4"><el-col>软卧</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data.SoftSleeperUpPrice}}</el-col><el-col>{{data.SoftSleeperUpNum+data.SoftSleeperDownNum}}张</el-col></el-checkbox-button>
<!--                <el-checkbox-button v-show="data.SoftSleeperDownPrice!=0" label="SoftSleeperDown" key="5"><el-col>软卧下</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data.SoftSleeperDownPrice}}</el-col><el-col>{{data.SoftSleeperDownNum}}张</el-col></el-checkbox-button>-->
                <el-checkbox-button v-show="data.HardSleeperUpPrice!=0" label="HardSleeper" key="6"><el-col>硬卧</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data.HardSleeperUpPrice}}</el-col><el-col>{{data.HardSleeperUpNum+data.HardSleeperMidNum+data.HardSleeperDownNum}}张</el-col></el-checkbox-button>
<!--                <el-checkbox-button v-show="data.HardSleeperMidPrice!=0" label="HardSleeperMid" key="7"><el-col>硬卧中</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data.HardSleeperMidPrice}}</el-col><el-col>{{data.HardSleeperMidNum}}张</el-col></el-checkbox-button>-->
<!--                <el-checkbox-button v-show="data.HardSleeperDownPrice!=0" label="HardSleeperDown" key="8"><el-col>硬卧下</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data.HardSleeperDownPrice}}</el-col><el-col>{{data.HardSleeperDownNum}}张</el-col></el-checkbox-button>-->
                <el-checkbox-button v-show="data.SoftSeatPrice!=0" label="SoftSeat" key="9"><el-col>软座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data.SoftSeatPrice}}</el-col><el-col>{{data.SoftSeatNum}}张</el-col></el-checkbox-button>
                <el-checkbox-button v-show="data.HardSeatPrice!=0" label="HardSeat" key="10"><el-col>硬座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data.HardSeatPrice}}</el-col><el-col>{{data.HardSeatNum}}张</el-col></el-checkbox-button>
                <el-checkbox-button v-show="data.NoSeatPrice!=0" label="NoSeat" key="11"><el-col>无座</el-col><el-col style="margin-top: 3px;margin-bottom: 3px">￥{{data.NoSeatPrice}}</el-col><el-col>{{data.NoSeatNum}}张</el-col></el-checkbox-button>
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
                  <el-input size="medium" v-model="adduser" :disabled="tfChange"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item>
                   <el-button type="primary" @click="onAdd" :disabled="tfChange">添加</el-button>
                </el-form-item>
              </el-col>
            </el-form>
          </el-row>
          <el-row style="margin-left: 15px;margin-bottom: 15px">
            <el-col :span="3">已添加乘车人：</el-col>
            <el-col :span="3" v-for="(item,index) in userlist">{{item}} <el-button :disabled="tfChange" size="mini" icon="el-icon-delete" @click="userdelete(index)"></el-button> </el-col>
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
        name: "buyDirectTicket",
        data(){
            return{
              data:{},
               checkboxGroup1: [],
               userlist:[],
               adduser:'',
               tfChange:false,
               oldrow:{}
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
                if(this.checkboxGroup1.length==0){
                    alert("请选择座位类型")
                    return
                }
                if(this.userlist==null||this.userlist.length==0){
                    alert("乘车人不能为空")
                    return
                }
                let type=this.checkboxGroup1[0]
                // alert(type)
                let TicketPrice=''
                if(type=='SoftSleeper') TicketPrice=this.data["SoftSleeperUpPrice"]+'|'+this.data["SoftSleeperDownPrice"]
                else if(type=='HardSleeper') TicketPrice=this.data["HardSleeperUpPrice"]+'|'+this.data["HardSleeperMidPrice"]+'|'+this.data["HardSleeperDownPrice"]
                else TicketPrice=this.data[type+"Price"]
                // alert(TicketPrice)
                let that=this

                if (this.tfChange){
                    this.$axios.get(
                        'http://localhost:8181/changeticket',
                        {
                            params: {
                                oldid:that.oldrow.Id,
                                TrainNo:that.data.TrainNo,
                                Start:that.data.StartStation,
                                End:that.data.EndStation,
                                Date:that.data.date,
                                Account:sessionStorage.getItem('account'),
                                StartTime:that.data.Depart,
                                EndTime:that.data.Arrival,
                                TicketPrice:TicketPrice,
                                SeatType:type,
                                StartStationNo:that.data.StartStationNo,
                                EndStationNo:that.data.EndStationNo
                            }
                        }
                    ).then(function (response) {
                        console.log(response);
                        let info=response.data
                        var ht=info.toString().split("|")
                        ht[0]='<Strong>'+ht[0]+'</Strong>'
                        var html=ht.join("<br>")
                        that.$alert(html, '提醒', {
                            confirmButtonText: '确定',
                            dangerouslyUseHTMLString: true
                        });

                        that.$router.push({ path: '/order' });
                    }),function (err) {
                        console.log(err);
                    }
                }
                if(this.tfChange) return;

                this.$axios.get(
                    'http://localhost:8181/directBuy',
                    {
                        params: {
                            userlist:that.userlist.join("|"),
                            TrainNo:that.data.TrainNo,
                            Start:that.data.StartStation,
                            End:that.data.EndStation,
                            Date:that.data.date,
                            Account:sessionStorage.getItem('account'),
                            StartTime:that.data.Depart,
                            EndTime:that.data.Arrival,
                            TicketPrice:TicketPrice,
                            SeatType:type,
                            StartStationNo:that.data.StartStationNo,
                            EndStationNo:that.data.EndStationNo
                        }
                    }
                ).then(function (response) {
                    console.log(response);
                    let info=response.data
                    var ht=info.toString().split("|")
                    ht[0]='<Strong>'+ht[0]+'</Strong>'
                    var html=ht.join("<br>")
                    that.$alert(html, '提醒', {
                        confirmButtonText: '确定',
                        dangerouslyUseHTMLString: true
                    });
                    // that.$router.push({ path: '/order' });
                }),function (err) {
                    console.log(err);
                }
            }

        },
        created() {
            // alert(this.$route.query.tf)

            // alert(this.$route.query.row.TrainNo)
            this.userlist.push(sessionStorage.getItem('account').replace(/"/g,""))
            this.data=this.$route.query.row

            this.tfChange=this.$route.query.tfChange
            if(this.tfChange==undefined) this.tfChange=false
            this.oldrow=this.$route.query.oldrow
            // alert(this.tfChange+""+this.oldrow.Id)
        }
    }
</script>

<style scoped>

</style>
