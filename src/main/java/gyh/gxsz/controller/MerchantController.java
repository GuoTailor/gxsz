package gyh.gxsz.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gyh.gxsz.bean.BaseUser;
import gyh.gxsz.bean.Merchant;
import gyh.gxsz.bean.RespBody;
import gyh.gxsz.bean.User;
import gyh.gxsz.common.UserUtils;
import gyh.gxsz.common.page.PageQuery;
import gyh.gxsz.config.Constant;
import gyh.gxsz.config.token.TokenMgr;
import gyh.gxsz.service.MerchantCouponService;
import gyh.gxsz.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

/**
 * Created by gyh on 2019/8/26.
 */
@RestController
@RequestMapping("/merchant")
public class MerchantController {
    @Autowired
    MerchantService merchantService;
    @Autowired
    private MerchantCouponService merchantCouponService;

    /**
     * @api {get} /merchant 获取所有商家
     * @apiDescription  获取所有商家
     * @apiName getAllMerchant
     * @apiVersion 0.0.1
     * @apiUse PageQuery
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup merchant
     * @apiPermission admin
     */
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public RespBody getAllMerchant(@Valid PageQuery pageQuery) {
        return new RespBody<>(0, merchantService.getAll(pageQuery), "成功");
    }

    /**
     * @api {get} /merchant/{id} 获取商家信息
     * @apiDescription  获取商家信息
     * @apiName getMerchant
     * @apiVersion 0.0.1
     * @apiParam {Integer} id 商家id
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup merchant
     * @apiPermission admin
     */
    @GetMapping("/{id}")
    public RespBody getMerchant(@PathVariable Integer id) {
        return new RespBody<>(0, merchantService.selectByPrimaryKey(id), "成功");
    }

    /**
     * @api {post} /merchant 添加商家
     * @apiDescription  添加商家
     * @apiName addMerchant
     * @apiVersion 0.0.1
     * @apiParam {String} name 商家名称
     * @apiParam {String} site 地址
     * @apiParam {String} contact 联系人
     * @apiParam {String} number 联系号码
     * @apiParam {String} account 账户
     * @apiParam {String} password 密码
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup merchant
     * @apiPermission admin
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RespBody addMerchant(Merchant merchant) {
        merchantService.addMerchant(merchant);
        return new RespBody<>(0, "成功");
    }

    /**
     * @api {put} /merchant 修改商家
     * @apiDescription  修改商家
     * @apiName editMerchant
     * @apiVersion 0.0.1
     * @apiParam {Integer} id
     * @apiParam {String} [name] 商家名称
     * @apiParam {String} [site] 地址
     * @apiParam {String} [contact] 联系人
     * @apiParam {String} [number] 联系号码
     * @apiParam {String} [account] 账户
     * @apiParam {String} [password] 密码
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":1}
     * @apiGroup merchant
     * @apiPermission admin
     */
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RespBody editMerchant(Merchant merchant) {
        return new RespBody<>(0, merchantService.updateByPrimaryKeySelective(merchant), "成功");
    }

    /**
     * @api {delete} /merchant 删除商家
     * @apiDescription  删除商家
     * @apiName deleteMerchant
     * @apiVersion 0.0.1
     * @apiParam {Integer} id 商家id
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup merchant
     * @apiPermission admin
     */
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RespBody deleteMerchant(@RequestParam Integer id){
        return new RespBody<>(0, merchantService.deleteByPrimaryKey(id), "成功");
    }

    /**
     * @api {put} /merchant/scanCode 扫码使用优惠券
     * @apiDescription  扫码使用优惠券
     * @apiName scanCode
     * @apiVersion 0.0.1
     * @apiParam {String} code 优惠券码
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiSuccessExample {json} 没有拥有该优惠券:
     * {"code":1,"msg":"成功","data":null}
     * @apiSuccessExample {json} 优惠券不存在:
     * {"code":2,"msg":"成功","data":null}
     * @apiSuccessExample {json} 优惠券不是本商家的:
     * {"code":3,"msg":"成功","data":null}
     * @apiSuccessExample {json} 用户不存在:
     * {"code":4,"msg":"成功","data":null}
     * @apiGroup merchant
     * @apiPermission mer
     */
    @PutMapping("/scanCode")
    @PreAuthorize("hasRole('MERCHANT')")
    public RespBody scanCode(@RequestParam String code) {
        return new RespBody<>(merchantCouponService.scanCode(code))
                .put(0, "成功")
                .put(1, "没有拥有该优惠券")
                .put(2, "优惠券不存在")
                .put(3, "优惠券不是本商家的")
                .put(4, "用户不存在");
    }

    /**
     * @api {get} /merchant/coupon 获取所有已使用的优惠券
     * @apiDescription  获取所有已使用的优惠券
     * @apiName getAllUseCoupon
     * @apiVersion 0.0.1
     * @apiUse PageQuery
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup coupon
     * @apiPermission token
     */
    @GetMapping("/coupon")
    @PreAuthorize("hasRole('MERCHANT')")
    public RespBody getAllUseCoupon(@Valid PageQuery pageQuery) {
        return new RespBody<>(0, merchantCouponService.getAllUseCoupon(pageQuery), "成功");
    }

    /**
     * @api {get} /merchant/login 商家登陆
     * @apiDescription  商家登陆
     * @apiName loginMerchant
     * @apiVersion 0.0.1
     * @apiParam {String} account 账号
     * @apiParam {String} password 密码
     * @apiParamExample {json} Request-Example:
     * {"phone":"12459874125","code":"1234"}
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":"token"}
     * @apiSuccessExample {json} 用户不存在:
     * {"code":1,"msg":"用户不存在","data":null}
     * @apiGroup merchant
     * @apiPermission none
     */
    @GetMapping("/login")
    public RespBody loginMerchant(@RequestParam String account, @RequestParam String password) {
        Merchant merchant = merchantService.selectByAccount(account);
        if (UserUtils.passwordMatches(password, merchant.getPassword())) {
            User user = new User();
            user.setId(merchant.getId());
            user.setUserName(merchant.getAccount());
            user.setRoles(Collections.singletonList((GrantedAuthority) () -> "ROLE_MERCHANT"));
            String token = TokenMgr.createJWT(user, Constant.JWT_TTL);
            user.setToken(token);
            return new RespBody(0, "成功");
        }
        return new RespBody(1, "用户不存在");
    }

}
