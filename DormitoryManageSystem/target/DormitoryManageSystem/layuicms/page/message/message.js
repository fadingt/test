var $;
layui.config({
	base : "../../js/"
}).use(['form','layer','layedit'],function(){
    var form = layui.form(),
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        layedit = layui.layedit;
        $ = layui.jquery;

    //消息回复
    var editIndex = layedit.build('msgReply',{
         tool: ['face'],
         height:100
    });
        
    
    
    //用户问的问题显示
    	$.ajax({
    		url:"../../../../adminMessage",
    		type:"post",
    		data:{},
    		async:false,
    		dataType:"json",
    		success:function(data){

    			var msgHtml = '';
    		        for(var i=0; i<data.length; i++){
    		            msgHtml += '<tr>';
    		            msgHtml += '  <td class="msg_info">';
    		            msgHtml += '    <img src="../../../../proscenium'+data[i].userHead+'" width="50" height="50"><input type="hidden" value="'+data[i].messageId+'">';
    		            msgHtml += '    <div class="user_info"><input type="hidden" name ="messageUserEmail" value="'+data[i].messageUserEmail+'">';
    		            msgHtml += '        <h2>'+data[i].userName+'</h2>';
    		            msgHtml += '        <p>'+data[i].messageContent+'</p>';
    		           /* alert(data[i].messageUserEmail);*/
    		            msgHtml += '    </div>';
    		            msgHtml += '  </td>';
    		            msgHtml += '  <td class="msg_time">'+data[i].messageTime+'</td>';
    		            msgHtml += '  <td class="msg_opr">';
    		            msgHtml += '    <a class="layui-btn layui-btn-mini reply_msg"><i class="layui-icon">&#xe611;</i> 回复</a>';
    		            msgHtml += '  </td>';
    		            msgHtml += '</tr>';
    		        }
    		        $(".msgHtml").html(msgHtml);
    		}
    	});
       
    	
    	var id;
    	var messageUserEmail;
    	 //回复
        $("body").on("click",".reply_msg,.msgHtml .user_info h2,.msgHtml .msg_info>img",function(){
            
        	messageUserEmail = $(this).parents("tr").find("input[name=messageUserEmail]").val();
        	id = $(this).parents("tr").find("input[type=hidden]").val(); 
            var userName = $(this).parents("tr").find(".user_info h2").text();
           /* alert(messageUserEmail);*/
            var index = layui.layer.open({
                title : "与 "+userName+" 的聊天",
                type : 2,
                content : "messageReply.html?messageId="+id+"&messageUserEmail="+messageUserEmail,
                success : function(layero, index){
                    setTimeout(function(){
                        layui.layer.tips('点击此处返回消息列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    },500)
                    var body = layui.layer.getChildFrame('body', index);
                //加载回复信息
                    $.ajax({
                		url:"../../../../adminReplyMessage",
                		type:"post",
                		data:{
                			messageId:id
                		},
                		async:false,
                		dataType:"json",
                		success:function(data){
                    var msgReplyHtml = '';
    		        for(var i=0; i<data.length; i++){
    		            msgReplyHtml += '<tr>';
    		            msgReplyHtml += '  <td class="msg_info">';
    		            msgReplyHtml += '    <img src="../../../../proscenium'+data[i].userHead+'" width="50" height="50"><input type="hidden" value="'+data[i].messageId+'">';
    		            msgReplyHtml += '    <div class="user_info">';
    		            msgReplyHtml += '        <h2>'+data[i].userName+'</h2>';
    		            msgReplyHtml += '        <p>'+data[i].messageContent+'</p>';
    		            msgReplyHtml += '    </div>';
    		            msgReplyHtml += '  </td>';
    		            msgReplyHtml += '  <td class="msg_time">'+data[i].messageTime+'</td>';
    		            msgReplyHtml += '</tr>';
    		        }
                    body.find(".msgReplyHtml").html(msgReplyHtml);
                		}
                		})
            }
        })
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function(){
            layui.layer.full(index);
        })
        layui.layer.full(index);
    })

    
    function getvalue(name) {
			var str = window.location.search;
			if (str.indexOf(name) != -1) {
				var pos_start = str.indexOf(name)
						+ name.length + 1;
				var pos_end = str.indexOf("&", pos_start);
				if (pos_end == -1) {
					return str.substring(pos_start);
				} else {
					return str
							.substring(pos_start, pos_end)
				}
			}
		}
        
    //提交回复
        $(".send_msg").click(function(){
        mId = getvalue("messageId");
        messageUserEmail = getvalue("messageUserEmail");
        /*alert(messageUserEmail);*/
    	
        if(layedit.getContent(editIndex) != ''){
        	var content = layedit.getContent(editIndex);
        	 $.ajax({
         		url:"../../../../replyMessageContent",
         		type:"post",
         		data:{
         			messageUserEmail:messageUserEmail,
         			messageId:mId,
         			replyContent:content
         		},
         		success:function(data){
         			if (data == "101"){
         				
                    	window.parent.location.href   =  "message.html";
                    	layer.msg("回复成功");
         			}else{
         				 layer.msg("回复失败");
         			}
         			
         		}
        	 })
        }else{
            layer.msg("请输入回复信息");
        }
   })
})

