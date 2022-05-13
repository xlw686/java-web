package com.kong.servlet.provider;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.kong.pojo.Provider;
import com.kong.pojo.User;
import com.kong.service.provider.ProviderService;
import com.kong.service.provider.ProviderServiceImpl;
import com.kong.util.Constants;
import com.kong.util.PageSupport;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ThreePure
 * @date 20/12/13 8:32
 * @description: provider的servlet
 * @since 1.8
 */
public class ProviderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if ("query".equals(method)) {
            this.providerQuery(req, resp);
        }else if ("view".equals(method)){
            this.providerView(req, resp,"providerview.jsp");
        }else if ("modify".equals(method)){
            this.providerView(req, resp,"providermodify.jsp");
        }else if ("modifyexe".equals(method)){
            this.providerModify(req, resp);
        }else if ("delprovider".equals(method)){
            this.providerDelete(req, resp);
        }else if ("pcexist".equals(method) && method != null) {
            this.providerCodeExist(req, resp);
        } else if ("add".equals(method)){
            this.providerAdd(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * @description:查询供应商
     * @date 20/12/14 17:52
     * @Param: [req, resp]
     * @Return: void
     */
    private void providerQuery(HttpServletRequest req, HttpServletResponse resp) {
        //查询供应商列表
        String queryProCode = req.getParameter("queryProCode");
        String queryProName = req.getParameter("queryProName");
        String pageIndex = req.getParameter("pageIndex");

//        System.out.println("Servlet->queryProCode:"+queryProCode);
//        System.out.println("Servlet->queryProName:"+queryProName);
//        System.out.println("Servlet->pageIndex:"+pageIndex);


        //获取供应商列表
        ProviderServiceImpl providerService = new ProviderServiceImpl();
        //第一次走这个请求，一定是第一页，页面大小固定的
        //可用把pageSize写道配置文件，方便后期修改
        int pageSize = 5;
        int currentPageNo = 1;
        if (queryProName == null) {
            queryProName = "";
        }
        if (queryProCode == null) {
            queryProCode = "";
        }
        if (pageIndex != null) {
            currentPageNo = Integer.parseInt(pageIndex);
        }

        //获取供应商数量
        int totalCount = providerService.getProviderCount(queryProName, queryProCode);

        //总页数支持
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        //最大页数，总共有几页【如果totalCount刚好是pageSize的整数倍，那么是不是多算了一页】
        int totalPageCount = ((int) (totalCount / pageSize)) + 1;

        //控制首页和尾页
        if (currentPageNo < 1) {
            //如果页面小于第一页，就显示第一页
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            //如果页面大于最后一页，就显示最后一页
            currentPageNo = totalPageCount;
        }
        //获取用户列表展示
        List<Provider> providerList = null;
        providerList = providerService.getProviderList(queryProName, queryProCode, currentPageNo, pageSize);
        req.setAttribute("providerList", providerList);

        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPageNo", currentPageNo);
        req.setAttribute("totalPageCount", totalPageCount);
        req.setAttribute("queryProName", queryProName);
        req.setAttribute("queryProCode", queryProCode);

        //返回前端
        try {
            req.getRequestDispatcher("providerlist.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @description:查看某一具体的供应商
     * @date 20/12/14 17:52
     * @Param: [req, resp,url]
     * @Return: void
     */
    private void providerView(HttpServletRequest req, HttpServletResponse resp,String url) throws ServletException, IOException {
        String id = req.getParameter("proid");
        if (!StringUtils.isNullOrEmpty(id)){
            //调用后台方法得到user对象
            ProviderService providerService = new ProviderServiceImpl();
            Provider provider = null;
            provider = providerService.getProviderById(id);
            req.setAttribute("provider", provider);
            req.getRequestDispatcher(url).forward(req, resp);
        }
    }

    /**
     * @description: 修改供应商
     * @date 20/12/15 8:23
     * @Param: [req, resp]
     * @Return: void
     */
    private void providerModify(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //接收更改内容
        String id = req.getParameter("providerid");
        String proName = req.getParameter("proName");
        String proContact = req.getParameter("proContact");
        String proPhone = req.getParameter("proPhone");
        String proAddress = req.getParameter("proAddress");
        String proFax = req.getParameter("proFax");
        String proDesc = req.getParameter("proDesc");
        //创建一个Provider对象，并且根据表单获取的内容设置其属性值
        Provider provider = new Provider();
        provider.setId(Integer.valueOf(id));
        provider.setProName(proName);
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProAddress(proAddress);
        provider.setProFax(proFax);
        provider.setProDesc(proDesc);
        provider.setModifyBy(((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        provider.setModifyDate(new Date());

        ProviderService providerService = new ProviderServiceImpl();
        boolean b = providerService.providerModify(provider);
        if (b){
            resp.sendRedirect(req.getContextPath() + "/jsp/provider.do?method=query");
        } else {
            req.getRequestDispatcher("usermodify.jsp").forward(req, resp);
        }
    }

    /**
     * @description: 删除供应商
     * @date 20/12/15 8:29
     * @Param: [req, resp]
     * @Return: void
     */
    private void providerDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String proid = req.getParameter("proid");
        Integer delId=0;
        boolean b=false;
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            delId = Integer.parseInt(proid);
        } catch (Exception e) {
            delId = 0;
        }
        ProviderServiceImpl providerService = new ProviderServiceImpl();

        /*
        先判断供应商是否存在，如果存在则继续判断是否含有订单信息，如果均没有再执行删除操作
         */
        Provider providerById = providerService.getProviderById(proid);
        int count = providerService.noPayBillCount(delId);
        if (providerById==null){
            map.put("delResult", "notexist");
        }else if (count>0){
            String cont= String.valueOf(count);
            map.put("delResult", cont);
        }else {
            b = providerService.deleteProviderById(delId);
            if (b) {
                map.put("delResult", "true");
            } else {
                map.put("delResult", "false");
            }
        }

        //把map转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(map));
        writer.flush();
        writer.close();

    }

    /**
     * @description: 判断是供应商编码是否可用
     * @date 20/12/15 8:35
     * @Param: [req, resp]
     * @Return: void
     */
    private void providerCodeExist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //首先我们判断数据库中是否存在该供应商proCode，首先得获取到用户输入的proCode
        String proCode = req.getParameter("proCode");
        //万能的Map：结果集
        Map<String, String> map = new HashMap<String, String>();
        //判断输入是否为空
        if (StringUtils.isNullOrEmpty(proCode)) {
            map.put("proCode", "empty");
        }else {
            ProviderService providerService = new ProviderServiceImpl();
            Provider provider = providerService.providerCodeExist(proCode);
            if (provider!=null){
                map.put("proCode", "exist");
            }else {
                map.put("proCode", "notexist");
            }
        }
        //将Map数据转换成json返回
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(map));
        writer.flush();
        writer.close();
    }

    /**
     * @description: 添加供应商
     * @date 20/12/15 8:29
     * @Param: [req, resp]
     * @Return: void
     */
    private void providerAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //获取用户输入的要添加的供应商数据
        String proCode = req.getParameter("proCode");
        String proName = req.getParameter("proName");
        String proContact = req.getParameter("proContact");
        String proPhone = req.getParameter("proPhone");
        String proAddress = req.getParameter("proAddress");
        String proFax = req.getParameter("proFax");
        String proDesc = req.getParameter("proDesc");

        //创建provider对象，存储数据
        Provider provider = new Provider();
        provider.setProCode(proCode);
        provider.setProName(proName);
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProAddress(proAddress);
        provider.setProFax(proFax);
        provider.setProDesc(proDesc);
        provider.setCreatedBy(((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        provider.setCreationDate(new Date());

        //调用Service层的方法
        ProviderServiceImpl providerService = new ProviderServiceImpl();
        boolean b = providerService.addProvider(provider);
        if (b){
            resp.sendRedirect(req.getContextPath() + "/jsp/provider.do?method=query");
        }else {
            req.getRequestDispatcher("provideradd.jsp").forward(req, resp);
        }
    }
}
