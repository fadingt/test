package org.vaalbara.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.vaalbara.advice.SystemControllerLog;
import org.vaalbara.bean.DroStudent;
import org.vaalbara.bean.Dromitory;
import org.vaalbara.service.IStuInfoService;
import org.vaalbara.service.IdromitoryService;
import org.vaalbara.advice.SystemControllerLog;

import javax.ws.rs.POST;
import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */
@Controller
@RequestMapping(value = "dromitoryinfo")
public class DromitoryController{

    //声明dromitory
    @Autowired //默认根据类型自动装配的
    IdromitoryService idromitoryService;

    @Autowired
    IStuInfoService iStuInfoService;

    /**
     * 获取宿舍全部信息
     * @return
     */
    @RequestMapping(value = "dinfo",method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "显示所有宿舍信息")
    public List<Dromitory> getDromitoryAll(){
        System.out.println("dinfo进来了");
        List list=idromitoryService.getDromitoryAll();
        System.out.println(idromitoryService.getDromitoryAll());
        System.out.println("dinfo出去了");
        return list;
    }

    /**
     * 根据Id查询宿舍
     * @param dId
     * @return
     */
    @RequestMapping(value = "dinfobyid",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "根据Id查询宿舍")
    public List<Dromitory> getDromitoryById(Integer dId){
        System.out.println(dId);
        System.out.println("dinfobyid进来了");
       List list1 = idromitoryService.getDromitoryById(dId);
        System.out.println(list1);
        System.out.println("dinfobyid出去了");
       return  list1;
    }

    /**
     * 获取宿舍住宿情况消息
     * @param droStudent
     * @return
     */
    @RequestMapping(value = "sinfo",method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "获取宿舍住宿情况消息")
    public List<Dromitory> getStuInfo(DroStudent droStudent){
        System.out.println("id是"+droStudent.getdId());
        System.out.println("sinfo进来了");
        List list2=iStuInfoService.getStuInfo(droStudent);
        System.out.println(list2);
        System.out.println("sinfo出去了");
        return list2;
    }

    /**
     * 查询学生所在宿舍
     * @param sId
     * @return
     */
    @RequestMapping(value = "studro",method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "查询学生所在宿舍")
    public String existStu(Integer sId){
        System.out.println("studro进来了----------------"+"sId是:"+sId);
        String studro= iStuInfoService.existStu(sId);
        System.out.println("结果是。。。。。。。。。。。。。。。:"+studro);
        System.out.println("studro出去了");
        return studro;
    }

    /**
     * 修改宿舍住宿情况
     * @param droStudent
     * @return
     */
    @RequestMapping(value = "dsinfo",method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "修改宿舍住宿情况")
    public int updateDroStuInfo(DroStudent droStudent){
        System.out.println(droStudent);
        System.out.println("dsinfo进来了");
        //传的 sId 是 0 把 sId 改成 null
        if(droStudent.getsId()==0){
          return iStuInfoService.deleteDroStuInfo(droStudent);
        };

        //判断该学生是否住宿舍
        String existStu=iStuInfoService.existStu(droStudent.getsId());
        if(existStu!=null){
            System.out.println(existStu+"存在！");
            return Integer.parseInt(existStu);
        }
        //没住宿舍修改学生住宿
        int stuinfo=iStuInfoService.updateDroStuInfo(droStudent);

        System.out.println(stuinfo);
        System.out.println("dsinfo出去了");
        return stuinfo;
    }


}
