layui.config({
    base : "js/"
}).use(['form','layer','jquery','laypage'],function(){
    var form = layui.form(),
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;



    //加载页面数据
    var newsData = '';
    //***从后台获取数据
    $.ajax({
        url:"../../../repair/get",
        type:"post",
        data:{},
        dataType:"json",
        success:function(data){
            var newArray = [];
            //单击首页“待审核文章”加载的信息
            if($(".top_tab li.layui-this cite",parent.document).text() == "待审核文章"){
                if(window.sessionStorage.getItem("addNews")){
                    var addNews = window.sessionStorage.getItem("addNews");
                    newsData = JSON.parse(addNews).concat(data);
                }else{
                    newsData = data;
                }
                for(var i=0;i<newsData.length;i++){
                    if(newsData[i].vStatus == "待审核"){
                        newArray.push(newsData[i]);
                    }
                }
                newsData = newArray;
                newsList(newsData);
            }else{    //正常加载信息
                newsData = data;
                if(window.sessionStorage.getItem("addNews")){
                    var addNews = window.sessionStorage.getItem("addNews");
                    newsData = JSON.parse(addNews).concat(newsData);
                }
                //执行加载数据的方法
                newsList();
            }
        }
    });

    //查询
    $(".search_btn").click(function(){
        var newArray = [];
        if($(".search_input").val() != ''){
            var index = layer.msg('查询中，请稍候',{icon: 16,time:false,shade:0.8});
            setTimeout(function(){
                var dId=$(".search_input").val();
                $.ajax({
                    url : "../../../repair/otherinfo",
                    data:{dId:dId},
                    type : "post",
                    dataType : "json",
                    success : function(data) {
                        /*                        if(window.sessionStorage.getItem("addNews")){
                         var addNews = window.sessionStorage.getItem("addNews");
                         newsData = JSON.parse(addNews).concat(data);
                         }else{
                         newsData = data;
                         }
                         for(var i=0;i<newsData.length;i++){
                         var newsStr = newsData[i];
                         var selectStr = $(".search_input").val();
                         function changeStr(data){
                         var dataStr = '';
                         var showNum = data.split(eval("/"+selectStr+"/ig")).length - 1;
                         if(showNum > 1){
                         for (var j=0;j<showNum;j++) {
                         dataStr += data.split(eval("/"+selectStr+"/ig"))[j] + "<i style='color:#03c339;font-weight:bold;'>" + selectStr + "</i>";
                         }
                         dataStr += data.split(eval("/"+selectStr+"/ig"))[showNum];
                         return dataStr;
                         }else{
                         dataStr = data.split(eval("/"+selectStr+"/ig"))[0] + "<i style='color:#03c339;font-weight:bold;'>" + selectStr + "</i>" + data.split(eval("/"+selectStr+"/ig"))[1];
                         return dataStr;
                         }
                         }
                         //文章标题
                         if(newsStr.newsName.indexOf(selectStr) > -1){
                         newsStr["newsName"] = changeStr(newsStr.newsName);
                         }
                         //发布人
                         if(newsStr.newsAuthor.indexOf(selectStr) > -1){
                         newsStr["newsAuthor"] = changeStr(newsStr.newsAuthor);
                         }
                         //审核状态
                         if(newsStr.newsStatus.indexOf(selectStr) > -1){
                         newsStr["newsStatus"] = changeStr(newsStr.newsStatus);
                         }
                         //浏览权限
                         if(newsStr.newsLook.indexOf(selectStr) > -1){
                         newsStr["newsLook"] = changeStr(newsStr.newsLook);
                         }
                         //发布时间
                         /!* if(newsStr.newsTime1.indexOf(selectStr) > -1){
                         newsStr["newsTime1"] = changeStr(newsStr.newsTime1);
                         }
                         if(newsStr.newsTime2.indexOf(selectStr) > -1){
                         newsStr["newsTime2"] = changeStr(newsStr.newsTime2);
                         }*!/
                         if(newsStr.newsName.indexOf(selectStr)>-1 || newsStr.newsAuthor.indexOf(selectStr)>-1 || newsStr.newsStatus.indexOf(selectStr)>-1 || newsStr.newsLook.indexOf(selectStr)>-1 || newsStr.newsTime1.indexOf(selectStr)>-1|| newsStr.newsTime2.indexOf(selectStr)>-1){
                         newArray.push(newsStr);
                         }
                         }
                         newsData = newArray;
                         newsList(newsData);*/
                        $(".search_input").val("");
                            var newArray = [];
                            //单击首页“待审核文章”加载的信息
                            if ($(".top_tab li.layui-this cite", parent.document).text() == "待审核文章") {
                                if (window.sessionStorage.getItem("addNews")) {
                                    var addNews = window.sessionStorage.getItem("addNews");
                                    newsData = JSON.parse(addNews).concat(data);
                                } else {
                                    newsData = data;
                                }
                                for (var i = 0; i < newsData.length; i++) {
                                    if (newsData[i].vStatus == "待审核") {
                                        newArray.push(newsData[i]);
                                    }
                                }
                                newsData = newArray;
                                newsList(newsData);
                            } else {    //正常加载信息
                                newsData = data;
                                if (window.sessionStorage.getItem("addNews")) {
                                    var addNews = window.sessionStorage.getItem("addNews");
                                    newsData = JSON.parse(addNews).concat(newsData);
                                }
                                //执行加载数据的方法
                                newsList();
                            }
                    }
                })

                layer.close(index);
            },2000);
        }else{
            layer.msg("请输入需要查询的内容");
        }
    })



    //添加文章
    //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
    $(window).one("resize",function(){
        $(".newsAdd_btn").click(function(){
            var index = layui.layer.open({
                title : "添加文章",
                type : 2,
                content : "newsAdd.html",
                success : function(layero, index){
                    setTimeout(function(){
                        layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    },500)
                }
            })
            layui.layer.full(index);
        })
    }).resize();


    //推荐文章
    $(".recommend").click(function(){
        var $checkbox = $(".news_list").find('tbody input[type="checkbox"]:not([name="show"])');
        var $checked = $('.news_list tbody input[type="checkbox"][name="checked"]:checked');
        if($checkbox.is(":checked")){
            var index = layer.msg('推荐中，请稍候',{icon: 16,time:false,shade:0.8});

            //***推荐文章(需要获取到id)...(借鉴于文章审核)
            setTimeout(function(){
                for(var j=0;j<$checked.length;j++){
                    for(var i=0;i<newsData.length;i++){
                        if(newsData[i].newsId == $checked.eq(j).parents("tr").find(".news_del").attr("data-id")){

                            //***推荐文章(加精)
                            $.ajax({
                                url:"../../../../adminlistutil",
                                type:"post",
                                data:{operatType:4,
                                    cardId:newsData[i].newsId},
                                dataType:"json",
                                success:function(data){

                                }
                            });
                            //修改列表中的文字
                            $checked.eq(j).parents("tr").find("td:eq(3)").text("审核通过").removeAttr("style");
                            //将选中状态删除
                            $checked.eq(j).parents("tr").find('input[type="checkbox"][name="checked"]').prop("checked",false);
                            form.render();
                        }
                    }
                }

                layer.close(index);
                layer.msg("推荐成功");
            },2000);
        }else{
            layer.msg("请选择需要推荐的文章");
        }
    })

    //审核文章
    $(".audit_btn").click(function(){
        var $checkbox = $('.news_list tbody input[type="checkbox"][name="checked"]');
        var $checked = $('.news_list tbody input[type="checkbox"][name="checked"]:checked');
        if($checkbox.is(":checked")){
            var index = layer.msg('审核中，请稍候',{icon: 16,time:false,shade:0.8});
            setTimeout(function(){
                for(var j=0;j<$checked.length;j++){
                    for(var i=0;i<newsData.length;i++){
                        if(newsData[i].newsId == $checked.eq(j).parents("tr").find(".news_del").attr("data-id")){
                            //***审核文章
                            $.ajax({
                                url:"../../../vocation/check",
                                type:"post",
                                data:{
                                    newsId:newsData[i].newsId},
                                dataType:"json",
                                success:function(data){

                                }
                            });
                            //修改列表中的文字
                            $checked.eq(j).parents("tr").find("td:eq(3)").text("审核通过").removeAttr("style");
                            $checked.eq(j).parents("tr").find("td:eq(5)").html('<input name="show" lay-skin="switch" lay-text="是|否" lay-filter="isShow" checked="" type="checkbox"><div class="layui-unselect layui-form-switch layui-form-onswitch" lay-skin="_switch"><em>是</em><i></i></div>');
                            //将选中状态删除
                            $checked.eq(j).parents("tr").find('input[type="checkbox"][name="checked"]').prop("checked",false);
                            form.render();
                        }
                    }
                }
                layer.close(index);
                layer.msg("审核成功");
            },2000);
        }else{
            layer.msg("请选择需要审核的文章");
        }
    })

    //批量删除
    $(".batchDel").click(function(){
        var $checkbox = $('.news_list tbody input[type="checkbox"][name="checked"]');
        var $checked = $('.news_list tbody input[type="checkbox"][name="checked"]:checked');
        if($checkbox.is(":checked")){
            layer.confirm('确定删除选中的信息？',{icon:3, title:'提示信息'},function(index){
                var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});
                setTimeout(function(){
                    //删除数据
                    for(var j=0;j<$checked.length;j++){
                        for(var i=0;i<newsData.length;i++){
                            if(newsData[i].newsId == $checked.eq(j).parents("tr").find(".news_del").attr("data-id")){
                                //***逐条批量删除
                                $.ajax({
                                    url:"../../../vocation/delete",
                                    type:"post",
                                    data:{
                                        newsId:newsData[i].newsId},
                                    dataType:"json",
                                    success:function(data){

                                    }
                                });
                                newsData.splice(i,1);
                                newsList(newsData);
                            }
                        }
                    }
                    $('.news_list thead input[type="checkbox"]').prop("checked",false);
                    form.render();
                    layer.close(index);
                    layer.msg("删除成功，不批准学生请假理由");
                },2000);
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    })

    //全选
    form.on('checkbox(allChoose)', function(data){
        var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
        child.each(function(index, item){
            item.checked = data.elem.checked;
        });
        form.render('checkbox');
    });

    //通过判断文章是否全部选中来确定全选按钮是否选中
    form.on("checkbox(choose)",function(data){
        var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
        var childChecked = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"]):checked')
        if(childChecked.length == child.length){
            $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = true;
        }else{
            $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = false;
        }
        form.render('checkbox');
    })

    //是否展示
    form.on('switch(isShow)', function(data){
        //var _this = $(this);

        var id = $(this).parents("tr").find("input[type=hidden]").val();

        var index = layer.msg('修改中，请稍候',{icon: 16,time:false,shade:0.8});
        var check = $(this).parents("tr").find("div[class~=layui-form-switch]").hasClass("layui-form-onswitch");
        var cc = $(this).parents("tr").find("td[class]");
        setTimeout(function(){
            //***展示审核操作(需要获取到id)(id为cardId，check为开关点击后内容)
            var checked;

            if(check){
                checked = 2;
                cc.html("<span>审核通过</span>");
            }else{
                checked = 3;
                cc.html("<span style='color:#f00'>待审核</span>");
            }
            $.ajax({
                url:"../../../vocation/check",
                type:"post",
                data:{
                    newsId:id},
                dataType:"json",
                success:function(data){

                }
            });

            layer.close(index);
            layer.msg("学生请假状态批准成功！");
            setTimeout(location.reload(),1000);

        },2000);


    })

    //操作分配管理员
    $("body").on("click",".news_edit",function(){  //编辑分配维修工
        var _this = $(this);
        var sId=_this.attr("data-id");
        var rId=_this.attr("data-rid");
                $.jq_Panel({
                    url:"../../../addUser.html?sId="+sId+"&rId="+rId,
                    async:false,
                    iframeWidth:800,
                    iframeHeight:600,
                });
            //}
        //}
    })

    // $("body").on("click",".news_edit",function(){  //编辑
    //     var _this = $(this);
    //     // layer.confirm('不批准学生请假理由、确定删除此信息？',{icon:3, title:'提示信息'},function(index){
    //         //alert(index);
    //         //_this.parents("tr").remove();
    //         for(var i=0;i<newsAuthor.length;i++){
    //             if(newsAuthor[i].newsId == _this.attr("data-id")){
    //                 //***删除操作向后台传操作码和帖子id
    //                 $.ajax({
    //                     url:"../../../addUser.html",
    //                     type:"post",
    //                     data:{
    //                         newsAuthor:newsData[i].newsAuthor,
    //                         newsId:newsData[i].newsId},
    //                     dataType:"json",
    //                     success:function(data){
    //
    //                     }
    //                 });
    //                 newsData.splice(i,1);
    //                 newsList(newsData);
    //             }
    //         }
    //         layer.close(index);
    //     // });
    // })



    $("body").on("click",".news_collect",function(){  //收藏.
        if($(this).text().indexOf("已收藏") > 0){
            layer.msg("取消收藏成功！");
            $(this).html("<i class='layui-icon'>&#xe600;</i> 收藏");
        }else{
            layer.msg("收藏成功！");
            $(this).html("<i class='iconfont icon-star'></i> 已收藏");
        }
    })

    $("body").on("click",".news_del",function(){  //删除
        var _this = $(this);
        layer.confirm('不批准学生请假理由、确定删除此信息？',{icon:3, title:'提示信息'},function(index){
            //alert(index);
            //_this.parents("tr").remove();
            for(var i=0;i<newsData.length;i++){
                if(newsData[i].newsId == _this.attr("data-id")){
                    //***删除操作向后台传操作码和帖子id
                    $.ajax({
                        url:"../../../vocation/delete",
                        type:"post",
                        data:{
                            newsAuthor:newsData[i].newsAuthor,
                            newsId:newsData[i].newsId},
                        dataType:"json",
                        success:function(data){

                        }
                    });
                    newsData.splice(i,1);
                    newsList(newsData);
                }
            }
            layer.close(index);
        });
    })
    function newsList(that){
        //渲染数据
        function renderDate(data,curr){
            var dataHtml = '';
            if(!that){
                currData = newsData.concat().splice(curr*nums-nums, nums);
            }else{
                currData = that.concat().splice(curr*nums-nums, nums);
            }
            if(currData.length != 0){
                for(var i=0;i<currData.length;i++){
                    dataHtml += '<tr>'
                        +'<td><input type="checkbox" name="checked" lay-skin="primary" lay-filter="choose"></td>'
                        +'<td align="left">'+currData[i].newsName+'</td>'
                        +'<td>'+currData[i].newsAuthor+'</td>';
                    if(currData[i].newsStatus == "待审核"){
                        dataHtml += '<td class = "newsStatus"><span style="color:#f00">'+currData[i].newsStatus+'</span></td>';
                    }else{
                        dataHtml += '<td class = "newsStatus"><span>'+currData[i].newsStatus+'</span></td>';
                    }
                    dataHtml += '<td >'+currData[i].newsLook+'</td>'
                       /* +'<td><input type="checkbox" name="show" lay-skin="switch" lay-text="是|否" lay-filter="isShow"'+currData[i].isShow+'></td>'
                        +'<td>'+currData[i].newsTime1+'</td>'
                        +'<td>'+currData[i].newsTime2+'</td>'*/
                        +'<td>'
                        +  '<a class="layui-btn layui-btn-mini news_edit " data-id="'+currData[i].newsAuthor+'" data-rid="'+currData[i].newsId+'"><i class="iconfont icon-edit"></i> 分配</a>'
                       /*  +  '<a class="layui-btn layui-btn-normal layui-btn-mini news_collect"><i class="layui-icon">&#xe600;</i> 收藏</a>'*/

                        +  '<input type="hidden" value="'+data[i].newsId+'">'

                        /*+  '<a class="layui-btn layui-btn-danger layui-btn-mini news_del" data-id="'+data[i].newsId+'"><i class="layui-icon">&#xe640;</i> 删除</a>'*/
                        +'</td>'
                        +'</tr>';
                }
            }else{
                dataHtml = '<tr><td colspan="8">暂无数据</td></tr>';
            }
            return dataHtml;
        }

        //分页
        var nums = 13; //每页出现的数据量
        if(that){
            newsData = that;
        }
        laypage({
            cont : "page",
            pages : Math.ceil(newsData.length/nums),
            jump : function(obj){
                $(".news_content").html(renderDate(newsData,obj.curr));
                $('.news_list thead input[type="checkbox"]').prop("checked",false);
                form.render();
            }
        })
    }
})