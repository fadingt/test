$(function(){

    $('#num').keyup(function(event) {
        $('.tel-warn').addClass('hide');
        var inp = $.trim($('#num').val());
        if(checkAccount(inp)){
            $.ajax({
                url: '../students/reg',
                type: 'get',
                dataType:'json',
                data: {
                    sEmail:inp
                },
                success:function(data){
                    if (data.toString() == "0") {
                        $('.num-err').removeClass('hide').find('em').css("color","#8FD132").text("该邮箱可以注册");
                        $('.num-err').find('i').attr('class', 'icon-warn').css("color","#8FD132");
                    } else{
                        $('.num-err').removeClass('hide').find('em').css("color","#d9585b").text("该邮箱已被注册");
                        $('.num-err').find('i').attr('class', 'icon-warn').css("color","#d9585b");
                    }
                },
                error:function(){
                }
            });
        }
        checkBtn();
    });

    $('#pass').keyup(function(event) {
        $('.tel-warn').addClass('hide');
        checkBtn();
    });

    $('#pass2').keyup(function(event) {
        $('.tel-warn').addClass('hide');
        checkBtn();
    });



    // 按钮是否可点击
    function checkBtn()
    {
        $(".log-btn").off('click');
        var inp = $.trim($('#num').val());
        var pass = $.trim($('#pass').val());
        var pass2 = $.trim($('#pass2').val());
        if (inp != '' && pass != ''&& pass2 != '') {
            $(".log-btn").removeClass("off");
            sendBtn();
        } else {
            $(".log-btn").addClass("off");
        }
    }

    function checkAccount(username){
        var email = $("#num").val();
        var regEmail = /^([a-zA-Z0-9_.-]+)@([da-z.-]+).([a-z.]{2,6})$/;
        if (username == '') {
            $('.num-err').removeClass('hide').find("em").css("color","#d9585b").text('请输入账户');
            return false;
        } else if(!regEmail.test(email)){
            $('.num-err').removeClass('hide').find('em').css("color","#d9585b").text("email格式不正确");
            $('.num-err').find('i').attr('class', 'icon-warn').css("color","#d9585b");
            return false;
        }else {
            $('.num-err').addClass('hide');
            return true;
        }

    }

    function checkPass(pass){
        var pass2 = $.trim($('#pass2').val());
        if (pass == '') {
            $('.pass-err').removeClass('hide').text('请输入密码');
            return false;
        } else if (pass2 != pass) {
            $('.confirmpwd-err').removeClass('hide').find('em').text("两次密码不同");
            $('.confirmpwd-err').find('i').attr('class', 'icon-warn').css("color","#d9585b");
            return false;
        }else{
            $('.pass-err').addClass('hide');
            return true;
        }
    }

    // 登录点击事件
    function sendBtn(){
        $(".log-btn").click(function(){
            var inp = $.trim($('#num').val());
            var pass = $.trim($('#pass').val());

            if (checkAccount(inp) && checkPass(pass)) {
                var ldata = {sEmail:inp,sPass:pass};
                $.ajax({
                    url: '../students/add',
                    type: 'get',
                    dataType: 'json',
                    async: true,
                    data: ldata,
                    success:function(data){
                        if (data.toString() == '1') {
                            alert("注册成功,去完善信息");
                            $(window).attr('location','../Student/personal.html');
                        } else{
                            $(".log-btn").off('click').addClass("off");
                            alert("出问题了");
                        }
                    },
                    error:function(){
                    }
                });
            } else {
                return false;
            }
        });
    }

    // 登录的回车事件
    $(window).keydown(function(event) {
        if (event.keyCode == 13) {
            $('.log-btn').trigger('click');
        }
    });
});
