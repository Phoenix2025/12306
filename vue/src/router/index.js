import Vue from 'vue'
import Router from 'vue-router'
import index from '../components/index'
import ticket from "../components/ticket";

import ticketResultDirect from '../components/ticketResultDirect'
import ticketResultTransfer from "../components/ticketResultTransfer";
import sign from "../components/sign";
import home from "../components/home";

import buyDirectTicket from "../components/buyDirectTicket";
import buyTransferTicket from "../components/buyTransferTicket";
import order from "../components/order";

import ticketChange from "../components/ticketChange";


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: index,
      redirect: 'home',
      children:[
        {
          path: '/ticket', component: ticket,
          children:[
            {path: '/ticketResultDirect', component: ticketResultDirect},
            {path: '/ticketResultTransfer', component: ticketResultTransfer}
          ]
        },
        {path: '/home', name: 'home', component: home},
        {path: '/buyDirectTicket',component: buyDirectTicket},
        {path: '/order',component: order},
        {path: '/buyTransferTicket',component: buyTransferTicket},
        {path: '/ticketChange',component: ticketChange,
          children:[{path:'/ticketChangeResult',component: ticketResultDirect}]
        }
      ]
    },
    {
      path: '/sign',
      name: 'sign',
      component: sign
    }
  ],
  mode:'history'
})
