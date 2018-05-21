<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="app">
  {{ message }}
</div>
<div id="app-2">
  <span v-bind:title="message">
    Hover your mouse over me for a few seconds
    to see my dynamically bound title!
  </span>
</div>
<div id="app-3">
  <span v-if="seen">Now you see me</span>
</div>
<div id="app-4">
  <ol>
    <li v-for="todo in todos">
      {{ todo.text }}
    </li>
  </ol>
</div>
<div id="app-5">
  <p>{{ message }}</p>
  <button v-on:click="reverseMessage">Reverse Message</button>
</div>
<div id="app-6">
  <p>{{ message }}</p>
  <input v-model="message">
</div>
<script type="text/javascript">
	var app = new Vue({
		  el: '#app',
		  data: {
		    message: 'Hello Vue!'
		  }
		})
	var app2 = new Vue({
		  el: '#app-2',
		  data: {
		    message: 'You loaded this page on ' + new Date().toLocaleString()
		  }
		})
	var app3 = new Vue({
		  el: '#app-3',
		  data: {
		    seen: true
		  }
		})
	var app4 = new Vue({
		  el: '#app-4',
		  data: {
		    todos: [
		      { text: 'Learn JavaScript' },
		      { text: 'Learn Vue' },
		      { text: 'Build something awesome' }
		    ]
		  }
		})
	var app5 = new Vue({
		  el: '#app-5',
		  data: {
		    message: 'Hello Vue.js!'
		  },
		  methods: {
		    reverseMessage: function () {
		      this.message = this.message.split('').reverse().join('')
		    }
		  }
		})
	var app6 = new Vue({
		  el: '#app-6',
		  data: {
		    message: 'Hello Vue!'
		  }
		})
	
</script>

</body>
</html>