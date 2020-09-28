package com.zf.zjtf;

import com.zf.zjtf.domain.Configuration;
import com.zf.zjtf.util.WxApiUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.Map;

/**
 * @author zhangfei on 2020/9/21
 */
@Controller
public class Panoramic {
    @RequestMapping(value = {"","/","/home"})
    public ModelAndView getParamDemo1 () throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        //JSONObject wxConfig = WxApiUtil.createWxConfig();
        modelAndView.setViewName("panoramic");
//        Iterator iter = wxConfig.entrySet().iterator();
//        while (iter.hasNext()) {
//            Map.Entry entry = (Map.Entry) iter.next();
//            modelAndView.addObject(entry.getKey().toString(),entry.getValue().toString());
//        }
        return modelAndView;
    }

    @RequestMapping(path = {"/getAreaMap"})
    public String getAraMap (){
        return "areaMap";
    }
    @RequestMapping(path = {"/getPanoramaView"})
    public String getpanoramaView (){
        return "panoramaView";
    }
    @RequestMapping(path = {"/getPark"})
    public String getPark (){
        return "park";
    }
    @RequestMapping(path = {"/getRoomType"})
    public String getRoomType (){
        return "roomType";
    }
    @RequestMapping(path = {"/simple"})
    public String getSimple (){
        return "simple";
    }
    @RequestMapping(path = {"/projectOverview"})
    public String getProjectOverview (){
        return "projectOverview";
    }
    @RequestMapping(path = {"/planet"})
    public String getPlanet (){
        return "planet";
    }
}
