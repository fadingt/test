package org.vaalbara.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.vaalbara.advice.SystemControllerLog;
import org.vaalbara.bean.Maintain;
import org.vaalbara.bean.ManyIds;
import org.vaalbara.bean.Repair;
import org.vaalbara.service.IMaintainService;
import org.vaalbara.service.IRepairService;
import org.vaalbara.service.IStuInfoService;
import org.vaalbara.utils.JavaMailLearn;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */
@Controller
@RequestMapping("repair")
public class RepairController {
    @Autowired
    IRepairService repairService;

    @Autowired
    IMaintainService iMaintainService;

    @Autowired
    IStuInfoService iStuInfoService;

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "学生提交维修信息")
      public int addRepair(Repair repair){
//        System.out.println("repair进来了");
        repairService.addRepair(repair);
        return 1;
    }
    @RequestMapping(value = "get",method = RequestMethod.POST,produces="text/html;charset=UTF-8;")
    @ResponseBody
    @SystemControllerLog(description = "获取所有维修信息")
    public String getAllRepair(){
        System.out.println("getAllRepair进来了");
        Gson gson=new Gson();
        String rtn=gson.toJson(repairService.getAllRepair());
        return rtn;
    }
//    @RequestMapping(value = "check",method = RequestMethod.POST,produces="text/html;charset=UTF-8;")
//    @ResponseBody
//    public int checkRepair(VocationDao voDao){
//        System.out.println("checkVocation进来了");
//        return repairService.checkRepair(voDao);
//    }
//    @RequestMapping(value = "delete",method = RequestMethod.POST,produces="text/html;charset=UTF-8;")
//    @ResponseBody
//    public int deleteRepair(VocationDao voDao){
//        System.out.println("deleteRepair");
//        System.out.println(voDao.getNewsAuthor());
//        System.out.println(voDao.getNewsId());
//
//        return repairService.deleteRepair(voDao);
//    }

    @RequestMapping(value = "represon",method = RequestMethod.GET,produces="text/html;charset=UTF-8;")
    @ResponseBody
    @SystemControllerLog(description = "根据id获取维修信息")
    public String getRepair(Integer rId) {
        System.out.println("represon进来了"+"rId"+rId);
        String repairinfo=repairService.getRepair(rId);
        System.out.println("represon出去了");
        return repairinfo;
    }

    /**
     * 随机查询数据
     * @param repair
     * @return
     */
    @RequestMapping(value = "otherinfo",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "Repair随机查询数据")
    public List<Repair> selectRepInfo(Repair repair) {
        System.out.println("otherinfo进来了");
        List list=repairService.selectRepInfo(repair.getdId());
        System.out.println(list+"是..........................");
        System.out.println("otherinfo出去了");
        return list;
    }

    @RequestMapping(value = "statusinfo",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "分配维修工")
    public int updateRepStatus(ManyIds manyIds) {
        System.out.println("statusinfo"+"rId是"+manyIds.getrId()+":"+"其他id"+manyIds);
        //维修工名字
        String name=iMaintainService.getMaintainById(manyIds.getmId()).getmName();
        String email=iMaintainService.getMaintainById(manyIds.getmId()).getmEmail();
        //宿舍号
        String droinfo=iStuInfoService.existStu(manyIds.getsId());
        //维修原因
        String reason=repairService.getRepair(manyIds.getrId());
        System.out.println(name+"是-------------是:"+email+"是-------------是:"+droinfo+"是-------------是:"+reason);
        String msg="尊敬的"+name+":您好，"+droinfo+"号宿舍，需要维修，"+"维修原因："+reason+"!请尽快上门维修。";
        System.out.println(msg);
        try {
            JavaMailLearn.ready(email,msg);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("邮件发送异常");
        }

        int status=repairService.updateRepStatus(manyIds);
        System.out.println("statusinfo出去了");
        System.out.println(status);
        return status;
    }
}
