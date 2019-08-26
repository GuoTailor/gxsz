package gyh.gxsz.controller;

import gyh.gxsz.bean.Audit;
import gyh.gxsz.bean.RespBody;
import gyh.gxsz.common.page.PageQuery;
import gyh.gxsz.service.AuditService;
import org.hibernate.validator.constraints.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by gyh on 2019/8/18.
 */
@RestController
@RequestMapping("/audit")
public class AuditController {
    private final AuditService auditService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    /**
     * @api {post} /audit/uploadProtocol 上传协议图片
     * @apiDescription  上传协议图片
     * @apiName uploadProtocol
     * @apiVersion 0.0.1
     * @apiParam {File} file 图片地址
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data": "/gxsz/avatar/0\\1-a1f840f4-6f98-4728-952b-654168f4f5c2.jpg"}
     * @apiGroup audit
     * @apiPermission token
     */
    @PostMapping("/uploadProtocol")
    public RespBody uploadProtocol(@RequestParam MultipartFile file) throws IOException {
        return new RespBody<>(0,auditService.uploadFile(file, "protocol") , "成功");
    }

    /**
     * @api {post} /audit/uploadStudent 上传学生证图片
     * @apiDescription 上传学生证图片
     * @apiName uploadStudent
     * @apiVersion 0.0.1
     * @apiParam {File} file 图片地址
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data": "/gxsz/avatar/0\\1-a1f840f4-6f98-4728-952b-654168f4f5c2.jpg"}
     * @apiGroup audit
     * @apiPermission token
     */
    @PostMapping("/uploadStudent")
    public RespBody uploadStudent(@RequestParam(required = false) MultipartFile file) throws IOException {
        return new RespBody<>(0, auditService.uploadFile(file, "student"), "成功");
    }

    /**
     * @api {post} /audit/uploadDisk1 上传书桌图片1
     * @apiDescription  上传书桌图片1
     * @apiName uploadDisk1
     * @apiVersion 0.0.1
     * @apiParam {File} file 图片地址
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":"/gxsz/avatar/0\\1-a1f840f4-6f98-4728-952b-654168f4f5c2.jpg"}
     * @apiGroup audit
     * @apiPermission token
     */
    @PostMapping("/uploadDisk1")
    public RespBody uploadDisk1(@RequestParam MultipartFile file) throws IOException {
        return new RespBody<>(0, auditService.uploadFile(file, "disk1"), "成功");
    }

    /**
     * @api {post} /audit/uploadDisk2 上传书桌图片2
     * @apiDescription  上传书桌图片2
     * @apiName uploadDisk2
     * @apiVersion 0.0.1
     * @apiParam {File} file 图片地址
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":"/gxsz/avatar/0\\1-a1f840f4-6f98-4728-952b-654168f4f5c2.jpg"}
     * @apiGroup audit
     * @apiPermission token
     */
    @PostMapping("/uploadDisk2")
    public RespBody uploadDisk2(@RequestParam MultipartFile file) throws IOException {
        return new RespBody<>(0, auditService.uploadFile(file, "disk2"), "成功");
    }

    /**
     * @api {put} /audit/submit 提交审核
     * @apiDescription  提交审核
     * @apiName submit
     * @apiVersion 0.0.1
     * @apiParam {String} number 手机号
     * @apiParam {String} way 退款方式 1：支付宝；2：优惠卷
     * @apiParam {String} accountId 账户id 如果是支付宝退款就是支付宝账号，如果是优惠卷就是优惠卷id.
     * @apiParam {File} [protocol] 图片地址
     * @apiParam {File} [student] 图片地址
     * @apiParam {File[]} [disk] 图片数组地址
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiSuccessExample {json} 优惠券已抢完:
     * {"code":1,"msg":"优惠券已抢光","data":null}
     * @apiGroup audit
     * @apiPermission token
     */
    @PutMapping("/submit")
    public RespBody submit(@Valid Audit audit,
                           @RequestParam(required = false) MultipartFile protocol,
                           @RequestParam(required = false) MultipartFile student,
                           @RequestParam(required = false) MultipartFile[] disk) throws IOException {
        return new RespBody(auditService.submit(audit, protocol, student, disk))
                .put(0, "成功")
                .put(1, "优惠券已抢完")
                .put(2, "优惠券不存在");
    }

    /**
     * @api {get} /audit 获取自己提交的审核
     * @apiDescription  获取自己提交的审核
     * @apiName getAudio
     * @apiVersion 0.0.1
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup audit
     * @apiPermission token
     */
    @GetMapping()
    public RespBody getAudio() {
        return new RespBody<>(0, auditService.getAudio(), "成功");
    }

    /**
     * @api {get} /audit/getAll 获取所有提交的审核
     * @apiDescription  获取所有提交的审核
     * @apiName getAllAudit
     * @apiVersion 0.0.1
     * @apiUse PageQuery
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup audit
     * @apiPermission admin
     */
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public RespBody getAllAudit(@Valid PageQuery pageQuery) {
        return new RespBody<>(0, auditService.getAllAudit(pageQuery), "成功");
    }

    /**
     * @api {put} /audit 审核订单
     * @apiDescription  审核订单
     * @apiName audit
     * @apiVersion 0.0.1
     * @apiParam {Integer} id 订单id
     * @apiParam {Integer} state 审核状态 1：未提交；2：审核中；3：审核通过；4：审核未通过
     * @apiParam {String} cause 原因
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":null}
     * @apiGroup audit
     * @apiPermission admin
     */
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RespBody audit(Integer id, String state, String cause) {
        auditService.audit(id, state, cause);
        return new RespBody(0, "成功");
    }

}
