package com.kaikanwu.cafe.infrastructure.domain.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kaikanwu.cafe.infrastructure.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Account extends BaseEntity {

    // 用来登陆账号的用户名
    @NotEmpty(message = "用户名不能为空")
    private String username;

    // 关注这个注解：对于信息敏感的字段，不参与序列化，即 get 这个对象时不会返回这个字段。
    // 但是是参与反序列化的，也就是支持写。
    // 类似的字段应该提供单独的接口来修改。即常规的 update 方法不处理这个字段。
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(updatable = false)
    private String password;


    // 对外展示的名字
    @NotEmpty(message = "用户姓名不能为空")
    private String name;

    // 用户头像
    private String avatar;

    @Pattern(regexp = "1\\d{10}", message = "手机号格式错误")
    private String telephone;

    @Email(message = "邮箱格式错误")
    private String email;

    private String location;

}
