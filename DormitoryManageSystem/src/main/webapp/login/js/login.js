$(function(){

    $('#num').keyup(function(event) {
        $('.tel-warn').addClass('hide');
        checkBtn();
    });

    $('#pass').keyup(function(event) {
        $('.tel-warn').addClass('hide');
        checkBtn();
    });



    // 按钮是否可点击
    function checkBtn()
    {
        $(".log-btn").off('click');
        var inp = $.trim($('#num').val());
        var pass = $.trim($('#pass').val());
        if (inp != '' && pass != '') {

            $(".log-btn").removeClass("off");
            sendBtn();
        } else {
            $(".log-btn").addClass("off");
        }
    }

    function checkccount(username){
        var num = $("#num").val();
        if (num == '') {
            $('.num-err').removeClass('hide').find("em").text('请输入账户');
            return false;
        }else {
            $('.num-err').addClass('hide');
            return true;
        }
    }


    function checkAccount(username){
        var email = $("#num").val();
        var regEmail = /^([a-zA-Z0-9_.-]+)@([da-z.-]+).([a-z.]{2,6})$/;
        if (username == '') {
            $('.num-err').removeClass('hide').find("em").text('请输入账户');
            return false;
        } else if(!regEmail.test(email)){
            $('.num-err').removeClass('hide').find('em').text("email格式不正确");
            $('.num-err').find('i').attr('class', 'icon-warn').css("color","#d9585b");
            return false;
        }else {
            $('.num-err').addClass('hide');
            return true;
        }

    }

    function checkPass(pass){
        if (pass == '') {
            $('.pass-err').removeClass('hide').text('请输入密码');
            return false;
        } else {
            $('.pass-err').addClass('hide');
            return true;
        }
    }

    // 登录点击事件
    function sendBtn(){
        $(".log-btn").click(function(){
            var inp = $.trim($('#num').val());
            var pass = $.trim($('#pass').val());
            var identity = $.trim($('#identity').val());

            var ldata = null;
            var url = null;
            var loginurl = null;
            var flog = false;

            if(identity == 1){
                ldata ={aName:inp,aPass:pass};
                url = '../admin';
                loginurl= '../index.html';
                flog = (checkccount(inp) && checkPass(pass));
            }else{
                ldata ={sEmail:inp,sPass:pass};
                url = '../students';
                loginurl= '../Student/index.html';
                flog = (checkAccount(inp) && checkPass(pass));
            }
            if (flog) {
                $.ajax({
                    url:url,
                    type: 'get',
                    dataType: 'json',
                    async: true,
                    data: ldata,
                    success:function(data){
                        if (data.toString() == '1') {
                            alert("登陆成功");
                            $(window).attr('location',loginurl);
                        } else if(data.toString() == '0') {
                            $(".log-btn").off('click').addClass("off");
                            alert("用户名或密码错误");
                        } else if(data.toString() == '2'){
                            alert("登陆成功,但是需要去完善个人信息");
                            $(window).attr('location','../Student/personal.html');
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