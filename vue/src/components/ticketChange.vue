<template>
  <div style="margin-left: 120px;margin-right: 120px">
    <div style="height: 150px;margin-top: 10px;border-radius: 5px;border: 1px solid #282eff;background: #ecf5ff">
      <div style="margin-left: 50px;margin-right: 50px;margin-top: 15px">

        <div style="float: left;">
          <div style="float: left;margin-top: 10px;font-size: 15px">*出发地 &nbsp;</div>
          <div style="float:left;">
            <el-select v-model="StartPoint" filterable :filter-method="filterMethod" placeholder="请选择" disabled>
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.value+'('+item.label+')'"
                :value="item.value+'|'+item.label+'|'+item.level">
              </el-option>
            </el-select>
          </div>
        </div>

        <div style="float: left;margin-left: 40px">
          <div style="float: left;margin-top: 10px;font-size: 15px">*目的地 &nbsp;</div>
          <div style="float:left;">
            <el-select v-model="EndPoint" filterable :filter-method="filterMethod" placeholder="请选择" disabled>
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.value+'('+item.label+')'"
                :value="item.value+'|'+item.label+'|'+item.level">
              </el-option>
            </el-select>
          </div>
        </div>

        <div style="float: left;margin-left: 40px;">
          <div style="float:left;">
            <div class="block">
              <span style="font-size: 15px">*乘车日期</span>
              <el-date-picker
                :picker-options="pickerOptions"
                v-model="date"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </div>


          </div>
        </div>

        <div style="float: left;margin-left: 10px;margin-top: 3px">
          <div>
            <input type="button" value="查询" id="search" @click="query"></input>
          </div>
        </div>

      </div>
      <div style="float:none;">&nbsp;</div>
      <div style="float:none;">&nbsp;</div>

      <div style="margin-left: 50px;margin-right: 50px;margin-top: 15px">
        <el-checkbox v-model="isTransfer" disabled>换乘</el-checkbox>
        <el-checkbox v-model="isTransferStation" disabled>指定换乘地</el-checkbox>
        <el-select v-model="TransferStation" filterable :filter-method="filterMethod" placeholder="请选择" size="mini" disabled>
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.value+'('+item.label+')'"
            :value="item.value+'|'+item.label+'|'+item.level">
          </el-option>
        </el-select>
      </div>

      <div style="margin-left: 30px;margin-right: 30px;margin-top: 10px;background: #f6ffd1;height: 25px;padding-top: 3px;border-radius: 5px;border: 1px solid #ff9529;">
        <span style="font-size: 14px;margin-left: 10px;">温馨提示：以下仅为您展示途中换乘一次的部分列车余票信息，购票时请您充分考虑各种影响换乘的因素，并自愿承担因自身或第三方等原因导致延误换乘的风险。</span>
      </div>

    </div>

    <div>
      <router-view v-if="sonRefresh"></router-view>
    </div>

  </div>
</template>

<script>
    export default {
        name: "ticket",
        data() {
            return {
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() < Date.now()- 8.64e7;
                    }
                },
                options: [{
                    //     value: '应县',
                    //     label: '城市',
                    //     level: 'District'
                    // },{
                    //     value: '济南市',
                    //     label: '城市',
                    //     level: 'City'
                }],
                sumoptions:[{}],
                StartPoint: '',
                EndPoint: '',
                date: '',
                sonRefresh: true,
                isTransfer:false,
                isTransferStation:false,
                TransferStation:'',
                oldrow:{}
            }
        },
        methods:{
            query(){
                if(this.StartPoint.length==0){
                    this.$alert('出发地不能为空', '警告', {
                        confirmButtonText: '确定'
                    });
                    return;
                }
                if(this.EndPoint.length==0){
                    this.$alert('目的地不能为空', '警告', {
                        confirmButtonText: '确定'
                    });
                    return;
                }
                if(this.date.length==0){
                    this.$alert('乘车日期不能为空', '警告', {
                        confirmButtonText: '确定'
                    });
                    return;
                }

                let Start=this.StartPoint+"|"+"火车站|"+"Name"
                let End=this.EndPoint+"|"+"火车站|"+"Name"
                sessionStorage.setItem('StartPoint', JSON.stringify(Start));
                sessionStorage.setItem('EndPoint', JSON.stringify(End));
                sessionStorage.setItem('date', JSON.stringify(this.date));

                this.$router.push({ path: '/ticketChangeResult',query:{tfChange:true,oldrow:this.oldrow}})

                this.sonRefresh= false;
                this.$nextTick(() => {
                    this.sonRefresh= true;
                });


            },
            filterMethod(query){//query是输入的关键字
                if(query == '')
                    this.options = this.sumoptions.slice(0,10)
                else{
                    let result = []                                                        //存储符合条件的下拉选项
                    this.sumoptions.forEach(val=>{
                        if((val.value+'('+val.label+')').includes(query)) result.push(val)
                    })
                    this.options = result.slice(0,10)//只取前10个
                }
            }

        },
        created() {
            this.oldrow=this.$route.query.row
            this.StartPoint = ""+ sessionStorage.getItem('ChangeStart');
            this.EndPoint = ""+ sessionStorage.getItem('ChangeEnd');
            this.StartPoint=this.StartPoint.replace(/"/g,"")
            this.EndPoint=this.EndPoint.replace(/"/g,"")

            const that=this
            this.cnt=0
            this.$axios.get("http://localhost:8181/CityStation").then(function (response) {
                console.log(response);
                that.sumoptions=response.data
                that.options=that.sumoptions.slice(0,10)
            }),function (err) {
                console.log(err);
            }
        }

    }
</script>

<style scoped>
  #search{
    width: 100px;
    height: 33px;
    background: #ff9529;
    border: #ff9529;
    border-radius: 5px;
    color: #ecf5ff;
    font-size: 16px;
  }
  #search:hover{
    cursor: pointer;
    background: #e58625;
  }
</style>
