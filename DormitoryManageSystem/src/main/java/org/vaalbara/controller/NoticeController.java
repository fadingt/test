package org.vaalbara.controller;

import com.google.gson.Gson;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.vaalbara.advice.SystemControllerLog;
import org.vaalbara.bean.Admin;
import org.vaalbara.bean.Links;

import org.vaalbara.bean.Notice;
import org.vaalbara.bean.Student;
import org.vaalbara.service.INoticeService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Huawei on 2018/3/12.
 */
@Controller
@RequestMapping("notice")
public class NoticeController {
    @Autowired
    private INoticeService iNoticeService;

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "管理员添加一条公告信息")
    public int addNotice(Notice notice,HttpSession httpSession){
        Admin admin = (Admin)httpSession.getAttribute("admin");
        notice.setaId(admin.getaId());
        notice.setaName(admin.getaName());

        //System.out.println("1111111111111111"+notice.toString());
//        Gson gson=new Gson();
//        String rtn=gson.toJson(iNoticeService.addNotice(notice));
        //System.out.println("2222222222222222"+notice.toString());
        return iNoticeService.addNotice(notice);
    }

    @RequestMapping(value = "all",method = RequestMethod.POST,produces = "text/.html;charset=UTF-8;")
    @ResponseBody
    @SystemControllerLog(description = "管理员获取所有公告信息")
    public String getAllLinks(){
//        System.out.println(iNoticeService.getAllLinks().toString());
        //必须借用gson封装成规定的特有数据格式的json
        Gson gson=new Gson();
        String rtn=gson.toJson(iNoticeService.getAllLinks());
        return rtn;
    }


    @RequestMapping(value = "showAll",method = RequestMethod.POST,produces = "text/.html;charset=UTF-8;")
    @ResponseBody
    @SystemControllerLog(description = "显示所有公告信息")
    public List<Links> showAll(){
        //System.out.println("在这里显示公告...");

//        Gson gson=new Gson();
//        String rtn=gson.toJson(iNoticeService.getAllLinks());

        return iNoticeService.getAllLinks();
    }


    @RequestMapping(value = "del",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "管理员删除一条公告信息")
    public int delNotice(int dataid){
//        int flag = iNoticeService.delNotice(dataid);
        return iNoticeService.delNotice(dataid);
    }



    @RequestMapping(value = "notices",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "显示所有公告信息")
    public List<Notice> getAllNotice(){
//        List<Notice> list = iNoticeService.getAllNotice();
//        System.out.println(list);

        return iNoticeService.getAllNotice();
    }


    @RequestMapping(value = "notice",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "根据ID获取一条公告信息")
    public Notice getNoticeById(int id){
//        Notice notice = iNoticeService.getNoticeById(id);
//        System.out.println(notice);

        return iNoticeService.getNoticeById(id);
    }



    @RequestMapping(value = "mod",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "管理员修改一条公告信息")
    public int updateNotice(Notice notice){
        //System.out.println("**************************"+notice);
        return iNoticeService.updateNotice(notice);
    }
}
