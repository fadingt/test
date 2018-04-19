layui.config({
	base : "js/"
}).use(['form','layer'],function(){
	var form = layui.form(),
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		$ = layui.jquery;
	//video背景
	$(window).resize(function(){
		if($(".video-player").width() > $(window).width()){
			$(".video-player").css({"height":$(window).height(),"width":"auto","left":-($(".video-player").width()-$(window).width())/2});
		}else{
			$(".video-player").css({"width":$(window).width(),"height":"auto","left":-($(".video-player").width()-$(window).width())/2});
		}
	}).resize();
	
	//登录按钮事件
	form.on("submit(login)",function(data){
    	var adminName=$("#adminName").val();
    	var adminPass=$("#adminPass").val();
    	$.ajax({
    			type:"POST",
    			url:"../../../../adminLogin",
    			async:false,
    			data:{
    				adminName:adminName,
    				adminPass:adminPass
    			},
    			success:function(result){
    				if(result=="101"){		//如果登录成功 
    					location.href="../../index.html";
    				}else{
    					layer.alert('用户名或密码错误', {icon: 3});
    					setTimeout(function(){location.href="login.html";},800);
    
    					
    				}
    			}
    		});
			    return false;
	})
})
