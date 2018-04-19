package org.vaalbara.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.vaalbara.advice.SystemControllerLog;
import org.vaalbara.bean.Criticize;
import org.vaalbara.bean.News;
import org.vaalbara.service.ICriticizeService;
import org.vaalbara.service.impl.CriticizeServiceImpl;

import java.util.List;

/**
 * Created by Huawei on 2018/3/14.
 */
@Controller
@RequestMapping("criticize")
public class CriticizeController {

    @Autowired
    ICriticizeService criticizeService;
    //CriticizeServiceImpl criticizeService;

    @RequestMapping(value = "all",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "管理员获取所有处分信息")
    public List<News> getAllNews(){
        //System.out.println("11111111111111111111");
        return criticizeService.getAllCriticize();
    }


    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "管理员添加一条处分信息")
    public String addCriticize(Criticize criticize){
            //String sId,String cRason,String cDate
            //System.out.println("****************"+sId+cRason+cDate);

        criticize.setcId(2);
        //System.out.println("*****************"+criticize);
        Gson gson=new Gson();
        String rtn=gson.toJson(criticizeService.addCriticize(criticize));

        return rtn;
    }

    @RequestMapping(value = "del",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "管理员删除一条处分信息")
    public int delCriticize(int newsId){
        //System.out.println("*******************"+newsId);
        return criticizeService.delCriticize(newsId);
    }

    @RequestMapping(value = "getone",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "管理员获取一条处分信息")
    public Criticize getCriticize(int cId){
        //System.out.println("*******************"+cId);
        return criticizeService.getCriticize(cId);
    }

    @RequestMapping(value = "mod",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "管理员修改一条处分信息")
    public int updateCriticize(Criticize criticize){
        //System.out.println("*******************"+criticize);
        return criticizeService.updateCriticize(criticize);
    }


}
