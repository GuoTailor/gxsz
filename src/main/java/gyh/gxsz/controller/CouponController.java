package gyh.gxsz.controller;

import gyh.gxsz.bean.Coupon;
import gyh.gxsz.bean.RespBody;
import gyh.gxsz.common.page.PageQuery;
import gyh.gxsz.service.CouponService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by gyh on 2019/8/22.
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    /**
     * @api {get} /coupon 获取所有优惠券
     * @apiDescription  获取所有优惠券
     * @apiName getAll
     * @apiVersion 0.0.1
     * @apiUse PageQuery
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup coupon
     * @apiPermission token
     */
    @GetMapping()
    public RespBody getAll(@Valid PageQuery pageQuery) {
        return new RespBody<>(0, couponService.getAll(pageQuery), "成功");
    }

    /**
     * @api {get} /coupon/couponList 获取自己拥有的优惠券
     * @apiDescription  获取自己拥有的优惠券
     * @apiName couponList
     * @apiVersion 0.0.1
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":[]}
     * @apiGroup coupon
     * @apiPermission token
     */
    @GetMapping("/couponList")
    public RespBody<List<Coupon>> couponList() {
        return new RespBody<>(0, couponService.couponList(), "成功");
    }

    //TODO 添加优惠券

    /**
     * @api {put} /coupon 修改优惠券
     * @apiDescription  修改优惠券
     * @apiName editCoupon
     * @apiVersion 0.0.1
     * @apiParam {Integer} id
     * @apiParam {String} [name] 优惠卷名称
     * @apiParam {String} [describe] 描述
     * @apiParam {String} [denomination] 面额
     * @apiParam {String} [condition] 条件，需要满足该条件才能减
     * @apiParam {Integer} [count] 剩余数量
     * @apiParam {Integer} [quantity] 总量
     * @apiParam {Date} [startTime] 开始日期
     * @apiParam {Date} [endTime] 结束日期
     * @apiParam {Integer} [merchant_id] 商家id
     * @apiParam {String} [enable] 是否启用：0：禁用，1：启用
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup coupon
     * @apiPermission admin
     */
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RespBody editCoupon(Coupon coupon) {
        return new RespBody<>(0, couponService.updateByPrimaryKeySelective(coupon), "成功");
    }

    /**
     * @api {delete} /coupon 删除优惠券
     * @apiDescription  删除优惠券
     * @apiName deleteCoupon
     * @apiVersion 0.0.1
     * @apiParam {Integer} id
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup coupon
     * @apiPermission admin
     */
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RespBody deleteCoupon(@RequestParam Integer id){
        return new RespBody<>(0, couponService.deleteByPrimaryKey(id), "成功");
    }

}
