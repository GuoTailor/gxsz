package gyh.gxsz.controller;

import gyh.gxsz.bean.Order;
import gyh.gxsz.bean.RespBody;
import gyh.gxsz.bean.User;
import gyh.gxsz.common.SmsSample;
import gyh.gxsz.common.Util;
import gyh.gxsz.config.Constant;
import gyh.gxsz.config.token.TokenMgr;
import gyh.gxsz.service.OrderService;
import gyh.gxsz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
     * @apiParam {List} numbers 用户的手机号
     * @apiParam {List} msg 消息
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

}
