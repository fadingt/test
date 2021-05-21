<%--
  Created by IntelliJ IDEA.
  User: fadin
  Date: 2018/6/4
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://unpkg.com/vuex@3.0.1/dist/vuex.js"></script>
<html>
<head>
    <title>learn vuex</title>
</head>
<body>
<div id="app">
    <button name="button"></button>
</div>
<script>
    const store = new Vuex.Store({
        state:{
            count:0
        },
        mutations:{
            increment(state){
                state.count++
            }
        }
    })
    const counter = {
        template: `<div>{{count}}</div>`,
        computed: {
            count() {
                console.log(this);
                return this.$store.state.count;
            }
        }
    }
    const app = new Vue({
        el:'#app',
        store,
        components:{counter},
        template:`
            <div class= 'app'>
                <counter></counter>
             </div>
         `
    })
    store.commit('increment');
    store.commit('increment');
</script>
</body>
</html>
