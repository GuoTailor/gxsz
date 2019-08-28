package gyh.gxsz.controller;

import gyh.gxsz.bean.Order;
import gyh.gxsz.bean.RespBody;
import gyh.gxsz.bean.User;
import gyh.gxsz.common.SmsSample;
import gyh.gxsz.common.Util;
import gyh.gxsz.common.page.PageQuery;
import gyh.gxsz.config.Constant;
import gyh.gxsz.config.token.CheckPOJO;
import gyh.gxsz.config.token.TokenMgr;
import gyh.gxsz.service.OrderService;
import gyh.gxsz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by gyh on 2019/8/18.
 */
@RestController
@RequestMapping("/common")
public class CommonController {
    private final UserService userService;
    private final OrderService orderService;
    private final Map<String, VerificationCode> codeCache = new WeakHashMap<>();

    public static class VerificationCode {
        public String code;
        public Date date;

        public VerificationCode(String code, Date date) {
            this.code = code;
            this.date = date;
        }
    }

    public static class Message{
        public String msg;
        public List<String> numbers;
    }

    @Autowired
    public CommonController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * @api {get} /common/sendCode 发生验证码
     * @apiDescription  发生验证码
     * @apiName sendCode
     * @apiVersion 0.0.1
     * @apiParam {String} phone 用户的手机号
     * @apiParamExample {json} Request-Example:
     * {"phone":"12459874125"}
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiSuccessExample {json} 用户不存在:
     * {"code":1,"msg":"用户不存在","data":null}
     * @apiGroup common
     * @apiPermission none
     */
    @GetMapping("/sendCode")
    public RespBody sendCode(@RequestParam String phone) {
        User user = userService.selectUserByPhone(phone);
        if (user != null) {
            String code = Util.getRandomInt(4);
            codeCache.put(phone, new VerificationCode(code, new Date(System.currentTimeMillis() + 5 * 60_000)));
            SmsSample.send(phone, code);
            return new RespBody(0, "成功");
        }
        return new RespBody(1, "用户不存在");
    }

    /**
     * @api {post} /common/sendMessage 批量发生短信
     * @apiDescription  批量发生短信
     * @apiName sendMessage
     * @apiVersion 0.0.1
     * @apiParam {String} msg 消息
     * @apiParamExample {json} Request-Example:
     * {"msg": "nmka123","numbers": ["17623819195"]}
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup common
     * @apiPermission admin
     */
    @PostMapping("/sendMessage")
    @PreAuthorize("hasRole('ADMIN')")
    public RespBody sendMessage(@RequestBody Message message) {
        message.numbers.forEach(num -> SmsSample.sendMessage(num, "【共享书桌】" + message.msg));
        return new RespBody(0, "成功");
    }

    /**
     * @api {post} /common/sendMessage/all 批量发生短信给所有用户
     * @apiDescription  批量发生短信给所有用户
     * @apiName sendMessageAll
     * @apiVersion 0.0.1
     * @apiParam {String} message 消息
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup common
     * @apiPermission admin
     */
    @PostMapping("/sendMessage/all")
    @PreAuthorize("hasRole('ADMIN')")
    public RespBody sendMessageAll(@RequestParam String message) {
        userService.getAll().forEach(user -> SmsSample.sendMessage(user.getPhone(), "【共享书桌】" + message));
        return new RespBody(0, "成功");
    }

    /**
     * @api {get} /common/verifyToken 验证token
     * @apiDescription  验证token
     * @apiName verifyToken
     * @apiVersion 0.0.1
     * @apiParam {String} token 消息
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":1564675200000}
     * @apiGroup common
     * @apiPermission admin
     */
    @GetMapping("/verifyToken")
    public RespBody verifyToken(@RequestParam String token, HttpServletRequest request) {
        if (token == null) {
            token = request.getHeader("Authorization");
        }
        CheckPOJO checkPOJO = TokenMgr.validateJWT(token);
        if (checkPOJO.isSuccess()) {
            return new RespBody<>(0, Util.createDate(checkPOJO.getClaims().getExpiration().getTime()), "成功");
        }else {
            return new RespBody<>(1, "token已过期");
        }
    }

    /**
     * @api {get} /common/verifyCode 验证验证码并登录
     * @apiDescription  验证验证码并登录
     * @apiName verify
     * @apiVersion 0.0.1
     * @apiParam {String} phone 用户的手机号
     * @apiParam {String} code 验证码
     * @apiParamExample {json} Request-Example:
     * {"phone":"12459874125","code":"1234"}
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":"token"}
     * @apiSuccessExample {json} 验证码已过期:
     * {"code":1,"msg":"验证码已过期","data":null}
     * @apiSuccessExample {json} 验证码错误:
     * {"code":1,"msg":"验证码错误","data":null}
     * @apiGroup common
     * @apiPermission none
     */
    @GetMapping("/verifyCode")
    public RespBody verify(@RequestParam String phone, @RequestParam String code, HttpServletResponse response) {
        VerificationCode vc = codeCache.get(phone);
        if (vc == null || vc.date.before(new Date())) {
            codeCache.remove(phone);
            return new RespBody(1, "验证码已过期");
        } else if (vc.code.equals(code)) {
            User user = userService.selectUserByPhone(phone);
            String token = TokenMgr.createJWT(user, Constant.JWT_TTL);
            user.setToken(token);
            response.addHeader("Authorization", "Bearer " + token);
            codeCache.remove(phone);
            return new RespBody<>(0, user, "成功");
        } else {
            return new RespBody(1, "验证码错误");
        }
    }

    /**
     * @api {put} /common/orders 下单
     * @apiDescription  下单
     * @apiName orders
     * @apiVersion 0.0.1
     * @apiUse Order
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup common
     * @apiPermission none
     */
    @PutMapping("/orders")
    public RespBody orders(Order order) {
        return new RespBody<>(0, orderService.insertOrUpdateSelective(order), "成功");
    }

    /**
     * @api {put} /common/orders/distribution 配送订单
     * @apiDescription  配送订单
     * @apiName orderDistribution
     * @apiVersion 0.0.1
     * @apiUse Order
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup common
     * @apiPermission none
     */
    @PutMapping("/orders/distribution")
    public RespBody orderDistribution(@RequestParam Integer orderId, @RequestParam String deliverymanId) {
        Order order = new Order();
        order.setId(orderId);
        order.setDeliverymanId(deliverymanId);
        return new RespBody<>(0, orderService.updateByPrimaryKeySelective(order), "成功");
    }

    /**
     * @api {get} /orders/al 获取所有订单
     * @apiDescription  获取所有订单
     * @apiName getAllOrders
     * @apiVersion 0.0.1
     * @apiUse PageQuery
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup common
     * @apiPermission none
     */
    @GetMapping("/orders/all")
    public RespBody getAllOrders(@Valid PageQuery pageQuery) {
        return new RespBody<>(0, orderService.getAll(pageQuery), "成功");
    }
}
