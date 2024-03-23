<template>
    <div style="margin-top: 5px" v-show="isRefresh">
      <div style="margin-bottom: 5px;font-weight: bold;font-size: 14px">
        <span>应县</span>&nbsp;-->&nbsp;<span>济南</span>
      </div>
      <div style="border: #117edd 1px solid;">
        <div>
          <div class="header" style="width: 9%"><div class="header-inside">车次</div></div>
          <div class="header" style="width: 9%;"><div class="header-inside">出发站<br>到达站</div></div>
          <div class="header" style="width: 9%;"><div class="header-inside">出发时间<br>到达时间</div></div>
          <div class="header" style="width: 9%"><div class="header-inside">历时</div></div>
          <div class="header" style="width: 7%"><div class="header-inside">商务座</div></div>
          <div class="header" style="width: 7%"><div class="header-inside">一等座</div></div>
          <div class="header" style="width: 7%"><div class="header-inside">二等座</div></div>
          <div class="header" style="width: 7%"><div class="header-inside">软卧</div></div>
          <div class="header" style="width: 7%"><div class="header-inside">硬卧</div></div>
          <div class="header" style="width: 7%"><div class="header-inside">软座</div></div>
          <div class="header" style="width: 7%"><div class="header-inside">硬座</div></div>
          <div class="header" style="width: 7%;"><div class="header-inside">无座</div></div>
          <div class="header" style="width: 8.1%;border: none"><div class="header-inside">备注</div></div>
        </div>
        <div style="float: none"></div>
        <div v-for="item in tableData">
          <div v-show="false">{{Colortf=!Colortf}}</div>
          <div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 9%"><div class="cell-inside" style="border-left: 1px solid #0f1361;">{{item.TrainNo}}</div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 9%;cursor: default"><div class="cell-inside">{{item.StartStation}}<br>{{item.EndStation}}</div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 9%;cursor: default"><div class="cell-inside">{{item.Depart}}<br>{{item.Arrival}}</div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 9%;cursor: default"><div class="cell-inside">04:12<br>当日到达</div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 7%" @click="doPriceShow(item)"><div class="cell-inside">
              <div v-if="item.BusinessPrice==0">--</div>
              <div v-else-if="item.BussinessNum<=10">{{item.BussinessNum}}</div>
              <div v-else class="have">有</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 7%" @click="doPriceShow(item)"><div class="cell-inside">
              <div v-if="item.FirstPrice==0">--</div>
              <div v-else-if="item.FirstNum<=10" style="font-weight: bold">{{item.FirstNum}}</div>
              <div v-else class="have">有</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 7%" @click="doPriceShow(item)"><div class="cell-inside">
              <div v-if="item.SecondPrice==0">--</div>
              <div v-else-if="item.SecondNum<=10" style="font-weight: bold">{{item.SecondNum}}</div>
              <div v-else class="have">有</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 7%" @click="doPriceShow(item)"><div class="cell-inside">
              <div v-if="item.SoftSleeperUpPrice==0">--</div>
              <div v-else-if="(item.SoftSleeperUpNum+item.SoftSleeperDownNum)<=10" style="font-weight: bold">{{item.SoftSleeperUpNum+item.SoftSleeperDownNum}}</div>
              <div v-else class="have">有</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 7%" @click="doPriceShow(item)"><div class="cell-inside">
              <div v-if="item.HardSleeperUpPrice==0">--</div>
              <div v-else-if="(item.HardSleeperUpNum+item.HardSleeperMidNum+item.HardSleeperDownNum)<=10" style="font-weight: bold">{{item.HardSleeperUpNum+item.HardSleeperMidNum+item.HardSleeperDownNum}}</div>
              <div v-else class="have">有</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 7%" @click="doPriceShow(item)"><div class="cell-inside">
              <div v-if="item.SoftSeatPrice==0">--</div>
              <div v-else-if="item.SoftSeatNum<=10" style="font-weight: bold">{{item.SoftSeatNum}}</div>
              <div v-else class="have">有</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' "style="width: 7%" @click="doPriceShow(item)"><div class="cell-inside">
              <div v-if="item.HardSeatPrice==0">--</div>
              <div v-else-if="item.HardSeatNum<=10" style="font-weight: bold">{{item.HardSeatNum}}</div>
              <div v-else class="have">有</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 7%" @click="doPriceShow(item)"><div class="cell-inside">
              <div v-if="item.NoSeatPrice==0">--</div>
              <div v-else-if="item.NoSeatNum<=10" style="font-weight: bold">{{item.NoSeatNum}}</div>
              <div v-else class="have">有</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 8.1%"><div class="cell-inside"><input type="button" value="预订"></input></div></div>
          </div>

          <div v-show="item.isPriceShow">
            <div :class="Colortf ? 'cell':'cell2' " style="width: 9%;"><div class="cell-inside" style="border-right: none;border-left: 1px solid #0f1361;"></div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 9%;"><div class="cell-inside" style="border-right: none"></div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 9%;"><div class="cell-inside" style="border-right: none"></div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 9%;"><div class="cell-inside"></div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 7%"><div class="cell-inside">
              <div v-if="item.BusinessPrice==0"></div>
              <div v-else>￥{{item.BusinessPrice}}</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 7%"><div class="cell-inside2">
              <div v-if="item.FirstPrice==0"></div>
              <div v-else>￥{{item.FirstPrice}}</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 7%"><div class="cell-inside2">
              <div v-if="item.SecondPrice==0"></div>
              <div v-else>￥{{item.SecondPrice}}</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 7%"><div class="cell-inside2">
              <div v-if="item.SoftSleeperUpPrice==0"></div>
              <div v-else>￥{{item.SoftSleeperUpPrice}}</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 7%"><div class="cell-inside2">
              <div v-if="item.HardSleeperUpPrice==0"></div>
              <div v-else>￥{{item.HardSleeperUpPrice}}</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 7%"><div class="cell-inside2">
              <div v-if="item.SoftSeatPrice==0"></div>
              <div v-else>￥{{item.SoftSeatPrice}}</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' "style="width: 7%"><div class="cell-inside2">
              <div v-if="item.HardSeatPrice==0"></div>
              <div v-else>￥{{item.HardSeatPrice}}</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 7%"><div class="cell-inside2">
              <div v-if="item.NoSeatPrice==0"></div>
              <div v-else>￥{{item.NoSeatPrice}}</div>
            </div></div>
            <div :class="Colortf ? 'cell':'cell2' " style="width: 8.1%"><div class="cell-inside"></div></div>
          </div>

        </div>
      </div>

      <div style="float: none">&nbsp;</div>
      <div align="center">
        <el-pagination
          layout="prev, pager, next"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
          :total="total">
        </el-pagination>
      </div>

    </div>

</template>

<script>
    export default {
        name: "ticketResult",
        data() {
            return {
                isRefresh:true,
                total:100,
                Colortf:false,
                table:[],
                tableData:[
                    // {
                    //     TrainNo:'1133',
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
                    // },
                    // {
                    //     TrainNo:'1133',
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
            }
        },
        methods:{
            doPriceShow:function (item) {
                item.isPriceShow=!item.isPriceShow;
            },
            handleCurrentChange(val) {
                this.tableData=this.table.slice((val-1)*10,val*10)
                this.$forceUpdate()
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            }
        },
        created() {
            let StartPoint = ""+ sessionStorage.getItem('StartPoint');
            let EndPoint = ""+ sessionStorage.getItem('EndPoint');
            let date = ""+ sessionStorage.getItem('date');
            console.log(StartPoint);console.log(EndPoint);console.log(date)
            let url="http://localhost:8181/ticketResultDirect/"+StartPoint+"/"+EndPoint+"/"+date
            url=url.replace(/"/g,"")
            console.log(url)
            const that=this
            this.$axios.get(url).then(function (response) {
                console.log(response);
                that.table=response.data
                that.tableData=that.table.slice(0,10)
                that.total=that.table.length
            }),function (err) {
                console.log(err);
            }
        }
    }
</script>

<style scoped>
.header{
  float: left;
  height: 60px;
  background: #117edd;
  display: table;
}
.header-inside{
  border-right: 1px solid #0f1361;
  font-size: 15px;
  color: white;

  text-align: center;
  vertical-align:middle;
  display:table-cell;
  width:100%;
}
.cell{
   float: left;
   height: 50px;
   display: table;
   background: #cddee4;
   cursor: pointer;
 }
.cell2{
  float: left;
  height: 50px;
  display: table;
  background: #f0faff;
  cursor: pointer;
}
.cell-inside{
  border-right: 1px solid #0f1361;
  border-bottom: 1px solid #0f1361;
  font-size: 15px;
  color: black;

  text-align: center;
  vertical-align:middle;
  display:table-cell;
  width:100%;
}
.cell-inside2{
  border-right: 1px solid #0f1361;
  border-bottom: 1px solid #0f1361;
  font-size: 15px;
  color: red;

  text-align: center;
  vertical-align:middle;
  display:table-cell;
  width:100%;
}
.have{
  color: #42b983;
}
</style>
