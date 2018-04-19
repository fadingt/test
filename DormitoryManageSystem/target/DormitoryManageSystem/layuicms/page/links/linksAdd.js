layui.config({
	base : "js/"
}).use(['form','layer','jquery','layedit','laydate'],function(){
	var form = layui.form(),
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		layedit = layui.layedit,
		laydate = layui.laydate,
		$ = layui.jquery;


    // function getvalue(name) {
     //    var str = window.location.search;
     //    if (str.indexOf(name) != -1) {
     //        var pos_start = str.indexOf(name) + name.length + 1;
     //        var pos_end = str.indexOf("&", pos_start);
     //        if (pos_end == -1) {
     //            return str.substring(pos_start);
     //        }
     //        else {
     //            return str.substring(pos_start, pos_end)
     //        }
     //    }
    // }
    // var type = getvalue("type");
    // var id = getvalue("id");
    //
    // alert(type);
	// alert(id);


	//创建一个编辑器
 	var editIndex = layedit.build('links_content');
 	var addLinksArray = [],addLinks;
 	form.on("submit(addLinks)",function(data){


        var nTitle = $("#nTitle").val();
        var nPic = $("#nPic").val();
        var nContent = $("#nContent").val();

        // alert(nTitle);
        // alert(nPic);
        // alert(nContent);

        $.ajax({
            type:"POST",
            url:"../../../notice/add",
            async:false,
            data:{
                nTitle:nTitle,
                nContent:nContent,
				nPic:nPic
            },
            dataType:"json",
            success:function(result){
                // setTimeout(function(){location.href="linksList.html";},8000);
                //layer.alert('添加成功！', {icon: 1});
                //setTimeout(function(){location.href="../../../index.html";},8000);

                // location.reload();
                // setTimeout(location.href = "linksList.html",2000);
                // setTimeout(function(){
                //     top.layer.close(index);
                //     top.layer.msg("公告添加成功！");
                //     layer.closeAll("iframe");
                //     //刷新父页面
                //     parent.location.reload();
                // },2000);


                alert("添加成功！");
                if(window != top){
                    top.location.href = "../../../index.html";
                }
            }
        });

		//alert("333333333333333333");




 		//是否添加过信息
	 	if(window.sessionStorage.getItem("addLinks")){
	 		addLinksArray = JSON.parse(window.sessionStorage.getItem("addLinks"));
	 	}
	 	//显示、审核状态
 		var homePage = data.field.homePage=="on" ? "首页" : "",
 			subPage = data.field.subPage=="on" ? "子页" : "";
 			showAddress = '';
 		if(subPage == '' && homePage == ''){
 			showAddress = "暂不展示";
 		}else if(homePage == ''){
 			showAddress = subPage;
 		}else if(subPage == ''){
 			showAddress = homePage;
 		}else{
 			showAddress = homePage + '，' + subPage;
 		}

 		addLinks = '{"linksName":"'+ $(".linksName").val() +'",';  //网站名称
 		addLinks += '"linksUrl":"'+ $(".linksUrl").val() +'",';	 //网站地址
 		addLinks += '"linksDesc":"'+ $(".linksDesc").text() +'",'; //站点描述
 		addLinks += '"linksTime":"'+ $(".linksTime").val() +'",'; //发布时间
 		addLinks += '"masterEmail":"'+ $(".masterEmail").val() +'",'; //站长邮箱
 		addLinks += '"showAddress":"'+ showAddress +'"}';  //展示位置
 		addLinksArray.unshift(JSON.parse(addLinks));
 		window.sessionStorage.setItem("addLinks",JSON.stringify(addLinksArray));
 		//弹出loading
 		var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // var essayTitle=$(".linksName").val() ;
        // var essayAddress=$(".linksUrl").val() ;
        // var founderEmail=$(".masterEmail").val();

        // var nTitle = $("#nTitle").val();
        // var nPic = $("#nPic").val();
        // var nContent = $("#nContent").val();

        // alert(nTitle);
        // alert(nPic);
        // alert(nContent);

 		$.ajax({
			type:"POST",
			url:"../../../notice/add",
			async:false,
			data:{
                nTitle:nTitle,
                nContent:nContent,
                nPic:nPic
			},
			success:function(result){
				setTimeout(function(){
		            top.layer.close(index);
					top.layer.msg("文章添加成功！");
		 			layer.closeAll("iframe");
			 		//刷新父页面
			 		parent.location.reload();
		        },2000);
			
			}
		});
 		
 		return false;
 	})
	
});
