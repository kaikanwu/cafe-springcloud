package com.kaikanwu.cafe.account.interfaces;


import com.kaikanwu.cafe.account.application.AccountApplicationService;
import com.kaikanwu.cafe.account.domain.validation.NotConflictAccount;
import com.kaikanwu.cafe.infrastructure.domain.account.Account;
import com.kaikanwu.cafe.infrastructure.infrasturcture.response.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/account")
@Validated
public class AccountResource {

    @Resource
    private AccountApplicationService accountService;


    @PostMapping
    public Response createUser(@Valid Account user) {
        accountService.createAccount(user);
        return Response.success();
    }

    @GetMapping("/{username}")
    public Response getUser(@PathVariable String username) {
        return Response.success(accountService.getAccountByUsername(username));
    }

    @PutMapping
    public Response updateUser(@Valid @NotConflictAccount Account account) {
        //todo: 需要更多的想想业务参数如何校验？也就是放在 service 中，还是放在方法参数注解里处理
        // 如果放注解里，逻辑复杂时，可能会没法复用。
        accountService.updateAccount(account);
        return Response.success();
    }

}
