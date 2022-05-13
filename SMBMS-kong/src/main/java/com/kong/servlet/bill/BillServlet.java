package com.kong.servlet.bill;

import com.alibaba.fastjson.JSONArray;
import com.kong.pojo.Bill;
import com.kong.pojo.Provider;
import com.kong.pojo.User;
import com.kong.service.bill.BillService;
import com.kong.service.bill.BillServiceImpl;
import com.kong.service.provider.ProviderServiceImpl;
import com.kong.util.Constants;
import com.kong.util.PageSupport;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ThreePure
 * @date 20/12/13 8:32
 * @description: 订单管理的servlet层
 * @since 1.8
 */
public class BillServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if ("query".equals(method)) {
            this.billQuery(req, resp);
        } else if ("view".equals(method)) {
            this.billView(req, resp, "billview.jsp");
        } else if ("modify".equals(method)) {
            this.billView(req, resp, "billmodify.jsp");
        } else if ("getproviderlist".equals(method)) {
            this.getProviderList(req, resp);
        } else if ("modifysave".equals(method)) {
            this.billModify(req, resp);
        } else if ("delbill".equals(method)) {
            this.billDelete(req, resp);
        } else if ("bcexist".equals(method)) {
            this.billCodeExist(req, resp);
        } else if ("add".equals(method)) {
            this.billAdd(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * @description: 查询订单列表
     * @date 20/12/15 15:48
     * @Param: [req, resp]
     * @Return: void
     */
    private void billQuery(HttpServletRequest req, HttpServletResponse resp) {
        String queryProductName = req.getParameter("queryProductName");
        String queryProviderId = req.getParameter("queryProviderId");
        String queryIsPayment = req.getParameter("queryIsPayment");
        String pageIndex = req.getParameter("pageIndex");
        int queryProviderId2 = 0;
        int queryIsPayment2 = 0;

        //获取用户列表
        BillServiceImpl billService = new BillServiceImpl();
        //第一次走这个请求，一定是第一页，页面大小固定的
        //可用把pageSize写道配置文件，方便后期修改

        int pageSize = 5;
        int currentPageNo = 1;

        if (queryProductName == null) {
            queryProductName = "";
        }
        if (queryProviderId != null && !queryProviderId.equals("")) {
            //给查询赋值 0，1，2，3，
            queryProviderId2 = Integer.parseInt(queryProviderId);
        }
        if (queryIsPayment != null && !queryIsPayment.equals("")) {
            //给查询赋值 0，1，2，3，
            queryIsPayment2 = Integer.parseInt(queryIsPayment);
        }
        if (pageIndex != null) {
            currentPageNo = Integer.parseInt(pageIndex);
        }
        //获取订单数量
        int totalCount = billService.getBillCount(queryProductName, queryProviderId2, queryIsPayment2);

        List<Bill> billList = null;
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

        //获取订单列表展示
        billList = billService.getBillList(queryProductName, queryProviderId2, queryIsPayment2, currentPageNo, pageSize);
        req.setAttribute("billList", billList);

        //获取供应商列表
        ProviderServiceImpl providerService = new ProviderServiceImpl();
        List<Provider> providerList = providerService.getProviderList(null, null, currentPageNo, 0);

        req.setAttribute("providerList", providerList);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPageNo", currentPageNo);
        req.setAttribute("totalPageCount", totalPageCount);
        req.setAttribute("queryProductName", queryProductName);
        req.setAttribute("queryProviderId", queryProviderId);
        req.setAttribute("queryIsPayment", queryIsPayment);

        //返回前端
        try {
            req.getRequestDispatcher("billlist.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 查看某一具体的订单
     * @date 20/12/15 15:50
     * @Param: [req, resp, url]
     * @Return: void
     */
    private void billView(HttpServletRequest req, HttpServletResponse resp, String url) throws ServletException, IOException {
        String billid = req.getParameter("billid");
        if (!StringUtils.isNullOrEmpty(billid)) {
            BillServiceImpl billService = new BillServiceImpl();
            Bill biller = billService.getBillById(billid);
            req.setAttribute("bill", biller);
            req.getRequestDispatcher(url).forward(req, resp);
        }
    }

    /**
     * @description: 获取供应商列表
     * @date 20/12/15 20:10
     * @Param: [req, resp]
     * @Return: java.util.List<com.threepure.pojo.Provider>
     */
    private void getProviderList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Provider> providerList = null;
        ProviderServiceImpl providerService = new ProviderServiceImpl();
        providerList = providerService.getProviderList(null, null, 0, 0);
        //把providerList转换成json对象返回
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(providerList));
        writer.flush();
        writer.close();
    }

    /**
     * @description: 修改订单
     * @date 20/12/15 15:51
     * @Param: [req, resp, url]
     * @Return: void
     */
    private void billModify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String productCode = req.getParameter("billCode");
        String productName = req.getParameter("productName");
        String productDesc = req.getParameter("productDesc");
        String productUnit = req.getParameter("productUnit");
        String productCount = req.getParameter("productCount");
        String totalPrice = req.getParameter("totalPrice");
        String providerId = req.getParameter("providerId");
        String isPayment = req.getParameter("isPayment");

        Bill bill = new Bill();
        bill.setId(Integer.valueOf(id));
        bill.setProductName(productName);
        bill.setProductDesc(productDesc);
        bill.setProductUnit(productUnit);
        bill.setProductCount(new BigDecimal(productCount).setScale(2, BigDecimal.ROUND_DOWN));
        bill.setIsPayment(Integer.parseInt(isPayment));
        bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2, BigDecimal.ROUND_DOWN));
        bill.setProviderId(Integer.parseInt(providerId));

        bill.setModifyBy(((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        bill.setModifyDate(new Date());
        boolean flag = false;
        BillService billService = new BillServiceImpl();
        try {
            flag = billService.modifyBill(bill);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag) {
            resp.sendRedirect(req.getContextPath() + "/jsp/bill.do?method=query");
        } else {
            req.getRequestDispatcher("billmodify.jsp").forward(req, resp);
        }
    }

    /**
     * @description: 删除订单
     * @date 20/12/15 15:52
     * @Param: [req, resp]
     * @Return: void
     */
    private void billDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("billid");
        Integer delId = 0;
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            delId = Integer.parseInt(id);
        } catch (Exception e) {
            delId = 0;
        }
        /*
        如果存在则继续判断是否为未支付订单，如果均没有再执行删除操作
         */
        BillServiceImpl billService = new BillServiceImpl();
        Bill biller = billService.getBillById(id);
        System.out.println("AAA" + biller.getIsPayment());
        if (biller.getIsPayment() == 1) {
            map.put("delResult", "cannot");
        } else {
            boolean b = billService.deleteBillById(delId);
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
     * @description: 判断用户添加订单时输入的billCode是否已被使用
     * @date 20/12/15 15:54
     * @Param: [req, resp]
     * @Return: void
     */
    private void billCodeExist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String billCode = req.getParameter("billCode");

        //万能的Map：结果集
        Map<String, String> map = new HashMap<String, String>();
        //判断输入是否为空
        if (StringUtils.isNullOrEmpty(billCode)) {
            map.put("billCode", "empty");
        } else {
            BillServiceImpl billService = new BillServiceImpl();
            Bill bill = billService.billCodeExist(billCode);
            if (bill != null) {
                map.put("billCode", "exist");
            } else {
                map.put("billCode", "notexist");
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
     * @description: 添加订单
     * @date 20/12/15 15:55
     * @Param: [req, resp]
     * @Return: void
     */
    private void billAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String billCode = req.getParameter("billCode");
        String productName = req.getParameter("productName");
        String productDesc = req.getParameter("productDesc");
        String productUnit = req.getParameter("productUnit");

        String productCount = req.getParameter("productCount");
        String totalPrice = req.getParameter("totalPrice");
        String providerId = req.getParameter("providerId");
        String isPayment = req.getParameter("isPayment");

        //创建一个Bill对象，并且根据表单获取的内容设置其属性值
        Bill bill = new Bill();
        bill.setBillCode(billCode);
        bill.setProductName(productName);
        bill.setProductDesc(productDesc);
        bill.setProductUnit(productUnit);
        bill.setProductCount(new BigDecimal(productCount).setScale(2, BigDecimal.ROUND_DOWN));
        bill.setIsPayment(Integer.parseInt(isPayment));
        bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2, BigDecimal.ROUND_DOWN));
        bill.setProviderId(Integer.parseInt(providerId));
        bill.setCreatedBy(((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        bill.setCreationDate(new Date());
        boolean flag = false;
        BillService billService = new BillServiceImpl();
        try {
            flag = billService.addBill(bill);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag) {
            resp.sendRedirect(req.getContextPath() + "/jsp/bill.do?method=query");
        } else {
            req.getRequestDispatcher("billadd.jsp").forward(req, resp);
        }

    }
}
