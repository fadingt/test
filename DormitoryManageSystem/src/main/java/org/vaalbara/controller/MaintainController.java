package org.vaalbara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.vaalbara.advice.SystemControllerLog;
import org.vaalbara.bean.Maintain;
import org.vaalbara.service.IMaintainService;
import org.vaalbara.service.IdromitoryService;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */
@Controller
@RequestMapping(value = "maintain")
public class MaintainController {
    //声明maintain
    @Autowired //默认根据类型自动装配的
    IMaintainService iMaintainService;

    /**
     * 查询所有维修工
     * @return
     */
    @RequestMapping(value = "minfo",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "查询所有维修工")
    public List<Maintain> getMaintainAll(){
        System.out.println("minfo进来了");
        List list=iMaintainService.getMaintainAll();
        System.out.println(list);
        System.out.println("minfo出去了");
        return list;
    };
    @RequestMapping(value = "maintains",method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "管理员查询所有的维修工信息")
    public List<Maintain> getMaintains(){
        System.out.println("我正在查询维修工");
        List<Maintain> maintains = iMaintainService.getMaintain();
        System.out.println(maintains);
        return maintains;
    }
    @RequestMapping(value = "zeng",method = RequestMethod.POST )
    @ResponseBody
    @SystemControllerLog(description = "管理员增加维修工")
    public int  addMaintain(Maintain maintain){
        System.out.println("正在增加维修工");
        System.out.println(maintain.toString());
        int i=0;
        i= iMaintainService.addMaintain(maintain);
        return i;
    }

    @RequestMapping(value = "gai",method = RequestMethod.POST )
    @ResponseBody
    @SystemControllerLog(description = "管理员修改维修工信息")
    public int  updateMaintain(Maintain maintain){
        System.out.println("正在修改维修工");
        System.out.println(maintain.toString());
        int i=0;
        i= iMaintainService.updateMaintain(maintain);
        return i;
    }

    @RequestMapping(value = "shana",method = RequestMethod.POST )
    @ResponseBody
    @SystemControllerLog(description = "管理员删除维修工")
    public int  deleteMaintain(int mId){
        System.out.println("进来了，进来了");
        System.out.println("正在删除维修工");
        System.out.println(mId);
        int i=0;
        i= iMaintainService.deleteMaintain(mId);
        System.out.println(i);
        return i;
    }
    @RequestMapping(value = "cha",method = RequestMethod.POST )
    @ResponseBody
    @SystemControllerLog(description = "管理员按条件查询维修工信息")
    public List<Maintain>  selectMaintain(String mName){
        System.out.println("进来了，进来了");
        System.out.println("正在查询维修工");
        System.out.println(mName);
        List<Maintain> maintainList= iMaintainService.selectMaintain(mName);
        return maintainList;
    }
}
