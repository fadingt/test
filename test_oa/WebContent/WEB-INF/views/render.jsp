<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<div id="demo">
    <liu>aaaa</liu>
    <button @click="showToogle">showToogle</button>
    <p v-show="isShow">v-show example</p>
</div>

<script>

    Vue.component('liu', {
        data:function(){
            return{
                count:0
            }
        },
/*        render: function (createElement) {
            return createElement(
                'h',   // tag name 标签名称
                this.$slots.default // 子组件中的阵列
            )
        }*/
/*        render:function (createElement) {
            const _element = createElement('h1','hahaha');
            return _element;
        }*/
        render:hahaha=>hahaha('h1','hahaha')
        // template:'<div>{{count}}</div>'
    })

    const demo = new Vue({
        el:'#demo',
        data: {
            isShow:true
        },
        methods:{
            showToggle:function () {
                this.isShow = !this.isShow
            }
        }
    })
</script>
</body>
</html>