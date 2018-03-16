<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <script src="//ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.js"></script> -->
<script type="text/javascript" src="jquery-3.3.1.js"></script>
<script>
	$(document).ready(function() {
		$("button.b1").click(function() {
			$("p").toggle();
		});
		$("p").click(function(){
			$(this).toggle();
		})
		$("button.b2").click(function() {
			$("#div1").fadeTo("slow",0.2);
			$("#div2").fadeTo("fast",0.4);
			$("#div3").fadeTo("fast",0.8);
		});
		$("button.b3").click(function() {
			$("#div1").fadeToggle();
			$("#div1").fadeToggle("fast");
			$("#div1").fadeToggle("3000");
			$("#div1").fadeToggle("0");
			$("#div2").fadeToggle();
			$("#div2").fadeToggle("fast");
			$("#div2").fadeToggle("3000");
			$("#div2").fadeToggle("0");
			$("#div3").fadeToggle();
			$("#div3").fadeToggle("fast");
			$("#div3").fadeToggle("3000");
			$("#div3").fadeToggle("0");
		});
	});
</script>
<title>learn jQuery</title>
</head>
<body>
	<h2>这是标题</h2>
	<p>这是一个段落。</p>
	<p>这是另一个段落。</p>
	<p>点我啊！</p>
	<button class="b1" >点击这里</button>
	<br/>
	<div id="div1" style="width:80px;height:80px;background-color:red;"></div>
	<button class="b2">矩形淡出</button>
	<div id="div2" style="width:80px;height:80px;background-color:green;"></div>
	<div id="div3" style="width:80px;height:80px;background-color:blue;"></div>
	<button class="b3">矩形淡入淡出</button>
</body>
</html>