package com.tca.projectdemo.controller;

import com.tca.beans.ErrorCode;
import com.tca.cache.CacheHelper;
import com.tca.projectdemo.constant.Constants;
import com.tca.projectdemo.controller.req.AccountLoginReq;
import com.tca.projectdemo.controller.req.UploadPicReq;
import com.tca.projectdemo.controller.resp.AccountLoginResp;
import com.tca.projectdemo.controller.resp.AccountResp;
import com.tca.projectdemo.controller.resp.UploadPicResp;
import com.tca.projectdemo.entity.Account;
import com.tca.projectdemo.entity.AccountExample;
import com.tca.projectdemo.exception.ProjectException;
import com.tca.projectdemo.mapper.AccountDAO;
import com.tca.utils.WebBaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoua
 * @Date 2020/2/27
 */
@RestController
@Slf4j
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private CacheHelper cacheHelper;

    @Value("${path.pic}")
    private String picPath;

    /**
     * 登录
     * @param accountLoginReq
     * @return
     */
    @PostMapping("/login")
    public AccountLoginResp login(@RequestBody AccountLoginReq accountLoginReq) {
        log.info("用户登录: {}", accountLoginReq);
        AccountLoginResp resultBean = new AccountLoginResp();

        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria()
                .andLoginNameEqualTo(accountLoginReq.getLoginName())
                .andPasswordEqualTo(accountLoginReq.getPassword());
        // 登录成功
        if (accountDAO.countByExample(accountExample) > 0) {
            String token = UUID.randomUUID().toString();
            resultBean.setToken(token);
            WebBaseUtils.setReturnBaseMessage(resultBean, ErrorCode.S0000);
            cacheHelper.set(token, accountLoginReq.getLoginName(), Constants.TOKEN_EXPIRE_TIME,
                    TimeUnit.MINUTES);
            return resultBean;
        }
        WebBaseUtils.setReturnBaseMessage(resultBean, ErrorCode.S0000, "用户名或密码错误");
        return resultBean;
    }

    /**
     * 用户查询
     * @return
     */
    @GetMapping
    public AccountResp get() {
        String token = request.getHeader("token");
        String loginName = cacheHelper.get(token);
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andLoginNameEqualTo(loginName);
        List<Account> accountList = accountDAO.selectByExample(accountExample);
        Account account = accountList.get(0);

        AccountResp resultBean = new AccountResp();
        resultBean.setId(account.getId());
        resultBean.setLoginName(account.getLoginName());
        resultBean.setPassword(account.getPassword());
        WebBaseUtils.setReturnBaseMessage(resultBean, ErrorCode.S0000);
        return resultBean;
    }

    /**
     * 上传图片
     * @param uploadPicReq
     * @return
     */
    @PostMapping("/uploadPic")
    public UploadPicResp uploadPic(@RequestBody UploadPicReq uploadPicReq) throws Exception{
        String fileName = UUID.randomUUID().toString();
        String fullPath = System.getProperty("user.dir") + picPath + fileName;
        File file = new File(fullPath);
        if (!file.createNewFile()) {
            log.error("创建文件失败");
            throw new ProjectException("S4002", "创建文件失败");
        }
        // step1 创建文件流对象
        FileOutputStream fileOutputStream = new FileOutputStream(fullPath);
        // step2 获取通道
        FileChannel fileChannel = fileOutputStream.getChannel();
        // step3 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(Constants.BUFFER_SIZE);
        // step4 数据移到缓冲区
        byte[] message = uploadPicReq.getPicBase64().getBytes();
        for (int i = 0; i < message.length; i++) {
            byteBuffer.put(message[i]);
        }
        byteBuffer.flip();
        // step5 输出
        fileChannel.write(byteBuffer);

        UploadPicResp resultBean = new UploadPicResp();
        resultBean.setPicName(fileName);
        WebBaseUtils.setReturnBaseMessage(resultBean, ErrorCode.S0000);
        return resultBean;
    }
}
