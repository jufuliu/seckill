package org.seckill.web;

import org.seckill.entity.Seckill;
import org.seckill.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by liu on 2016/7/9.
 */
@Controller
@RequestMapping(value="/seckill/*")
public class SeckillController {

    @Autowired
    private ISeckillService seckillService;

    @RequestMapping(value="list",method= RequestMethod.GET)
    public String list(Model model){
        List<Seckill> list=seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }
}
