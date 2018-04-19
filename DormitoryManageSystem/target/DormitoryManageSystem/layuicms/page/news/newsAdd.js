layui.config({
	base : "js/"
}).use(['form','layer','jquery','layedit','laydate'],function(){
	var form = layui.form(),
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		layedit = layui.layedit,
		laydate = layui.laydate,
		$ = layui.jquery;

	//创建一个编辑器
 	var editIndex = layedit.build('news_content');
 	var addNewsArray = [],addNews;
 	form.on("submit(addNews)",function(data){


        alert("2222222222222222");

        $.ajax({
            type:"POST",
            url:"../../../notice/add",
            //async:false,
            data:{
                // nTitle:"nTitle",
                // nContent:"nContent"
            },
            dataType:"json",
            success:function(result){
                alert("添加成功...！");

                setTimeout(function(){location.href="newsList.html";},800);
            }
        });

		alert("33333333333333333333");
        location.href="./newsList.html";

 	// 	//是否添加过信息
	 // 	if(window.sessionStorage.getItem("addNews")){
	 // 		addNewsArray = JSON.parse(window.sessionStorage.getItem("addNews"));
	 // 	}
	 // 	//显示、审核状态
 	// 	var isShow = data.field.show=="on" ? "checked" : "",
 	// 		newsStatus = data.field.shenhe=="on" ? "审核通过" : "待审核";
     //
 	// 	addNews = '{"newsName":"'+$(".newsName").val()+'",';  //文章名称
 	// 	addNews += '"newsId":"'+new Date().getTime()+'",';	 //文章id
 	// 	addNews += '"newsLook":"'+$(".newsLook option").eq($(".newsLook").val()).text()+'",'; //开放浏览
 	// 	addNews += '"newsTime":"'+$(".newsTime").val()+'",'; //发布时间
 	// 	addNews += '"newsAuthor":"'+$(".newsAuthor").val()+'",'; //文章作者
 	// 	addNews += '"isShow":"'+ isShow +'",';  //是否展示
 	// 	addNews += '"newsStatus":"'+ newsStatus +'"}'; //审核状态
 	// 	addNewsArray.unshift(JSON.parse(addNews));
 	// 	window.sessionStorage.setItem("addNews",JSON.stringify(addNewsArray));
 	// 	//弹出loading
 	// 	var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
      //   setTimeout(function(){
      //       top.layer.close(index);
		// 	top.layer.msg("公告添加成功！");
 	// 		layer.closeAll("iframe");
	 // 		//刷新父页面
		// 	alert("4444444444444");
	 // 		parent.location.reload();
      //   },2000);
 	// 	return false;
 	})
	
})
