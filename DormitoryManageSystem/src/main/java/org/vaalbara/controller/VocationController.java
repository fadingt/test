package org.vaalbara.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.vaalbara.advice.SystemControllerLog;
import org.vaalbara.bean.Vocation;
import org.vaalbara.bean.dao.VocationDao;
import org.vaalbara.service.IVocationService;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/13.
 */
@Controller
@RequestMapping("vocation")
public class VocationController {
    @Autowired
    IVocationService vocationService;
    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "申请请假")
    public int addVocation(Vocation vocation){
        vocationService.addVocation(vocation);
        return 1;
    }
    @RequestMapping(value = "get",method = RequestMethod.POST,produces="text/html;charset=UTF-8;"
    )
    @ResponseBody
    @SystemControllerLog(description = "管理员获取所有请求")
    public String getAllVocation(){
        Gson gson=new Gson();
        String rtn=gson.toJson(vocationService.getAllVocation());
        return rtn;
    }
    @RequestMapping(value = "shenhe",method = RequestMethod.POST,produces="text/html;charset=UTF-8;")
    @ResponseBody
    @SystemControllerLog(description = "管理员审核请假请求")
    public int updateVocation(VocationDao voDao){
         System.out.println("shenheVocation进来了"+voDao.getNewsId());
         return vocationService.updateVocation(voDao);
    }
    @RequestMapping(value = "delete",method = RequestMethod.POST,produces="text/html;charset=UTF-8;")
    @ResponseBody
    @SystemControllerLog(description = "管理员不批准请假")
    public int deleteVocation(VocationDao voDao){
        System.out.println("deleteVocation进来了");
        System.out.println(voDao.getNewsAuthor());
        System.out.println(voDao.getNewsId());

        return vocationService.deleteVocation(voDao);
    }
}
